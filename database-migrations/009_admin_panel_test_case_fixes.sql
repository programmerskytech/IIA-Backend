-- Database migration for Admin Panel Test Case Fixes (TC_13, TC_14, TC_15, TC_16)
-- Run this script on PostgreSQL database

-- ==================================================
-- TC_14 FIX: Add first login tracking columns
-- ==================================================

-- Add is_first_login column to user_master table
ALTER TABLE user_master
ADD COLUMN IF NOT EXISTS is_first_login BOOLEAN DEFAULT TRUE;

-- Add last_password_change_date column to user_master table
ALTER TABLE user_master
ADD COLUMN IF NOT EXISTS last_password_change_date TIMESTAMP;

-- Set existing users to NOT first login (since they already exist)
UPDATE user_master
SET is_first_login = FALSE
WHERE is_first_login IS NULL;

-- ==================================================
-- Verification Queries
-- ==================================================

-- Verify new columns were added
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'user_master'
  AND column_name IN ('is_first_login', 'last_password_change_date');

-- Check sample data
SELECT user_id, user_name, employee_id, is_first_login, last_password_change_date
FROM user_master
LIMIT 5;
