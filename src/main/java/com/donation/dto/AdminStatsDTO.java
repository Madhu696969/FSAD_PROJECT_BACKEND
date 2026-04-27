package com.donation.dto;
 
public class AdminStatsDTO {
    private long donors;
    private long consumers;
    private long donations;
    private long requests;
    private long pending;
    private long feedbacks;
 
    public AdminStatsDTO(long donors, long consumers, long donations,
                         long requests, long pending, long feedbacks) {
        this.donors = donors;
        this.consumers = consumers;
        this.donations = donations;
        this.requests = requests;
        this.pending = pending;
        this.feedbacks = feedbacks;
    }
 
    public long getDonors() { return donors; }
    public long getConsumers() { return consumers; }
    public long getDonations() { return donations; }
    public long getRequests() { return requests; }
    public long getPending() { return pending; }
    public long getFeedbacks() { return feedbacks; }
}