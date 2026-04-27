package com.donation.dto;
 
public class FeedbackDTO {
    private Long id;
    private String userName;
    private String userEmail;
    private int rating;
    private String message;
    private String role; // "DONOR" or "CONSUMER"
 
    public FeedbackDTO() {}
 
    public FeedbackDTO(Long id, String userName, String userEmail,
                       int rating, String message, String role) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.rating = rating;
        this.message = message;
        this.role = role;
    }
 
    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public int getRating() { return rating; }
    public String getMessage() { return message; }
    public String getRole() { return role; }
}
 