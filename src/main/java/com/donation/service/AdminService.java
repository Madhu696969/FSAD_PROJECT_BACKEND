package com.donation.service;

import com.donation.dto.AdminRequestDTO;
import com.donation.dto.AdminStatsDTO;
import com.donation.dto.DonationResponseDTO;
import com.donation.dto.FeedbackDTO;
import com.donation.entity.ConsumerRequest;
import com.donation.entity.DonateItem;
import com.donation.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final DonorRepository donorRepository;
    private final ConsumerRepository consumerRepository;
    private final DonateItemRepository donateItemRepository;
    private final ConsumerRequestRepository consumerRequestRepository;
    private final DonorFeedbackRepository donorFeedbackRepository;
    private final ConsumerFeedbackRepository consumerFeedbackRepository;

    public AdminService(DonorRepository donorRepository,
                        ConsumerRepository consumerRepository,
                        DonateItemRepository donateItemRepository,
                        ConsumerRequestRepository consumerRequestRepository,
                        DonorFeedbackRepository donorFeedbackRepository,
                        ConsumerFeedbackRepository consumerFeedbackRepository) {
        this.donorRepository = donorRepository;
        this.consumerRepository = consumerRepository;
        this.donateItemRepository = donateItemRepository;
        this.consumerRequestRepository = consumerRequestRepository;
        this.donorFeedbackRepository = donorFeedbackRepository;
        this.consumerFeedbackRepository = consumerFeedbackRepository;
    }

    // ─── Stats ────────────────────────────────────────────
    public AdminStatsDTO getStats() {
        long donors = donorRepository.count();
        long consumers = consumerRepository.count();
        long donations = donateItemRepository.count();
        long requests = consumerRequestRepository.count();
        long pending = consumerRequestRepository.countByStatus("PENDING");
        long feedbacks = donorFeedbackRepository.count() + consumerFeedbackRepository.count();
        return new AdminStatsDTO(donors, consumers, donations, requests, pending, feedbacks);
    }

    // ─── All Requests ─────────────────────────────────────
    public List<AdminRequestDTO> getAllRequests() {
        return consumerRequestRepository.findAll()
                .stream()
                .map(req -> new AdminRequestDTO(
                        req.getId(),
                        req.getDonateItem().getItemName(),
                        req.getDonateItem().getDonationType(),
                        req.getConsumer().getName(),
                        req.getDonateItem().getDonor() != null
                                ? req.getDonateItem().getDonor().getName() : "Unknown",
                        req.getStatus(),
                        req.getRequestedAt()
                ))
                .collect(Collectors.toList());
    }

    // ─── Approve Request ──────────────────────────────────
    public void approveRequest(Long requestId) {
        ConsumerRequest req = consumerRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        req.setStatus("APPROVED");
        // Mark the item as GIVEN
        DonateItem item = req.getDonateItem();
        item.setStatus("GIVEN");
        donateItemRepository.save(item);
        consumerRequestRepository.save(req);
    }

    // ─── Reject Request ───────────────────────────────────
    public void rejectRequest(Long requestId) {
        ConsumerRequest req = consumerRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        req.setStatus("REJECTED");
        // Put item back to AVAILABLE
        DonateItem item = req.getDonateItem();
        item.setStatus("AVAILABLE");
        donateItemRepository.save(item);
        consumerRequestRepository.save(req);
    }

    // ─── All Donations ────────────────────────────────────
    public List<DonationResponseDTO> getAllDonations() {
        return donateItemRepository.findAll()
                .stream()
                .map(item -> new DonationResponseDTO(
                        item.getId(),
                        item.getDonationType(),
                        item.getItemName(),
                        item.getQuantity(),
                        item.getCondition(),
                        item.getStatus(),
                        item.getImageBase64(),
                        item.getDonor() != null ? item.getDonor().getName() : "Unknown"
                ))
                .collect(Collectors.toList());
    }

    // ─── Delete Donation ──────────────────────────────────
    public void deleteDonation(Long itemId) {
        donateItemRepository.deleteById(itemId);
    }

    // ─── All Feedbacks ────────────────────────────────────
    public List<FeedbackDTO> getAllFeedbacks() {
        List<FeedbackDTO> all = new ArrayList<>();

        donorFeedbackRepository.findAll().forEach(fb ->
                all.add(new FeedbackDTO(
                        fb.getId(),
                        fb.getDonor().getName(),
                        fb.getDonor().getEmail(),
                        fb.getRating(),
                        fb.getMessage(),
                        "DONOR"
                ))
        );

        consumerFeedbackRepository.findAll().forEach(fb ->
                all.add(new FeedbackDTO(
                        fb.getId(),
                        fb.getConsumer().getName(),
                        fb.getConsumer().getEmail(),
                        fb.getRating(),
                        fb.getMessage(),
                        "CONSUMER"
                ))
        );

        return all;
    }
}