-- =====================================================
-- LOV Seed Data Migration Script
-- Purpose: Populate all dropdown values for forms
-- Date: 2025-12-23
-- =====================================================

-- This script seeds List of Values (LOV) for all forms mentioned in the requirements:
-- 1. Asset Master (Locator)
-- 2. Contingency Purchase (GST%, Payment To)
-- 3. Indent Creation (Consignee Location)
-- 4. Employee Registration (Department, Designation, Location)
-- 5. Job Master (Job Category, Job Subcategory, UOM, Currency)
-- 6. Material Master (Category, Subcategory, UOM, Currency)
-- 7. Vendor Master (Primary Business)
-- 8. Purchase Order (Delivery Period, Warranty, Applicable PBG to be Submitted)
-- 9. Tender (INCO Terms, Payment Terms)

-- =====================================================
-- STEP 1: Create Forms in form_master
-- =====================================================

INSERT INTO form_master (form_name, form_display_name, form_description, module_name, is_active, display_order, created_by, updated_by)
VALUES
-- Inventory Module
('AssetMaster', 'Asset Master', 'Asset inventory management', 'Inventory', true, 1, 'SYSTEM', 'SYSTEM'),

-- Procurement Module
('ContingencyPurchase', 'Contingency Purchase', 'Contingency purchase management', 'Procurement', true, 2, 'SYSTEM', 'SYSTEM'),
('IndentCreation', 'Indent Creation', 'Indent creation and management', 'Procurement', true, 3, 'SYSTEM', 'SYSTEM'),
('PurchaseOrder', 'Purchase Order', 'Purchase order management', 'Procurement', true, 4, 'SYSTEM', 'SYSTEM'),
('TenderRequest', 'Tender Request', 'Tender request management', 'Procurement', true, 5, 'SYSTEM', 'SYSTEM'),

-- Master Data
('EmployeeRegistration', 'Employee Registration', 'Employee master data management', 'HumanResources', true, 6, 'SYSTEM', 'SYSTEM'),
('JobMaster', 'Job Master', 'Job/Service master data', 'MasterData', true, 7, 'SYSTEM', 'SYSTEM'),
('MaterialMaster', 'Material Master', 'Material master data', 'MasterData', true, 8, 'SYSTEM', 'SYSTEM'),
('VendorMaster', 'Vendor Master', 'Vendor master data', 'MasterData', true, 9, 'SYSTEM', 'SYSTEM')
ON CONFLICT (form_name) DO NOTHING;

-- =====================================================
-- STEP 2: Create Designators in designator_master
-- =====================================================

-- Get form IDs (you'll need to adjust these based on actual IDs)
-- For AssetMaster
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'locator',
    'Locator',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'AssetMaster'
ON CONFLICT DO NOTHING;

-- For ContingencyPurchase
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'gstPercentage',
    'GST (%)',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'paymentTo',
    'Payment To',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'budgetCode',
    'Budget Code',
    'STRING',
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'materialCategory',
    'Material Category',
    'STRING',
    true,
    4,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'materialSubCategory',
    'Material Sub Category',
    'STRING',
    true,
    5,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'countryOfOrigin',
    'Country of Origin',
    'STRING',
    true,
    6,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'ContingencyPurchase'
ON CONFLICT DO NOTHING;

-- For IndentCreation
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'consigneeLocation',
    'Consignee Location',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'IndentCreation'
ON CONFLICT DO NOTHING;

-- For EmployeeRegistration
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'department',
    'Department',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeRegistration'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'designation',
    'Designation',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeRegistration'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'location',
    'Location',
    'STRING',
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'EmployeeRegistration'
ON CONFLICT DO NOTHING;

-- For JobMaster
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'jobCategory',
    'Job Category',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'jobSubcategory',
    'Job Subcategory',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'uom',
    'UOM (Unit of Measure)',
    'STRING',
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'currency',
    'Currency',
    'STRING',
    true,
    4,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'JobMaster'
ON CONFLICT DO NOTHING;

-- For MaterialMaster
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'category',
    'Category',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'subcategory',
    'Subcategory',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'uom',
    'UOM (Unit of Measure)',
    'STRING',
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'currency',
    'Currency',
    'STRING',
    true,
    4,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'MaterialMaster'
ON CONFLICT DO NOTHING;

-- For VendorMaster
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'primaryBusiness',
    'Primary Business',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'VendorMaster'
ON CONFLICT DO NOTHING;

-- For PurchaseOrder
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'deliveryPeriod',
    'Delivery Period',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'warranty',
    'Warranty',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'applicablePbgToBeSubmitted',
    'Applicable PBG to be Submitted',
    'STRING',
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'PurchaseOrder'
ON CONFLICT DO NOTHING;

-- For TenderRequest
INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'incoTerms',
    'INCO Terms',
    'STRING',
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest'
ON CONFLICT DO NOTHING;

INSERT INTO designator_master (form_id, designator_name, designator_display_name, data_type, is_active, display_order, created_by, updated_by)
SELECT
    fm.form_id,
    'paymentTerms',
    'Payment Terms',
    'STRING',
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM form_master fm WHERE fm.form_name = 'TenderRequest'
ON CONFLICT DO NOTHING;

-- =====================================================
-- STEP 3: Insert LOV Values
-- =====================================================

-- ============ ASSET MASTER - Locator ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'BANGALORE',
    'Bangalore',
    'Bangalore Location',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DELHI',
    'Delhi',
    'Delhi Location',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MUMBAI',
    'Mumbai',
    'Mumbai Location',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'KOLKATA',
    'Kolkata',
    'Kolkata Location',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'AssetMaster' AND dm.designator_name = 'locator'
ON CONFLICT DO NOTHING;

-- ============ CONTINGENCY PURCHASE - GST Percentage ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '0',
    '0%',
    'No GST',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '5',
    '5%',
    '5% GST',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '12',
    '12%',
    '12% GST',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '18',
    '18%',
    '18% GST',
    true,
    true,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '28',
    '28%',
    '28% GST',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'gstPercentage'
ON CONFLICT DO NOTHING;

-- ============ CONTINGENCY PURCHASE - Payment To ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'VENDOR',
    'Vendor',
    'Payment to Vendor',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CONTRACTOR',
    'Contractor',
    'Payment to Contractor',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'SERVICE_PROVIDER',
    'Service Provider',
    'Payment to Service Provider',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'paymentTo'
ON CONFLICT DO NOTHING;

-- ============ CONTINGENCY PURCHASE - Material Category ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'COMPUTER',
    'Computer',
    'Computer Equipment',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'materialCategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NON_COMPUTER',
    'Non-Computer',
    'Non-Computer Equipment',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'materialCategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'OFFICE_SUPPLIES',
    'Office Supplies',
    'General Office Supplies',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'materialCategory'
ON CONFLICT DO NOTHING;

-- ============ CONTINGENCY PURCHASE - Country of Origin ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'INDIA',
    'India',
    'Made in India',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'countryOfOrigin'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'USA',
    'United States',
    'Made in USA',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'countryOfOrigin'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CHINA',
    'China',
    'Made in China',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'countryOfOrigin'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'JAPAN',
    'Japan',
    'Made in Japan',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'countryOfOrigin'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'GERMANY',
    'Germany',
    'Made in Germany',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'ContingencyPurchase' AND dm.designator_name = 'countryOfOrigin'
ON CONFLICT DO NOTHING;

-- ============ INDENT CREATION - Consignee Location ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'BANGALORE',
    'Bangalore',
    'Bangalore Location',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DELHI',
    'Delhi',
    'Delhi Location',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MUMBAI',
    'Mumbai',
    'Mumbai Location',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'IndentCreation' AND dm.designator_name = 'consigneeLocation'
ON CONFLICT DO NOTHING;

-- ============ EMPLOYEE REGISTRATION - Department ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ADMINISTRATION',
    'Administration',
    'Administration Department',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'department'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'FINANCE',
    'Finance',
    'Finance Department',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'department'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'IT',
    'IT',
    'Information Technology Department',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'department'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'HR',
    'HR',
    'Human Resources Department',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'department'
ON CONFLICT DO NOTHING;

-- ============ EMPLOYEE REGISTRATION - Designation ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MANAGER',
    'Manager',
    'Manager Level',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'designation'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'SENIOR_ENGINEER',
    'Senior Engineer',
    'Senior Engineer Level',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'designation'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ENGINEER',
    'Engineer',
    'Engineer Level',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'designation'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ASSISTANT',
    'Assistant',
    'Assistant Level',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'designation'
ON CONFLICT DO NOTHING;

-- ============ EMPLOYEE REGISTRATION - Location ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'BANGALORE',
    'Bangalore',
    'Bangalore Office',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'location'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DELHI',
    'Delhi',
    'Delhi Office',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'location'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MUMBAI',
    'Mumbai',
    'Mumbai Office',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'EmployeeRegistration' AND dm.designator_name = 'location'
ON CONFLICT DO NOTHING;

-- ============ JOB MASTER - Job Category ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MAINTENANCE',
    'Maintenance',
    'Maintenance Services',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CONSULTING',
    'Consulting',
    'Consulting Services',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'INSTALLATION',
    'Installation',
    'Installation Services',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'SUPPORT',
    'Support',
    'Support Services',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobCategory'
ON CONFLICT DO NOTHING;

-- ============ JOB MASTER - Job Subcategory ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ELECTRICAL',
    'Electrical',
    'Electrical Work',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'PLUMBING',
    'Plumbing',
    'Plumbing Work',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CARPENTRY',
    'Carpentry',
    'Carpentry Work',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'IT_SUPPORT',
    'IT Support',
    'IT Support Services',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'jobSubcategory'
ON CONFLICT DO NOTHING;

-- ============ JOB MASTER - UOM ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'HOUR',
    'Hour',
    'Per Hour',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DAY',
    'Day',
    'Per Day',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MONTH',
    'Month',
    'Per Month',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'JOB',
    'Job',
    'Per Job/Project',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

-- ============ JOB MASTER - Currency ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'INR',
    'INR (₹)',
    'Indian Rupee',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'USD',
    'USD ($)',
    'US Dollar',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'EUR',
    'EUR (€)',
    'Euro',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'GBP',
    'GBP (£)',
    'British Pound',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'JobMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

-- ============ MATERIAL MASTER - Category ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'COMPUTER',
    'Computer',
    'Computer Equipment',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NON_COMPUTER',
    'Non-Computer',
    'Non-Computer Equipment',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'OFFICE_SUPPLIES',
    'Office Supplies',
    'Office Supplies',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'FURNITURE',
    'Furniture',
    'Office Furniture',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category'
ON CONFLICT DO NOTHING;

-- ============ MATERIAL MASTER - Subcategory ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'LAPTOP',
    'Laptop',
    'Laptop Computers',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DESKTOP',
    'Desktop',
    'Desktop Computers',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'PRINTER',
    'Printer',
    'Printing Devices',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subcategory'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'STATIONERY',
    'Stationery',
    'Stationery Items',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'subcategory'
ON CONFLICT DO NOTHING;

-- ============ MATERIAL MASTER - UOM ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NOS',
    'Nos',
    'Numbers',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'KG',
    'Kg',
    'Kilogram',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'LITER',
    'Liter',
    'Liter',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'METER',
    'Meter',
    'Meter',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'BOX',
    'Box',
    'Box/Carton',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'uom'
ON CONFLICT DO NOTHING;

-- ============ MATERIAL MASTER - Currency ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'INR',
    'INR (₹)',
    'Indian Rupee',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'USD',
    'USD ($)',
    'US Dollar',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'EUR',
    'EUR (€)',
    'Euro',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'GBP',
    'GBP (£)',
    'British Pound',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'currency'
ON CONFLICT DO NOTHING;

-- ============ VENDOR MASTER - Primary Business ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'MANUFACTURING',
    'Manufacturing',
    'Manufacturing Business',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'TRADING',
    'Trading',
    'Trading Business',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'SERVICE_PROVIDER',
    'Service Provider',
    'Service Provider Business',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DISTRIBUTOR',
    'Distributor',
    'Distributor Business',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'VendorMaster' AND dm.designator_name = 'primaryBusiness'
ON CONFLICT DO NOTHING;

-- ============ PURCHASE ORDER - Delivery Period ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '7_DAYS',
    '7 Days',
    'Within 7 Days',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '15_DAYS',
    '15 Days',
    'Within 15 Days',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '30_DAYS',
    '30 Days',
    'Within 30 Days',
    true,
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '60_DAYS',
    '60 Days',
    'Within 60 Days',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '90_DAYS',
    '90 Days',
    'Within 90 Days',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'deliveryPeriod'
ON CONFLICT DO NOTHING;

-- ============ PURCHASE ORDER - Warranty ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NO_WARRANTY',
    'No Warranty',
    'No Warranty Period',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '6_MONTHS',
    '6 Months',
    '6 Months Warranty',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '1_YEAR',
    '1 Year',
    '1 Year Warranty',
    true,
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '2_YEARS',
    '2 Years',
    '2 Years Warranty',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    '3_YEARS',
    '3 Years',
    '3 Years Warranty',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'warranty'
ON CONFLICT DO NOTHING;

-- ============ PURCHASE ORDER - Applicable PBG to be Submitted ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NOT_APPLICABLE',
    'Not Applicable',
    'No PBG Required',
    true,
    true,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'BANK_GUARANTEE',
    'Bank Guarantee',
    'Bank Guarantee Required',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'SECURITY_DEPOSIT',
    'Security Deposit',
    'Security Deposit Required',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'PERFORMANCE_BOND',
    'Performance Bond',
    'Performance Bond Required',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'PurchaseOrder' AND dm.designator_name = 'applicablePbgToBeSubmitted'
ON CONFLICT DO NOTHING;

-- ============ TENDER REQUEST - INCO Terms ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'FOB',
    'FOB (Free on Board)',
    'Free on Board',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CIF',
    'CIF (Cost, Insurance and Freight)',
    'Cost, Insurance and Freight',
    true,
    true,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'EXW',
    'EXW (Ex Works)',
    'Ex Works',
    true,
    false,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'DDP',
    'DDP (Delivered Duty Paid)',
    'Delivered Duty Paid',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'CFR',
    'CFR (Cost and Freight)',
    'Cost and Freight',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'incoTerms'
ON CONFLICT DO NOTHING;

-- ============ TENDER REQUEST - Payment Terms ============
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ADVANCE_100',
    '100% Advance',
    '100% Advance Payment',
    true,
    false,
    1,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ADVANCE_50_DELIVERY_50',
    '50% Advance, 50% on Delivery',
    '50% Advance, 50% on Delivery',
    true,
    false,
    2,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NET_30',
    'Net 30 Days',
    'Payment within 30 days of delivery',
    true,
    true,
    3,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'NET_45',
    'Net 45 Days',
    'Payment within 45 days of delivery',
    true,
    false,
    4,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT DO NOTHING;

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, lov_description, is_active, is_default, display_order, created_by, updated_by)
SELECT
    dm.designator_id,
    'ON_DELIVERY',
    'On Delivery',
    'Payment on Delivery',
    true,
    false,
    5,
    'SYSTEM',
    'SYSTEM'
FROM designator_master dm
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'TenderRequest' AND dm.designator_name = 'paymentTerms'
ON CONFLICT DO NOTHING;

-- =====================================================
-- Migration Complete
-- =====================================================

-- To verify the data:
-- SELECT fm.form_name, dm.designator_name, COUNT(lm.lov_id) as lov_count
-- FROM form_master fm
-- JOIN designator_master dm ON fm.form_id = dm.form_id
-- LEFT JOIN lov_master lm ON dm.designator_id = lm.designator_id
-- WHERE fm.form_name IN ('AssetMaster', 'ContingencyPurchase', 'IndentCreation', 'EmployeeRegistration',
--                        'JobMaster', 'MaterialMaster', 'VendorMaster', 'PurchaseOrder', 'TenderRequest')
-- GROUP BY fm.form_name, dm.designator_name
-- ORDER BY fm.form_name, dm.designator_name;
