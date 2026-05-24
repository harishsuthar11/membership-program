
package com.membership.program.scheduler;

import com.membership.program.entity.SubscriptionStatus;
import com.membership.program.entity.UserMembership;
import com.membership.program.repository.UserMembershipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@ConditionalOnExpression("${expire.membership.job:false}")
public class MembershipExpiryScheduler {

    @Autowired
    private UserMembershipRepository userMembershipRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void run() {
        log.info("Checking expired memberships");
        List<UserMembership> memberships = userMembershipRepository.findByExpiryDateBeforeAndStatus(LocalDateTime.now(), SubscriptionStatus.ACTIVE);
        memberships.forEach( m ->
                m.setStatus(SubscriptionStatus.EXPIRED));
        userMembershipRepository.saveAll(memberships);
        log.info("Expired membership updated");
    }
}
