package com.donation.dto;

public class DonationResponseDTO {

    private Long id;
    private String donationType;
    private String itemName;
    private int quantity;
    private String condition;
    private String status;
    // ✅ FIXED: was "imageUrl" — entity stores it as imageBase64, frontend expects imageBase64
    private String imageBase64;
    private String donorName;

    public DonationResponseDTO() {}

    public DonationResponseDTO(Long id, String donationType, String itemName,
                                int quantity, String condition, String status,
                                String imageBase64, String donorName) {
        this.id = id;
        this.donationType = donationType;
        this.itemName = itemName;
        this.quantity = quantity;
        this.condition = condition;
        this.status = status;
        this.imageBase64 = imageBase64;
        this.donorName = donorName;
    }

    public Long getId() { return id; }
    public String getDonationType() { return donationType; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public String getCondition() { return condition; }
    public String getStatus() { return status; }
    // ✅ FIXED: was getImageUrl() → Jackson serialized as "imageUrl" in JSON
    public String getImageBase64() { return imageBase64; }
    public String getDonorName() { return donorName; }
}