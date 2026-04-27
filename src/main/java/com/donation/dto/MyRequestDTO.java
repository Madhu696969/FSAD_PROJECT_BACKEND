package com.donation.dto;

import java.time.LocalDateTime;

public class MyRequestDTO {

    private Long id;
    private String itemName;        // ← req.itemName
    private String donationType;    // ← req.donationType
    private String condition;       // ← req.condition
    private String donorName;       // ← req.donorName
    private String status;          // ← req.status
    private LocalDateTime requestedAt; // ← req.requestedAt

    public MyRequestDTO() {}

    public MyRequestDTO(Long id, String itemName, String donationType,
                        String condition, String donorName,
                        String status, LocalDateTime requestedAt) {
        this.id = id;
        this.itemName = itemName;
        this.donationType = donationType;
        this.condition = condition;
        this.donorName = donorName;
        this.status = status;
        this.requestedAt = requestedAt;
    }

    public Long getId() { return id; }
    public String getItemName() { return itemName; }
    public String getDonationType() { return donationType; }
    public String getCondition() { return condition; }
    public String getDonorName() { return donorName; }
    public String getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
}