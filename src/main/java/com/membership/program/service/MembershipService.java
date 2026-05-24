
package com.membership.program.service;

import com.membership.program.dto.SubscribeRequest;
import com.membership.program.entity.UserMembership;

import java.util.List;

public interface MembershipService {

    UserMembership subscribe(SubscribeRequest request);

    UserMembership upgrade(Long userId, Long tierId);

    UserMembership downgrade(Long userId, Long tierId);

    UserMembership cancel(Long userId);

    UserMembership getMembership(Long userId);

    List<?> getPlans();

    List<?> getTiers();
}
