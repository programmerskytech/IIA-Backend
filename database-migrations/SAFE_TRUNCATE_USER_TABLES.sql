-- ============================================================================
-- SAFE USER MASTER TRUNCATION SCRIPT
-- ============================================================================
-- This script safely truncates user_master and all related tables
-- to prevent orphaned role mappings and employee data
-- ============================================================================

USE astrodatabase;

-- Disable foreign key checks temporarily
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================================
-- Step 1: Backup current data (Optional - Run this first if you want backup)
-- ============================================================================
-- CREATE TABLE user_master_backup AS SELECT * FROM user_master;
-- CREATE TABLE user_role_master_backup AS SELECT * FROM user_role_master;
-- CREATE TABLE employee_user_details_backup AS SELECT * FROM employee_user_details;

-- ============================================================================
-- Step 2: Truncate all related tables in the correct order
-- ============================================================================

-- Truncate user-role mappings (THIS IS THE KEY!)
TRUNCATE TABLE user_role_master;

-- Truncate employee details linked to users
TRUNCATE TABLE employee_user_details;

-- Truncate the main user table
TRUNCATE TABLE user_master;

-- ============================================================================
-- Step 3: Reset auto-increment counters to start fresh
-- ============================================================================

-- Reset user_id to start from 1 (or any value you prefer)
ALTER TABLE user_master AUTO_INCREMENT = 1;

-- Reset user_role_master ID
ALTER TABLE user_role_master AUTO_INCREMENT = 1;

-- Reset employee_user_details ID
ALTER TABLE employee_user_details AUTO_INCREMENT = 1;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================================================
-- Step 4: Verification Queries
-- ============================================================================

-- Verify all tables are empty
SELECT 'user_master count:' as table_name, COUNT(*) as count FROM user_master
UNION ALL
SELECT 'user_role_master count:', COUNT(*) FROM user_role_master
UNION ALL
SELECT 'employee_user_details count:', COUNT(*) FROM employee_user_details;

-- Check auto-increment values
SELECT
    TABLE_NAME,
    AUTO_INCREMENT
FROM
    INFORMATION_SCHEMA.TABLES
WHERE
    TABLE_SCHEMA = 'astrodatabase'
    AND TABLE_NAME IN ('user_master', 'user_role_master', 'employee_user_details');

-- ============================================================================
-- END OF SAFE TRUNCATION SCRIPT
-- ============================================================================

-- NOTE: After running this script, you can create the admin user fresh
-- and all new users will have clean role mappings with no conflicts.
