# Blood and Organ Donation Management System

## Overview
A comprehensive Blood and Organ Donation Management System built with Java Swing and MySQL, demonstrating core OOP principles.

## Features
- **Donor Module**: Register, login, and manage donor information
- **Reception Module**: Search and filter donors by blood group/organ type
- **Charity Module**: Create and manage charity requests with mock payment gateway

## OOP Concepts Demonstrated
- **Encapsulation**: Private attributes with getter/setter methods
- **Inheritance**: User base class extended by Donor and Receptionist
- **Polymorphism**: Method overriding in search functionality
- **Abstraction**: PaymentGateway interface with concrete implementations

## Technology Stack
- Java (JDK 8+)
- Java Swing (GUI)
- MySQL (Database)
- JDBC (Database connectivity)

## Database Setup

### Prerequisites
- MySQL Server installed
- MySQL JDBC Driver (included in lib folder)

### Database Creation
Run the following SQL script to create the database and tables:

```sql
CREATE DATABASE IF NOT EXISTS blood_organ_donation;
USE blood_organ_donation;

CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE donor (
    donor_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    blood_group VARCHAR(5) NOT NULL,
    organ VARCHAR(50),
    contact VARCHAR(15) NOT NULL,
    location VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE charity_request (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    requester_name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    goal_amount DECIMAL(10,2) NOT NULL,
    raised_amount DECIMAL(10,2) DEFAULT 0,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

CREATE TABLE donation (
    donation_id INT PRIMARY KEY AUTO_INCREMENT,
    donor_name VARCHAR(100) NOT NULL,
    request_id INT,
    amount DECIMAL(10,2) NOT NULL,
    donation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50),
    FOREIGN KEY (request_id) REFERENCES charity_request(request_id) ON DELETE CASCADE
);

-- Insert default receptionist user
INSERT INTO user (username, password, role) VALUES ('admin', 'admin123', 'RECEPTIONIST');
```

## Configuration
Update database credentials in `src/database/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/blood_organ_donation";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

## How to Run
1. Set up MySQL database using the SQL script above
2. Update database credentials in DatabaseConnection.java
3. Compile and run MainApplication.java
4. Use the GUI to navigate between modules

## Default Credentials
- **Receptionist**: username: `admin`, password: `admin123`

## Project Structure
```
bodm/
├── src/
│   ├── models/          # Core OOP classes
│   ├── database/        # Database connection and DAO
│   ├── gui/             # Swing GUI components
│   ├── interfaces/      # Abstraction interfaces
│   └── utils/           # Utility classes
├── lib/                 # External libraries (JDBC driver)
└── database_schema.sql  # Database creation script
```

## Color Theme
- Primary: Red (#DC143C)
- Secondary: White (#FFFFFF)
- Accent: Dark Red (#8B0000)

## Author
Created as a demonstration of OOP principles in Java
