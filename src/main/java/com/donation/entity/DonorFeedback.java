package com.donation.entity;

import jakarta.persistence.*;

@Entity
public class DonorFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String message;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    public DonorFeedback() {}

    public Long getId() { return id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Donor getDonor() { return donor; }
    public void setDonor(Donor donor) { this.donor = donor; }  // ← this was missing
}