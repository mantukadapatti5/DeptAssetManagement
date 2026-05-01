-- Create the database
CREATE DATABASE IF NOT EXISTS asset_management;

-- Use the database
USE asset_management;

-- Create the assets table
CREATE TABLE IF NOT EXISTS assets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(255) NOT NULL,
    asset_type VARCHAR(255) NOT NULL,
    condition_status VARCHAR(50) NOT NULL,
    assigned_to VARCHAR(255),
    added_date DATE NOT NULL
);

-- Note: We use 'condition_status' instead of 'condition' because 'condition' might be a reserved keyword in some SQL dialects/contexts, 
-- though 'condition' works in MySQL, it is safer to use a non-reserved word.

-- Example insert statement (Optional)
-- INSERT INTO assets (asset_name, asset_type, condition_status, assigned_to, added_date) VALUES ('Dell XPS 15', 'Laptop', 'Good', 'John Doe', '2023-10-25');
