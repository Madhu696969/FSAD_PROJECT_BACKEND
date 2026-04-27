package com.donation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donate_item")
public class DonateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donation_type")
    private String donationType;

    @Column(name = "item_name")
    private String itemName;

    private int quantity;

    @Column(name = "item_condition")
    private String condition;

    private String status;

    @Column(name = "image_base64", columnDefinition = "LONGTEXT")
    private String imageBase64;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    public DonateItem() {}

    public Long getId() { return id; }

    public String getDonationType() { return donationType; }
    public void setDonationType(String donationType) { this.donationType = donationType; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }

    public Donor getDonor() { return donor; }
    public void setDonor(Donor donor) { this.donor = donor; }
}