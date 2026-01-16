-- Add missing columns to indent_creation table for type tracking
-- These columns are used by branch workflow system

ALTER TABLE indent_creation
ADD COLUMN IF NOT EXISTS indent_type VARCHAR(50) DEFAULT 'material',
ADD COLUMN IF NOT EXISTS material_category_type VARCHAR(100);

-- Add missing columns for workflow tracking (if they don't exist)
ALTER TABLE indent_creation
ADD COLUMN IF NOT EXISTS is_editable BOOLEAN DEFAULT TRUE,
ADD COLUMN IF NOT EXISTS is_locked_for_tender BOOLEAN DEFAULT FALSE,
ADD COLUMN IF NOT EXISTS locked_reason VARCHAR(500),
ADD COLUMN IF NOT EXISTS version INT DEFAULT 1,
ADD COLUMN IF NOT EXISTS parent_indent_id VARCHAR(255),
ADD COLUMN IF NOT EXISTS current_status VARCHAR(50) DEFAULT 'DRAFT',
ADD COLUMN IF NOT EXISTS current_stage VARCHAR(50) DEFAULT 'INDENT_CREATION',
ADD COLUMN IF NOT EXISTS approval_level INT DEFAULT 0;

-- Add index on indent_type for performance
CREATE INDEX IF NOT EXISTS idx_indent_type ON indent_creation(indent_type);
CREATE INDEX IF NOT EXISTS idx_material_category_type ON indent_creation(material_category_type);
CREATE INDEX IF NOT EXISTS idx_current_status ON indent_creation(current_status);
