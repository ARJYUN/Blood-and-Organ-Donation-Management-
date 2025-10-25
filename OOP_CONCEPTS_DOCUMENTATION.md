# OOP Concepts Implementation Documentation

## Blood and Organ Donation Management System

This document provides detailed explanation of how Object-Oriented Programming (OOP) concepts are implemented in this project.

---

## 1. Encapsulation

**Definition**: Bundling data (attributes) and methods that operate on that data within a single unit (class), and restricting direct access to some components.

### Implementation Examples:

#### Example 1: Donor Class (`src/models/Donor.java`)

```java
public class Donor extends User {
    // Private attributes - data hiding
    private int donorId;
    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String organ;
    private String contact;
    private String location;
    
    // Public getter methods - controlled access
    public String getName() {
        return name;
    }
    
    // Public setter methods - controlled modification
    public void setName(String name) {
        this.name = name;
    }
    
    // More getters and setters...
}
```

**Benefits Demonstrated:**
- Private fields prevent direct external modification
- Getters/setters provide controlled access
- Data validation can be added in setters
- Internal representation can change without affecting external code

#### Example 2: CharityRequest Class (`src/models/CharityRequest.java`)

```java
public class CharityRequest {
    private int requestId;
    private String title;
    private double goalAmount;
    private double raisedAmount;
    
    // Business logic encapsulated within the class
    public double getProgressPercentage() {
        if (goalAmount == 0) return 0;
        return (raisedAmount / goalAmount) * 100;
    }
    
    public boolean isGoalReached() {
        return raisedAmount >= goalAmount;
    }
}
```

**Benefits:**
- Business logic is encapsulated with the data
- External code doesn't need to know calculation details
- Easy to maintain and modify

---

## 2. Inheritance

**Definition**: A mechanism where a new class (child/subclass) derives properties and behaviors from an existing class (parent/superclass).

### Implementation Examples:

#### Example 1: User Base Class Hierarchy

```
        User (Parent/Base Class)
         |
         |-- Donor (Child Class)
         |
         |-- Receptionist (Child Class)
```

#### User.java (Parent Class)
```java
public class User {
    // Common attributes for all users
    private int userId;
    private String username;
    private String password;
    private String role;
    
    // Common method
    public String getDisplayInfo() {
        return "User: " + username + " (Role: " + role + ")";
    }
}
```

#### Donor.java (Child Class)
```java
public class Donor extends User {
    // Additional attributes specific to donors
    private int donorId;
    private String name;
    private String bloodGroup;
    private String organ;
    
    // Inherits all User methods and attributes
    // Can add donor-specific methods
}
```

#### Receptionist.java (Child Class)
```java
public class Receptionist extends User {
    // Additional attributes specific to receptionists
    private String employeeId;
    private String department;
    
    // Inherits all User methods and attributes
    // Can add receptionist-specific methods
}
```

**Benefits Demonstrated:**
- Code reusability (common user attributes defined once)
- Logical hierarchy (IS-A relationship: Donor IS-A User)
- Easier maintenance (changes to User affect all subclasses)
- Extensibility (easy to add new user types)

---

## 3. Polymorphism

**Definition**: The ability of objects to take multiple forms. Same method name can behave differently in different classes.

### Types Implemented:

#### A. Method Overriding (Runtime Polymorphism)

**Example 1: getDisplayInfo() Method**

```java
// In User.java (Parent)
public String getDisplayInfo() {
    return "User: " + username + " (Role: " + role + ")";
}

// In Donor.java (Child) - Overridden
@Override
public String getDisplayInfo() {
    return "Donor: " + name + " | Blood Group: " + bloodGroup + 
           " | Contact: " + contact + " | Location: " + location;
}

// In Receptionist.java (Child) - Overridden
@Override
public String getDisplayInfo() {
    return "Receptionist: " + getUsername() + " | Department: " + 
           (department != null ? department : "General");
}
```

**Usage:**
```java
User user1 = new Donor();
User user2 = new Receptionist();

// Same method call, different behavior
System.out.println(user1.getDisplayInfo()); // Calls Donor's version
System.out.println(user2.getDisplayInfo()); // Calls Receptionist's version
```

#### B. Interface Implementation (Polymorphism through Abstraction)

**Example 2: PaymentGateway Interface**

```java
// Different implementations of the same interface
PaymentGateway gateway1 = new DemoPaymentGateway();
PaymentGateway gateway2 = new UPIPaymentGateway();

// Same method call, different implementations
boolean result1 = gateway1.processPayment(name, amount, card, cvv);
boolean result2 = gateway2.processPayment(name, amount, upi, pin);
```

#### C. Polymorphic Search Methods

**Example 3: SearchDonorsPanel.java**

```java
// Polymorphic behavior based on search type
private void performSearch() {
    String searchType = (String) searchTypeCombo.getSelectedItem();
    String searchValue = searchField.getText().trim();
    
    List<Donor> results = null;
    
    // Different search methods called based on type
    switch (searchType) {
        case "Blood Group":
            results = donorDAO.searchByBloodGroup(searchValue);
            break;
        case "Organ":
            results = donorDAO.searchByOrgan(searchValue);
            break;
        case "Location":
            results = donorDAO.searchByLocation(searchValue);
            break;
    }
}
```

**Benefits Demonstrated:**
- Flexibility in code behavior
- Same interface, different implementations
- Easy to extend with new behaviors
- Cleaner, more maintainable code

---

## 4. Abstraction

**Definition**: Hiding complex implementation details and showing only essential features. Achieved through abstract classes and interfaces.

### Implementation Examples:

#### Example 1: PaymentGateway Interface (`src/interfaces/PaymentGateway.java`)

```java
public interface PaymentGateway {
    // Abstract methods - no implementation
    boolean processPayment(String donorName, double amount, 
                          String cardNumber, String cvv);
    
    boolean validatePaymentDetails(String cardNumber, String cvv);
    
    String getGatewayName();
    
    String getTransactionId();
}
```

#### Implementation 1: DemoPaymentGateway

```java
public class DemoPaymentGateway implements PaymentGateway {
    private String transactionId;
    
    @Override
    public boolean processPayment(String donorName, double amount, 
                                 String cardNumber, String cvv) {
        // Demo implementation - simulates card payment
        if (!validatePaymentDetails(cardNumber, cvv)) {
            return false;
        }
        
        // Simulate processing
        transactionId = "TXN" + UUID.randomUUID().toString();
        return true; // 95% success rate
    }
    
    @Override
    public boolean validatePaymentDetails(String cardNumber, String cvv) {
        // Card validation logic
        return cardNumber.length() >= 13 && cvv.length() >= 3;
    }
    
    // Other methods...
}
```

#### Implementation 2: UPIPaymentGateway

```java
public class UPIPaymentGateway implements PaymentGateway {
    private String transactionId;
    
    @Override
    public boolean processPayment(String donorName, double amount, 
                                 String upiId, String pin) {
        // UPI implementation - different logic
        if (!validatePaymentDetails(upiId, pin)) {
            return false;
        }
        
        // Simulate UPI processing
        transactionId = "UPI" + UUID.randomUUID().toString();
        return true; // 98% success rate
    }
    
    @Override
    public boolean validatePaymentDetails(String upiId, String pin) {
        // UPI validation logic - different from card
        return upiId.contains("@") && pin.length() >= 4;
    }
    
    // Other methods...
}
```

#### Usage in DonationDialog.java

```java
// Client code doesn't know implementation details
PaymentGateway paymentGateway;

if ("UPI".equals(paymentMethod)) {
    paymentGateway = new UPIPaymentGateway();
} else {
    paymentGateway = new DemoPaymentGateway();
}

// Same interface, different behavior
boolean success = paymentGateway.processPayment(donorName, amount, 
                                               cardNumber, cvv);

if (success) {
    String txnId = paymentGateway.getTransactionId();
    // Process success...
}
```

**Benefits Demonstrated:**
- Hides complex payment processing details
- Easy to add new payment methods (just implement interface)
- Client code remains unchanged when adding new implementations
- Loose coupling between components
- Follows Open/Closed Principle (open for extension, closed for modification)

---

## Additional OOP Principles Demonstrated

### 5. Single Responsibility Principle (SRP)

Each class has a single, well-defined responsibility:

- **User**: Manages user authentication data
- **Donor**: Manages donor-specific information
- **DonorDAO**: Handles database operations for donors
- **ValidationUtils**: Handles input validation
- **SessionManager**: Manages user session

### 6. Separation of Concerns

The project is organized into clear layers:

- **Models** (`models/`): Data representation
- **Database** (`database/`): Data access layer
- **GUI** (`gui/`): Presentation layer
- **Interfaces** (`interfaces/`): Abstraction contracts
- **Utils** (`utils/`): Helper utilities

### 7. Design Patterns Used

#### Singleton Pattern
```java
// SessionManager.java
public class SessionManager {
    private static SessionManager instance;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
}
```

#### DAO Pattern (Data Access Object)
- Separates business logic from data access
- `UserDAO`, `DonorDAO`, `CharityDAO`

---

## Summary Table

| OOP Concept | Location | Description |
|-------------|----------|-------------|
| **Encapsulation** | All model classes | Private fields with public getters/setters |
| **Inheritance** | `Donor`, `Receptionist` extend `User` | Code reuse and hierarchical relationships |
| **Polymorphism** | Method overriding, Interface implementations | Same interface, different behaviors |
| **Abstraction** | `PaymentGateway` interface | Hide implementation details, show only essentials |

---

## How to Verify OOP Concepts

### Test Encapsulation:
1. Try to access private fields directly - compilation error
2. Use getters/setters - works correctly
3. Modify validation in setters - external code unaffected

### Test Inheritance:
1. Create Donor object - has User properties
2. Create Receptionist object - has User properties
3. Both can use User methods

### Test Polymorphism:
1. Call `getDisplayInfo()` on different user types
2. Use different PaymentGateway implementations
3. Search donors using different criteria

### Test Abstraction:
1. Switch between payment gateways - code unchanged
2. Add new payment method - implement interface only
3. Client code doesn't know implementation details

---

## Conclusion

This project successfully demonstrates all four pillars of OOP:

1. ✅ **Encapsulation**: Data hiding and controlled access
2. ✅ **Inheritance**: Code reuse through class hierarchies
3. ✅ **Polymorphism**: Multiple forms of methods and objects
4. ✅ **Abstraction**: Hiding complexity through interfaces

The implementation follows industry best practices and SOLID principles, making the code maintainable, extensible, and easy to understand.
