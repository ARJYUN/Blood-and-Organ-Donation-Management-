package models;

/**
 * Recipient class representing a blood/organ recipient
 * Demonstrates Inheritance (extends User) and Encapsulation
 */
public class Recipient extends User {
    private int recipientId;
    private String name;
    private int age;
    private String gender;
    private String bloodGroupNeeded;
    private String organNeeded;
    private String contact;
    private String location;
    private String urgencyLevel;
    private String medicalCondition;
    
    // Constructors
    public Recipient() {
        super();
    }
    
    public Recipient(String username, String password) {
        super(username, password, "RECIPIENT");
    }
    
    // Getters and Setters (Encapsulation)
    public int getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getBloodGroupNeeded() {
        return bloodGroupNeeded;
    }
    
    public void setBloodGroupNeeded(String bloodGroupNeeded) {
        this.bloodGroupNeeded = bloodGroupNeeded;
    }
    
    public String getOrganNeeded() {
        return organNeeded;
    }
    
    public void setOrganNeeded(String organNeeded) {
        this.organNeeded = organNeeded;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getUrgencyLevel() {
        return urgencyLevel;
    }
    
    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }
    
    public String getMedicalCondition() {
        return medicalCondition;
    }
    
    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }
    
    /**
     * Polymorphism - Override parent method
     */
    @Override
    public String getDisplayInfo() {
        return "Recipient: " + name + " | Needs: " + bloodGroupNeeded + 
               (organNeeded != null && !organNeeded.isEmpty() ? " | Organ: " + organNeeded : "") +
               " | Urgency: " + urgencyLevel;
    }
    
    @Override
    public String toString() {
        return "Recipient{" +
                "recipientId=" + recipientId +
                ", name='" + name + '\'' +
                ", bloodGroupNeeded='" + bloodGroupNeeded + '\'' +
                ", organNeeded='" + organNeeded + '\'' +
                ", urgencyLevel='" + urgencyLevel + '\'' +
                '}';
    }
}
