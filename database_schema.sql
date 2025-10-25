-- Blood and Organ Donation Management System Database Schema

CREATE DATABASE IF NOT EXISTS blood_organ_donation;
USE blood_organ_donation;

-- User table for authentication
CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Donor table
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
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- Charity request table
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

-- Donation table
CREATE TABLE donation (
    donation_id INT PRIMARY KEY AUTO_INCREMENT,
    donor_name VARCHAR(100) NOT NULL,
    request_id INT,
    amount DECIMAL(10,2) NOT NULL,
    donation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method VARCHAR(50),
    FOREIGN KEY (request_id) REFERENCES charity_request(request_id) ON DELETE CASCADE
);

-- Recipient table
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

-- Insert default receptionist user
INSERT INTO user (username, password, role) VALUES ('admin', 'admin123', 'RECEPTIONIST');

-- Insert sample charity requests
INSERT INTO charity_request (title, description, requester_name, type, goal_amount, raised_amount) 
VALUES 
('Children Vaccine Drive', 'Help provide vaccines to underprivileged children in rural areas', 'Hope Foundation', 'Medical', 50000.00, 15000.00),
('Emergency Medical Fund', 'Support for patients requiring urgent medical treatment', 'City Hospital', 'Medical', 100000.00, 25000.00),
('Blood Donation Camp', 'Organize blood donation camps across the city', 'Red Cross Society', 'Event', 30000.00, 10000.00);
