-- Add missing columns to indent_creation table for type tracking
-- These columns are used by branch workflow system
-- MySQL version

-- INSTRUCTIONS:
-- Run each ALTER TABLE statement separately
-- If you get "Duplicate column name" error, that column already exists - skip it and continue

-- Add indent_type column
ALTER TABLE indent_creation
ADD COLUMN indent_type VARCHAR(50) DEFAULT 'material';

-- Add material_category_type column
ALTER TABLE indent_creation
ADD COLUMN material_category_type VARCHAR(100);

-- Add workflow tracking columns
ALTER TABLE indent_creation
ADD COLUMN is_editable BOOLEAN DEFAULT TRUE;

ALTER TABLE indent_creation
ADD COLUMN is_locked_for_tender BOOLEAN DEFAULT FALSE;

ALTER TABLE indent_creation
ADD COLUMN locked_reason VARCHAR(500);

ALTER TABLE indent_creation
ADD COLUMN version INT DEFAULT 1;

ALTER TABLE indent_creation
ADD COLUMN parent_indent_id VARCHAR(255);

ALTER TABLE indent_creation
ADD COLUMN current_status VARCHAR(50) DEFAULT 'DRAFT';

ALTER TABLE indent_creation
ADD COLUMN current_stage VARCHAR(50) DEFAULT 'INDENT_CREATION';

ALTER TABLE indent_creation
ADD COLUMN approval_level INT DEFAULT 0;

-- Add rate_contract_job_codes column if missing
ALTER TABLE indent_creation
ADD COLUMN rate_contract_job_codes VARCHAR(2000);

-- Add indexes for performance (run these at the end)
CREATE INDEX idx_indent_type ON indent_creation(indent_type);
CREATE INDEX idx_material_category_type ON indent_creation(material_category_type);
CREATE INDEX idx_current_status ON indent_creation(current_status);
