package com.donation.controller;

import com.donation.dto.AdminRequestDTO;
import com.donation.dto.AdminStatsDTO;
import com.donation.dto.DonationResponseDTO;
import com.donation.dto.FeedbackDTO;
import com.donation.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ─── Stats ────────────────────────────────────────────
    @GetMapping("/stats")
    public AdminStatsDTO getStats() {
        return adminService.getStats();
    }

    // ─── All Requests ─────────────────────────────────────
    @GetMapping("/requests")
    public List<AdminRequestDTO> getAllRequests() {
        return adminService.getAllRequests();
    }

    // ─── Approve Request ──────────────────────────────────
    @PutMapping("/requests/{id}/approve")
    public Map<String, String> approveRequest(@PathVariable Long id) {
        adminService.approveRequest(id);
        return Map.of("message", "Request approved");
    }

    // ─── Reject Request ───────────────────────────────────
    @PutMapping("/requests/{id}/reject")
    public Map<String, String> rejectRequest(@PathVariable Long id) {
        adminService.rejectRequest(id);
        return Map.of("message", "Request rejected");
    }

    // ─── All Donations ────────────────────────────────────
    @GetMapping("/donations")
    public List<DonationResponseDTO> getAllDonations() {
        return adminService.getAllDonations();
    }

    // ─── Delete Donation ──────────────────────────────────
    @DeleteMapping("/donations/{id}")
    public Map<String, String> deleteDonation(@PathVariable Long id) {
        adminService.deleteDonation(id);
        return Map.of("message", "Donation deleted");
    }

    // ─── All Feedbacks ────────────────────────────────────
    @GetMapping("/feedbacks")
    public List<FeedbackDTO> getAllFeedbacks() {
        return adminService.getAllFeedbacks();
    }
}