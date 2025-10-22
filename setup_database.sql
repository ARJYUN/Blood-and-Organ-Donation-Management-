-- Blood Donation Management System - Database Setup
-- Run this script in MySQL to set up the database

-- Create database
CREATE DATABASE IF NOT EXISTS blood_donation_db;
USE blood_donation_db;

-- Create users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'DONOR', 'RECEIVER', 'CHARITY') NOT NULL,
    location VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create donors table
CREATE TABLE donors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    organ_type ENUM('BLOOD', 'KIDNEY', 'LIVER', 'HEART', 'LUNG', 'PANCREAS', 'EYE', 'BONE_MARROW') NOT NULL,
    availability_status ENUM('AVAILABLE', 'UNAVAILABLE', 'DONATED') DEFAULT 'AVAILABLE',
    last_donation_date DATE,
    medical_clearance BOOLEAN DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create receivers table
CREATE TABLE receivers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    required_blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    required_organ ENUM('BLOOD', 'KIDNEY', 'LIVER', 'HEART', 'LUNG', 'PANCREAS', 'EYE', 'BONE_MARROW') NOT NULL,
    urgency_level ENUM('LOW', 'MEDIUM', 'HIGH', 'CRITICAL') DEFAULT 'MEDIUM',
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'FULFILLED') DEFAULT 'PENDING',
    medical_condition TEXT,
    hospital_id INT,
    request_date DATE DEFAULT (CURRENT_DATE),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create charities table
CREATE TABLE charities (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    organization_name VARCHAR(100) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    event_location VARCHAR(100) NOT NULL,
    contact_info VARCHAR(100),
    description TEXT,
    status ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create hospitals table
CREATE TABLE hospitals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    contact_number VARCHAR(20),
    email VARCHAR(100),
    specialization VARCHAR(200),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create donation_requests table
CREATE TABLE donation_requests (
    id INT PRIMARY KEY AUTO_INCREMENT,
    donor_id INT NOT NULL,
    receiver_id INT NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED') DEFAULT 'PENDING',
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_date TIMESTAMP NULL,
    notes TEXT,
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES receivers(id) ON DELETE CASCADE
);

-- Create charity_participants table
CREATE TABLE charity_participants (
    id INT PRIMARY KEY AUTO_INCREMENT,
    charity_id INT NOT NULL,
    user_id INT NOT NULL,
    participation_type ENUM('DONOR', 'VOLUNTEER', 'RECEIVER') NOT NULL,
    status ENUM('REGISTERED', 'CONFIRMED', 'CANCELLED') DEFAULT 'REGISTERED',
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (charity_id) REFERENCES charities(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (name, email, password, role, location, phone) VALUES
('Admin User', 'admin@blooddonation.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 'New York', '555-0101'),
('John Smith', 'john.smith@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'New York', '555-0102'),
('Sarah Johnson', 'sarah.johnson@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'Los Angeles', '555-0103'),
('Mike Wilson', 'mike.wilson@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEIVER', 'Chicago', '555-0104'),
('Emily Davis', 'emily.davis@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEIVER', 'Houston', '555-0105'),
('Red Cross', 'redcross@charity.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CHARITY', 'New York', '555-0106');

INSERT INTO hospitals (name, address, contact_number, email, specialization) VALUES
('General Hospital', '123 Main St, New York, NY 10001', '555-1001', 'info@generalhospital.com', 'General Medicine, Emergency'),
('City Medical Center', '456 Oak Ave, Los Angeles, CA 90210', '555-1002', 'contact@citymedical.com', 'Cardiology, Oncology'),
('Regional Hospital', '789 Pine St, Chicago, IL 60601', '555-1003', 'admin@regionalhospital.com', 'Surgery, Pediatrics');

INSERT INTO donors (user_id, blood_group, organ_type, availability_status, medical_clearance) VALUES
(2, 'O+', 'BLOOD', 'AVAILABLE', TRUE),
(3, 'A+', 'BLOOD', 'AVAILABLE', TRUE);

INSERT INTO receivers (user_id, required_blood_group, required_organ, urgency_level, medical_condition, hospital_id) VALUES
(4, 'O+', 'BLOOD', 'HIGH', 'Severe anemia, needs immediate blood transfusion', 1),
(5, 'A+', 'BLOOD', 'MEDIUM', 'Scheduled surgery requiring blood', 2);

INSERT INTO charities (user_id, organization_name, event_name, event_date, event_location, contact_info, description) VALUES
(6, 'Red Cross', 'Blood Drive 2024', '2024-03-15', 'Central Park, New York', '555-0106', 'Annual blood donation drive to help local hospitals');

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_donors_blood_group ON donors(blood_group);
CREATE INDEX idx_donors_organ_type ON donors(organ_type);
CREATE INDEX idx_donors_availability ON donors(availability_status);
CREATE INDEX idx_receivers_blood_group ON receivers(required_blood_group);
CREATE INDEX idx_receivers_organ ON receivers(required_organ);
CREATE INDEX idx_receivers_status ON receivers(status);
CREATE INDEX idx_charities_event_date ON charities(event_date);
CREATE INDEX idx_hospitals_name ON hospitals(name);

-- Show success message
SELECT 'Database setup completed successfully!' as Status;
