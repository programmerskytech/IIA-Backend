-- =====================================================
-- ADD EMPLOYEE REGISTRATION LOV VALUES
-- Purpose: Add LOV values for Employee Registration form designators
-- Date: 2025-12-26
-- Note: Run this AFTER cleanup_and_fix_designators.sql
-- =====================================================

-- =====================================================
-- Get designator IDs for Employee Registration
-- =====================================================

SET @job_title_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'jobTitle');
SET @department_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'department');
SET @designation_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'designation');
SET @employment_type_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'employmentType');
SET @emp_location_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'location');

-- =====================================================
-- 1. JOB TITLE LOV VALUES (8 values)
-- =====================================================

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@job_title_id, 'SOFTWARE_ENGINEER', 'Software Engineer', 'Software Engineer position', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'SENIOR_SOFTWARE_ENGINEER', 'Senior Software Engineer', 'Senior Software Engineer position', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'TEAM_LEAD', 'Team Lead', 'Team Lead position', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'PROJECT_MANAGER', 'Project Manager', 'Project Manager position', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'HR_MANAGER', 'HR Manager', 'HR Manager position', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'FINANCE_MANAGER', 'Finance Manager', 'Finance Manager position', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'ADMINISTRATIVE_ASSISTANT', 'Administrative Assistant', 'Administrative Assistant position', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_title_id, 'ACCOUNTANT', 'Accountant', 'Accountant position', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    lov_description = VALUES(lov_description),
    updated_date = NOW();

-- =====================================================
-- 2. DEPARTMENT LOV VALUES (6 values)
-- =====================================================

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@department_id, 'ADMINISTRATION', 'Administration', 'Administration department', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'FINANCE', 'Finance', 'Finance department', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'IT', 'Information Technology', 'IT department', 3, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'HR', 'Human Resources', 'HR department', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'OPERATIONS', 'Operations', 'Operations department', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'SALES', 'Sales', 'Sales department', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    lov_description = VALUES(lov_description),
    updated_date = NOW();

-- =====================================================
-- 3. DESIGNATION LOV VALUES (4 values)
-- =====================================================

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@designation_id, 'MANAGER', 'Manager', 'Manager level', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'SENIOR_ENGINEER', 'Senior Engineer', 'Senior engineer level', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'ENGINEER', 'Engineer', 'Engineer level', 3, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'ASSISTANT', 'Assistant', 'Assistant level', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    lov_description = VALUES(lov_description),
    updated_date = NOW();

-- =====================================================
-- 4. EMPLOYMENT TYPE LOV VALUES (5 values)
-- =====================================================

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@employment_type_id, 'FULL_TIME', 'Full-time', 'Full-time employee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'PART_TIME', 'Part-time', 'Part-time employee', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'CONTRACT', 'Contract', 'Contract employee', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'INTERN', 'Intern', 'Intern employee', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'CONSULTANT', 'Consultant', 'Consultant employee', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    lov_description = VALUES(lov_description),
    updated_date = NOW();

-- =====================================================
-- 5. LOCATION LOV VALUES (6 values)
-- =====================================================

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@emp_location_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'HYDERABAD', 'Hyderabad', 'Hyderabad office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'CHENNAI', 'Chennai', 'Chennai office', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'PUNE', 'Pune', 'Pune office', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    lov_description = VALUES(lov_description),
    updated_date = NOW();

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check Employee Registration designators
SELECT
    d.designator_name,
    d.designator_display_name,
    COUNT(l.lov_id) as lov_count
FROM designator_master d
LEFT JOIN lov_master l ON d.designator_id = l.designator_id AND l.is_active = true
WHERE d.form_id = 4 AND d.is_active = true
GROUP BY d.designator_id, d.designator_name, d.designator_display_name
ORDER BY d.designator_name;

-- List all Employee Registration LOV values
SELECT
    d.designator_name,
    l.lov_value,
    l.lov_display_value,
    l.display_order
FROM designator_master d
JOIN lov_master l ON d.designator_id = l.designator_id
WHERE d.form_id = 4 AND d.is_active = true AND l.is_active = true
ORDER BY d.designator_name, l.display_order;

-- =====================================================
-- EXPECTED RESULTS
-- =====================================================
-- ✓ 5 designators for Employee Registration:
--   - jobTitle (8 values)
--   - department (6 values)
--   - designation (4 values)
--   - employmentType (5 values)
--   - location (6 values)
-- ✓ Total: 29 LOV values for Employee Registration
-- =====================================================
