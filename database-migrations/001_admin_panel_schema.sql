-- =====================================================
-- ADMIN PANEL - DATABASE MIGRATION SCRIPT
-- Version: 1.0
-- Description: Complete schema changes for Admin Panel
-- =====================================================

-- =====================================================
-- 1. UPDATE EMPLOYEE_DEPARTMENT_MASTER TABLE
-- =====================================================

-- Split name field
ALTER TABLE employee_department_master
ADD COLUMN first_name VARCHAR(100) AFTER employee_id,
ADD COLUMN last_name VARCHAR(100) AFTER first_name;

-- Split address fields
ALTER TABLE employee_department_master
ADD COLUMN street_address VARCHAR(255) AFTER address,
ADD COLUMN city VARCHAR(100) AFTER street_address,
ADD COLUMN state VARCHAR(100) AFTER city,
ADD COLUMN zip_code VARCHAR(20) AFTER state;

-- Add new employment fields
ALTER TABLE employee_department_master
ADD COLUMN date_of_birth DATE AFTER email_address,
ADD COLUMN manager VARCHAR(100) AFTER designation,
ADD COLUMN employment_type VARCHAR(50) AFTER manager,
ADD COLUMN hire_date DATE AFTER employment_type;

-- Add optional resignation date
ALTER TABLE employee_department_master
ADD COLUMN end_date DATE COMMENT 'Resignation/Termination Date' AFTER hire_date;

-- Update existing data (migrate employeeName to firstName)
UPDATE employee_department_master
SET first_name = SUBSTRING_INDEX(employee_name, ' ', 1),
    last_name = SUBSTRING_INDEX(employee_name, ' ', -1)
WHERE employee_name IS NOT NULL;

-- =====================================================
-- 2. UPDATE PROJECT_MASTER TABLE
-- =====================================================

ALTER TABLE project_master
ADD COLUMN status VARCHAR(50) DEFAULT 'Active'
    COMMENT 'Active, Completed, Closed' AFTER end_date,
ADD COLUMN category VARCHAR(100) AFTER budget_type;

-- Create index for status filtering
CREATE INDEX idx_project_status ON project_master(status);

-- =====================================================
-- 3. CREATE BUDGET_MASTER TABLE
-- =====================================================

CREATE TABLE budget_master (
    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    budget_code VARCHAR(50) NOT NULL UNIQUE,
    budget_name VARCHAR(200) NOT NULL,
    category VARCHAR(100),

    -- Financial fields
    allocated_amount DECIMAL(15,2) NOT NULL DEFAULT 0,
    on_hold_amount DECIMAL(15,2) DEFAULT 0 COMMENT 'Amount reserved by POs',
    spent_amount DECIMAL(15,2) DEFAULT 0 COMMENT 'Amount deducted by GRNs',
    remaining_amount DECIMAL(15,2) GENERATED ALWAYS AS
        (allocated_amount - on_hold_amount - spent_amount) STORED,

    -- Period
    fiscal_year VARCHAR(10) NOT NULL,
    start_date DATE,
    end_date DATE,

    -- Status
    status VARCHAR(50) DEFAULT 'Active'
        COMMENT 'Active, Closed, Exhausted',

    -- Optional project link
    project_code VARCHAR(50),
    department_name VARCHAR(100),

    -- Audit fields
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign keys
    CONSTRAINT fk_budget_project
        FOREIGN KEY (project_code) REFERENCES project_master(project_code)
        ON DELETE SET NULL,

    -- Indexes
    INDEX idx_budget_code (budget_code),
    INDEX idx_budget_status (status),
    INDEX idx_budget_fiscal_year (fiscal_year),
    INDEX idx_budget_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 4. CREATE BUDGET_CATEGORY_MASTER TABLE
-- =====================================================

CREATE TABLE budget_category_master (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    display_order INT DEFAULT 0,
    created_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_category_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default categories
INSERT INTO budget_category_master (category_name, display_order) VALUES
('IT Infrastructure', 1),
('Software Development', 2),
('Marketing', 3),
('Operations', 4),
('Training & Development', 5),
('Office Supplies', 6),
('Capital Expenditure', 7),
('Operational Expenditure', 8);

-- =====================================================
-- 5. CREATE LIST OF VALUES TABLES
-- =====================================================

-- Form Master (represents application pages/modules)
CREATE TABLE form_master (
    form_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_name VARCHAR(100) NOT NULL UNIQUE
        COMMENT 'e.g., Indent, PurchaseOrder, Employee, Project, Budget',
    form_display_name VARCHAR(200) NOT NULL,
    form_description TEXT,
    module_name VARCHAR(100) COMMENT 'Procurement, Inventory, Admin, etc.',
    is_active BOOLEAN DEFAULT TRUE,
    display_order INT DEFAULT 0,
    created_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_form_active (is_active),
    INDEX idx_form_module (module_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Designator Master (represents dropdown fields within forms)
CREATE TABLE designator_master (
    designator_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT NOT NULL,
    designator_name VARCHAR(100) NOT NULL
        COMMENT 'e.g., status, category, priority',
    designator_display_name VARCHAR(200) NOT NULL,
    designator_description TEXT,
    data_type VARCHAR(50) DEFAULT 'STRING'
        COMMENT 'STRING, NUMBER, DATE, BOOLEAN',
    is_active BOOLEAN DEFAULT TRUE,
    display_order INT DEFAULT 0,
    created_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_designator_form
        FOREIGN KEY (form_id) REFERENCES form_master(form_id)
        ON DELETE CASCADE,

    UNIQUE KEY uk_form_designator (form_id, designator_name),
    INDEX idx_designator_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- List of Values Master (stores actual dropdown values)
CREATE TABLE lov_master (
    lov_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    designator_id BIGINT NOT NULL,
    lov_value VARCHAR(200) NOT NULL,
    lov_display_value VARCHAR(200) NOT NULL,
    lov_description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_default BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,

    -- Optional attributes
    color_code VARCHAR(20) COMMENT 'For UI display (e.g., #28a745 for Active)',
    icon_name VARCHAR(50) COMMENT 'Icon identifier',

    -- Hierarchical support
    parent_lov_id BIGINT COMMENT 'For hierarchical LOVs',

    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_lov_designator
        FOREIGN KEY (designator_id) REFERENCES designator_master(designator_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_lov_parent
        FOREIGN KEY (parent_lov_id) REFERENCES lov_master(lov_id)
        ON DELETE SET NULL,

    UNIQUE KEY uk_designator_value (designator_id, lov_value),
    INDEX idx_lov_active (is_active),
    INDEX idx_lov_parent (parent_lov_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 6. CREATE WORKFLOW BRANCH TABLES
-- =====================================================

-- Workflow Branch Master (represents conditional branches in workflows)
CREATE TABLE workflow_branch_master (
    branch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    workflow_id INT NOT NULL,
    branch_code VARCHAR(50) NOT NULL,
    branch_name VARCHAR(200) NOT NULL,
    branch_description TEXT,

    -- Condition configuration (JSON for flexibility)
    condition_type VARCHAR(50)
        COMMENT 'CATEGORY, LOCATION, AMOUNT, CUSTOM',
    condition_config JSON
        COMMENT 'Stores condition rules as JSON',

    is_active BOOLEAN DEFAULT TRUE,
    display_order INT DEFAULT 0,
    created_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_branch_workflow
        FOREIGN KEY (workflow_id) REFERENCES WORKFLOW_MASTER(WORKFLOWID)
        ON DELETE CASCADE,

    UNIQUE KEY uk_workflow_branch_code (workflow_id, branch_code),
    INDEX idx_branch_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 7. CREATE APPROVER MASTER TABLE
-- =====================================================

CREATE TABLE approver_master (
    approver_id BIGINT AUTO_INCREMENT PRIMARY KEY,

    -- Auto-generated code: W{workflow_id}-B{branch_id}-{sequence}
    approver_code VARCHAR(50) NOT NULL UNIQUE,

    -- Workflow and Branch
    workflow_id INT NOT NULL,
    branch_id BIGINT NOT NULL,

    -- Role-based approver (not individual employee)
    role_id INT NOT NULL,
    role_name VARCHAR(100) NOT NULL,

    -- Approval hierarchy
    approval_level INT NOT NULL DEFAULT 1
        COMMENT 'Level 1, Level 2, Level 3, etc.',
    approval_sequence INT NOT NULL DEFAULT 1
        COMMENT 'Order within the same level',

    -- Approval logic
    is_parallel_approval BOOLEAN DEFAULT FALSE
        COMMENT 'If true, any one approver at this level can approve (OR logic)',
    is_mandatory BOOLEAN DEFAULT TRUE
        COMMENT 'If false, this approval step can be skipped',

    -- Status
    status VARCHAR(50) DEFAULT 'Active'
        COMMENT 'Active, Inactive',

    -- Audit
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Foreign Keys
    CONSTRAINT fk_approver_workflow
        FOREIGN KEY (workflow_id) REFERENCES WORKFLOW_MASTER(WORKFLOWID)
        ON DELETE CASCADE,

    CONSTRAINT fk_approver_branch
        FOREIGN KEY (branch_id) REFERENCES workflow_branch_master(branch_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_approver_role
        FOREIGN KEY (role_id) REFERENCES ROLE_MASTER(ROLEID)
        ON DELETE RESTRICT,

    -- Indexes
    INDEX idx_approver_workflow (workflow_id),
    INDEX idx_approver_branch (branch_id),
    INDEX idx_approver_role (role_id),
    INDEX idx_approver_status (status),
    INDEX idx_approver_level_seq (approval_level, approval_sequence)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- 8. ADD PAYMENT VOUCHER WORKFLOW
-- =====================================================

-- Add to workflow master
INSERT INTO WORKFLOW_MASTER (WORKFLOWNAME, CREATEDBY, CREATEDDATE)
VALUES ('Payment Voucher Workflow', 'SYSTEM', NOW());

-- Get the workflow ID (will be used for transitions)
SET @pv_workflow_id = LAST_INSERT_ID();

-- =====================================================
-- 9. SEED DATA FOR FORMS AND DESIGNATORS
-- =====================================================

-- Insert Forms
INSERT INTO form_master (form_name, form_display_name, module_name, display_order) VALUES
('IndentCreation', 'Indent/Requisition', 'Procurement', 1),
('PurchaseOrder', 'Purchase Order', 'Procurement', 2),
('ServiceOrder', 'Service Order', 'Procurement', 3),
('WorkOrder', 'Work Order', 'Procurement', 4),
('TenderRequest', 'Tender Request', 'Procurement', 5),
('PaymentVoucher', 'Payment Voucher', 'Finance', 6),
('Employee', 'Employee Master', 'Admin', 7),
('User', 'User Master', 'Admin', 8),
('Project', 'Project Master', 'Admin', 9),
('Budget', 'Budget Master', 'Admin', 10),
('Material', 'Material Master', 'Master Data', 11),
('Vendor', 'Vendor Master', 'Master Data', 12);

-- Insert Designators for common status fields
INSERT INTO designator_master (form_id, designator_name, designator_display_name, display_order)
SELECT form_id, 'status', 'Status', 1 FROM form_master WHERE form_name IN ('Employee', 'Project', 'Budget');

-- Insert Status LOVs for Employee
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Active', 'Active', '#28a745', 1
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Employee');

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Inactive', 'Inactive', '#dc3545', 2
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Employee');

-- Insert Status LOVs for Project
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Active', 'Active', '#28a745', 1
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Project');

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Completed', 'Completed', '#007bff', 2
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Project');

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Closed', 'Closed', '#6c757d', 3
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Project');

-- Insert Status LOVs for Budget
INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Active', 'Active', '#28a745', 1
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Budget');

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Closed', 'Closed', '#6c757d', 2
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Budget');

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, color_code, display_order)
SELECT designator_id, 'Exhausted', 'Exhausted', '#ffc107', 3
FROM designator_master WHERE designator_name = 'status'
AND form_id = (SELECT form_id FROM form_master WHERE form_name = 'Budget');

-- Insert Employment Type designator and LOVs
INSERT INTO designator_master (form_id, designator_name, designator_display_name, display_order)
SELECT form_id, 'employmentType', 'Employment Type', 2
FROM form_master WHERE form_name = 'Employee';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, display_order)
SELECT designator_id, 'Full-time', 'Full-time', 1
FROM designator_master WHERE designator_name = 'employmentType';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, display_order)
SELECT designator_id, 'Part-time', 'Part-time', 2
FROM designator_master WHERE designator_name = 'employmentType';

INSERT INTO lov_master (designator_id, lov_value, lov_display_value, display_order)
SELECT designator_id, 'Contract', 'Contract', 3
FROM designator_master WHERE designator_name = 'employmentType';

-- =====================================================
-- 10. CREATE DEFAULT WORKFLOW BRANCHES
-- =====================================================

-- Get Indent Workflow ID
SET @indent_workflow_id = (SELECT WORKFLOWID FROM WORKFLOW_MASTER WHERE WORKFLOWNAME = 'IndentWorkflow');

-- Insert default branches for Indent Workflow
INSERT INTO workflow_branch_master (workflow_id, branch_code, branch_name, condition_type, condition_config, display_order)
VALUES
(@indent_workflow_id, 'COMPUTER', 'Computer Category', 'CATEGORY',
 JSON_OBJECT('field', 'materialCategoryType', 'operator', 'equals', 'value', 'Computer'), 1),
(@indent_workflow_id, 'NON_COMPUTER', 'Non-Computer Category', 'CATEGORY',
 JSON_OBJECT('field', 'materialCategoryType', 'operator', 'equals', 'value', 'Non-Computer'), 2),
(@indent_workflow_id, 'BANGALORE', 'Bangalore Location', 'LOCATION',
 JSON_OBJECT('field', 'consignesLocation', 'operator', 'contains', 'value', 'Bangalore'), 3),
(@indent_workflow_id, 'NON_BANGALORE', 'Non-Bangalore Location', 'LOCATION',
 JSON_OBJECT('field', 'consignesLocation', 'operator', 'not_contains', 'value', 'Bangalore'), 4);

-- =====================================================
-- 11. CREATE AUDIT LOG TABLE (Optional but recommended)
-- =====================================================

CREATE TABLE admin_audit_log (
    audit_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_type VARCHAR(100) NOT NULL COMMENT 'Employee, User, Project, Budget, etc.',
    entity_id VARCHAR(100) NOT NULL,
    action VARCHAR(50) NOT NULL COMMENT 'CREATE, UPDATE, DELETE, ACTIVATE, DEACTIVATE',
    old_value JSON,
    new_value JSON,
    changed_by VARCHAR(100) NOT NULL,
    changed_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(50),
    user_agent TEXT,

    INDEX idx_audit_entity (entity_type, entity_id),
    INDEX idx_audit_date (changed_date),
    INDEX idx_audit_user (changed_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =====================================================
-- MIGRATION COMPLETE
-- =====================================================

-- Verify table creation
SELECT
    'Migration completed successfully. Tables created:' as message
UNION ALL
SELECT CONCAT('  - ', TABLE_NAME)
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = DATABASE()
AND TABLE_NAME IN (
    'budget_master',
    'budget_category_master',
    'form_master',
    'designator_master',
    'lov_master',
    'workflow_branch_master',
    'approver_master',
    'admin_audit_log'
);
