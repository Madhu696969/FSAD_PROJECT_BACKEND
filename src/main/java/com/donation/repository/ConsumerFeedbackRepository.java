package com.donation.repository;

import com.donation.entity.ConsumerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerFeedbackRepository extends JpaRepository<ConsumerFeedback, Long> {
}