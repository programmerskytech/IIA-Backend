-- Database Migration Script for Indent Bug Fixes
-- Date: 2025-12-15
-- Description: Add new columns to indent_creation table for bug fixes

-- Bug Fix 1 & 2: Add columns for edit control and tender locking
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS is_editable BOOLEAN DEFAULT TRUE;
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS is_locked_for_tender BOOLEAN DEFAULT FALSE;
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS locked_reason VARCHAR(500);

-- Bug Fix 3: Add columns for version tracking
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS version INTEGER DEFAULT 1;
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS parent_indent_id VARCHAR(50);

-- Bug Fix 4: Add columns for enhanced status tracking
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS current_status VARCHAR(50) DEFAULT 'DRAFT';
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS current_stage VARCHAR(100) DEFAULT 'INDENT_CREATION';
ALTER TABLE indent_creation ADD COLUMN IF NOT EXISTS approval_level INTEGER DEFAULT 0;

-- Add comments for documentation
COMMENT ON COLUMN indent_creation.is_editable IS 'Controls whether indent can be edited. Set to false after submission, true when sent back for revision';
COMMENT ON COLUMN indent_creation.is_locked_for_tender IS 'Locks indent for editing when tender is created';
COMMENT ON COLUMN indent_creation.locked_reason IS 'Reason why indent is locked';
COMMENT ON COLUMN indent_creation.version IS 'Version number of indent, incremented on each update';
COMMENT ON COLUMN indent_creation.parent_indent_id IS 'Reference to parent indent if this is a revised version';
COMMENT ON COLUMN indent_creation.current_status IS 'Current status: DRAFT, IN_APPROVAL, APPROVED, CHANGE_REQUESTED, TENDER_CREATED, etc.';
COMMENT ON COLUMN indent_creation.current_stage IS 'Current workflow stage: INDENT_CREATION, INDENT_REVISION, INDENT_APPROVAL_LEVEL_1, INDENT_APPROVED, TENDER_GENERATION, etc.';
COMMENT ON COLUMN indent_creation.approval_level IS 'Current approval level in workflow (0 = not submitted, 1+ = approval level)';

-- Update existing records with default values
UPDATE indent_creation
SET
    is_editable = CASE
        WHEN cancel_status = TRUE THEN FALSE
        ELSE TRUE
    END,
    is_locked_for_tender = FALSE,
    version = 1,
    current_status = CASE
        WHEN cancel_status = TRUE THEN 'CANCELLED'
        ELSE 'DRAFT'
    END,
    current_stage = CASE
        WHEN cancel_status = TRUE THEN 'CANCELLED'
        ELSE 'INDENT_CREATION'
    END,
    approval_level = 0
WHERE version IS NULL;

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_indent_current_status ON indent_creation(current_status);
CREATE INDEX IF NOT EXISTS idx_indent_current_stage ON indent_creation(current_stage);
CREATE INDEX IF NOT EXISTS idx_indent_is_editable ON indent_creation(is_editable);
CREATE INDEX IF NOT EXISTS idx_indent_is_locked_for_tender ON indent_creation(is_locked_for_tender);
CREATE INDEX IF NOT EXISTS idx_indent_version ON indent_creation(version);
CREATE INDEX IF NOT EXISTS idx_indent_approval_level ON indent_creation(approval_level);

-- Verification queries
-- SELECT indent_id, current_status, current_stage, is_editable, is_locked_for_tender, version, approval_level FROM indent_creation LIMIT 10;
