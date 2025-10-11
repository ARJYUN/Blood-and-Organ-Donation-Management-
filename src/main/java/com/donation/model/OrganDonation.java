package com.donation.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model class representing an organ donation record in the system
 */
public class OrganDonation {
    private int id;
    private int donorId;
    private String organType;
    private Date registrationDate;
    private String consentDocument;
    private String medicalEvaluationStatus;
    private String notes;
    private Timestamp createdAt;
    
    // Default constructor
    public OrganDonation() {
    }
    
    // Constructor with fields
    public OrganDonation(int id, int donorId, String organType, Date registrationDate, 
                        String consentDocument, String medicalEvaluationStatus, 
                        String notes, Timestamp createdAt) {
        this.id = id;
        this.donorId = donorId;
        this.organType = organType;
        this.registrationDate = registrationDate;
        this.consentDocument = consentDocument;
        this.medicalEvaluationStatus = medicalEvaluationStatus;
        this.notes = notes;
        this.createdAt = createdAt;
    }
    
    // Constructor for creating new organ donation (without id and timestamp)
    public OrganDonation(int donorId, String organType, Date registrationDate, 
                        String consentDocument, String medicalEvaluationStatus, 
                        String notes) {
        this.donorId = donorId;
        this.organType = organType;
        this.registrationDate = registrationDate;
        this.consentDocument = consentDocument;
        this.medicalEvaluationStatus = medicalEvaluationStatus;
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
    
    public String getOrganType() {
        return organType;
    }
    
    public void setOrganType(String organType) {
        this.organType = organType;
    }
    
    public Date getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public String getConsentDocument() {
        return consentDocument;
    }
    
    public void setConsentDocument(String consentDocument) {
        this.consentDocument = consentDocument;
    }
    
    public String getMedicalEvaluationStatus() {
        return medicalEvaluationStatus;
    }
    
    public void setMedicalEvaluationStatus(String medicalEvaluationStatus) {
        this.medicalEvaluationStatus = medicalEvaluationStatus;
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
        return "OrganDonation{" +
                "id=" + id +
                ", donorId=" + donorId +
                ", organType='" + organType + '\'' +
                ", registrationDate=" + registrationDate +
                ", medicalEvaluationStatus='" + medicalEvaluationStatus + '\'' +
                '}';
    }
}