
package com.membership.program.repository;

import com.membership.program.entity.SubscriptionStatus;
import com.membership.program.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

    Optional<UserMembership> findByUserIdAndStatus(Long userId,
                                                   SubscriptionStatus status);

    List<UserMembership> findByExpiryDateBeforeAndStatus(
            LocalDateTime now,
            SubscriptionStatus status
    );
}
