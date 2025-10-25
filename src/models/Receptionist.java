package models;

import java.util.List;

/**
 * Receptionist class demonstrating Inheritance (extends User) and Polymorphism
 */
public class Receptionist extends User {
    private String employeeId;
    private String department;
    
    // Constructors
    public Receptionist() {
        super();
    }
    
    public Receptionist(String username, String password) {
        super(username, password, "RECEPTIONIST");
    }
    
    public Receptionist(int userId, String username, String password, 
                       String employeeId, String department) {
        super(userId, username, password, "RECEPTIONIST");
        this.employeeId = employeeId;
        this.department = department;
    }
    
    // Getter and Setter methods
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Method overriding (Polymorphism)
    @Override
    public String getDisplayInfo() {
        return "Receptionist: " + getUsername() + " | Department: " + 
               (department != null ? department : "General");
    }
    
    // Polymorphic search method - can be overridden for different search strategies
    public List<Donor> searchDonors(List<Donor> donors, String criteria, String value) {
        // This method demonstrates polymorphism - can be overridden
        return donors;
    }
    
    @Override
    public String toString() {
        return "Receptionist{" +
                "userId=" + getUserId() +
                ", username='" + getUsername() + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
