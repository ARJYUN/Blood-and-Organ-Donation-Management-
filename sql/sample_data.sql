-- Sample data for Blood and Organ Donation Management System
USE blood_donation_db;

-- Insert sample users (passwords are hashed using BCrypt)
-- Default password for all users: "password123"
INSERT INTO users (name, email, password, role, location, phone) VALUES
('Admin User', 'admin@blooddonation.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 'New York', '555-0101'),
('John Smith', 'john.smith@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'New York', '555-0102'),
('Sarah Johnson', 'sarah.johnson@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'Los Angeles', '555-0103'),
('Mike Wilson', 'mike.wilson@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEIVER', 'Chicago', '555-0104'),
('Emily Davis', 'emily.davis@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEIVER', 'Houston', '555-0105'),
('Red Cross', 'redcross@charity.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CHARITY', 'New York', '555-0106'),
('Blood Bank Foundation', 'bloodbank@charity.org', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'CHARITY', 'Los Angeles', '555-0107'),
('Alice Brown', 'alice.brown@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'Miami', '555-0108'),
('Robert Taylor', 'robert.taylor@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'RECEIVER', 'Phoenix', '555-0109'),
('Lisa Anderson', 'lisa.anderson@email.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'DONOR', 'Seattle', '555-0110');

-- Insert sample hospitals
INSERT INTO hospitals (name, address, contact_number, email, specialization) VALUES
('General Hospital', '123 Main St, New York, NY 10001', '555-1001', 'info@generalhospital.com', 'General Medicine, Emergency'),
('City Medical Center', '456 Oak Ave, Los Angeles, CA 90210', '555-1002', 'contact@citymedical.com', 'Cardiology, Oncology'),
('Regional Hospital', '789 Pine St, Chicago, IL 60601', '555-1003', 'admin@regionalhospital.com', 'Surgery, Pediatrics'),
('Metro Health Center', '321 Elm St, Houston, TX 77001', '555-1004', 'info@metrohealth.com', 'Internal Medicine, Neurology'),
('University Hospital', '654 Maple Dr, Miami, FL 33101', '555-1005', 'contact@universityhospital.com', 'Research, Transplant Surgery'),
('Community Medical', '987 Cedar Ln, Phoenix, AZ 85001', '555-1006', 'info@communitymedical.com', 'Family Medicine, Emergency'),
('Northwest Medical', '147 Birch St, Seattle, WA 98101', '555-1007', 'contact@northwestmedical.com', 'Orthopedics, Cardiology');

-- Insert sample donors
INSERT INTO donors (user_id, blood_group, organ_type, availability_status, last_donation_date, medical_clearance, notes) VALUES
(2, 'O+', 'BLOOD', 'AVAILABLE', '2024-01-15', TRUE, 'Regular blood donor, healthy'),
(3, 'A+', 'BLOOD', 'AVAILABLE', '2024-02-10', TRUE, 'Universal plasma donor'),
(8, 'B+', 'BLOOD', 'AVAILABLE', '2024-01-20', TRUE, 'Rare blood type, very committed'),
(10, 'AB+', 'BLOOD', 'UNAVAILABLE', '2024-02-05', TRUE, 'Recently donated, waiting period'),
(2, 'O+', 'KIDNEY', 'AVAILABLE', NULL, TRUE, 'Willing to donate kidney to family member'),
(3, 'A+', 'BONE_MARROW', 'AVAILABLE', NULL, TRUE, 'Registered bone marrow donor'),
(8, 'B+', 'EYE', 'AVAILABLE', NULL, TRUE, 'Eye tissue donor');

-- Insert sample receivers
INSERT INTO receivers (user_id, required_blood_group, required_organ, urgency_level, status, medical_condition, hospital_id, request_date) VALUES
(4, 'O+', 'BLOOD', 'HIGH', 'PENDING', 'Severe anemia, needs immediate blood transfusion', 1, '2024-02-15'),
(5, 'A+', 'KIDNEY', 'CRITICAL', 'APPROVED', 'End-stage renal disease, dialysis dependent', 2, '2024-02-10'),
(9, 'B+', 'BLOOD', 'MEDIUM', 'PENDING', 'Scheduled surgery requiring blood', 3, '2024-02-12'),
(4, 'O-', 'BONE_MARROW', 'HIGH', 'PENDING', 'Leukemia treatment, needs bone marrow transplant', 4, '2024-02-14'),
(5, 'A+', 'LIVER', 'CRITICAL', 'APPROVED', 'Liver failure, urgent transplant needed', 5, '2024-02-08');

-- Insert sample charities
INSERT INTO charities (user_id, organization_name, event_name, event_date, event_location, contact_info, description, status) VALUES
(6, 'Red Cross', 'Blood Drive 2024', '2024-03-15', 'Central Park, New York', '555-0106', 'Annual blood donation drive to help local hospitals', 'ACTIVE'),
(7, 'Blood Bank Foundation', 'Organ Donation Awareness', '2024-03-20', 'Los Angeles Convention Center', '555-0107', 'Educational event about organ donation', 'ACTIVE'),
(6, 'Red Cross', 'Emergency Blood Drive', '2024-02-25', 'Times Square, New York', '555-0106', 'Emergency blood collection for disaster relief', 'ACTIVE'),
(7, 'Blood Bank Foundation', 'Community Health Fair', '2024-04-10', 'Griffith Park, Los Angeles', '555-0107', 'Health screening and blood donation event', 'ACTIVE');

-- Insert sample charity participants
INSERT INTO charity_participants (charity_id, user_id, participation_type, status) VALUES
(1, 2, 'DONOR', 'CONFIRMED'),
(1, 3, 'DONOR', 'CONFIRMED'),
(1, 8, 'DONOR', 'REGISTERED'),
(1, 10, 'DONOR', 'CONFIRMED'),
(2, 4, 'RECEIVER', 'CONFIRMED'),
(2, 5, 'RECEIVER', 'CONFIRMED'),
(1, 6, 'VOLUNTEER', 'CONFIRMED'),
(2, 7, 'VOLUNTEER', 'CONFIRMED');

-- Insert sample donation requests
INSERT INTO donation_requests (donor_id, receiver_id, status, request_date, response_date, notes) VALUES
(1, 1, 'PENDING', '2024-02-15 10:30:00', NULL, 'Urgent blood donation request'),
(2, 2, 'APPROVED', '2024-02-10 14:20:00', '2024-02-10 16:45:00', 'Kidney donation approved, surgery scheduled'),
(3, 3, 'PENDING', '2024-02-12 09:15:00', NULL, 'Blood donation for surgery preparation'),
(4, 4, 'PENDING', '2024-02-14 11:00:00', NULL, 'Bone marrow donation request'),
(5, 5, 'APPROVED', '2024-02-08 13:30:00', '2024-02-08 15:20:00', 'Liver donation approved, transplant scheduled');
