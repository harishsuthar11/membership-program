
package com.membership.program.strategy;

import com.membership.program.entity.MembershipTier;
import com.membership.program.entity.OrderSummary;
import com.membership.program.entity.UserProfile;

public interface TierEligibilityStrategy {

    boolean isEligible(UserProfile userProfile,OrderSummary orderSummary,
                       MembershipTier membershipTier);
}
