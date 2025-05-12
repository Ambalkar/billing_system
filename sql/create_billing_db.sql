-- Billing Software Database Schema

CREATE DATABASE IF NOT EXISTS billing_system;
USE billing_system;

-- Users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role ENUM('admin', 'user') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bills table
CREATE TABLE bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    bill_date DATE NOT NULL,
    due_date DATE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status ENUM('pending', 'paid', 'overdue') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE CASCADE
);

-- Payment History table
CREATE TABLE payment_history (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    payment_status ENUM('success', 'failed', 'pending') DEFAULT 'pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id) ON DELETE CASCADE
);

-- Sample data insertion

INSERT INTO users (username, password_hash, email, full_name, role) VALUES
('admin', SHA2('adminpassword', 256), 'admin@example.com', 'Administrator', 'admin'),
('user1', SHA2('user1password', 256), 'user1@example.com', 'User One', 'user');

INSERT INTO bills (user_id, bill_date, due_date, total_amount, status) VALUES
(2, '2024-06-01', '2024-06-15', 150.00, 'pending'),
(2, '2024-05-01', '2024-05-15', 200.00, 'paid');

INSERT INTO transactions (bill_id, transaction_date, amount, payment_method) VALUES
(2, '2024-05-10 10:00:00', 200.00, 'Credit Card');

INSERT INTO payment_history (transaction_id, payment_status, payment_date) VALUES
(1, 'success', '2024-05-10 10:05:00');
