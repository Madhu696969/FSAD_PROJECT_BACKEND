package com.donation.service;

import com.donation.entity.DonateItem;
import com.donation.entity.Donor;
import com.donation.entity.DonorFeedback;
import com.donation.repository.DonateItemRepository;
import com.donation.repository.DonorFeedbackRepository;
import com.donation.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final DonateItemRepository donateItemRepository;
    private final DonorFeedbackRepository donorFeedbackRepository;

    public DonorService(DonorRepository donorRepository,
                        DonateItemRepository donateItemRepository,
                        DonorFeedbackRepository donorFeedbackRepository) {

        this.donorRepository = donorRepository;
        this.donateItemRepository = donateItemRepository;
        this.donorFeedbackRepository = donorFeedbackRepository;
    }

    // ─── Register ─────────────────────────────
    public Donor register(Donor donor) {

        if (donorRepository.existsByEmail(donor.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        return donorRepository.save(donor);
    }

    // ─── Get Profile (JWT Email Based) ────────
    public Donor getProfileByEmail(String email) {

        return donorRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Donor not found"));
    }

    // ─── Donate Item (JWT Email Based) ────────
    public DonateItem donateItem(String email,
                                 DonateItem item) {

        Donor donor = donorRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Donor not found"));

        item.setDonor(donor);
        item.setStatus("AVAILABLE");

        return donateItemRepository.save(item);
    }

    // ─── Get My Items ─────────────────────────
    public List<DonateItem> getMyItems(String email) {

        Donor donor = donorRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Donor not found"));

        return donateItemRepository
                .findByDonorId(donor.getId());
    }

    // ─── Submit Feedback ──────────────────────
    public DonorFeedback submitFeedback(String email,
                                        DonorFeedback feedback) {

        Donor donor = donorRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Donor not found"));

        feedback.setDonor(donor);

        return donorFeedbackRepository.save(feedback);
    }
}