package com.donation.dto;
 
import java.time.LocalDateTime;
 
public class AdminRequestDTO {
    private Long id;
    private String itemName;
    private String donationType;
    private String consumerName;
    private String donorName;
    private String status;
    private LocalDateTime requestedAt;
 
    public AdminRequestDTO() {}
 
    public AdminRequestDTO(Long id, String itemName, String donationType,
                           String consumerName, String donorName,
                           String status, LocalDateTime requestedAt) {
        this.id = id;
        this.itemName = itemName;
        this.donationType = donationType;
        this.consumerName = consumerName;
        this.donorName = donorName;
        this.status = status;
        this.requestedAt = requestedAt;
    }
 
    public Long getId() { return id; }
    public String getItemName() { return itemName; }
    public String getDonationType() { return donationType; }
    public String getConsumerName() { return consumerName; }
    public String getDonorName() { return donorName; }
    public String getStatus() { return status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
}