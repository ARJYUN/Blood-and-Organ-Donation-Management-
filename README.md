# Blood and Organ Donation Management System

A comprehensive Java desktop application for managing blood and organ donations using Java Swing and MySQL.

## Features

### 🔐 Authentication System
- Secure login and registration
- Role-based access control (Admin, Donor, Receiver, Charity)
- Password hashing using BCrypt

### 👨‍💼 Admin Panel
- Complete system overview with statistics
- User management (view all users by role)
- Donor and receiver management
- Hospital management
- Real-time statistics dashboard

### 🩸 Donor Features
- Register donation details (blood type, organ type, availability)
- View personal donation history
- Browse available receiver requests
- Medical clearance tracking

### 🏥 Receiver Features
- Submit donation requests with urgency levels
- Search for available donors
- Track request status
- Hospital integration

### 🏛️ Charity Features
- Create and manage donation events
- Organize charity drives
- Event management and tracking

### 🏥 Hospital Integration
- Hospital database with contact information
- Specialization tracking
- Location-based services

## Technology Stack

- **Language**: Java 8+
- **UI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **Architecture**: MVC Pattern
- **Build Tool**: Maven
- **Password Hashing**: BCrypt
- **PDF Generation**: iText
- **CSV Processing**: OpenCSV

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
2. **MySQL Server 8.0 or higher**
3. **Maven 3.6 or higher** (optional, for building)

## Installation & Setup

### 1. Database Setup

1. **Start MySQL Server**
2. **Create Database and Tables**:
   ```sql
   -- Run the schema file
   source sql/schema.sql;
   
   -- Insert sample data
   source sql/sample_data.sql;
   ```

3. **Update Database Configuration**:
   Edit `src/main/resources/database.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/blood_donation_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   db.username=your_mysql_username
   db.password=your_mysql_password
   db.driver=com.mysql.cj.jdbc.Driver
   ```

### 2. Build and Run

#### Option 1: Using Maven
```bash
# Compile the project
mvn compile

# Run the application
mvn exec:java -Dexec.mainClass="com.blooddonation.main.Main"
```

#### Option 2: Using IDE
1. Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
2. Configure the database connection in `database.properties`
3. Run the `Main.java` class

#### Option 3: Create Executable JAR
```bash
# Package the application
mvn clean package

# Run the JAR file
java -jar target/blood-organ-donation-management-1.0.0.jar
```

## Default Login Credentials

### Admin Account
- **Email**: admin@blooddonation.com
- **Password**: password123

### Sample User Accounts
- **Donor**: john.smith@email.com / password123
- **Receiver**: mike.wilson@email.com / password123
- **Charity**: redcross@charity.org / password123

## Project Structure

```
src/main/java/com/blooddonation/
├── controller/          # Business logic controllers
│   └── AuthController.java
├── database/           # Database access layer
│   ├── DatabaseConnection.java
│   ├── UserDAO.java
│   ├── DonorDAO.java
│   ├── ReceiverDAO.java
│   ├── CharityDAO.java
│   └── HospitalDAO.java
├── model/              # Data models
│   ├── User.java
│   ├── Donor.java
│   ├── Receiver.java
│   ├── Charity.java
│   └── Hospital.java
├── view/               # User interface
│   ├── LoginForm.java
│   ├── SignupForm.java
│   ├── AdminDashboard.java
│   ├── DonorDashboard.java
│   ├── ReceiverDashboard.java
│   └── CharityDashboard.java
└── main/               # Application entry point
    └── Main.java
```

## Database Schema

### Core Tables
- **users**: User accounts and authentication
- **donors**: Donor information and availability
- **receivers**: Receiver requests and status
- **charities**: Charity organizations and events
- **hospitals**: Hospital information and contact details
- **donation_requests**: Links between donors and receivers
- **charity_participants**: Event participation tracking

## Key Features

### 🔍 Location-Based Search
- Find donors and receivers by location
- Hospital proximity search
- Geographic filtering

### 📊 Statistics Dashboard
- Real-time system statistics
- User activity tracking
- Donation success rates
- Hospital utilization metrics

### 🔒 Security Features
- Password hashing with BCrypt
- Role-based access control
- Input validation and sanitization
- SQL injection prevention

### 📱 User-Friendly Interface
- Clean, modern Swing interface
- Intuitive navigation
- Responsive design
- Error handling and user feedback

## Usage Guide

### For Administrators
1. Login with admin credentials
2. Access the comprehensive dashboard
3. Monitor system statistics
4. Manage users, donors, and receivers
5. Oversee hospital information

### For Donors
1. Register as a donor
2. Add donation details (blood type, organ type)
3. Set availability status
4. Browse receiver requests
5. Update medical clearance

### For Receivers
1. Register as a receiver
2. Submit donation requests
3. Specify urgency levels
4. Select preferred hospitals
5. Track request status

### For Charity Organizations
1. Register as a charity
2. Create donation events
3. Manage event details
4. Track participation
5. Organize drives

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running
   - Check database.properties configuration
   - Ensure database exists and tables are created

2. **Login Issues**
   - Use correct email and password
   - Check if user account exists
   - Verify role selection (Admin vs User)

3. **Build Errors**
   - Ensure JDK 8+ is installed
   - Check Maven configuration
   - Verify all dependencies are available

### Support

For technical support or questions:
- Check the database connection settings
- Verify all required dependencies are installed
- Review the console output for error messages

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Java Swing for the user interface
- MySQL for database management
- BCrypt for secure password hashing
- Maven for project management
