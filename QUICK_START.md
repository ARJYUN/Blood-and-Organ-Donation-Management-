# 🚀 Quick Start Guide - Blood Donation Management System

## ⚠️ IMPORTANT: Database Setup Required

The application requires MySQL database to run. Follow these steps:

### Step 1: Install and Start MySQL

1. **Download MySQL Server 8.0+** from https://dev.mysql.com/downloads/mysql/
2. **Install MySQL** with default settings
3. **Start MySQL service**

### Step 2: Create Database

**Option A: Using MySQL Command Line**
```bash
mysql -u root -p
```
Then run:
```sql
CREATE DATABASE blood_donation_db;
USE blood_donation_db;
source sql/schema.sql;
source sql/sample_data.sql;
```

**Option B: Using MySQL Workbench**
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Run the SQL files in order:
   - `sql/schema.sql`
   - `sql/sample_data.sql`

### Step 3: Update Database Credentials

Edit `src/main/resources/database.properties`:
```properties
# Update these to match your MySQL setup
db.username=root
db.password=your_mysql_password_here
```

### Step 4: Run the Application

```bash
java -jar target/blood-organ-donation-management-1.0.0.jar
```

## 🔑 Default Login Credentials

**Admin Account:**
- Email: `admin@blooddonation.com`
- Password: `password123`

**Sample Users:**
- **Donor**: `john.smith@email.com` / `password123`
- **Receiver**: `mike.wilson@email.com` / `password123`
- **Charity**: `redcross@charity.org` / `password123`

## 🛠️ Common Issues & Solutions

### "Access denied for user 'root'@'localhost'"
- **Solution**: Update the password in `database.properties` to match your MySQL root password
- **Default MySQL passwords**: Usually empty or the password you set during installation

### "Unknown database 'blood_donation_db'"
- **Solution**: Run the SQL schema file to create the database
- **Command**: `source sql/schema.sql;` in MySQL

### "MySQL JDBC Driver not found"
- **Solution**: The JAR file includes all dependencies, but if issues persist:
  ```bash
  mvn clean package
  java -jar target/blood-organ-donation-management-1.0.0.jar
  ```

## 📱 Application Features

### 🔐 Authentication
- Secure login with role-based access
- User registration with validation
- Password hashing with BCrypt

### 👨‍💼 Admin Dashboard
- System statistics and overview
- User management
- Donor and receiver management
- Hospital management
- Data export (CSV/PDF)

### 🩸 Donor Features
- Register donation details
- View donation history
- Browse receiver requests
- Medical clearance tracking

### 🏥 Receiver Features
- Submit donation requests
- Search for available donors
- Track request status
- Hospital integration

### 🏛️ Charity Features
- Create donation events
- Manage charity drives
- Event tracking

## 🎯 System Requirements

- **Java 8+** (JDK 8 or higher)
- **MySQL Server 8.0+**
- **Windows/Linux/Mac** (Java Swing)

## 📞 Need Help?

1. **Check MySQL is running**: `mysql -u root -p`
2. **Verify database exists**: `SHOW DATABASES;`
3. **Check credentials**: Update `database.properties`
4. **Rebuild if needed**: `mvn clean package`

The application will show a database connection error if MySQL is not properly configured. Follow the steps above to resolve.
