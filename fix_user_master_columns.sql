-- SQL Script to fix user_master table column names
-- This script renames columns from camelCase to snake_case to match Hibernate's default naming convention

-- Rename columns in user_master table
ALTER TABLE user_master
    CHANGE COLUMN `userId` `user_id` INT AUTO_INCREMENT,
    CHANGE COLUMN `userName` `user_name` VARCHAR(255),
    CHANGE COLUMN `mobileNumber` `mobile_number` VARCHAR(255),
    CHANGE COLUMN `createdBy` `created_by` VARCHAR(255),
    CHANGE COLUMN `createdDate` `created_date` DATETIME;

-- Note: role_name and employee_id already use snake_case, so no changes needed for them

-- Verify the changes
DESCRIBE user_master;
