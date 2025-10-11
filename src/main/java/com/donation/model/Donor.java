package com.donation.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model class representing a donor in the system
 */
public class Donor {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String bloodType;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    private String medicalHistory;
    private boolean isBloodDonor;
    private boolean isOrganDonor;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Donor() {
    }
    
    // Constructor with fields
    public Donor(int id, String firstName, String lastName, Date dateOfBirth, String gender, 
                String bloodType, String address, String city, String state, String zipCode, 
                String phone, String email, String medicalHistory, boolean isBloodDonor, 
                boolean isOrganDonor, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.medicalHistory = medicalHistory;
        this.isBloodDonor = isBloodDonor;
        this.isOrganDonor = isOrganDonor;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Constructor for creating new donor (without id and timestamps)
    public Donor(String firstName, String lastName, Date dateOfBirth, String gender, 
                String bloodType, String address, String city, String state, String zipCode, 
                String phone, String email, String medicalHistory, boolean isBloodDonor, 
                boolean isOrganDonor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.medicalHistory = medicalHistory;
        this.isBloodDonor = isBloodDonor;
        this.isOrganDonor = isOrganDonor;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public boolean isBloodDonor() {
        return isBloodDonor;
    }
    
    public void setBloodDonor(boolean bloodDonor) {
        isBloodDonor = bloodDonor;
    }
    
    public boolean isOrganDonor() {
        return isOrganDonor;
    }
    
    public void setOrganDonor(boolean organDonor) {
        isOrganDonor = organDonor;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", email='" + email + '\'' +
                ", isBloodDonor=" + isBloodDonor +
                ", isOrganDonor=" + isOrganDonor +
                '}';
    }
}