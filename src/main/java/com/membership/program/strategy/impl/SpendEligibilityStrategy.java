package com.membership.program.strategy.impl;

import com.membership.program.entity.MembershipTier;
import com.membership.program.entity.OrderSummary;
import com.membership.program.entity.UserProfile;
import com.membership.program.strategy.TierEligibilityStrategy;
import org.springframework.stereotype.Component;

@Component
public class SpendEligibilityStrategy
        implements TierEligibilityStrategy {

    @Override
    public boolean isEligible(
            UserProfile userProfile,
            OrderSummary orderSummary,
            MembershipTier membershipTier) {

        return orderSummary.getMonthlySpend()
                .compareTo(
                        membershipTier.getMinMonthlySpend()
                ) >= 0;
    }
}