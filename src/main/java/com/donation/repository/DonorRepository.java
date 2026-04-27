package com.donation.repository;

import com.donation.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT d FROM Donor d WHERE d.email = :email")
    Optional<Donor> findByEmail(@Param("email") String email);
}