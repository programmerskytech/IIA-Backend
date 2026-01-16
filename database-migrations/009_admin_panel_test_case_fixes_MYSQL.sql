-- Database migration for Admin Panel Test Case Fixes (TC_13, TC_14, TC_15, TC_16)
-- Run this script on MySQL database

-- ==================================================
-- TC_14 FIX: Add first login tracking columns
-- ==================================================

-- Add is_first_login column to user_master table
ALTER TABLE user_master
ADD COLUMN is_first_login BOOLEAN DEFAULT TRUE;

-- Add last_password_change_date column to user_master table
ALTER TABLE user_master
ADD COLUMN last_password_change_date DATETIME;

-- Set existing users to NOT first login (since they already exist)
-- This marks all existing users as having already logged in (not their first time)
-- Run this to mark existing users as NOT first login:
UPDATE user_master
SET is_first_login = FALSE
WHERE user_id > 0;

-- Alternative: If you want to keep some users as first login, uncomment and modify:
-- UPDATE user_master
-- SET is_first_login = FALSE
-- WHERE user_id IN (87, 88, 89, 90, 91);  -- Specify user IDs

-- ==================================================
-- Verification Queries
-- ==================================================

-- Verify new columns were added
DESCRIBE user_master;

-- Check sample data
SELECT user_id, user_name, employee_id, is_first_login, last_password_change_date
FROM user_master
LIMIT 5;
