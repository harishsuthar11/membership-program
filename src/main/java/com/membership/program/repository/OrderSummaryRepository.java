
package com.membership.program.repository;

import com.membership.program.entity.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {

    Optional<OrderSummary> findByUserId(Long userId);
}
