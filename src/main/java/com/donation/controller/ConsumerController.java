package com.donation.controller;

import com.donation.dto.DonationResponseDTO;
import com.donation.dto.MyRequestDTO;
import com.donation.entity.Consumer;
import com.donation.entity.ConsumerFeedback;
import com.donation.entity.ConsumerRequest;
import com.donation.service.ConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    // ─── Signup ───────────────────────────────────────────
    @PostMapping("/signup")
    public Map<String, Object> register(@RequestBody Consumer consumer) {
        Consumer saved = consumerService.register(consumer);
        Map<String, Object> response = new HashMap<>();
        response.put("token", "dummy-token");
        response.put("role", "CONSUMER");
        response.put("userId", saved.getId());
        response.put("name", saved.getName());
        response.put("email", saved.getEmail());
        return response;
    }

    // ─── Profile ──────────────────────────────────────────
    @GetMapping("/profile")
    public Consumer getProfile(@RequestHeader("X-User-Id") Long consumerId) {
        return consumerService.getProfile(consumerId);
    }

    // ─── Available Donations ──────────────────────────────
    @GetMapping("/donations")
    public List<DonationResponseDTO> getAvailableDonations() {
        return consumerService.getAvailableDonations();
    }

    // ─── Request a Donation ───────────────────────────────
    @PostMapping("/request/{itemId}")
    public ConsumerRequest requestDonation(@RequestHeader("X-User-Id") Long consumerId,
                                           @PathVariable Long itemId) {
        return consumerService.requestDonation(consumerId, itemId);
    }

    // ─── My Requests ──────────────────────────────────────
    // NOTE: frontend calls /consumer/requests (not /consumer/myrequests)
    @GetMapping("/requests")
    public List<MyRequestDTO> getMyRequests(@RequestHeader("X-User-Id") Long consumerId) {
        return consumerService.getMyRequests(consumerId);
    }

    // ─── Feedback ─────────────────────────────────────────
    @PostMapping("/feedback")
    public ConsumerFeedback submitFeedback(@RequestHeader("X-User-Id") Long consumerId,
                                           @RequestBody ConsumerFeedback feedback) {
        return consumerService.submitFeedback(consumerId, feedback);
    }
}