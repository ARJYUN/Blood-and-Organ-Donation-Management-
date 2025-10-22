# Blood Donation Management System - Setup Instructions

## 🚀 Quick Start Guide

### Step 1: Database Setup

1. **Install and Start MySQL Server**
   - Download and install MySQL Server 8.0+ from https://dev.mysql.com/downloads/mysql/
   - Start MySQL service

2. **Create Database and Tables**
   ```sql
   -- Open MySQL command line or MySQL Workbench
   -- Run the following commands:
   
   -- Create database
   CREATE DATABASE blood_donation_db;
   USE blood_donation_db;
   
   -- Run the schema file
   source sql/schema.sql;
   
   -- Insert sample data
   source sql/sample_data.sql;
   ```

3. **Update Database Credentials**
   - Edit `src/main/resources/database.properties`
   - Update username and password to match your MySQL setup:
   ```properties
   db.username=your_mysql_username
   db.password=your_mysql_password
   ```

### Step 2: Run the Application

**Option 1: Using the JAR file (Recommended)**
```bash
java -jar target/blood-organ-donation-management-1.0.0.jar
```

**Option 2: Using the batch file (Windows)**
```bash
run_application.bat
```

**Option 3: Using Maven**
```bash
mvn exec:java -Dexec.mainClass=com.blooddonation.main.Main
```

### Step 3: Login to the Application

**Admin Account:**
- Email: `admin@blooddonation.com`
- Password: `password123`

**Sample User Accounts:**
- **Donor**: `john.smith@email.com` / `password123`
- **Receiver**: `mike.wilson@email.com` / `password123`
- **Charity**: `redcross@charity.org` / `password123`

## 🔧 Troubleshooting

### Database Connection Issues

**Error: "Access denied for user 'root'@'localhost'"**
- Check your MySQL username and password in `database.properties`
- Ensure MySQL server is running
- Try connecting to MySQL manually to verify credentials

**Error: "Unknown database 'blood_donation_db'"**
- Run the SQL schema file to create the database
- Make sure you're using the correct database name

**Error: "MySQL JDBC Driver not found"**
- The application should include all dependencies
- Try running `mvn clean package` to rebuild

### Common MySQL Setup Issues

1. **Default MySQL Installation:**
   - Username: `root`
   - Password: (empty) or the password you set during installation

2. **XAMPP/WAMP Users:**
   - Username: `root`
   - Password: (usually empty)
   - Port: 3306

3. **Docker MySQL:**
   - Username: `root`
   - Password: `password` (or as configured)

## 📋 Database Schema

The application uses the following main tables:
- `users` - User accounts and authentication
- `donors` - Donor information and availability
- `receivers` - Receiver requests and status
- `charities` - Charity organizations and events
- `hospitals` - Hospital information and contact details
- `donation_requests` - Links between donors and receivers
- `charity_participants` - Event participation tracking

## 🎯 Features Overview

### Admin Dashboard
- Complete system overview with statistics
- User management (view all users by role)
- Donor and receiver management
- Hospital management
- Data export (CSV/PDF)

### Donor Features
- Register donation details (blood type, organ type)
- View personal donation history
- Browse available receiver requests
- Medical clearance tracking

### Receiver Features
- Submit donation requests with urgency levels
- Search for available donors
- Track request status
- Hospital integration

### Charity Features
- Create and manage donation events
- Organize charity drives
- Event management and tracking

## 📞 Support

If you encounter issues:
1. Check that MySQL is running
2. Verify database credentials in `database.properties`
3. Ensure the database and tables are created
4. Check the console output for specific error messages

## 🔄 Rebuilding the Application

If you need to rebuild the application:
```bash
mvn clean package
java -jar target/blood-organ-donation-management-1.0.0.jar
```
