# Blood and Organ Donation Management System - Setup Guide

## Prerequisites

1. **Java Development Kit (JDK)**
   - JDK 8 or higher
   - Download from: https://www.oracle.com/java/technologies/downloads/

2. **MySQL Server**
   - MySQL 5.7 or higher
   - Download from: https://dev.mysql.com/downloads/mysql/

3. **MySQL JDBC Driver**
   - Download from: https://dev.mysql.com/downloads/connector/j/
   - Or use Maven/Gradle dependency

4. **IDE (Optional but Recommended)**
   - IntelliJ IDEA / Eclipse / NetBeans / VS Code

## Step-by-Step Setup

### Step 1: Database Setup

1. **Start MySQL Server**
   ```bash
   # Windows
   net start MySQL80
   
   # Linux/Mac
   sudo systemctl start mysql
   ```

2. **Login to MySQL**
   ```bash
   mysql -u root -p
   ```

3. **Create Database and Tables**
   ```sql
   # Run the database_schema.sql file
   source /path/to/database_schema.sql
   
   # Or copy-paste the SQL commands from database_schema.sql
   ```

4. **Verify Database Creation**
   ```sql
   USE blood_organ_donation;
   SHOW TABLES;
   ```

### Step 2: Configure Database Connection

1. Open `src/database/DatabaseConnection.java`

2. Update the following constants with your MySQL credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/blood_organ_donation";
   private static final String USER = "root";
   private static final String PASSWORD = "your_mysql_password";
   ```

### Step 3: Add MySQL JDBC Driver

#### Option A: Manual Setup
1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
2. Extract the JAR file (e.g., `mysql-connector-java-8.0.33.jar`)
3. Create a `lib` folder in the project root
4. Copy the JAR file to the `lib` folder

#### Option B: Using IDE
- **IntelliJ IDEA**: File → Project Structure → Libraries → Add → Java → Select JAR
- **Eclipse**: Right-click project → Build Path → Add External Archives → Select JAR
- **NetBeans**: Right-click Libraries → Add JAR/Folder → Select JAR

### Step 4: Compile the Project

#### Using Command Line:
```bash
# Navigate to project directory
cd /path/to/bodm

# Compile all Java files
javac -d bin -cp "lib/*" src/**/*.java src/*.java

# If on Windows, use semicolon instead of colon
javac -d bin -cp "lib/*" src/**/*.java src/*.java
```

#### Using IDE:
- Most IDEs will automatically compile when you build/run the project

### Step 5: Run the Application

#### Using Command Line:
```bash
# Run from project directory
java -cp "bin;lib/*" MainApplication

# On Linux/Mac, use colon instead of semicolon
java -cp "bin:lib/*" MainApplication
```

#### Using IDE:
- Right-click on `MainApplication.java` → Run

## Default Login Credentials

### Receptionist Account
- **Username**: `admin`
- **Password**: `admin123`

### Donor Account
- Register a new donor account through the "Register as Donor" button on the login screen

## Project Structure

```
bodm/
├── src/
│   ├── MainApplication.java          # Main entry point
│   ├── models/                       # OOP Model classes
│   │   ├── User.java                 # Base class (Inheritance)
│   │   ├── Donor.java                # Extends User (Inheritance)
│   │   ├── Receptionist.java        # Extends User (Inheritance)
│   │   ├── CharityRequest.java      # Encapsulation
│   │   └── Donation.java            # Encapsulation
│   ├── database/                     # Database layer
│   │   ├── DatabaseConnection.java  # Singleton pattern
│   │   ├── UserDAO.java             # User operations
│   │   ├── DonorDAO.java            # Donor operations
│   │   └── CharityDAO.java          # Charity operations
│   ├── interfaces/                   # Abstraction
│   │   ├── PaymentGateway.java      # Interface (Abstraction)
│   │   ├── DemoPaymentGateway.java  # Implementation
│   │   └── UPIPaymentGateway.java   # Implementation (Polymorphism)
│   ├── gui/                          # Swing GUI components
│   │   ├── LoginFrame.java
│   │   ├── DonorRegistrationFrame.java
│   │   ├── MainDashboard.java
│   │   ├── DonorProfilePanel.java
│   │   ├── DonorUpdatePanel.java
│   │   ├── ReceptionDashboardPanel.java
│   │   ├── ViewDonorsPanel.java
│   │   ├── SearchDonorsPanel.java
│   │   ├── CharityPanel.java
│   │   ├── CharityManagementPanel.java
│   │   ├── DonationDialog.java
│   │   ├── CreateCharityDialog.java
│   │   └── ViewDonationsDialog.java
│   └── utils/                        # Utility classes
│       ├── ValidationUtils.java
│       └── SessionManager.java
├── lib/                              # External libraries
│   └── mysql-connector-java-x.x.xx.jar
├── database_schema.sql               # Database creation script
├── README.md                         # Project documentation
└── SETUP_GUIDE.md                   # This file

```

## OOP Concepts Demonstrated

### 1. Encapsulation
- **Location**: All model classes (`User`, `Donor`, `CharityRequest`, `Donation`)
- **Implementation**: Private attributes with public getter/setter methods
- **Example**: `Donor.java` - private fields with controlled access

### 2. Inheritance
- **Location**: `models/Donor.java`, `models/Receptionist.java`
- **Implementation**: Both extend the `User` base class
- **Example**: `public class Donor extends User`

### 3. Polymorphism
- **Location**: Method overriding in `Donor` and `Receptionist` classes
- **Implementation**: `getDisplayInfo()` method overridden in child classes
- **Example**: Different search methods in `SearchDonorsPanel.java`

### 4. Abstraction
- **Location**: `interfaces/PaymentGateway.java`
- **Implementation**: Interface with multiple concrete implementations
- **Example**: `DemoPaymentGateway` and `UPIPaymentGateway` implement `PaymentGateway`

## Features

### Donor Module
- ✓ Donor registration with validation
- ✓ Login and authentication
- ✓ View profile information
- ✓ Update donor details
- ✓ View and donate to charity requests

### Reception Module
- ✓ Dashboard with statistics
- ✓ View all registered donors
- ✓ Search donors by blood group, organ, or location
- ✓ Manage charity requests
- ✓ View donation history

### Charity Module
- ✓ Create charity requests
- ✓ View active requests
- ✓ Mock payment gateway integration
- ✓ Support for multiple payment methods (Card/UPI)
- ✓ Transaction tracking
- ✓ Progress tracking for charity goals

## Troubleshooting

### Database Connection Issues
1. Verify MySQL is running: `mysql -u root -p`
2. Check database exists: `SHOW DATABASES;`
3. Verify credentials in `DatabaseConnection.java`
4. Ensure JDBC driver is in classpath

### Compilation Errors
1. Verify JDK is installed: `java -version`
2. Check JDBC driver is in `lib` folder
3. Ensure all source files are present

### Runtime Errors
1. Check console for error messages
2. Verify database schema is created correctly
3. Check file permissions
4. Ensure all dependencies are available

## Testing the Application

### Test Donor Registration
1. Click "Register as Donor"
2. Fill in all required fields
3. Use a 10-digit phone number
4. Age must be between 18-65
5. Submit and verify success message

### Test Receptionist Functions
1. Login with admin credentials
2. Navigate to "View All Donors"
3. Try searching by blood group (e.g., "O+")
4. Create a new charity request
5. View donations for a request

### Test Charity Donations
1. Login as donor or receptionist
2. Navigate to "Charity Requests"
3. Click "DONATE NOW" on any request
4. Enter donation details
5. Use demo card: 4111111111111111, CVV: 123
6. Or UPI: test@upi, PIN: 1234
7. Verify transaction success

## Color Theme
- **Primary Red**: #DC143C (220, 20, 60)
- **Dark Red**: #8B0000 (139, 0, 0)
- **White**: #FFFFFF
- **Light Gray**: #F5F5F5

## Support
For issues or questions, refer to the README.md file or check the source code comments.

## License
This is an educational project demonstrating OOP concepts in Java.
