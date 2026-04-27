package com.donation.service;

import com.donation.dto.DonationResponseDTO;
import com.donation.dto.MyRequestDTO;
import com.donation.entity.*;
import com.donation.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;
    private final DonateItemRepository donateItemRepository;
    private final ConsumerRequestRepository consumerRequestRepository;
    private final ConsumerFeedbackRepository consumerFeedbackRepository;

    public ConsumerService(ConsumerRepository consumerRepository,
                           DonateItemRepository donateItemRepository,
                           ConsumerRequestRepository consumerRequestRepository,
                           ConsumerFeedbackRepository consumerFeedbackRepository) {
        this.consumerRepository = consumerRepository;
        this.donateItemRepository = donateItemRepository;
        this.consumerRequestRepository = consumerRequestRepository;
        this.consumerFeedbackRepository = consumerFeedbackRepository;
    }

    // ─── Signup ───────────────────────────────────────────
    public Consumer register(Consumer consumer) {
        if (consumerRepository.existsByEmail(consumer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return consumerRepository.save(consumer);
    }

    // ─── Profile ──────────────────────────────────────────
    public Consumer getProfile(Long consumerId) {
        return consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));
    }

    // ─── Available Donations ──────────────────────────────
    public List<DonationResponseDTO> getAvailableDonations() {
        return donateItemRepository.findByStatus("AVAILABLE")
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

    // ─── Request a Donation ───────────────────────────────
    public ConsumerRequest requestDonation(Long consumerId, Long itemId) {
        if (consumerRequestRepository.existsByConsumerIdAndDonateItemId(consumerId, itemId)) {
            throw new RuntimeException("You have already requested this item");
        }

        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));

        DonateItem item = donateItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("This item is no longer available");
        }

        item.setStatus("REQUESTED");
        donateItemRepository.save(item);

        ConsumerRequest request = new ConsumerRequest();
        request.setConsumer(consumer);
        request.setDonateItem(item);
        request.setStatus("PENDING");
        request.setRequestedAt(LocalDateTime.now());  // ← set timestamp

        return consumerRequestRepository.save(request);
    }

    // ─── My Requests → flat DTO ───────────────────────────
    public List<MyRequestDTO> getMyRequests(Long consumerId) {
        return consumerRequestRepository.findByConsumerId(consumerId)
                .stream()
                .map(req -> new MyRequestDTO(
                        req.getId(),
                        req.getDonateItem().getItemName(),
                        req.getDonateItem().getDonationType(),
                        req.getDonateItem().getCondition(),
                        req.getDonateItem().getDonor() != null
                                ? req.getDonateItem().getDonor().getName() : "Unknown",
                        req.getStatus(),
                        req.getRequestedAt()
                ))
                .collect(Collectors.toList());
    }

    // ─── Feedback ─────────────────────────────────────────
    public ConsumerFeedback submitFeedback(Long consumerId, ConsumerFeedback feedback) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));
        feedback.setConsumer(consumer);
        return consumerFeedbackRepository.save(feedback);
    }
}