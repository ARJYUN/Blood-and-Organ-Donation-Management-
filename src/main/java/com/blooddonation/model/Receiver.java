package com.blooddonation.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Receiver model class representing blood and organ receivers
 */
public class Receiver {
    private int id;
    private int userId;
    private BloodGroup requiredBloodGroup;
    private OrganType requiredOrgan;
    private UrgencyLevel urgencyLevel;
    private RequestStatus status;
    private String medicalCondition;
    private int hospitalId;
    private LocalDate requestDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Enum for blood groups (reusing from Donor)
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
    
    // Enum for organ types (reusing from Donor)
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
    
    // Enum for urgency levels
    public enum UrgencyLevel {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        CRITICAL("Critical");
        
        private final String displayName;
        
        UrgencyLevel(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Enum for request status
    public enum RequestStatus {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected"),
        FULFILLED("Fulfilled");
        
        private final String displayName;
        
        RequestStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public Receiver() {}
    
    public Receiver(int userId, BloodGroup requiredBloodGroup, OrganType requiredOrgan, 
                   UrgencyLevel urgencyLevel, String medicalCondition, int hospitalId) {
        this.userId = userId;
        this.requiredBloodGroup = requiredBloodGroup;
        this.requiredOrgan = requiredOrgan;
        this.urgencyLevel = urgencyLevel;
        this.medicalCondition = medicalCondition;
        this.hospitalId = hospitalId;
        this.status = RequestStatus.PENDING;
        this.requestDate = LocalDate.now();
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
    
    public BloodGroup getRequiredBloodGroup() {
        return requiredBloodGroup;
    }
    
    public void setRequiredBloodGroup(BloodGroup requiredBloodGroup) {
        this.requiredBloodGroup = requiredBloodGroup;
    }
    
    public OrganType getRequiredOrgan() {
        return requiredOrgan;
    }
    
    public void setRequiredOrgan(OrganType requiredOrgan) {
        this.requiredOrgan = requiredOrgan;
    }
    
    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }
    
    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }
    
    public RequestStatus getStatus() {
        return status;
    }
    
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    
    public String getMedicalCondition() {
        return medicalCondition;
    }
    
    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }
    
    public int getHospitalId() {
        return hospitalId;
    }
    
    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    public LocalDate getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
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
        return "Receiver{" +
                "id=" + id +
                ", userId=" + userId +
                ", requiredBloodGroup=" + requiredBloodGroup +
                ", requiredOrgan=" + requiredOrgan +
                ", urgencyLevel=" + urgencyLevel +
                ", status=" + status +
                ", medicalCondition='" + medicalCondition + '\'' +
                ", hospitalId=" + hospitalId +
                ", requestDate=" + requestDate +
                '}';
    }
}
