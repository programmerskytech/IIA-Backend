-- =====================================================
-- Complete LOV Seed Data for 11 Required Forms
-- Purpose: Populate all dropdown values as per requirements
-- Date: 2025-12-26
-- =====================================================

-- This script seeds LOVs for exactly these 11 forms with their specific designators:
-- 1. Indent Creation → Consignee Location
-- 2. Purchase Order → Delivery Period, Warranty, Applicable PBG to be Submitted
-- 3. Tender Request → INCO Terms, Payment Terms
-- 4. Budget Master → Status
-- 5. Project Master → Status, Budget Type
-- 6. Asset Master → Locator
-- 7. Contingency Purchase → GST(%), Payment To
-- 8. Employee Master → Department, Designation, Location
-- 9. Job Master → Job Category, Job SubCategory, UOM, Currency
-- 10. Material Master → Category, SubCategory, UOM, Currency
-- 11. Vendor Master → Primary Business

-- =====================================================
-- STEP 1: Create/Update Forms in form_master
-- =====================================================

INSERT INTO form_master (form_name, form_display_name, form_description, module_name, is_active, display_order, created_by, updated_by)
VALUES
-- Procurement Module
('IndentCreation', 'Indent Creation', 'Indent/Requisition creation and management', 'Procurement', true, 1, 'SYSTEM', 'SYSTEM'),
('PurchaseOrder', 'Purchase Order', 'Purchase order management', 'Procurement', true, 2, 'SYSTEM', 'SYSTEM'),
('TenderRequest', 'Tender Request', 'Tender request management', 'Procurement', true, 3, 'SYSTEM', 'SYSTEM'),
('ContingencyPurchase', 'Contingency Purchase', 'Contingency purchase management', 'Procurement', true, 4, 'SYSTEM', 'SYSTEM'),

-- Admin Module
('BudgetMaster', 'Budget Master', 'Budget master data management', 'Admin', true, 5, 'SYSTEM', 'SYSTEM'),
('ProjectMaster', 'Project Master', 'Project master data management', 'Admin', true, 6, 'SYSTEM', 'SYSTEM'),
('EmployeeMaster', 'Employee Master', 'Employee master data management', 'Admin', true, 7, 'SYSTEM', 'SYSTEM'),

-- Inventory Module
('AssetMaster', 'Asset Master', 'Asset inventory management', 'Inventory', true, 8, 'SYSTEM', 'SYSTEM'),

-- Master Data Module
('JobMaster', 'Job Master', 'Job/Service master data', 'MasterData', true, 9, 'SYSTEM', 'SYSTEM'),
('MaterialMaster', 'Material Master', 'Material master data', 'MasterData', true, 10, 'SYSTEM', 'SYSTEM'),
('VendorMaster', 'Vendor Master', 'Vendor master data', 'MasterData', true, 11, 'SYSTEM', 'SYSTEM')
ON CONFLICT (form_name) DO UPDATE SET
    form_display_name = EXCLUDED.form_display_name,
    form_description = EXCLUDED.form_description,
    module_name = EXCLUDED.module_name,
    is_active = EXCLUDED.is_active,
    display_order = EXCLUDED.display_order,
    updated_by = EXCLUDED.updated_by,
    updated_date = CURRENT_TIMESTAMP;

-- =====================================================
-- STEP 2: Create Designators in designator_master
-- =====================================================

-- 1. INDENT CREATION: Consignee Location
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'consigneeLocation', 'Consignee Location', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'IndentCreation'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 2. PURCHASE ORDER: Delivery Period, Warranty, Applicable PBG to be Submitted
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'deliveryPeriod', 'Delivery Period', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'warranty', 'Warranty', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'applicablePbgToBeSubmitted', 'Applicable PBG to be Submitted', 'STRING', true, 3, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 3. TENDER REQUEST: INCO Terms, Payment Terms
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'incoTerms', 'INCO Terms', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'paymentTerms', 'Payment Terms', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 4. BUDGET MASTER: Status
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'status', 'Status', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'BudgetMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 5. PROJECT MASTER: Status, Budget Type
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'status', 'Status', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ProjectMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'budgetType', 'Budget Type', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ProjectMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 6. ASSET MASTER: Locator
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'locator', 'Locator', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'AssetMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 7. CONTINGENCY PURCHASE: GST(%), Payment To
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'gstPercentage', 'GST (%)', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'paymentTo', 'Payment To', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 8. EMPLOYEE MASTER: Department, Designation, Location
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'department', 'Department', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'designation', 'Designation', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'location', 'Location', 'STRING', true, 3, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 9. JOB MASTER: Job Category, Job SubCategory, UOM, Currency
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'jobCategory', 'Job Category', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'jobSubCategory', 'Job SubCategory', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'uom', 'UOM', 'STRING', true, 3, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'currency', 'Currency', 'STRING', true, 4, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 10. MATERIAL MASTER: Category, SubCategory, UOM, Currency
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'category', 'Category', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'subCategory', 'SubCategory', 'STRING', true, 2, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'uom', 'UOM', 'STRING', true, 3, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'currency', 'Currency', 'STRING', true, 4, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- 11. VENDOR MASTER: Primary Business
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT fm.form_id, 'primaryBusiness', 'Primary Business', 'STRING', true, 1, 'SYSTEM', 'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'VendorMaster'
ON CONFLICT (form_id, designator_name) DO NOTHING;

-- =====================================================
-- STEP 3: Seed Sample LOV Values
-- =====================================================

-- 1. INDENT CREATION: Consignee Location
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MUMBAI', 'Mumbai', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'DELHI', 'Delhi', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'BANGALORE', 'Bangalore', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CHENNAI', 'Chennai', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 2. PURCHASE ORDER: Delivery Period
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '7_DAYS', '7 Days', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '15_DAYS', '15 Days', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '30_DAYS', '30 Days', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '60_DAYS', '60 Days', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '90_DAYS', '90 Days', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 2. PURCHASE ORDER: Warranty
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '6_MONTHS', '6 Months', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '1_YEAR', '1 Year', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '2_YEARS', '2 Years', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '3_YEARS', '3 Years', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '5_YEARS', '5 Years', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 2. PURCHASE ORDER: Applicable PBG to be Submitted
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'YES', 'Yes', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'NO', 'No', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 3. TENDER REQUEST: INCO Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EXW', 'EXW - Ex Works', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'FOB', 'FOB - Free On Board', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CIF', 'CIF - Cost, Insurance and Freight', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'DDP', 'DDP - Delivered Duty Paid', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 3. TENDER REQUEST: Payment Terms
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ADVANCE_100', '100% Advance', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ADVANCE_50_DELIVERY_50', '50% Advance + 50% on Delivery', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'NET_30', 'Net 30 Days', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'NET_60', 'Net 60 Days', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 4. BUDGET MASTER: Status
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'DRAFT', 'Draft', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'BudgetMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'APPROVED', 'Approved', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'BudgetMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ACTIVE', 'Active', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'BudgetMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CLOSED', 'Closed', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'BudgetMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 5. PROJECT MASTER: Status
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'PLANNING', 'Planning', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'IN_PROGRESS', 'In Progress', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ON_HOLD', 'On Hold', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'COMPLETED', 'Completed', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CANCELLED', 'Cancelled', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'status'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 5. PROJECT MASTER: Budget Type
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CAPEX', 'CAPEX - Capital Expenditure', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'budgetType'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'OPEX', 'OPEX - Operational Expenditure', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'budgetType'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MIXED', 'Mixed Budget', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ProjectMaster' AND dm.designator_name = 'budgetType'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 6. ASSET MASTER: Locator
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'WAREHOUSE_A', 'Warehouse A', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'WAREHOUSE_B', 'Warehouse B', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'OFFICE_FLOOR_1', 'Office - Floor 1', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'OFFICE_FLOOR_2', 'Office - Floor 2', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 7. CONTINGENCY PURCHASE: GST(%)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '0', '0%', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '5', '5%', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '12', '12%', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '18', '18%', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, '28', '28%', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 7. CONTINGENCY PURCHASE: Payment To
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'VENDOR', 'Vendor', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EMPLOYEE', 'Employee', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CONTRACTOR', 'Contractor', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 8. EMPLOYEE MASTER: Department
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'IT', 'Information Technology', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'department'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'HR', 'Human Resources', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'department'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'FINANCE', 'Finance', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'department'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'PROCUREMENT', 'Procurement', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'department'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'OPERATIONS', 'Operations', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'department'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 8. EMPLOYEE MASTER: Designation
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MANAGER', 'Manager', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'designation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'SENIOR_MANAGER', 'Senior Manager', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'designation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ASSISTANT_MANAGER', 'Assistant Manager', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'designation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EXECUTIVE', 'Executive', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'designation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'SENIOR_EXECUTIVE', 'Senior Executive', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'designation'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 8. EMPLOYEE MASTER: Location
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MUMBAI', 'Mumbai', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'location'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'DELHI', 'Delhi', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'location'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'BANGALORE', 'Bangalore', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'location'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CHENNAI', 'Chennai', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'location'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'PUNE', 'Pune', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeMaster' AND dm.designator_name = 'location'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 9. JOB MASTER: Job Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'INSTALLATION', 'Installation Services', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MAINTENANCE', 'Maintenance Services', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'REPAIR', 'Repair Services', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CONSULTING', 'Consulting Services', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 9. JOB MASTER: Job SubCategory (sample)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ELECTRICAL', 'Electrical Work', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'PLUMBING', 'Plumbing Work', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'HVAC', 'HVAC Services', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 9 & 10. UOM (Unit of Measurement) - Shared by Job and Material Master
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EA', 'Each (EA)', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'KG', 'Kilogram (KG)', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'LITRE', 'Litre (L)', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'METER', 'Meter (M)', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'HOUR', 'Hour (HR)', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 9 & 10. Currency - Shared by Job and Material Master
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'INR', 'Indian Rupee (₹)', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'USD', 'US Dollar ($)', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EUR', 'Euro (€)', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'GBP', 'British Pound (£)', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 10. MATERIAL MASTER: Category
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'RAW_MATERIAL', 'Raw Materials', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'FINISHED_GOODS', 'Finished Goods', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CONSUMABLES', 'Consumables', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'SPARES', 'Spare Parts', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 10. MATERIAL MASTER: SubCategory (sample)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'ELECTRONICS', 'Electronics', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MECHANICAL', 'Mechanical', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CHEMICALS', 'Chemicals', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subCategory'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- Material Master: UOM (same as Job Master)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EA', 'Each (EA)', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'KG', 'Kilogram (KG)', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'LITRE', 'Litre (L)', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'METER', 'Meter (M)', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- Material Master: Currency (same as Job Master)
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'INR', 'Indian Rupee (₹)', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'USD', 'US Dollar ($)', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'EUR', 'Euro (€)', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'GBP', 'British Pound (£)', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- 11. VENDOR MASTER: Primary Business
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'MANUFACTURING', 'Manufacturing', true, 1, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'TRADING', 'Trading', true, 2, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'SERVICES', 'Services', true, 3, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'CONSULTING', 'Consulting', true, 4, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, is_active, display_order, created_by, updated_by)
SELECT dm.designator_id, 'DISTRIBUTOR', 'Distributor', true, 5, 'SYSTEM', 'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT (designator_id, lov_value) DO NOTHING;

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Verify all forms were created
SELECT form_id, form_name, form_display_name, module_name
FROM form_master
WHERE form_name IN (
    'IndentCreation', 'PurchaseOrder', 'TenderRequest', 'BudgetMaster',
    'ProjectMaster', 'AssetMaster', 'ContingencyPurchase', 'EmployeeMaster',
    'JobMaster', 'MaterialMaster', 'VendorMaster'
)
ORDER BY display_order;

-- Verify all designators were created
SELECT fm.form_name, dm.designator_name, dm.designator_display_name
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name IN (
    'IndentCreation', 'PurchaseOrder', 'TenderRequest', 'BudgetMaster',
    'ProjectMaster', 'AssetMaster', 'ContingencyPurchase', 'EmployeeMaster',
    'JobMaster', 'MaterialMaster', 'VendorMaster'
)
ORDER BY fm.form_name, dm.display_order;

-- Verify LOV counts by form
SELECT
    fm.form_name,
    dm.designator_name,
    COUNT(lm.lov_id) as lov_count
FROM form_master fm
JOIN designator_master dm ON fm.form_id = dm.form_id
LEFT JOIN lov_master lm ON dm.designator_id = lm.designator_id
WHERE fm.form_name IN (
    'IndentCreation', 'PurchaseOrder', 'TenderRequest', 'BudgetMaster',
    'ProjectMaster', 'AssetMaster', 'ContingencyPurchase', 'EmployeeMaster',
    'JobMaster', 'MaterialMaster', 'VendorMaster'
)
GROUP BY fm.form_name, dm.designator_name
ORDER BY fm.form_name, dm.designator_name;
