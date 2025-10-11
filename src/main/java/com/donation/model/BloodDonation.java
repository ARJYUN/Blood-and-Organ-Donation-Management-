package com.donation.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model class representing a blood donation record in the system
 */
public class BloodDonation {
    private int id;
    private int donorId;
    private Date donationDate;
    private int quantityMl;
    private float hemoglobinLevel;
    private String bloodPressure;
    private int pulseRate;
    private String donationCenter;
    private int staffId;
    private String notes;
    private Timestamp createdAt;
    
    // Default constructor
    public BloodDonation() {
    }
    
    // Constructor with fields
    public BloodDonation(int id, int donorId, Date donationDate, int quantityMl, float hemoglobinLevel,
                        String bloodPressure, int pulseRate, String donationCenter, int staffId,
                        String notes, Timestamp createdAt) {
        this.id = id;
        this.donorId = donorId;
        this.donationDate = donationDate;
        this.quantityMl = quantityMl;
        this.hemoglobinLevel = hemoglobinLevel;
        this.bloodPressure = bloodPressure;
        this.pulseRate = pulseRate;
        this.donationCenter = donationCenter;
        this.staffId = staffId;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    
    // Constructor for creating new blood donation (without id and timestamp)
    public BloodDonation(int donorId, Date donationDate, int quantityMl, float hemoglobinLevel,
                        String bloodPressure, int pulseRate, String donationCenter, int staffId,
                        String notes) {
        this.donorId = donorId;
        this.donationDate = donationDate;
        this.quantityMl = quantityMl;
        this.hemoglobinLevel = hemoglobinLevel;
        this.bloodPressure = bloodPressure;
        this.pulseRate = pulseRate;
        this.donationCenter = donationCenter;
        this.staffId = staffId;
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
    
    public Date getDonationDate() {
        return donationDate;
    }
    
    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }
    
    public int getQuantityMl() {
        return quantityMl;
    }
    
    public void setQuantityMl(int quantityMl) {
        this.quantityMl = quantityMl;
    }
    
    public float getHemoglobinLevel() {
        return hemoglobinLevel;
    }
    
    public void setHemoglobinLevel(float hemoglobinLevel) {
        this.hemoglobinLevel = hemoglobinLevel;
    }
    
    public String getBloodPressure() {
        return bloodPressure;
    }
    
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }
    
    public int getPulseRate() {
        return pulseRate;
    }
    
    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }
    
    public String getDonationCenter() {
        return donationCenter;
    }
    
    public void setDonationCenter(String donationCenter) {
        this.donationCenter = donationCenter;
    }
    
    public int getStaffId() {
        return staffId;
    }
    
    public void setStaffId(int staffId) {
        this.staffId = staffId;
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
        return "BloodDonation{" +
                "id=" + id +
                ", donorId=" + donorId +
                ", donationDate=" + donationDate +
                ", quantityMl=" + quantityMl +
                ", donationCenter='" + donationCenter + '\'' +
                '}';
    }
}