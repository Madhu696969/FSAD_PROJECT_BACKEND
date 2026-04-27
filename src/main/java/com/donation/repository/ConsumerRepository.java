package com.donation.repository;

import com.donation.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Consumer c WHERE c.email = :email")
    Optional<Consumer> findByEmail(@Param("email") String email);
}