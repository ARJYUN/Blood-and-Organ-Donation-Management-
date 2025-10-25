# Testing Guide - Blood and Organ Donation Management System

## Overview
This guide provides comprehensive testing scenarios to verify all features and OOP concepts in the application.

---

## Pre-Testing Setup

### 1. Database Setup
```sql
-- Ensure database is created and populated
USE blood_organ_donation;

-- Verify tables exist
SHOW TABLES;

-- Check default admin user exists
SELECT * FROM user WHERE username = 'admin';
```

### 2. Application Launch
- Run `compile.bat` (Windows) or `./compile.sh` (Linux/Mac)
- Run `run.bat` (Windows) or `./run.sh` (Linux/Mac)
- Verify login screen appears with red and white theme

---

## Test Cases

### Module 1: Authentication & User Management

#### Test Case 1.1: Receptionist Login
**Objective**: Verify receptionist can login with default credentials

**Steps**:
1. Launch application
2. Enter username: `admin`
3. Enter password: `admin123`
4. Click LOGIN button

**Expected Result**:
- ✅ Success message displayed
- ✅ Main dashboard opens
- ✅ Welcome message shows "Welcome, admin (RECEPTIONIST)"
- ✅ Receptionist menu options visible

**OOP Concept Tested**: Encapsulation (User authentication), Inheritance (Receptionist extends User)

---

#### Test Case 1.2: Invalid Login
**Objective**: Verify validation for incorrect credentials

**Steps**:
1. Enter username: `wronguser`
2. Enter password: `wrongpass`
3. Click LOGIN

**Expected Result**:
- ✅ Error message: "Invalid username or password!"
- ✅ User remains on login screen
- ✅ Password field cleared

**OOP Concept Tested**: Encapsulation (Data validation)

---

#### Test Case 1.3: Donor Registration
**Objective**: Verify new donor can register successfully

**Steps**:
1. Click "Register as Donor" button
2. Fill in the form:
   - Username: `donor1`
   - Password: `password123`
   - Confirm Password: `password123`
   - Full Name: `John Doe`
   - Age: `25`
   - Gender: `Male`
   - Blood Group: `O+`
   - Organ: `Kidney`
   - Contact: `9876543210`
   - Location: `Mumbai`
3. Click REGISTER

**Expected Result**:
- ✅ Success message: "Registration successful! You can now login."
- ✅ Registration dialog closes
- ✅ New user created in database
- ✅ Can login with new credentials

**OOP Concept Tested**: Encapsulation (Donor class), Inheritance (Donor extends User)

---

#### Test Case 1.4: Registration Validation
**Objective**: Verify input validation during registration

**Test Scenarios**:

| Field | Invalid Input | Expected Error |
|-------|---------------|----------------|
| Username | (empty) | "Username is required!" |
| Username | `admin` (existing) | "Username already exists!" |
| Password | `12345` (< 6 chars) | "Password must be at least 6 characters!" |
| Confirm Password | `different` | "Passwords do not match!" |
| Name | (empty) | "Name is required!" |
| Age | `17` or `66` | "Age must be between 18 and 65!" |
| Contact | `123` (< 10 digits) | "Please enter a valid 10-digit phone number!" |
| Location | (empty) | "Location is required!" |

**OOP Concept Tested**: Encapsulation (Data validation in setters)

---

### Module 2: Donor Module

#### Test Case 2.1: View Donor Profile
**Objective**: Verify donor can view their profile

**Steps**:
1. Login as donor (username: `donor1`, password: `password123`)
2. Dashboard opens with "My Profile" selected by default
3. Verify all information displayed

**Expected Result**:
- ✅ Donor ID displayed
- ✅ All personal information visible
- ✅ Blood group, organ, contact, location shown correctly
- ✅ Red and white theme applied

**OOP Concept Tested**: Encapsulation (Getter methods), Inheritance

---

#### Test Case 2.2: Update Donor Information
**Objective**: Verify donor can update their details

**Steps**:
1. Login as donor
2. Click "Update Information" in sidebar
3. Modify fields:
   - Contact: `9999888877`
   - Location: `Delhi`
   - Organ: `Liver`
4. Click UPDATE INFORMATION

**Expected Result**:
- ✅ Success message: "Information updated successfully!"
- ✅ Changes saved to database
- ✅ Profile shows updated information

**OOP Concept Tested**: Encapsulation (Setter methods), Data persistence

---

### Module 3: Reception Module

#### Test Case 3.1: View Dashboard Statistics
**Objective**: Verify receptionist dashboard shows correct statistics

**Steps**:
1. Login as receptionist (admin/admin123)
2. Dashboard panel displays automatically

**Expected Result**:
- ✅ Total Donors count displayed
- ✅ Active Charity Requests count shown
- ✅ Total Donations amount visible
- ✅ System Status shows "Active"
- ✅ Statistics cards have red borders

**OOP Concept Tested**: Encapsulation (Data aggregation)

---

#### Test Case 3.2: View All Donors
**Objective**: Verify receptionist can view all registered donors

**Steps**:
1. Login as receptionist
2. Click "View All Donors" in sidebar
3. Observe table

**Expected Result**:
- ✅ Table displays all donors
- ✅ Columns: ID, Name, Age, Gender, Blood Group, Organ, Contact, Location
- ✅ Data sorted by registration date (newest first)
- ✅ Refresh button works
- ✅ Red table header

**OOP Concept Tested**: Encapsulation (Data access through DAO)

---

#### Test Case 3.3: Search Donors by Blood Group
**Objective**: Verify polymorphic search functionality

**Steps**:
1. Login as receptionist
2. Click "Search Donors"
3. Select "Blood Group" from dropdown
4. Enter: `O+`
5. Click SEARCH

**Expected Result**:
- ✅ Only O+ blood group donors displayed
- ✅ Result count message shown
- ✅ Table populated with matching donors
- ✅ CLEAR button resets search

**OOP Concept Tested**: **Polymorphism** (Different search methods based on criteria)

---

#### Test Case 3.4: Search Donors by Organ
**Objective**: Verify polymorphic search with different criteria

**Steps**:
1. Select "Organ" from dropdown
2. Enter: `Kidney`
3. Click SEARCH

**Expected Result**:
- ✅ Only donors with kidney listed displayed
- ✅ Partial matches included (e.g., "Kidney, Liver")

**OOP Concept Tested**: **Polymorphism** (Same search interface, different implementation)

---

#### Test Case 3.5: Search Donors by Location
**Objective**: Verify polymorphic search with location

**Steps**:
1. Select "Location" from dropdown
2. Enter: `Mumbai`
3. Click SEARCH

**Expected Result**:
- ✅ Donors from Mumbai displayed
- ✅ Partial matches work (e.g., "Mumbai, Maharashtra")

**OOP Concept Tested**: **Polymorphism** (Runtime method selection)

---

#### Test Case 3.6: Search with No Results
**Objective**: Verify handling of empty search results

**Steps**:
1. Search for blood group: `XYZ`
2. Click SEARCH

**Expected Result**:
- ✅ Warning message: "No donors found matching your search criteria!"
- ✅ Table remains empty
- ✅ No errors thrown

---

### Module 4: Charity Module

#### Test Case 4.1: Create Charity Request (Receptionist)
**Objective**: Verify receptionist can create charity requests

**Steps**:
1. Login as receptionist
2. Click "Charity Management"
3. Click "Create New Request"
4. Fill form:
   - Title: `Children Vaccination Drive 2024`
   - Description: `Help vaccinate 1000 children in rural areas`
   - Requester Name: `Health Foundation`
   - Type: `Medical`
   - Goal Amount: `50000`
5. Click CREATE

**Expected Result**:
- ✅ Success message displayed
- ✅ Request appears in charity table
- ✅ Status shows "ACTIVE"
- ✅ Raised amount is 0.00
- ✅ Progress shows 0.0%

**OOP Concept Tested**: Encapsulation (CharityRequest class)

---

#### Test Case 4.2: View Charity Requests (Donor)
**Objective**: Verify donors can view active charity requests

**Steps**:
1. Login as donor
2. Click "Charity Requests"
3. Observe displayed requests

**Expected Result**:
- ✅ All active requests displayed as cards
- ✅ Each card shows: title, description, requester, type
- ✅ Goal and raised amounts visible
- ✅ Progress bar shows percentage
- ✅ "DONATE NOW" button present
- ✅ Red theme applied

**OOP Concept Tested**: Encapsulation (Data presentation)

---

#### Test Case 4.3: Make Donation - Credit Card
**Objective**: Verify payment gateway abstraction with card payment

**Steps**:
1. Login as donor
2. Navigate to "Charity Requests"
3. Click "DONATE NOW" on any request
4. Fill donation form:
   - Your Name: `John Doe`
   - Amount: `1000`
   - Payment Method: `Credit/Debit Card`
   - Card Number: `4111111111111111`
   - CVV: `123`
5. Click DONATE

**Expected Result**:
- ✅ "Processing payment..." dialog appears
- ✅ Success message with transaction ID
- ✅ Donation recorded in database
- ✅ Charity raised amount updated
- ✅ Progress bar updated
- ✅ Dialog closes

**OOP Concept Tested**: **Abstraction** (PaymentGateway interface), **Polymorphism** (DemoPaymentGateway implementation)

---

#### Test Case 4.4: Make Donation - UPI
**Objective**: Verify polymorphic payment gateway with UPI

**Steps**:
1. Click "DONATE NOW"
2. Fill form:
   - Your Name: `Jane Smith`
   - Amount: `500`
   - Payment Method: `UPI`
   - UPI ID: `test@paytm`
   - UPI PIN: `1234`
3. Click DONATE

**Expected Result**:
- ✅ Processing dialog appears
- ✅ Success with UPI transaction ID (starts with "UPI")
- ✅ Donation recorded
- ✅ Amount added to charity total

**OOP Concept Tested**: **Abstraction** (Same interface, different implementation), **Polymorphism** (UPIPaymentGateway)

---

#### Test Case 4.5: Payment Validation
**Objective**: Verify payment gateway validation

**Test Scenarios**:

| Field | Invalid Input | Expected Error |
|-------|---------------|----------------|
| Name | (empty) | "Please enter your name!" |
| Amount | `0` or `-100` | "Amount must be greater than 0!" |
| Amount | `abc` | "Please enter a valid amount!" |
| Card Number | `123` | Payment fails (validation) |
| CVV | `12` | Payment fails (validation) |
| UPI ID | `invalid` (no @) | Payment fails (validation) |
| UPI PIN | `12` | Payment fails (validation) |

**OOP Concept Tested**: **Abstraction** (validatePaymentDetails method in interface)

---

#### Test Case 4.6: View Donations for Request
**Objective**: Verify receptionist can view donation history

**Steps**:
1. Login as receptionist
2. Click "Charity Management"
3. Select a charity request from table
4. Click "View Donations for Selected"

**Expected Result**:
- ✅ Dialog opens showing donation history
- ✅ Table displays: Donation ID, Donor Name, Amount, Payment Method, Date
- ✅ All donations for that request shown
- ✅ Dates formatted correctly

**OOP Concept Tested**: Encapsulation (Data retrieval)

---

### Module 5: OOP Concepts Verification

#### Test Case 5.1: Encapsulation Verification
**Objective**: Verify data hiding and controlled access

**Manual Code Check**:
```java
// This should NOT compile (private fields)
Donor donor = new Donor();
donor.name = "Test"; // ERROR: name has private access

// This SHOULD work (public getters/setters)
donor.setName("Test"); // OK
String name = donor.getName(); // OK
```

**Expected Result**:
- ✅ Direct field access fails
- ✅ Getter/setter access works

---

#### Test Case 5.2: Inheritance Verification
**Objective**: Verify child classes inherit parent properties

**Steps**:
1. Create donor object
2. Access User class methods

**Code Example**:
```java
Donor donor = new Donor("testuser", "password");
donor.setUserId(1); // From User class
donor.setName("Test"); // From Donor class
String info = donor.getDisplayInfo(); // Overridden method
```

**Expected Result**:
- ✅ Donor has access to User methods
- ✅ Can set both User and Donor properties
- ✅ Method overriding works

---

#### Test Case 5.3: Polymorphism Verification
**Objective**: Verify runtime polymorphism

**Test in Application**:
1. Login as different user types
2. Observe different behaviors for same method

**Code Example**:
```java
User user1 = new Donor();
User user2 = new Receptionist();

// Same method call, different output
System.out.println(user1.getDisplayInfo()); // Donor format
System.out.println(user2.getDisplayInfo()); // Receptionist format
```

**Expected Result**:
- ✅ Different output for same method
- ✅ Correct version called at runtime

---

#### Test Case 5.4: Abstraction Verification
**Objective**: Verify interface abstraction

**Test in Application**:
1. Make donation with Card
2. Make donation with UPI
3. Observe same interface, different behavior

**Code Example**:
```java
PaymentGateway gateway;

// Can switch implementations
if (method.equals("UPI")) {
    gateway = new UPIPaymentGateway();
} else {
    gateway = new DemoPaymentGateway();
}

// Same method call, different processing
boolean result = gateway.processPayment(...);
```

**Expected Result**:
- ✅ Both implementations work with same interface
- ✅ Client code unchanged
- ✅ Different validation and processing logic

---

## Integration Testing

### Test Case 6.1: Complete Donor Workflow
**Objective**: Test end-to-end donor journey

**Steps**:
1. Register new donor
2. Login with new credentials
3. View profile
4. Update information
5. View charity requests
6. Make a donation
7. Logout

**Expected Result**:
- ✅ All steps complete without errors
- ✅ Data persists across sessions
- ✅ UI remains responsive

---

### Test Case 6.2: Complete Receptionist Workflow
**Objective**: Test end-to-end receptionist journey

**Steps**:
1. Login as receptionist
2. View dashboard statistics
3. View all donors
4. Search for specific blood group
5. Create charity request
6. View donations for request
7. Logout

**Expected Result**:
- ✅ All operations successful
- ✅ Data updates reflected immediately
- ✅ No data inconsistencies

---

## Performance Testing

### Test Case 7.1: Large Dataset Handling
**Objective**: Verify application handles multiple records

**Steps**:
1. Register 50+ donors
2. Create 20+ charity requests
3. Make 100+ donations
4. Search and filter data

**Expected Result**:
- ✅ UI remains responsive
- ✅ Search results accurate
- ✅ No memory issues

---

## Error Handling Testing

### Test Case 8.1: Database Connection Failure
**Objective**: Verify graceful handling of DB errors

**Steps**:
1. Stop MySQL server
2. Try to launch application

**Expected Result**:
- ✅ Error dialog displayed
- ✅ Clear error message
- ✅ Application doesn't crash

---

### Test Case 8.2: Invalid Data Entry
**Objective**: Verify validation catches all invalid inputs

**Test all validation scenarios from Test Cases 1.4 and 4.5**

**Expected Result**:
- ✅ All invalid inputs rejected
- ✅ Clear error messages
- ✅ No data corruption

---

## UI/UX Testing

### Test Case 9.1: Theme Consistency
**Objective**: Verify red and white theme throughout

**Check**:
- ✅ Primary color: #DC143C (Crimson Red)
- ✅ Secondary color: White
- ✅ Headers use red background
- ✅ Buttons use red theme
- ✅ Consistent across all screens

---

### Test Case 9.2: Navigation Testing
**Objective**: Verify smooth navigation

**Steps**:
1. Navigate between all menu items
2. Open and close dialogs
3. Use back/cancel buttons

**Expected Result**:
- ✅ All navigation works
- ✅ No broken links
- ✅ Dialogs close properly

---

## Test Summary Checklist

### Functional Requirements
- ✅ Donor registration and login
- ✅ Donor profile management
- ✅ Receptionist dashboard
- ✅ Donor search and filtering
- ✅ Charity request creation
- ✅ Donation processing
- ✅ Payment gateway simulation

### OOP Concepts
- ✅ Encapsulation demonstrated
- ✅ Inheritance implemented
- ✅ Polymorphism working
- ✅ Abstraction verified

### Non-Functional Requirements
- ✅ Red and white theme
- ✅ Input validation
- ✅ Error handling
- ✅ Data persistence
- ✅ User-friendly interface

---

## Bug Reporting Template

If you find any issues during testing:

```
Bug ID: [Unique ID]
Title: [Brief description]
Module: [Donor/Reception/Charity]
Severity: [Critical/High/Medium/Low]
Steps to Reproduce:
1. 
2. 
3. 
Expected Result: 
Actual Result: 
Screenshots: [If applicable]
```

---

## Conclusion

This testing guide covers:
- ✅ All functional modules
- ✅ All OOP concepts
- ✅ Validation and error handling
- ✅ Integration scenarios
- ✅ UI/UX verification

Complete all test cases to ensure the application meets all requirements and demonstrates proper OOP implementation.
