package com.donation.repository;

import com.donation.entity.ConsumerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConsumerRequestRepository extends JpaRepository<ConsumerRequest, Long> {
    List<ConsumerRequest> findByConsumerId(Long consumerId);
    boolean existsByConsumerIdAndDonateItemId(Long consumerId, Long donateItemId);
    long countByStatus(String status); 
}