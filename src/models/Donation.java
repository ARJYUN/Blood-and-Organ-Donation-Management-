package models;

import java.sql.Timestamp;

/**
 * Donation class demonstrating Encapsulation
 */
public class Donation {
    private int donationId;
    private String donorName;
    private int requestId;
    private double amount;
    private Timestamp donationDate;
    private String paymentMethod;
    
    // Constructors
    public Donation() {
    }
    
    public Donation(String donorName, int requestId, double amount, String paymentMethod) {
        this.donorName = donorName;
        this.requestId = requestId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }
    
    public Donation(int donationId, String donorName, int requestId, double amount, 
                   Timestamp donationDate, String paymentMethod) {
        this.donationId = donationId;
        this.donorName = donorName;
        this.requestId = requestId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.paymentMethod = paymentMethod;
    }
    
    // Getter and Setter methods
    public int getDonationId() {
        return donationId;
    }
    
    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }
    
    public String getDonorName() {
        return donorName;
    }
    
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    
    public int getRequestId() {
        return requestId;
    }
    
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Timestamp getDonationDate() {
        return donationDate;
    }
    
    public void setDonationDate(Timestamp donationDate) {
        this.donationDate = donationDate;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    @Override
    public String toString() {
        return "Donation{" +
                "donationId=" + donationId +
                ", donorName='" + donorName + '\'' +
                ", requestId=" + requestId +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
