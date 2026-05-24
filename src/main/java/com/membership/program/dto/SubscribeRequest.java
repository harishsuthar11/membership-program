
package com.membership.program.dto;

import com.membership.program.entity.PlanType;
import com.membership.program.entity.TierType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscribeRequest {

    @NotNull
    private Long userProfileId;

    @NotNull
    private PlanType planType;

    @NotNull
    private TierType tierType;
}
