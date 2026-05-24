package com.membership.program.config;

import com.membership.program.entity.*;
import com.membership.program.entity.*;
import com.membership.program.repository.MembershipPlanRepository;
import com.membership.program.repository.MembershipTierRepository;
import com.membership.program.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MembershipPlanRepository planRepository;
    private final MembershipTierRepository tierRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public void run(String... args) {

        if (planRepository.count() == 0) {

            planRepository.save(
                    MembershipPlan.builder()
                            .planType(PlanType.MONTHLY)
                            .price(BigDecimal.valueOf(199))
                            .durationInDays(30)
                            .build()
            );

            planRepository.save(
                    MembershipPlan.builder()
                            .planType(PlanType.QUARTERLY)
                            .price(BigDecimal.valueOf(499))
                            .durationInDays(90)
                            .build()
            );

            planRepository.save(
                    MembershipPlan.builder()
                            .planType(PlanType.YEARLY)
                            .price(BigDecimal.valueOf(1499))
                            .durationInDays(365)
                            .build()
            );
        }

        if (tierRepository.count() == 0) {

            tierRepository.save(
                    MembershipTier.builder()
                            .tierType(TierType.SILVER)
                            .discountPercentage(BigDecimal.valueOf(5))
                            .freeDelivery(false)
                            .prioritySupport(false)
                            .minOrders(0)
                            .minMonthlySpend(BigDecimal.ZERO)
                            .eligibleCohort(CohortType.DEFAULT)
                            .build()
            );

            tierRepository.save(
                    MembershipTier.builder()
                            .tierType(TierType.GOLD)
                            .discountPercentage(BigDecimal.valueOf(10))
                            .freeDelivery(true)
                            .prioritySupport(false)
                            .minOrders(10)
                            .minMonthlySpend(BigDecimal.valueOf(5000))
                            .eligibleCohort(CohortType.PREMIUM)
                            .build()
            );

            tierRepository.save(
                    MembershipTier.builder()
                            .tierType(TierType.PLATINUM)
                            .discountPercentage(BigDecimal.valueOf(15))
                            .freeDelivery(true)
                            .prioritySupport(true)
                            .minOrders(25)
                            .minMonthlySpend(BigDecimal.valueOf(20000))
                            .eligibleCohort(CohortType.VIP)
                            .build()
            );
            userProfileRepository.save(
                    UserProfile.builder()
                            .fullName("Harish")
                            .email("harish@gmail.com")
                            .phoneNumber("9999999999")
                            .city("Delhi")
                            .cohortType(CohortType.PREMIUM)
                            .active(true)
                            .build()
            );
        }
    }
}