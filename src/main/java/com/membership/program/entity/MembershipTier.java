
package com.membership.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipTier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TierType tierType;

    private BigDecimal discountPercentage;

    private Boolean freeDelivery;

    private Boolean prioritySupport;

    private Integer minOrders;

    private BigDecimal minMonthlySpend;

    @Enumerated(EnumType.STRING)
    private CohortType eligibleCohort;
}
