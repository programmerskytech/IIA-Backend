-- ============================================
-- CLEANUP AND FIX DESIGNATORS
-- This ensures ONLY the correct designators exist for each form
-- Date: 2024-12-24
-- ============================================

-- ============================================
-- STEP 0: Disable safe update mode temporarily
-- ============================================
SET SQL_SAFE_UPDATES = 0;

-- ============================================
-- STEP 1: Delete ALL existing designators and LOVs (Clean Slate)
-- ============================================
-- This ensures we start fresh with only the correct designators

-- Delete all LOV values first (due to foreign key constraint)
DELETE FROM lov_master;

-- Delete all designators
DELETE FROM designator_master;

-- ============================================
-- STEP 2: Insert ONLY the correct designators
-- ============================================

-- 1. ASSET MASTER - 1 designator ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(1, 'locator', 'Locator', 'Asset location', true, NOW(), 'SYSTEM');

-- 2. CONTINGENCY PURCHASE - 6 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(2, 'gstPercentage', 'GST Percentage', 'GST percentage options', true, NOW(), 'SYSTEM'),
(2, 'paymentTo', 'Payment To', 'Payment recipient type', true, NOW(), 'SYSTEM'),
(2, 'budgetCode', 'Budget Code', 'Budget code options', true, NOW(), 'SYSTEM'),
(2, 'materialCategory', 'Material Category', 'Material category', true, NOW(), 'SYSTEM'),
(2, 'materialSubCategory', 'Material Sub Category', 'Material subcategory', true, NOW(), 'SYSTEM'),
(2, 'countryOfOrigin', 'Country of Origin', 'Country of origin', true, NOW(), 'SYSTEM');

-- 3. INDENT CREATION - 1 designator ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(3, 'consigneeLocation', 'Consignee Location', 'Consignee location', true, NOW(), 'SYSTEM');

-- 4. EMPLOYEE REGISTRATION - 5 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(4, 'jobTitle', 'Job Title', 'Employee job title', true, NOW(), 'SYSTEM'),
(4, 'department', 'Department', 'Employee department', true, NOW(), 'SYSTEM'),
(4, 'designation', 'Designation', 'Employee designation/level', true, NOW(), 'SYSTEM'),
(4, 'employmentType', 'Employment Type', 'Employment type (Full-time, Part-time, Contract, etc.)', true, NOW(), 'SYSTEM'),
(4, 'location', 'Location', 'Employee office location', true, NOW(), 'SYSTEM');

-- 5. JOB MASTER - 4 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(5, 'jobCategory', 'Job Category', 'Job category', true, NOW(), 'SYSTEM'),
(5, 'jobSubcategory', 'Job Subcategory', 'Job subcategory', true, NOW(), 'SYSTEM'),
(5, 'uom', 'Unit of Measurement', 'Unit of measurement for jobs', true, NOW(), 'SYSTEM'),
(5, 'currency', 'Currency', 'Currency for job pricing', true, NOW(), 'SYSTEM');

-- 6. MATERIAL MASTER - 4 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(6, 'category', 'Category', 'Material category', true, NOW(), 'SYSTEM'),
(6, 'subcategory', 'Subcategory', 'Material subcategory', true, NOW(), 'SYSTEM'),
(6, 'uom', 'Unit of Measurement', 'Unit of measurement for materials', true, NOW(), 'SYSTEM'),
(6, 'currency', 'Currency', 'Currency for material pricing', true, NOW(), 'SYSTEM');

-- 7. VENDOR MASTER - 1 designator ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(7, 'primaryBusiness', 'Primary Business', 'Vendor primary business type', true, NOW(), 'SYSTEM');

-- 8. PURCHASE ORDER - 3 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(8, 'deliveryPeriod', 'Delivery Period', 'Delivery period options', true, NOW(), 'SYSTEM'),
(8, 'warranty', 'Warranty', 'Warranty period options', true, NOW(), 'SYSTEM'),
(8, 'applicablePbgToBeSubmitted', 'Applicable PBG to be Submitted', 'PBG submission requirements', true, NOW(), 'SYSTEM');

-- 9. TENDER REQUEST - 2 designators ONLY
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(9, 'incoTerms', 'INCO Terms', 'International commercial terms', true, NOW(), 'SYSTEM'),
(9, 'paymentTerms', 'Payment Terms', 'Payment terms', true, NOW(), 'SYSTEM');

-- ============================================
-- STEP 3: Verify the cleanup
-- ============================================

SELECT
    fm.form_id,
    fm.form_name,
    fm.form_display_name,
    COUNT(dm.designator_id) as designator_count
FROM form_master fm
LEFT JOIN designator_master dm ON fm.form_id = dm.form_id AND dm.is_active = true
WHERE fm.is_active = true
GROUP BY fm.form_id, fm.form_name, fm.form_display_name
ORDER BY fm.form_id;

-- Expected output:
-- form_id | form_name             | form_display_name       | designator_count
-- 1       | AssetMaster           | Asset Master            | 1
-- 2       | ContingencyPurchase   | Contingency Purchase    | 6
-- 3       | IndentCreation        | Indent Creation         | 1
-- 4       | EmployeeRegistration  | Employee Registration   | 3
-- 5       | JobMaster             | Job Master              | 4
-- 6       | MaterialMaster        | Material Master         | 4
-- 7       | VendorMaster          | Vendor Master           | 1
-- 8       | PurchaseOrder         | Purchase Order          | 3
-- 9       | TenderRequest         | Tender Request          | 2

-- Show all designators by form
SELECT
    fm.form_name,
    dm.designator_name,
    dm.designator_display_name
FROM form_master fm
JOIN designator_master dm ON fm.form_id = dm.form_id
WHERE fm.is_active = true AND dm.is_active = true
ORDER BY fm.form_id, dm.designator_id;

-- ============================================
-- Re-enable safe update mode
-- ============================================
SET SQL_SAFE_UPDATES = 1;

-- ============================================
-- SUCCESS!
-- Now each form has ONLY the correct designators
-- ============================================
