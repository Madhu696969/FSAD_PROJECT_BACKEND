package com.donation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "consumer_feedback")
public class ConsumerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String message;

    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    public ConsumerFeedback() {}

    public Long getId() { return id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Consumer getConsumer() { return consumer; }
    public void setConsumer(Consumer consumer) { this.consumer = consumer; }
}