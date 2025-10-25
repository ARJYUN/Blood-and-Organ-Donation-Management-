package models;

/**
 * Donor class demonstrating Inheritance (extends User) and Encapsulation
 */
public class Donor extends User {
    // Private attributes specific to Donor
    private int donorId;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String organ;
    private String contact;
    private String location;
    
    // Constructors
    public Donor() {
        super();
    }
    
    public Donor(String username, String password) {
        super(username, password, "DONOR");
    }
    
    public Donor(int donorId, int userId, String name, int age, String gender, 
                 String bloodGroup, String organ, String contact, String location) {
        super(userId, null, null, "DONOR");
        this.donorId = donorId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.organ = organ;
        this.contact = contact;
        this.location = location;
    }
    
    // Getter and Setter methods (Encapsulation)
    public int getDonorId() {
        return donorId;
    }
    
    public void setDonorId(int donorId) {
        this.donorId = donorId;
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
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public String getOrgan() {
        return organ;
    }
    
    public void setOrgan(String organ) {
        this.organ = organ;
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
    
    // Method overriding (Polymorphism)
    @Override
    public String getDisplayInfo() {
        return "Donor: " + name + " | Blood Group: " + bloodGroup + 
               " | Contact: " + contact + " | Location: " + location;
    }
    
    @Override
    public String toString() {
        return "Donor{" +
                "donorId=" + donorId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", organ='" + organ + '\'' +
                ", contact='" + contact + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
