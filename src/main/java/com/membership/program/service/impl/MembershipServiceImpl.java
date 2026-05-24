
package com.membership.program.service.impl;

import com.membership.program.dto.SubscribeRequest;
import com.membership.program.entity.*;
import com.membership.program.entity.*;
import com.membership.program.exception.NotFoundException;
import com.membership.program.exception.ValidationException;
import com.membership.program.repository.*;
import com.membership.program.repository.*;
import com.membership.program.service.MembershipService;
import com.membership.program.strategy.TierEligibilityStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final UserMembershipRepository membershipRepository;
    private final OrderSummaryRepository orderSummaryRepository;
    private final UserProfileRepository userProfileRepository;
    private final List<TierEligibilityStrategy> eligibilityRules;

    @Override
    @Transactional
    public UserMembership subscribe(SubscribeRequest request) {

        UserProfile userProfile = userProfileRepository
                .findById(request.getUserProfileId())
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        Optional<UserMembership> existingMembership =
                membershipRepository.findByUserIdAndStatus(
                        request.getUserProfileId(),
                        SubscriptionStatus.ACTIVE
                );

        if (existingMembership.isPresent()) {
            throw new ValidationException(
                    "User already has active membership"
            );
        }

        MembershipPlan plan = planRepository.findAll()
                .stream()
                .filter(p -> p.getPlanType().equals(request.getPlanType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        MembershipTier tier = tierRepository.findAll()
                .stream()
                .filter(t -> t.getTierType().equals(request.getTierType()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Tier not found"));

        UserMembership membership = UserMembership.builder()
                .userId(userProfile.getId())
                .userProfile(userProfile)
                .membershipPlan(plan)
                .membershipTier(tier)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .expiryDate(
                        LocalDateTime.now()
                                .plusDays(plan.getDurationInDays())
                )
                .build();

        return membershipRepository.save(membership);

    }

    @Override
    @Transactional
    public UserMembership upgrade(Long userId, Long tierId) {

        // 1. Fetch active membership
        UserMembership membership = getMembership(userId);

        // 2. Fetch requested tier
        MembershipTier tier = tierRepository.findById(tierId)
                .orElseThrow(() ->
                        new NotFoundException("Tier not found"));

        // 3. Prevent same tier upgrade
        if (membership.getMembershipTier()
                .getId()
                .equals(tierId)) {

            throw new ValidationException(
                    "User already belongs to this tier"
            );
        }

        // 4. Prevent downgrade using upgrade API
        if (tier.getTierType().ordinal()
                < membership.getMembershipTier()
                .getTierType()
                .ordinal()) {

            throw new ValidationException(
                    "Requested tier is lower than current tier. " +
                            "Use downgrade API instead."
            );
        }

        // 5. Fetch user profile
        UserProfile userProfile = membership.getUserProfile();

        // 6. Fetch order summary
        OrderSummary orderSummary = orderSummaryRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException(
                                "Order summary not found"));

        // 7. Validate all eligibility rules
        boolean eligible = eligibilityRules.stream()
                .allMatch(rule ->
                        rule.isEligible(
                                userProfile,
                                orderSummary,
                                tier
                        ));

        if (!eligible) {

            throw new ValidationException(
                    "User not eligible for selected tier"
            );
        }

        // 8. Upgrade membership
        membership.setMembershipTier(tier);

        // 9. Save upgraded membership
        return membershipRepository.save(membership);
    }

    @Override
    public UserMembership downgrade(Long userId, Long tierId) {
        return upgrade(userId, tierId);
    }

    @Override
    public UserMembership cancel(Long userId) {

        UserMembership membership = getMembership(userId);

        membership.setStatus(SubscriptionStatus.CANCELLED);

        return membershipRepository.save(membership);
    }

    @Override
    public UserMembership getMembership(Long userId) {

        return membershipRepository.findByUserIdAndStatus(
                userId,
                SubscriptionStatus.ACTIVE
        ).orElseThrow(() -> new NotFoundException("Membership not found"));
    }

    @Override
    public List<?> getPlans() {
        return planRepository.findAll();
    }

    @Override
    public List<?> getTiers() {
        return tierRepository.findAll();
    }
}
