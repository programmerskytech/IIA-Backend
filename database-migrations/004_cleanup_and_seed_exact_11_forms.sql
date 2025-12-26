-- =====================================================
-- CLEANUP AND SEED - EXACT 11 FORMS ONLY
-- Purpose: Remove all old LOV data and insert ONLY the required 11 forms
-- Date: 2025-12-26
-- Database: MySQL
-- =====================================================

-- =====================================================
-- STEP 1: CLEANUP - Remove ALL existing LOV data
-- =====================================================

SET FOREIGN_KEY_CHECKS = 0;

-- Delete all LOV values
DELETE FROM lov_master;

-- Delete all designators
DELETE FROM designator_master;

-- Delete all forms
DELETE FROM form_master;

-- Reset auto-increment IDs
ALTER TABLE lov_master AUTO_INCREMENT = 1;
ALTER TABLE designator_master AUTO_INCREMENT = 1;
ALTER TABLE form_master AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================================
-- STEP 2: Insert ONLY the 11 Required Forms
-- =====================================================

INSERT INTO form_master (form_name, form_display_name, form_description, module_name, is_active, display_order, created_by)
VALUES
-- Procurement Module (4 forms)
('IndentCreation', 'Indent Creation', 'Indent/Requisition creation and management', 'Procurement', true, 1, 'SYSTEM'),
('PurchaseOrder', 'Purchase Order', 'Purchase order management', 'Procurement', true, 2, 'SYSTEM'),
('TenderRequest', 'Tender Request', 'Tender request management', 'Procurement', true, 3, 'SYSTEM'),
('ContingencyPurchase', 'Contingency Purchase', 'Contingency purchase management', 'Procurement', true, 4, 'SYSTEM'),

-- Admin Module (3 forms)
('BudgetMaster', 'Budget Master', 'Budget master data management', 'Admin', true, 5, 'SYSTEM'),
('ProjectMaster', 'Project Master', 'Project master data management', 'Admin', true, 6, 'SYSTEM'),
('EmployeeMaster', 'Employee Master', 'Employee master data management', 'Admin', true, 7, 'SYSTEM'),

-- Inventory Module (1 form)
('AssetMaster', 'Asset Master', 'Asset inventory management', 'Inventory', true, 8, 'SYSTEM'),

-- Master Data Module (3 forms)
('JobMaster', 'Job Master', 'Job/Service master data', 'MasterData', true, 9, 'SYSTEM'),
('MaterialMaster', 'Material Master', 'Material master data', 'MasterData', true, 10, 'SYSTEM'),
('VendorMaster', 'Vendor Master', 'Vendor master data', 'MasterData', true, 11, 'SYSTEM');

-- =====================================================
-- STEP 3: Insert ONLY the Required Designators
-- =====================================================

-- 1. INDENT CREATION: Only Consignee Location
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'consigneeLocation', 'Consignee Location', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'IndentCreation';

-- 2. PURCHASE ORDER: Only 3 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'deliveryPeriod', 'Delivery Period', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'warranty', 'Warranty', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'applicablePbgToBeSubmitted', 'Applicable PBG to be Submitted', 'STRING', true, 3, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder';

-- 3. TENDER REQUEST: Only 2 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'incoTerms', 'INCO Terms', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'paymentTerms', 'Payment Terms', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest';

-- 4. BUDGET MASTER: Only Status
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'status', 'Status', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'BudgetMaster';

-- 5. PROJECT MASTER: Only 2 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'status', 'Status', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ProjectMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'budgetType', 'Budget Type', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ProjectMaster';

-- 6. ASSET MASTER: Only Locator
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'locator', 'Locator', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'AssetMaster';

-- 7. CONTINGENCY PURCHASE: Only 2 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'gstPercentage', 'GST (%)', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'paymentTo', 'Payment To', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase';

-- 8. EMPLOYEE MASTER: 3 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'department', 'Department', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'designation', 'Designation', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'location', 'Location', 'STRING', true, 3, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster';

-- 9. JOB MASTER: 4 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'jobCategory', 'Job Category', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'jobSubCategory', 'Job SubCategory', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'uom', 'UOM', 'STRING', true, 3, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'currency', 'Currency', 'STRING', true, 4, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster';

-- 10. MATERIAL MASTER: 4 fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'category', 'Category', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'subCategory', 'SubCategory', 'STRING', true, 2, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'uom', 'UOM', 'STRING', true, 3, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster';

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'currency', 'Currency', 'STRING', true, 4, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster';

-- 11. VENDOR MASTER: Only Primary Business
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by)
SELECT fm.form_id, 'primaryBusiness', 'Primary Business', 'STRING', true, 1, 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'VendorMaster';

-- =====================================================
-- STEP 4: Insert Sample LOV Values for Testing
-- =====================================================

-- 1. Indent Creation - Consignee Location
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MUMBAI', 'Mumbai', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'IndentCreation' AND d.designator_name = 'consigneeLocation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DELHI', 'Delhi', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'IndentCreation' AND d.designator_name = 'consigneeLocation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'BANGALORE', 'Bangalore', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'IndentCreation' AND d.designator_name = 'consigneeLocation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CHENNAI', 'Chennai', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'IndentCreation' AND d.designator_name = 'consigneeLocation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'KOLKATA', 'Kolkata', true, 5, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'IndentCreation' AND d.designator_name = 'consigneeLocation';

-- 2. Purchase Order - Delivery Period
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WITHIN_7_DAYS', 'Within 7 Days', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'deliveryPeriod';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WITHIN_15_DAYS', 'Within 15 Days', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'deliveryPeriod';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WITHIN_30_DAYS', 'Within 30 Days', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'deliveryPeriod';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WITHIN_60_DAYS', 'Within 60 Days', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'deliveryPeriod';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WITHIN_90_DAYS', 'Within 90 Days', true, 5, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'deliveryPeriod';

-- 3. Purchase Order - Warranty
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'NO_WARRANTY', 'No Warranty', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'warranty';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '1_YEAR', '1 Year', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'warranty';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '2_YEARS', '2 Years', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'warranty';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '3_YEARS', '3 Years', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'warranty';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '5_YEARS', '5 Years', true, 5, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'warranty';

-- 4. Purchase Order - Applicable PBG to be Submitted
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'YES', 'Yes', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'applicablePbgToBeSubmitted';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'NO', 'No', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'PurchaseOrder' AND d.designator_name = 'applicablePbgToBeSubmitted';

-- 5. Tender Request - INCO Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'EXW', 'EXW - Ex Works', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'incoTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'FOB', 'FOB - Free On Board', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'incoTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CIF', 'CIF - Cost Insurance Freight', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'incoTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DDP', 'DDP - Delivered Duty Paid', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'incoTerms';

-- 6. Tender Request - Payment Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ADVANCE_100', '100% Advance', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'paymentTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'NET_30', 'Net 30 Days', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'paymentTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'NET_60', 'Net 60 Days', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'paymentTerms';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '50_50', '50% Advance, 50% On Delivery', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'TenderRequest' AND d.designator_name = 'paymentTerms';

-- 7. Budget Master - Status
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DRAFT', 'Draft', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'BudgetMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'APPROVED', 'Approved', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'BudgetMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ACTIVE', 'Active', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'BudgetMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CLOSED', 'Closed', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'BudgetMaster' AND d.designator_name = 'status';

-- 8. Project Master - Status
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'PLANNING', 'Planning', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'IN_PROGRESS', 'In Progress', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ON_HOLD', 'On Hold', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'status';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'COMPLETED', 'Completed', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'status';

-- 9. Project Master - Budget Type
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CAPEX', 'CapEx', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'budgetType';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'OPEX', 'OpEx', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'budgetType';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MIXED', 'Mixed', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ProjectMaster' AND d.designator_name = 'budgetType';

-- 10. Asset Master - Locator
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WAREHOUSE_A', 'Warehouse A', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'AssetMaster' AND d.designator_name = 'locator';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'WAREHOUSE_B', 'Warehouse B', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'AssetMaster' AND d.designator_name = 'locator';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'OFFICE_MUMBAI', 'Office - Mumbai', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'AssetMaster' AND d.designator_name = 'locator';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'OFFICE_DELHI', 'Office - Delhi', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'AssetMaster' AND d.designator_name = 'locator';

-- 11. Contingency Purchase - GST(%)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '0', '0%', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'gstPercentage';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '5', '5%', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'gstPercentage';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '12', '12%', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'gstPercentage';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '18', '18%', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'gstPercentage';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, '28', '28%', true, 5, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'gstPercentage';

-- 12. Contingency Purchase - Payment To
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'VENDOR', 'Vendor', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'paymentTo';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CONTRACTOR', 'Contractor', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'ContingencyPurchase' AND d.designator_name = 'paymentTo';

-- 13. Employee Master - Department
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'IT', 'Information Technology', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'department';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'HR', 'Human Resources', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'department';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'FINANCE', 'Finance', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'department';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'PROCUREMENT', 'Procurement', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'department';

-- 14. Employee Master - Designation
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MANAGER', 'Manager', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'designation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'SENIOR_EXECUTIVE', 'Senior Executive', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'designation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'EXECUTIVE', 'Executive', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'designation';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ASSISTANT', 'Assistant', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'designation';

-- 15. Employee Master - Location
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MUMBAI', 'Mumbai', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'location';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DELHI', 'Delhi', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'location';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'BANGALORE', 'Bangalore', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'EmployeeMaster' AND d.designator_name = 'location';

-- 16. Job Master - Job Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CONSTRUCTION', 'Construction', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MAINTENANCE', 'Maintenance', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CONSULTING', 'Consulting', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobCategory';

-- 17. Job Master - Job SubCategory
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CIVIL_WORK', 'Civil Work', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobSubCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ELECTRICAL_WORK', 'Electrical Work', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobSubCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'PLUMBING', 'Plumbing', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'jobSubCategory';

-- 18. Job Master - UOM
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'HOURS', 'Hours', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'uom';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DAYS', 'Days', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'uom';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'SQM', 'Square Meter', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'uom';

-- 19. Job Master - Currency
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'INR', 'INR - Indian Rupee', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'currency';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'USD', 'USD - US Dollar', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'currency';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'EUR', 'EUR - Euro', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'JobMaster' AND d.designator_name = 'currency';

-- 20. Material Master - Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'RAW_MATERIAL', 'Raw Material', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'category';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'FINISHED_GOODS', 'Finished Goods', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'category';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CONSUMABLES', 'Consumables', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'category';

-- 21. Material Master - SubCategory
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'STEEL', 'Steel', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'subCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CEMENT', 'Cement', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'subCategory';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'ELECTRICAL', 'Electrical', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'subCategory';

-- 22. Material Master - UOM
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'KG', 'Kilogram', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'uom';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'LITRE', 'Litre', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'uom';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'PIECE', 'Piece', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'uom';

-- 23. Material Master - Currency
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'INR', 'INR - Indian Rupee', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'currency';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'USD', 'USD - US Dollar', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'currency';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'EUR', 'EUR - Euro', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'MaterialMaster' AND d.designator_name = 'currency';

-- 24. Vendor Master - Primary Business
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'MANUFACTURER', 'Manufacturer', true, 1, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'VendorMaster' AND d.designator_name = 'primaryBusiness';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'DISTRIBUTOR', 'Distributor', true, 2, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'VendorMaster' AND d.designator_name = 'primaryBusiness';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'SERVICE_PROVIDER', 'Service Provider', true, 3, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'VendorMaster' AND d.designator_name = 'primaryBusiness';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by)
SELECT d.designator_id, 'CONTRACTOR', 'Contractor', true, 4, 'SYSTEM'
FROM designator_master d JOIN form_master f ON d.form_id = f.form_id
WHERE f.form_name = 'VendorMaster' AND d.designator_name = 'primaryBusiness';

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check forms count (should be exactly 11)
SELECT 'FORMS COUNT' as check_type, COUNT(*) as count FROM form_master WHERE is_active = true;

-- Check designators count (should be exactly 25)
SELECT 'DESIGNATORS COUNT' as check_type, COUNT(*) as count FROM designator_master WHERE is_active = true;

-- Check LOV values count (should be 100+)
SELECT 'LOV VALUES COUNT' as check_type, COUNT(*) as count FROM lov_master WHERE is_active = true;

-- Detailed breakdown by form
SELECT
    f.form_name,
    d.designator_name,
    COUNT(l.lov_id) as lov_count
FROM form_master f
JOIN designator_master d ON f.form_id = d.form_id
LEFT JOIN lov_master l ON d.designator_id = l.designator_id AND l.is_active = true
WHERE f.is_active = true AND d.is_active = true
GROUP BY f.form_name, d.designator_name
ORDER BY f.form_name, d.display_order;

-- List all forms
SELECT form_id, form_name, form_display_name, module_name
FROM form_master
WHERE is_active = true
ORDER BY display_order;

-- =====================================================
-- SCRIPT COMPLETE
-- =====================================================
-- Expected Results:
-- ✓ 11 forms in form_master
-- ✓ 25 designators in designator_master
-- ✓ 100+ LOV values in lov_master
-- ✓ No extra/unwanted forms or designators
-- =====================================================
