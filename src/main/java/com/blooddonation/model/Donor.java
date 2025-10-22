package com.blooddonation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Donor model class representing blood and organ donors
 */
public class Donor {
    private int id;
    private int userId;
    private BloodGroup bloodGroup;
    private OrganType organType;
    private AvailabilityStatus availabilityStatus;
    private LocalDate lastDonationDate;
    private boolean medicalClearance;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Enum for blood groups
    public enum BloodGroup {
        A_POSITIVE("A+"),
        A_NEGATIVE("A-"),
        B_POSITIVE("B+"),
        B_NEGATIVE("B-"),
        AB_POSITIVE("AB+"),
        AB_NEGATIVE("AB-"),
        O_POSITIVE("O+"),
        O_NEGATIVE("O-");
        
        private final String displayName;
        
        BloodGroup(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Enum for organ types
    public enum OrganType {
        BLOOD("Blood"),
        KIDNEY("Kidney"),
        LIVER("Liver"),
        HEART("Heart"),
        LUNG("Lung"),
        PANCREAS("Pancreas"),
        EYE("Eye"),
        BONE_MARROW("Bone Marrow");
        
        private final String displayName;
        
        OrganType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Enum for availability status
    public enum AvailabilityStatus {
        AVAILABLE("Available"),
        UNAVAILABLE("Unavailable"),
        DONATED("Donated");
        
        private final String displayName;
        
        AvailabilityStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public Donor() {}
    
    public Donor(int userId, BloodGroup bloodGroup, OrganType organType, AvailabilityStatus availabilityStatus) {
        this.userId = userId;
        this.bloodGroup = bloodGroup;
        this.organType = organType;
        this.availabilityStatus = availabilityStatus;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public OrganType getOrganType() {
        return organType;
    }
    
    public void setOrganType(OrganType organType) {
        this.organType = organType;
    }
    
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    
    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }
    
    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
    
    public boolean isMedicalClearance() {
        return medicalClearance;
    }
    
    public void setMedicalClearance(boolean medicalClearance) {
        this.medicalClearance = medicalClearance;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", userId=" + userId +
                ", bloodGroup=" + bloodGroup +
                ", organType=" + organType +
                ", availabilityStatus=" + availabilityStatus +
                ", lastDonationDate=" + lastDonationDate +
                ", medicalClearance=" + medicalClearance +
                '}';
    }
}
