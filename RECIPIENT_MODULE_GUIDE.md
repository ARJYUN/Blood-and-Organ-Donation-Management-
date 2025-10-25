# Recipient Module - User Guide

## 📋 Overview

The **Recipient Module** has been successfully added to the Blood and Organ Donation Management System. Recipients can now register, login, and search for matching donors based on their blood group and organ requirements.

---

## 🎯 Features Added

### ✅ Recipient Registration
- Complete registration form with validation
- Blood group and organ requirements
- Urgency level selection (NORMAL, URGENT, CRITICAL)
- Medical condition description
- Contact and location information

### ✅ Recipient Dashboard
- View matching donors based on blood group
- Filter donors by organ type (if specified)
- Display donor contact information
- Real-time match count
- View personal profile

### ✅ Donor Search Functionality
- **Automatic matching** based on recipient's requirements
- Shows donors with matching blood group
- Filters by organ type if specified
- Displays donor contact details for direct communication

---

## 🗄️ Database Changes

### New Table: `recipient`

```sql
CREATE TABLE recipient (
    recipient_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    blood_group_needed VARCHAR(5) NOT NULL,
    organ_needed VARCHAR(50),
    contact VARCHAR(15) NOT NULL,
    location VARCHAR(100) NOT NULL,
    urgency_level VARCHAR(20) DEFAULT 'NORMAL',
    medical_condition TEXT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);
```

### To Update Your Database:

**Option 1: Run the updated schema file**
```sql
-- In MySQL Command Line or Workbench
USE blood_organ_donation;
source C:/Users/arjun/Downloads/bodm/database_schema.sql;
```

**Option 2: Run this SQL command directly**
```sql
USE blood_organ_donation;

CREATE TABLE recipient (
    recipient_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    blood_group_needed VARCHAR(5) NOT NULL,
    organ_needed VARCHAR(50),
    contact VARCHAR(15) NOT NULL,
    location VARCHAR(100) NOT NULL,
    urgency_level VARCHAR(20) DEFAULT 'NORMAL',
    medical_condition TEXT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);
```

---

## 💻 New Files Created

### Model Layer
- **`src/models/Recipient.java`** - Recipient model class (extends User)
  - Demonstrates **Inheritance** and **Encapsulation**
  - Overrides `getDisplayInfo()` for **Polymorphism**

### Database Layer
- **`src/database/RecipientDAO.java`** - Recipient data access object
  - CRUD operations for recipients
  - Search and retrieval methods

### GUI Layer
- **`src/gui/RecipientRegistrationFrame.java`** - Registration form
  - Complete validation
  - Red & white theme
  
- **`src/gui/RecipientDashboardPanel.java`** - Recipient dashboard
  - Donor search functionality
  - Match display with contact information

### Updated Files
- **`src/gui/LoginFrame.java`** - Added "Register as Recipient" button
- **`src/gui/MainDashboard.java`** - Added recipient role support
- **`src/utils/ValidationUtils.java`** - Added password and phone validation methods
- **`database_schema.sql`** - Added recipient table

---

## 🚀 How to Use

### 1. Update Database
Run the SQL commands above to add the recipient table to your database.

### 2. Compile & Run
```bash
# Windows
.\compile.bat
.\run.bat

# Linux/Mac
./compile.sh
./run.sh
```

### 3. Register as Recipient
1. Launch the application
2. Click **"Register as Recipient"** on the login screen
3. Fill in all required fields:
   - Username and password
   - Personal information (name, age, gender)
   - **Blood group needed** (required)
   - Organ needed (optional)
   - Contact and location
   - Urgency level
   - Medical condition (optional)
4. Click **REGISTER**

### 4. Login as Recipient
1. Use your registered username and password
2. Click **LOGIN**

### 5. Find Matching Donors
1. After login, you'll see the **"Find Donors"** dashboard
2. The system automatically shows donors matching your:
   - Blood group requirement
   - Organ requirement (if specified)
3. View donor contact information to reach out directly
4. Click **REFRESH** to update the list
5. Click **MY PROFILE** to view your information

---

## 🎨 User Interface

### Login Screen
- **"Register as Donor"** button (existing)
- **"Register as Recipient"** button (NEW - dark red border)

### Recipient Dashboard
- **Header**: Shows recipient name, requirements, urgency, and location
- **Match Count**: Displays number of matching donors found
- **Donor Table**: Lists all matching donors with:
  - Donor ID
  - Name
  - Age
  - Gender
  - Blood Group
  - Organ
  - Contact Number
  - Location
- **Buttons**:
  - REFRESH - Update donor list
  - MY PROFILE - View personal information

### Navigation Menu (Recipient)
- **Find Donors** - Search for matching donors
- **Charity Requests** - View and donate to charity causes

---

## 🧩 OOP Concepts Demonstrated

### 1. Encapsulation ✅
**File**: `src/models/Recipient.java`
```java
private String bloodGroupNeeded;
private String organNeeded;

public String getBloodGroupNeeded() { return bloodGroupNeeded; }
public void setBloodGroupNeeded(String bloodGroupNeeded) { 
    this.bloodGroupNeeded = bloodGroupNeeded; 
}
```

### 2. Inheritance ✅
**File**: `src/models/Recipient.java`
```java
public class Recipient extends User {
    // Recipient inherits from User base class
    // Inherits: userId, username, password, role
}
```

### 3. Polymorphism ✅
**File**: `src/models/Recipient.java`
```java
@Override
public String getDisplayInfo() {
    return "Recipient: " + name + " | Needs: " + bloodGroupNeeded + 
           " | Urgency: " + urgencyLevel;
}
```

### 4. Abstraction ✅
**Already demonstrated** in the existing PaymentGateway interface used by recipients for charity donations.

---

## 📊 Database Operations

### Recipient DAO Methods
```java
// Register new recipient
RecipientDAO.registerRecipient(recipient)

// Get recipient by user ID
RecipientDAO.getRecipientByUserId(userId)

// Get recipient by recipient ID
RecipientDAO.getRecipientById(recipientId)

// Update recipient information
RecipientDAO.updateRecipient(recipient)

// Get all recipients
RecipientDAO.getAllRecipients()
```

---

## ✅ Validation Rules

### Registration Validation
- **Username**: Required, must be unique
- **Password**: Minimum 6 characters
- **Confirm Password**: Must match password
- **Name**: Required
- **Age**: 1-120 years (any age for recipients)
- **Blood Group**: Must select from dropdown
- **Contact**: 10-digit phone number
- **Location**: Required
- **Urgency Level**: NORMAL, URGENT, or CRITICAL

---

## 🔍 Search Logic

The recipient dashboard uses **polymorphic search** to find matching donors:

1. **Primary Search**: Finds all donors with matching blood group
   ```java
   donorDAO.searchByBloodGroup(recipient.getBloodGroupNeeded())
   ```

2. **Secondary Filter** (if organ specified): Filters by organ type
   ```java
   donorDAO.searchByOrgan(recipient.getOrganNeeded())
   ```

3. **Result**: Shows only donors matching BOTH criteria

---

## 📝 Example Usage Scenario

### Scenario: Patient Needs Kidney Transplant

1. **Registration**:
   - Name: John Smith
   - Age: 45
   - Blood Group Needed: O+
   - Organ Needed: Kidney
   - Urgency: URGENT
   - Medical Condition: Chronic kidney disease

2. **Login & Search**:
   - System automatically finds donors with:
     - Blood Group: O+
     - Organ: Kidney
   - Displays 5 matching donors

3. **Contact Donor**:
   - View donor contact numbers
   - Reach out directly for coordination

---

## 🎯 Testing Checklist

### Registration Testing
- [ ] Register with all fields filled
- [ ] Test username uniqueness validation
- [ ] Test password strength validation
- [ ] Test age validation (1-120)
- [ ] Test phone number validation (10 digits)
- [ ] Test with organ specified
- [ ] Test without organ (optional field)

### Login Testing
- [ ] Login with recipient credentials
- [ ] Verify recipient dashboard loads
- [ ] Check profile information displays correctly

### Search Testing
- [ ] Search with blood group only
- [ ] Search with blood group + organ
- [ ] Verify match count is accurate
- [ ] Test REFRESH button
- [ ] Test MY PROFILE button
- [ ] Verify donor contact information visible

### Integration Testing
- [ ] Register multiple recipients
- [ ] Register multiple donors
- [ ] Verify matching algorithm works
- [ ] Test with different urgency levels
- [ ] Test charity donation as recipient

---

## 🔧 Troubleshooting

### "Table 'recipient' doesn't exist"
**Solution**: Run the SQL commands to create the recipient table

### "No matching donors found"
**Solution**: 
- Register donors with matching blood groups
- Check if organ filter is too restrictive
- Verify donors exist in database

### "Cannot register recipient"
**Solution**:
- Check database connection
- Verify all required fields are filled
- Ensure username is unique

---

## 📈 Statistics

### New Code Added
- **4 new files** created
- **4 existing files** updated
- **~1,500+ lines** of new code
- **1 new database table** with 11 columns

### Features Count
- **1 new user role** (Recipient)
- **2 new GUI screens** (Registration + Dashboard)
- **1 new DAO class** (RecipientDAO)
- **1 new model class** (Recipient)

---

## 🎉 Benefits

### For Recipients
✅ Easy registration process
✅ Automatic donor matching
✅ Direct access to donor contacts
✅ Urgency level tracking
✅ Medical condition documentation

### For the System
✅ Complete OOP demonstration
✅ Third user role added
✅ Enhanced database design
✅ Improved search functionality
✅ Better user experience

---

## 🔄 Future Enhancements (Optional)

- Email notifications to recipients when new matching donors register
- SMS alerts for urgent cases
- Recipient-donor messaging system
- Appointment scheduling
- Medical report upload
- Priority queue based on urgency level

---

## 📞 Quick Reference

### Default Credentials
- **Receptionist**: admin / admin123
- **Donor**: Register through app
- **Recipient**: Register through app (NEW)

### Key Shortcuts
- **Register Recipient**: Click button on login screen
- **Find Donors**: Automatic after login
- **Refresh List**: Click REFRESH button
- **View Profile**: Click MY PROFILE button

---

## ✨ Summary

The Recipient Module is now **fully integrated** into the Blood and Organ Donation Management System with:

✅ Complete registration and login
✅ Automatic donor matching
✅ Professional red & white themed UI
✅ Full OOP concept demonstration
✅ Comprehensive validation
✅ Database integration
✅ Search and filter functionality

**The system now supports 3 user roles: Donor, Receptionist, and Recipient!**

---

*For complete system documentation, refer to README.md and other documentation files.*
