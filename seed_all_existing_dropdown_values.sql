use astrodatabase;

-- ============================================
-- LOV System - COMPLETE Seed Data with ALL Existing Dropdown Values
-- Date: 2024-12-24
-- Purpose: Seed ALL hardcoded dropdown values from frontend forms
-- This ensures BIDIRECTIONAL SYNC between Admin Panel and Forms
-- ============================================

-- This script includes EVERY hardcoded value from the frontend
-- so when you view LOV Management, you see all existing dropdown values

-- ============================================
-- STEP 1: Ensure Forms Exist
-- ============================================

INSERT INTO form_master (form_id, form_name, form_display_name, form_description, is_active, created_date, created_by)
VALUES
(1, 'IndentCreation', 'Indent Creation', 'Indent creation form', true, NOW(), 'SYSTEM'),
(2, 'PurchaseOrder', 'Purchase Order', 'Purchase order form', true, NOW(), 'SYSTEM'),
(3, 'ServiceOrder', 'Service Order', 'Service order form', true, NOW(), 'SYSTEM'),
(4, 'WorkOrder', 'Work Order', 'Work order form', true, NOW(), 'SYSTEM'),
(5, 'TenderRequest', 'Tender Request', 'Tender request form', true, NOW(), 'SYSTEM'),
(6, 'PaymentVoucher', 'Payment Voucher', 'Payment voucher form', true, NOW(), 'SYSTEM'),
(7, 'Employee', 'Employee', 'Employee form', true, NOW(), 'SYSTEM'),
(8, 'User', 'User', 'User form', true, NOW(), 'SYSTEM'),
(9, 'Project', 'Project', 'Project form', true, NOW(), 'SYSTEM'),
(10, 'Budget', 'Budget Master', 'Budget master form', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE
    form_display_name = VALUES(form_display_name);

-- ============================================
-- STEP 2: Ensure Designators Exist
-- ============================================

-- Indent Creation (form_id = 1)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES (1, 'consigneeLocation', 'Consignee Location', 'Consignee location', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Purchase Order (form_id = 2)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(2, 'gstPercentage', 'GST Percentage', 'GST percentage options', true, NOW(), 'SYSTEM'),
(2, 'paymentTo', 'Payment To', 'Payment recipient type', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Service Order (form_id = 3)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES (3, 'consigneeLocation', 'Consignee Location', 'Consignee location for service', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Work Order (form_id = 4)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(4, 'department', 'Department', 'Department', true, NOW(), 'SYSTEM'),
(4, 'designation', 'Designation', 'Designation', true, NOW(), 'SYSTEM'),
(4, 'location', 'Location', 'Location', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Tender Request (form_id = 5)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(5, 'jobCategory', 'Job Category', 'Job category', true, NOW(), 'SYSTEM'),
(5, 'jobSubcategory', 'Job Subcategory', 'Job subcategory', true, NOW(), 'SYSTEM'),
(5, 'uom', 'Unit of Measurement', 'Unit of measurement', true, NOW(), 'SYSTEM'),
(5, 'currency', 'Currency', 'Currency', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Payment Voucher (form_id = 6)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(6, 'category', 'Category', 'Material category', true, NOW(), 'SYSTEM'),
(6, 'subcategory', 'Subcategory', 'Material subcategory', true, NOW(), 'SYSTEM'),
(6, 'uom', 'Unit of Measurement', 'Unit of measurement', true, NOW(), 'SYSTEM'),
(6, 'currency', 'Currency', 'Currency', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Employee (form_id = 7)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES (7, 'primaryBusiness', 'Primary Business', 'Primary business type', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- User (form_id = 8)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(8, 'deliveryPeriod', 'Delivery Period', 'Delivery period options', true, NOW(), 'SYSTEM'),
(8, 'warranty', 'Warranty', 'Warranty period options', true, NOW(), 'SYSTEM'),
(8, 'applicablePbgToBeSubmitted', 'Applicable PBG to be Submitted', 'PBG submission requirements', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Project (form_id = 9)
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES
(9, 'incoTerms', 'INCO Terms', 'International commercial terms', true, NOW(), 'SYSTEM'),
(9, 'paymentTerms', 'Payment Terms', 'Payment terms', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- Budget Master (form_id = 10) - NEW: Budget Status
INSERT INTO designator_master (form_id, designator_name, designator_display_name, designator_description, is_active, created_date, created_by)
VALUES (10, 'status', 'Budget Status', 'Budget status options', true, NOW(), 'SYSTEM')
ON DUPLICATE KEY UPDATE designator_display_name = VALUES(designator_display_name);

-- ============================================
-- STEP 3: Get Designator IDs
-- ============================================

SET @indent_loc_id = (SELECT designator_id FROM designator_master WHERE form_id = 1 AND designator_name = 'consigneeLocation');
SET @po_gst_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'gstPercentage');
SET @po_payment_to_id = (SELECT designator_id FROM designator_master WHERE form_id = 2 AND designator_name = 'paymentTo');
SET @service_loc_id = (SELECT designator_id FROM designator_master WHERE form_id = 3 AND designator_name = 'consigneeLocation');
SET @work_department_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'department');
SET @work_designation_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'designation');
SET @work_location_id = (SELECT designator_id FROM designator_master WHERE form_id = 4 AND designator_name = 'location');
SET @tender_job_category_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'jobCategory');
SET @tender_job_subcategory_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'jobSubcategory');
SET @tender_uom_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'uom');
SET @tender_currency_id = (SELECT designator_id FROM designator_master WHERE form_id = 5 AND designator_name = 'currency');
SET @payment_category_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'category');
SET @payment_subcategory_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'subcategory');
SET @payment_uom_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'uom');
SET @payment_currency_id = (SELECT designator_id FROM designator_master WHERE form_id = 6 AND designator_name = 'currency');
SET @employee_primary_business_id = (SELECT designator_id FROM designator_master WHERE form_id = 7 AND designator_name = 'primaryBusiness');
SET @user_delivery_period_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'deliveryPeriod');
SET @user_warranty_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'warranty');
SET @user_pbg_id = (SELECT designator_id FROM designator_master WHERE form_id = 8 AND designator_name = 'applicablePbgToBeSubmitted');
SET @project_inco_terms_id = (SELECT designator_id FROM designator_master WHERE form_id = 9 AND designator_name = 'incoTerms');
SET @project_payment_terms_id = (SELECT designator_id FROM designator_master WHERE form_id = 9 AND designator_name = 'paymentTerms');
SET @budget_status_id = (SELECT designator_id FROM designator_master WHERE form_id = 10 AND designator_name = 'status');

-- ============================================
-- STEP 4: Insert ALL LOV Values (from frontend)
-- ============================================

-- ==========================================
-- 1. INDENT CREATION - Consignee Location
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@indent_loc_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@indent_loc_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@indent_loc_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@indent_loc_id, 'KOLKATA', 'Kolkata', 'Kolkata office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 2. PURCHASE ORDER - GST Percentage
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@po_gst_id, '0', 'Nil', 'No GST', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@po_gst_id, '5', '5%', '5% GST', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@po_gst_id, '12', '12%', '12% GST', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@po_gst_id, '18', '18%', '18% GST', 4, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@po_gst_id, '28', '28%', '28% GST', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 2. PURCHASE ORDER - Payment To
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@po_payment_to_id, 'vendor', 'Vendor', 'Payment to vendor', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@po_payment_to_id, 'employee', 'Employee', 'Payment to employee', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 3. SERVICE ORDER - Consignee Location
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@service_loc_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@service_loc_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@service_loc_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@service_loc_id, 'KOLKATA', 'Kolkata', 'Kolkata office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 4. WORK ORDER - Department
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@work_department_id, 'ADMINISTRATION', 'Administration', 'Administration department', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_department_id, 'FINANCE', 'Finance', 'Finance department', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_department_id, 'IT', 'Information Technology', 'IT department', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_department_id, 'HR', 'Human Resources', 'HR department', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_department_id, 'OPERATIONS', 'Operations', 'Operations department', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_department_id, 'SALES', 'Sales', 'Sales department', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 4. WORK ORDER - Designation
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@work_designation_id, 'MANAGER', 'Manager', 'Manager level', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_designation_id, 'SENIOR_ENGINEER', 'Senior Engineer', 'Senior engineer level', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_designation_id, 'ENGINEER', 'Engineer', 'Engineer level', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_designation_id, 'ASSISTANT', 'Assistant', 'Assistant level', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_designation_id, 'DIRECTOR', 'Director', 'Director level', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_designation_id, 'VP', 'Vice President', 'VP level', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 4. WORK ORDER - Location
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@work_location_id, 'BANGALORE', 'Bangalore', 'Bangalore office', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_location_id, 'DELHI', 'Delhi', 'Delhi office', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_location_id, 'MUMBAI', 'Mumbai', 'Mumbai office', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@work_location_id, 'KOLKATA', 'Kolkata', 'Kolkata office', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 5. TENDER REQUEST - Job Category
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@tender_job_category_id, 'AMC', 'AMC (Annual Maintenance Contract)', 'Annual Maintenance Contract', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_category_id, 'RATE_CONTRACT', 'Rate Contract', 'Rate contract jobs', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_category_id, 'REPAIR_SERVICE', 'Repair And Service', 'Repair and service', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_category_id, 'INTERNET_SERVICE', 'Internet Service', 'Internet service', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_category_id, 'OTHER_SERVICE', 'Other Service', 'Other services', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 5. TENDER REQUEST - Job Subcategory
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@tender_job_subcategory_id, 'CHEMICALS', 'Chemicals', 'Chemical work', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'COMPUTER_PERIPHERALS', 'Computer & Peripherals', 'Computer and peripherals', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'ELECTRICAL', 'Electrical', 'Electrical work', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'ELECTRONIC_ITEMS', 'Electronic Items', 'Electronic items', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'EQUIPMENT', 'Equipment', 'Equipment work', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'FURNITURE', 'Furniture', 'Furniture work', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'HARDWARE', 'HARDWARE', 'Hardware work', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'MISCELLANEOUS', 'Miscellaneous', 'Miscellaneous work', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'SOFTWARE', 'Software', 'Software work', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'STATIONARY', 'Stationary', 'Stationary items', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_job_subcategory_id, 'VEHICLES', 'Vehicles', 'Vehicle work', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 5. TENDER REQUEST - UOM
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@tender_uom_id, 'HOUR', 'Hour', 'Per hour', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_uom_id, 'DAY', 'Day', 'Per day', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_uom_id, 'MONTH', 'Month', 'Per month', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_uom_id, 'JOB', 'Job', 'Per job', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_uom_id, 'NOS', 'Nos', 'Numbers', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 5. TENDER REQUEST - Currency
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@tender_currency_id, 'INR', 'INR (₹)', 'Indian Rupee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_currency_id, 'USD', 'USD ($)', 'US Dollar', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_currency_id, 'EUR', 'EUR (€)', 'Euro', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@tender_currency_id, 'GBP', 'GBP (£)', 'British Pound', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 6. PAYMENT VOUCHER - Category
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_category_id, 'CAPITAL', 'Capital', 'Capital goods', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_category_id, 'CONSUMABLE', 'Consumable', 'Consumable items', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 6. PAYMENT VOUCHER - Subcategory
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_subcategory_id, 'CHEMICALS', 'Chemicals', 'Chemical materials', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'COMPUTER_PERIPHERALS', 'Computer & Peripherals', 'Computer and peripherals', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'ELECTRICAL', 'Electrical', 'Electrical materials', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'ELECTRONIC_ITEMS', 'Electronic Items', 'Electronic items', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'EQUIPMENT', 'Equipment', 'Equipment', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'FURNITURE', 'Furniture', 'Furniture items', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'HARDWARE', 'HARDWARE', 'Hardware items', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'MISCELLANEOUS', 'Miscellaneous', 'Miscellaneous items', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'SOFTWARE', 'Software', 'Software', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'STATIONARY', 'Stationary', 'Stationary items', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_subcategory_id, 'VEHICLES', 'Vehicles', 'Vehicles', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 6. PAYMENT VOUCHER - UOM
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_uom_id, 'NOS', 'Nos', 'Numbers', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_uom_id, 'KG', 'Kg', 'Kilogram', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_uom_id, 'LITER', 'Liter', 'Liter', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_uom_id, 'METER', 'Meter', 'Meter', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_uom_id, 'BOX', 'Box', 'Box', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 6. PAYMENT VOUCHER - Currency
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@payment_currency_id, 'INR', 'INR (₹)', 'Indian Rupee', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_currency_id, 'USD', 'USD ($)', 'US Dollar', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_currency_id, 'EUR', 'EUR (€)', 'Euro', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@payment_currency_id, 'GBP', 'GBP (£)', 'British Pound', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 7. EMPLOYEE - Primary Business
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@employee_primary_business_id, 'CHEMICALS', 'Chemicals', 'Chemicals supplier', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'COMPUTERS_PERIPHERALS', 'Computers & Peripherals', 'Computer equipment supplier', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'ELECTRICALS', 'Electricals', 'Electrical equipment supplier', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'ELECTRONICS', 'Electronics', 'Electronics supplier', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'OPTICS', 'Optics', 'Optics supplier', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'FABRICATION', 'Fabrication', 'Fabrication services', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'FURNITURE', 'Furniture', 'Furniture supplier', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'HARDWARE', 'Hardware', 'Hardware supplier', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'INSTRUMENT_EQUIPMENT', 'Instrument/ Equipment & Machinery', 'Instrument and equipment supplier', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'SOFTWARE', 'Software', 'Software supplier', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'VEHICLES', 'Vehicles', 'Vehicle supplier', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'STATIONARY', 'Stationary', 'Stationary supplier', 12, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'MISCELLANEOUS', 'Miscellaneous', 'Miscellaneous supplier', 13, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@employee_primary_business_id, 'SERVICES', 'Services', 'Service provider', 14, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 8. USER - Delivery Period
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@user_delivery_period_id, '1', '1 Week', '1 week delivery', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '2', '2 Weeks', '2 weeks delivery', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '3', '3 Weeks', '3 weeks delivery', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '4', '4 Weeks', '4 weeks delivery', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '5', '5 Weeks', '5 weeks delivery', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '6', '6 Weeks', '6 weeks delivery', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '7', '7 Weeks', '7 weeks delivery', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '8', '8 Weeks', '8 weeks delivery', 8, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '9', '9 Weeks', '9 weeks delivery', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '10', '10 Weeks', '10 weeks delivery', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '11', '11 Weeks', '11 weeks delivery', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '12', '12 Weeks', '12 weeks delivery', 12, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '13', '13 Weeks', '13 weeks delivery', 13, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '14', '14 Weeks', '14 weeks delivery', 14, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '15', '15 Weeks', '15 weeks delivery', 15, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '16', '16 Weeks', '16 weeks delivery', 16, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '17', '17 Weeks', '17 weeks delivery', 17, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '18', '18 Weeks', '18 weeks delivery', 18, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '19', '19 Weeks', '19 weeks delivery', 19, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_delivery_period_id, '20', '20 Weeks', '20 weeks delivery', 20, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 8. USER - Warranty
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@user_warranty_id, 'NA', 'NA', 'No warranty', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '1 Year', '1 Year', '1 year warranty', 2, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '2 Years', '2 Years', '2 years warranty', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '3 Years', '3 Years', '3 years warranty', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '4 Years', '4 Years', '4 years warranty', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '5 Years', '5 Years', '5 years warranty', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '6 Years', '6 Years', '6 years warranty', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '7 Years', '7 Years', '7 years warranty', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '8 Years', '8 Years', '8 years warranty', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '9 Years', '9 Years', '9 years warranty', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '10 Years', '10 Years', '10 years warranty', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '11 Years', '11 Years', '11 years warranty', 12, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '12 Years', '12 Years', '12 years warranty', 13, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '13 Years', '13 Years', '13 years warranty', 14, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '14 Years', '14 Years', '14 years warranty', 15, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '15 Years', '15 Years', '15 years warranty', 16, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '16 Years', '16 Years', '16 years warranty', 17, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '17 Years', '17 Years', '17 years warranty', 18, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '18 Years', '18 Years', '18 years warranty', 19, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '19 Years', '19 Years', '19 years warranty', 20, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_warranty_id, '20 Years', '20 Years', '20 years warranty', 21, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 8. USER - Applicable PBG
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@user_pbg_id, '1', '1%', '1% PBG', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '2', '2%', '2% PBG', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '3', '3%', '3% PBG', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '4', '4%', '4% PBG', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '5', '5%', '5% PBG', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '6', '6%', '6% PBG', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '7', '7%', '7% PBG', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '8', '8%', '8% PBG', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '9', '9%', '9% PBG', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '10', '10%', '10% PBG', 10, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '11', '11%', '11% PBG', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '12', '12%', '12% PBG', 12, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '13', '13%', '13% PBG', 13, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '14', '14%', '14% PBG', 14, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '15', '15%', '15% PBG', 15, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '16', '16%', '16% PBG', 16, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '17', '17%', '17% PBG', 17, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '18', '18%', '18% PBG', 18, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '19', '19%', '19% PBG', 19, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, '20', '20%', '20% PBG', 20, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@user_pbg_id, 'NA', 'NA', 'Not applicable', 21, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 9. PROJECT - INCO Terms
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@project_inco_terms_id, 'DAP', 'DAP', 'Delivered At Place', 1, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'EXWORKS', 'EXWORKS', 'Ex Works', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'DDP', 'DDP', 'Delivered Duty Paid', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'FCA', 'FCA', 'Free Carrier', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'FOB', 'FOB', 'Free On Board', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'CIF', 'CIF', 'Cost Insurance and Freight', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'CIP', 'CIP', 'Carriage and Insurance Paid To', 7, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'DPU', 'DPU', 'Delivered at Place Unloaded', 8, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'FAS', 'FAS', 'Free Alongside Ship', 9, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'CFR', 'CFR', 'Cost and Freight', 10, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'FOR', 'FOR', 'Free On Rail', 11, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'CPT', 'CPT', 'Carriage Paid To', 12, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_inco_terms_id, 'NA', 'NA', 'Not applicable', 13, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 9. PROJECT - Payment Terms
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@project_payment_terms_id, '100_30_DAYS', '100% payment within 30 days from the date of acceptance.', '100% payment within 30 days', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@project_payment_terms_id, 'QUARTERLY_AMC', 'Quarterly in advance on submission of invoice (in case of AMCs)', 'Quarterly AMC payment', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ==========================================
-- 10. BUDGET MASTER - Status
-- ==========================================
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, display_order, is_active, is_default, created_date, updated_date, created_by, updated_by)
VALUES
(@budget_status_id, 'DRAFT', 'Draft', 'Budget in draft status', 1, true, true, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_status_id, 'PENDING_APPROVAL', 'Pending Approval', 'Budget pending approval', 2, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_status_id, 'APPROVED', 'Approved', 'Budget approved', 3, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_status_id, 'REJECTED', 'Rejected', 'Budget rejected', 4, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_status_id, 'ACTIVE', 'Active', 'Budget is active', 5, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
(@budget_status_id, 'CLOSED', 'Closed', 'Budget is closed', 6, true, false, NOW(), NOW(), 'SYSTEM', 'SYSTEM')
ON DUPLICATE KEY UPDATE lov_display_value = VALUES(lov_display_value), updated_date = NOW();

-- ============================================
-- VERIFICATION QUERIES
-- ============================================

SELECT 'FORMS COUNT:' as Info, COUNT(*) as Count FROM form_master WHERE is_active = true;
SELECT 'DESIGNATORS COUNT:' as Info, COUNT(*) as Count FROM designator_master WHERE is_active = true;
SELECT 'LOV VALUES COUNT:' as Info, COUNT(*) as Count FROM lov_master WHERE is_active = true;

-- Show LOV count per form
SELECT
    fm.form_name as Form,
    fm.form_display_name as DisplayName,
    COUNT(DISTINCT dm.designator_id) as Designators,
    COUNT(lm.lov_id) as TotalLOVs
FROM form_master fm
LEFT JOIN designator_master dm ON fm.form_id = dm.form_id AND dm.is_active = true
LEFT JOIN lov_master lm ON dm.designator_id = lm.designator_id AND lm.is_active = true
WHERE fm.is_active = true
GROUP BY fm.form_id, fm.form_name, fm.form_display_name
ORDER BY fm.form_id;

-- ============================================
-- SUCCESS!
-- ============================================
-- ✅ 10 Forms created (no duplicates)
-- ✅ Budget Master with Status designator added
-- ✅ Purchase Order has only GST & Payment To
-- ✅ All LOV values seeded correctly
-- ✅ BIDIRECTIONAL SYNC enabled
-- ============================================
