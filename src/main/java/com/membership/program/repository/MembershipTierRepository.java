
package com.membership.program.repository;

import com.membership.program.entity.MembershipTier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipTierRepository extends JpaRepository<MembershipTier, Long> {
}
