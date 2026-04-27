package com.donation.repository;

import com.donation.entity.DonorFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonorFeedbackRepository extends JpaRepository<DonorFeedback, Long> {
    List<DonorFeedback> findByDonorId(Long donorId);
}