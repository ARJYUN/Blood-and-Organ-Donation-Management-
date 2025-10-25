# Quick Reference Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Database Setup (2 minutes)
```sql
-- Open MySQL Command Line or Workbench
mysql -u root -p

-- Run the schema file
source C:/Users/arjun/Downloads/bodm/database_schema.sql

-- Or copy-paste the contents of database_schema.sql
```

### Step 2: Configure Database (1 minute)
Open `src/database/DatabaseConnection.java` and update:
```java
private static final String PASSWORD = "your_mysql_password";
```

### Step 3: Compile & Run (2 minutes)
**Windows:**
```batch
compile.bat
run.bat
```

**Linux/Mac:**
```bash
chmod +x compile.sh run.sh
./compile.sh
./run.sh
```

---

## 🔑 Default Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Receptionist | `admin` | `admin123` |
| Donor | Register first | - |

---

## 📋 Common Tasks

### Register a New Donor
1. Click "Register as Donor" on login screen
2. Fill all fields (age: 18-65, phone: 10 digits)
3. Click REGISTER
4. Login with new credentials

### Search for Donors (Receptionist)
1. Login as admin
2. Click "Search Donors"
3. Select search type (Blood Group/Organ/Location)
4. Enter search value
5. Click SEARCH

### Create Charity Request (Receptionist)
1. Login as admin
2. Click "Charity Management"
3. Click "Create New Request"
4. Fill form (goal amount > 0)
5. Click CREATE

### Make a Donation (Donor)
1. Login as donor
2. Click "Charity Requests"
3. Click "DONATE NOW" on any request
4. Fill donation form
5. Use test card: `4111111111111111`, CVV: `123`
6. Or UPI: `test@paytm`, PIN: `1234`
7. Click DONATE

---

## 🎨 Color Codes (Red & White Theme)

```java
PRIMARY_RED = new Color(220, 20, 60);  // #DC143C
DARK_RED = new Color(139, 0, 0);      // #8B0000
WHITE = Color.WHITE;                   // #FFFFFF
LIGHT_GRAY = new Color(245, 245, 245); // #F5F5F5
```

---

## 🔍 OOP Concepts - Where to Find

| Concept | File Location | Line/Method |
|---------|---------------|-------------|
| **Encapsulation** | `src/models/Donor.java` | Private fields + getters/setters |
| **Inheritance** | `src/models/Donor.java` | `extends User` |
| **Polymorphism** | `src/models/Donor.java` | `@Override getDisplayInfo()` |
| **Abstraction** | `src/interfaces/PaymentGateway.java` | Interface definition |

---

## 🛠️ Troubleshooting

### "Database connection failed"
```bash
# Check MySQL is running
mysql -u root -p

# Verify database exists
SHOW DATABASES;

# Check credentials in DatabaseConnection.java
```

### "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
- Download MySQL Connector/J
- Place JAR in `lib/` folder
- Add to classpath

### "Compilation errors"
```bash
# Ensure JDK is installed
java -version
javac -version

# Check all source files are present
dir src /s /b  # Windows
find src -name "*.java"  # Linux/Mac
```

### "Table doesn't exist"
```sql
-- Run database schema again
USE blood_organ_donation;
source database_schema.sql;
```

---

## 📊 Database Quick Commands

### View All Donors
```sql
SELECT * FROM donor;
```

### View All Charity Requests
```sql
SELECT * FROM charity_request;
```

### View All Donations
```sql
SELECT d.*, cr.title 
FROM donation d 
JOIN charity_request cr ON d.request_id = cr.request_id;
```

### Check Total Donations
```sql
SELECT SUM(amount) as total_donations FROM donation;
```

### Reset Database
```sql
DROP DATABASE blood_organ_donation;
source database_schema.sql;
```

---

## 🎯 Test Data for Quick Testing

### Test Donor Registration
```
Username: testdonor1
Password: password123
Name: John Doe
Age: 25
Gender: Male
Blood Group: O+
Organ: Kidney
Contact: 9876543210
Location: Mumbai
```

### Test Charity Request
```
Title: Emergency Medical Fund
Description: Help patients in need
Requester: City Hospital
Type: Medical
Goal Amount: 50000
```

### Test Donation
```
Donor Name: Jane Smith
Amount: 1000
Payment Method: Credit/Debit Card
Card Number: 4111111111111111
CVV: 123
```

---

## 📁 Important Files

| File | Purpose |
|------|---------|
| `src/MainApplication.java` | Start here - main entry point |
| `src/database/DatabaseConnection.java` | Configure DB credentials |
| `database_schema.sql` | Database setup script |
| `README.md` | Full project documentation |
| `SETUP_GUIDE.md` | Detailed setup steps |

---

## 🔄 Common Workflows

### Donor Workflow
```
Register → Login → View Profile → Update Info → View Charity → Donate → Logout
```

### Receptionist Workflow
```
Login → Dashboard → View Donors → Search → Create Charity → View Donations → Logout
```

---

## 💡 Tips & Tricks

### For Development
- Use IntelliJ IDEA or Eclipse for better debugging
- Enable auto-import for faster coding
- Use database GUI tools like MySQL Workbench

### For Testing
- Test with multiple donors of different blood groups
- Create various charity requests
- Make donations to see progress bars update

### For Demonstration
1. Show login with both roles
2. Demonstrate donor registration
3. Show search functionality (polymorphism)
4. Make a donation (abstraction)
5. View updated statistics

---

## 🎓 Key OOP Examples to Demonstrate

### Encapsulation
```java
// Show in Donor.java
private String bloodGroup;  // Private field
public String getBloodGroup() { return bloodGroup; }  // Public getter
```

### Inheritance
```java
// Show class hierarchy
User (parent) → Donor (child)
User (parent) → Receptionist (child)
```

### Polymorphism
```java
// Show method overriding
User user = new Donor();
user.getDisplayInfo();  // Calls Donor's version
```

### Abstraction
```java
// Show interface usage
PaymentGateway gateway = new DemoPaymentGateway();
gateway.processPayment(...);  // Implementation hidden
```

---

## 📞 Quick Help

### Need to...
- **Add MySQL JDBC Driver**: Download from mysql.com, place in `lib/` folder
- **Change Database Password**: Edit `DatabaseConnection.java`
- **Reset Admin Password**: Update in database or run schema again
- **Add New Blood Group**: Modify combo box in registration form
- **Change Theme Colors**: Update Color constants in GUI classes

---

## ✅ Pre-Submission Checklist

- [ ] Database created and populated
- [ ] DatabaseConnection.java configured
- [ ] Application compiles without errors
- [ ] Can login as receptionist (admin/admin123)
- [ ] Can register new donor
- [ ] Can search donors by blood group
- [ ] Can create charity request
- [ ] Can make donation with mock payment
- [ ] All OOP concepts demonstrated
- [ ] Documentation files present

---

## 🎉 Success Indicators

✅ Application launches without errors
✅ Red and white theme visible throughout
✅ Can perform all CRUD operations
✅ Search functionality works
✅ Donations update charity progress
✅ All validations working
✅ No database errors

---

## 📚 Documentation Files Overview

1. **README.md** - Start here for overview
2. **SETUP_GUIDE.md** - Detailed setup instructions
3. **OOP_CONCEPTS_DOCUMENTATION.md** - OOP explanations
4. **TESTING_GUIDE.md** - Test all features
5. **PROJECT_SUMMARY.md** - Complete project summary
6. **QUICK_REFERENCE.md** - This file

---

## 🚨 Emergency Fixes

### Application won't start
1. Check MySQL is running
2. Verify database exists
3. Check credentials in DatabaseConnection.java
4. Ensure JDBC driver in lib/ folder

### Can't login
1. Verify user exists in database
2. Check password is correct
3. Ensure database connection works

### Donation fails
1. Check amount is positive
2. Verify card/UPI format
3. Check charity request exists

---

**For detailed information, refer to the complete documentation files!**

*This quick reference is designed for rapid setup and common tasks.*
