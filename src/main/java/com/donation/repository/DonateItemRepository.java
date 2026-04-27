package com.donation.repository;

import com.donation.entity.DonateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonateItemRepository extends JpaRepository<DonateItem, Long> {
    List<DonateItem> findByDonorId(Long donorId);
    List<DonateItem> findByStatus(String status);
}