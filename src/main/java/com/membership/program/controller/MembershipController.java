
package com.membership.program.controller;

import com.membership.program.ApiResponse;
import com.membership.program.dto.SubscribeRequest;
import com.membership.program.entity.UserMembership;
import com.membership.program.service.MembershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Membership APIs")
@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/plans")
    public Object getPlans() {
        return membershipService.getPlans();
    }

    @GetMapping("/tiers")
    public Object getTiers() {
        return membershipService.getTiers();
    }

    @Operation(summary = "Subscribe membership")
    @PostMapping("/subscribe")
    public ApiResponse<UserMembership> subscribe(@RequestBody @Valid SubscribeRequest request) {
        return ApiResponse.<UserMembership>builder()
                .success(true)
                .message("Membership created successfully")
                .data(membershipService.subscribe(request))
                .build();
    }

    @Operation(summary = "Upgrade membership")
    @PutMapping("/{userId}/upgrade/{tierId}")
    public UserMembership upgrade(@PathVariable Long userId,
                                  @PathVariable Long tierId) {

        return membershipService.upgrade(userId, tierId);
    }

    @PutMapping("/{userId}/downgrade/{tierId}")
    public UserMembership downgrade(@PathVariable Long userId,
                                    @PathVariable Long tierId) {

        return membershipService.downgrade(userId, tierId);
    }

    @DeleteMapping("/{userId}")
    public UserMembership cancel(@PathVariable Long userId) {
        return membershipService.cancel(userId);
    }

    @GetMapping("/{userId}")
    public UserMembership getMembership(@PathVariable Long userId) {
        return membershipService.getMembership(userId);
    }
}
