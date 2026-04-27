package com.donation.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumer_request")
public class ConsumerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // "PENDING", "APPROVED", "REJECTED"

    private LocalDateTime requestedAt;  // ← ADD THIS

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @ManyToOne
    @JoinColumn(name = "donate_item_id")
    private DonateItem donateItem;

    public ConsumerRequest() {}

    public Long getId() { return id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestedAt() { return requestedAt; }
    public void setRequestedAt(LocalDateTime requestedAt) { this.requestedAt = requestedAt; }

    public Consumer getConsumer() { return consumer; }
    public void setConsumer(Consumer consumer) { this.consumer = consumer; }

    public DonateItem getDonateItem() { return donateItem; }
    public void setDonateItem(DonateItem donateItem) { this.donateItem = donateItem; }
}