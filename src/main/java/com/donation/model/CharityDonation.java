package com.donation.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model class representing a charity donation record in the system
 */
public class CharityDonation {
    private int id;
    private int donorId;
    private BigDecimal amount;
    private Date donationDate;
    private String paymentMethod;
    private String transactionId;
    private String purpose;
    private boolean isAnonymous;
    private boolean receiptSent;
    private String notes;
    private Timestamp createdAt;
    
    // Default constructor
    public CharityDonation() {
    }
    
    // Constructor with fields
    public CharityDonation(int id, int donorId, BigDecimal amount, Date donationDate, 
                          String paymentMethod, String transactionId, String purpose, 
                          boolean isAnonymous, boolean receiptSent, String notes, 
                          Timestamp createdAt) {
        this.id = id;
        this.donorId = donorId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.purpose = purpose;
        this.isAnonymous = isAnonymous;
        this.receiptSent = receiptSent;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    
    // Constructor for creating new charity donation (without id and timestamp)
    public CharityDonation(int donorId, BigDecimal amount, Date donationDate, 
                          String paymentMethod, String transactionId, String purpose, 
                          boolean isAnonymous, boolean receiptSent, String notes) {
        this.donorId = donorId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.purpose = purpose;
        this.isAnonymous = isAnonymous;
        this.receiptSent = receiptSent;
        this.notes = notes;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDonorId() {
        return donorId;
    }
    
    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Date getDonationDate() {
        return donationDate;
    }
    
    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public boolean isAnonymous() {
        return isAnonymous;
    }
    
    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
    
    public boolean isReceiptSent() {
        return receiptSent;
    }
    
    public void setReceiptSent(boolean receiptSent) {
        this.receiptSent = receiptSent;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "CharityDonation{" +
                "id=" + id +
                ", donorId=" + donorId +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}