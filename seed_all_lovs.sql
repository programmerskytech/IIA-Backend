-- ============================================
-- LOV System - Complete Seed Data Script
-- Date: 2024-12-24
-- Purpose: Seed all LOV values for 9 forms, 25 designators
-- ============================================

-- Clear existing data (CAUTION: Only for development)
-- Uncomment if you need to reset
-- DELETE FROM lov_master;
-- DELETE FROM designator_master;
-- DELETE FROM form_master;

-- ============================================
-- STEP 1: Insert Forms (9 forms)
-- ============================================

INSERT INTO form_master (form_id, form_name, form_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(1, 'AssetMaster', 'Asset Master', 'Asset management form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'ContingencyPurchase', 'Contingency Purchase', 'Contingency purchase form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(3, 'IndentCreation', 'Indent Creation', 'Indent creation form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(4, 'EmployeeRegistration', 'Employee Registration', 'Employee registration form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(5, 'JobMaster', 'Job Master', 'Job master form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(6, 'MaterialMaster', 'Material Master', 'Material master form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(7, 'VendorMaster', 'Vendor Master', 'Vendor master form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(8, 'PurchaseOrder', 'Purchase Order', 'Purchase order form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(9, 'TenderRequest', 'Tender Request', 'Tender request form', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    form_display_name = VALUES(form_display_name),
    description = VALUES(description),
    updated_date = NOW();

-- ============================================
-- STEP 2: Insert Designators (25 designators)
-- ============================================

-- Asset Master Designators (1)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(1, 'locator', 'Locator', 'Asset location', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Contingency Purchase Designators (6)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(2, 'gstPercentage', 'GST Percentage', 'GST percentage options', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'paymentTo', 'Payment To', 'Payment recipient type', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'budgetCode', 'Budget Code', 'Budget code options', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'materialCategory', 'Material Category', 'Material category', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'materialSubCategory', 'Material Sub Category', 'Material subcategory', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(2, 'countryOfOrigin', 'Country of Origin', 'Country of origin', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Indent Creation Designators (1)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(3, 'consigneeLocation', 'Consignee Location', 'Consignee location', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Employee Registration Designators (5)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(4, 'jobTitle', 'Job Title', 'Employee job title', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(4, 'department', 'Department', 'Employee department', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(4, 'designation', 'Designation', 'Employee designation', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(4, 'employmentType', 'Employment Type', 'Employment type (Full-time, Part-time, Contract)', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(4, 'location', 'Location', 'Employee location', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Job Master Designators (4)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(5, 'jobCategory', 'Job Category', 'Job category', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(5, 'jobSubcategory', 'Job Subcategory', 'Job subcategory', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(5, 'uom', 'Unit of Measurement', 'Unit of measurement for jobs', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(5, 'currency', 'Currency', 'Currency for job pricing', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Material Master Designators (4)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(6, 'category', 'Category', 'Material category', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(6, 'subcategory', 'Subcategory', 'Material subcategory', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(6, 'uom', 'Unit of Measurement', 'Unit of measurement for materials', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(6, 'currency', 'Currency', 'Currency for material pricing', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Vendor Master Designators (1)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(7, 'primaryBusiness', 'Primary Business', 'Vendor primary business type', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Purchase Order Designators (3)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(8, 'deliveryPeriod', 'Delivery Period', 'Delivery period options', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(8, 'warranty', 'Warranty', 'Warranty period options', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(8, 'applicablePbgToBeSubmitted', 'Applicable PBG to be Submitted', 'PBG submission requirements', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- Tender Request Designators (2)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, description, is_active, created_date, updated_date, created_by, updated_by)
VALUES
(9, 'incoTerms', 'INCO Terms', 'International commercial terms', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(9, 'paymentTerms', 'Payment Terms', 'Payment terms', true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    designator_display_name = VALUES(designator_display_name),
    updated_date = NOW();

-- ============================================
-- STEP 3: Insert LOV Values
-- ============================================

-- Get designator IDs dynamically (these will be used in INSERT statements below)
SET @locator_id = (SELECT designator_id FROM designator_master WHERE form_id = 1 AND designator_name = 'locator');
SET @gst_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'gstPercentage');
SET @payment_to_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'paymentTo');
SET @budget_code_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'budgetCode');
SET @cp_mat_cat_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'materialCategory');
SET @cp_mat_subcat_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'materialSubCategory');
SET @country_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'countryOfOrigin');
SET @consignee_loc_id = (SELECT designator_id FROM designator_master WHERE form_id = 3 AND designator_name = 'consigneeLocation');
SET @job_title_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'jobTitle');
SET @department_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'department');
SET @designation_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'designation');
SET @employment_type_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'employmentType');
SET @emp_location_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'location');
SET @job_category_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'jobCategory');
SET @job_subcategory_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'jobSubcategory');
SET @job_uom_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'uom');
SET @job_currency_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'currency');
SET @mat_category_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'category');
SET @mat_subcategory_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'subcategory');
SET @mat_uom_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'uom');
SET @mat_currency_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'currency');
SET @primary_business_id = (SELECT designator_id FROM designator_master WHERE form_id = 7 AND designator_name = 'primaryBusiness');
SET @delivery_period_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'deliveryPeriod');
SET @warranty_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'warranty');
SET @pbg_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'applicablePbgToBeSubmitted');
SET @inco_terms_id = (SELECT designator_id FROM designator_master WHERE form_id = 9 AND designator_name = 'incoTerms');
SET @payment_terms_id = (SELECT designator_id FROM designator_master WHERE form_id = 9 AND designator_name = 'paymentTerms');

-- ============================================
-- Asset Master LOVs
-- ============================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@locator_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@locator_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@locator_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@locator_id, 'KOLKATA', 'Kolkata', 'Kolkata office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Contingency Purchase LOVs
-- ============================================

-- GST Percentage
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@gst_id, '0', 'Nil (0%)', 'No GST', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@gst_id, '5', '5%', '5% GST', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@gst_id, '12', '12%', '12% GST', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@gst_id, '18', '18%', '18% GST', 4, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@gst_id, '28', '28%', '28% GST', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Payment To
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_to_id, 'VENDOR', 'Vendor', 'Payment to vendor', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_to_id, 'CONTRACTOR', 'Contractor', 'Payment to contractor', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_to_id, 'SERVICE_PROVIDER', 'Service Provider', 'Payment to service provider', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Budget Code
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@budget_code_id, 'CAPEX', 'CAPEX', 'Capital Expenditure', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_code_id, 'OPEX', 'OPEX', 'Operational Expenditure', 2, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_code_id, 'R&D', 'R&D', 'Research and Development', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Material Category (Contingency Purchase)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@cp_mat_cat_id, 'COMPUTER', 'Computer', 'Computer equipment', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_cat_id, 'NON_COMPUTER', 'Non-Computer', 'Non-computer equipment', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_cat_id, 'OFFICE_SUPPLIES', 'Office Supplies', 'Office supplies', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_cat_id, 'FURNITURE', 'Furniture', 'Office furniture', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Material Sub Category (Contingency Purchase)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@cp_mat_subcat_id, 'LAPTOP', 'Laptop', 'Laptop computers', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_subcat_id, 'DESKTOP', 'Desktop', 'Desktop computers', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_subcat_id, 'PRINTER', 'Printer', 'Printers', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@cp_mat_subcat_id, 'STATIONERY', 'Stationery', 'Office stationery', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Country of Origin
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@country_id, 'INDIA', 'India', 'Made in India', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@country_id, 'USA', 'United States', 'Made in USA', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@country_id, 'CHINA', 'China', 'Made in China', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@country_id, 'JAPAN', 'Japan', 'Made in Japan', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@country_id, 'GERMANY', 'Germany', 'Made in Germany', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Indent Creation LOVs
-- ============================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@consignee_loc_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@consignee_loc_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@consignee_loc_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Employee Registration LOVs
-- ============================================

-- Job Title
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
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
    updated_date = NOW();

-- Department
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@department_id, 'ADMINISTRATION', 'Administration', 'Administration department', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'FINANCE', 'Finance', 'Finance department', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'IT', 'Information Technology', 'IT department', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'HR', 'Human Resources', 'HR department', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'OPERATIONS', 'Operations', 'Operations department', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@department_id, 'SALES', 'Sales', 'Sales department', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Designation
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@designation_id, 'MANAGER', 'Manager', 'Manager level', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'SENIOR_ENGINEER', 'Senior Engineer', 'Senior engineer level', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'ENGINEER', 'Engineer', 'Engineer level', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@designation_id, 'ASSISTANT', 'Assistant', 'Assistant level', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Employment Type
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@employment_type_id, 'FULL_TIME', 'Full-time', 'Full-time employee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'PART_TIME', 'Part-time', 'Part-time employee', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'CONTRACT', 'Contract', 'Contract employee', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'INTERN', 'Intern', 'Intern employee', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employment_type_id, 'CONSULTANT', 'Consultant', 'Consultant employee', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Location
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@emp_location_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'HYDERABAD', 'Hyderabad', 'Hyderabad office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'CHENNAI', 'Chennai', 'Chennai office', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@emp_location_id, 'PUNE', 'Pune', 'Pune office', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Job Master LOVs
-- ============================================

-- Job Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@job_category_id, 'MAINTENANCE', 'Maintenance', 'Maintenance jobs', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_category_id, 'CONSULTING', 'Consulting', 'Consulting services', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_category_id, 'INSTALLATION', 'Installation', 'Installation services', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_category_id, 'SUPPORT', 'Support', 'Support services', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Job Subcategory
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@job_subcategory_id, 'ELECTRICAL', 'Electrical', 'Electrical work', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_subcategory_id, 'PLUMBING', 'Plumbing', 'Plumbing work', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_subcategory_id, 'CARPENTRY', 'Carpentry', 'Carpentry work', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_subcategory_id, 'IT_SUPPORT', 'IT Support', 'IT support services', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Job UOM
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@job_uom_id, 'HOUR', 'Hour', 'Per hour', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_uom_id, 'DAY', 'Day', 'Per day', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_uom_id, 'MONTH', 'Month', 'Per month', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_uom_id, 'JOB', 'Job', 'Per job', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Job Currency
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@job_currency_id, 'INR', 'INR (₹)', 'Indian Rupee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_currency_id, 'USD', 'USD ($)', 'US Dollar', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_currency_id, 'EUR', 'EUR (€)', 'Euro', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@job_currency_id, 'GBP', 'GBP (£)', 'British Pound', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Material Master LOVs
-- ============================================

-- Material Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@mat_category_id, 'CAPITAL', 'Capital', 'Capital goods', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_category_id, 'CONSUMABLE', 'Consumable', 'Consumable items', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_category_id, 'COMPUTER', 'Computer', 'Computer equipment', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_category_id, 'NON_COMPUTER', 'Non-Computer', 'Non-computer equipment', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_category_id, 'OFFICE_SUPPLIES', 'Office Supplies', 'Office supplies', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Material Subcategory
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@mat_subcategory_id, 'LAPTOP', 'Laptop', 'Laptop computers', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_subcategory_id, 'DESKTOP', 'Desktop', 'Desktop computers', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_subcategory_id, 'PRINTER', 'Printer', 'Printers', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_subcategory_id, 'STATIONERY', 'Stationery', 'Office stationery', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Material UOM
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@mat_uom_id, 'NOS', 'Nos', 'Numbers', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_uom_id, 'KG', 'Kg', 'Kilogram', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_uom_id, 'LITER', 'Liter', 'Liter', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_uom_id, 'METER', 'Meter', 'Meter', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_uom_id, 'BOX', 'Box', 'Box', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Material Currency
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@mat_currency_id, 'INR', 'INR (₹)', 'Indian Rupee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_currency_id, 'USD', 'USD ($)', 'US Dollar', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_currency_id, 'EUR', 'EUR (€)', 'Euro', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@mat_currency_id, 'GBP', 'GBP (£)', 'British Pound', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Vendor Master LOVs
-- ============================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@primary_business_id, 'MANUFACTURING', 'Manufacturing', 'Manufacturing business', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@primary_business_id, 'TRADING', 'Trading', 'Trading business', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@primary_business_id, 'SERVICE_PROVIDER', 'Service Provider', 'Service provider', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@primary_business_id, 'DISTRIBUTOR', 'Distributor', 'Distributor', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@primary_business_id, 'CHEMICALS', 'Chemicals', 'Chemicals supplier', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@primary_business_id, 'COMPUTERS', 'Computers & Peripherals', 'Computer equipment supplier', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Purchase Order LOVs
-- ============================================

-- Delivery Period
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@delivery_period_id, '7', '7 Days', '7 days delivery', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@delivery_period_id, '15', '15 Days', '15 days delivery', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@delivery_period_id, '30', '30 Days', '30 days delivery', 3, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@delivery_period_id, '60', '60 Days', '60 days delivery', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@delivery_period_id, '90', '90 Days', '90 days delivery', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Warranty
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@warranty_id, 'NA', 'No Warranty', 'No warranty', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@warranty_id, '6_MONTHS', '6 Months', '6 months warranty', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@warranty_id, '1_YEAR', '1 Year', '1 year warranty', 3, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@warranty_id, '2_YEARS', '2 Years', '2 years warranty', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@warranty_id, '3_YEARS', '3 Years', '3 years warranty', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Applicable PBG to be Submitted
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@pbg_id, 'NA', 'Not Applicable', 'PBG not required', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@pbg_id, 'BANK_GUARANTEE', 'Bank Guarantee', 'Bank guarantee required', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@pbg_id, 'SECURITY_DEPOSIT', 'Security Deposit', 'Security deposit required', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@pbg_id, 'PERFORMANCE_BOND', 'Performance Bond', 'Performance bond required', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Tender Request LOVs
-- ============================================

-- INCO Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@inco_terms_id, 'FOB', 'FOB (Free on Board)', 'Free on Board', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@inco_terms_id, 'CIF', 'CIF (Cost, Insurance and Freight)', 'Cost, Insurance and Freight', 2, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@inco_terms_id, 'EXW', 'EXW (Ex Works)', 'Ex Works', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@inco_terms_id, 'DDP', 'DDP (Delivered Duty Paid)', 'Delivered Duty Paid', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@inco_terms_id, 'CFR', 'CFR (Cost and Freight)', 'Cost and Freight', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@inco_terms_id, 'DAP', 'DAP (Delivered at Place)', 'Delivered at Place', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- Payment Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_terms_id, '100_ADVANCE', '100% Advance', '100% payment in advance', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_terms_id, '50_50', '50% Advance, 50% on Delivery', '50% advance, 50% on delivery', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_terms_id, 'NET_30', 'Net 30 Days', 'Payment within 30 days', 3, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_terms_id, 'NET_45', 'Net 45 Days', 'Payment within 45 days', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_terms_id, 'ON_DELIVERY', 'On Delivery', 'Payment on delivery', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_terms_id, 'QUARTERLY_AMC', 'Quarterly in advance on submission of invoice (in case of AMCs)', 'Quarterly payment for AMC', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE
    lov_display_value = VALUES(lov_display_value),
    updated_date = NOW();

-- ============================================
-- Verification Queries
-- ============================================

-- Verify Form Count
SELECT COUNT(*) as form_count FROM form_master WHERE is_active = true;
-- Expected: 9

-- Verify Designator Count
SELECT COUNT(*) as designator_count FROM designator_master WHERE is_active = true;
-- Expected: 25

-- Verify LOV Count
SELECT COUNT(*) as lov_count FROM lov_master WHERE is_active = true;
-- Expected: ~100+ values

-- Verify LOVs per form
SELECT
    fm.form_name,
    COUNT(DISTINCT dm.designator_id) as designator_count,
    COUNT(lm.lov_id) as lov_count
FROM form_master fm
LEFT JOIN designator_master dm ON fm.form_id = dm.form_id AND dm.is_active = true
LEFT JOIN lov_master lm ON dm.designator_id = lm.designator_id AND lm.is_active = true
WHERE fm.is_active = true
GROUP BY fm.form_id, fm.form_name
ORDER BY fm.form_id;

-- ============================================
-- SUCCESS! All LOV data seeded
-- ============================================
-- Forms: 9
-- Designators: 25
-- LOV Values: 100+
-- ============================================
