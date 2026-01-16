-- ============================================================================
-- Tender Module Schema Updates - TC_40 to TC_52
-- Date: January 5, 2026
-- Description: Adds versioning, pre-bid meeting, and locking fields to tender_request table
-- ============================================================================

-- TC_44: Tender Versioning
ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS tender_version INTEGER DEFAULT 1;

-- TC_46: Update Reason Tracking
ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS update_reason VARCHAR(1000);

-- TC_47: Pre-bid Meeting Recording
ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS pre_bid_meeting_status VARCHAR(50) DEFAULT 'NOT_CONDUCTED';

ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS pre_bid_meeting_discussion TEXT;

ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS pre_bid_meeting_date DATE;

-- TC_48: Tender Lock Mechanism (after PO creation)
ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS is_locked BOOLEAN DEFAULT FALSE;

ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS locked_reason VARCHAR(500);

ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS locked_for_po VARCHAR(50);

ALTER TABLE tender_request
ADD COLUMN IF NOT EXISTS locked_date TIMESTAMP;

-- ============================================================================
-- Update existing records to have default values
-- ============================================================================

-- Set tender_version to 1 for all existing records where it's NULL
UPDATE tender_request
SET tender_version = 1
WHERE tender_version IS NULL;

-- Set pre_bid_meeting_status to 'NOT_CONDUCTED' for all existing records where it's NULL
UPDATE tender_request
SET pre_bid_meeting_status = 'NOT_CONDUCTED'
WHERE pre_bid_meeting_status IS NULL;

-- Set is_locked to FALSE for all existing records where it's NULL
UPDATE tender_request
SET is_locked = FALSE
WHERE is_locked IS NULL;

-- ============================================================================
-- Add comments to columns for documentation
-- ============================================================================

COMMENT ON COLUMN tender_request.tender_version IS 'TC_44: Auto-incremented version number, starts at 1';
COMMENT ON COLUMN tender_request.update_reason IS 'TC_46: Reason provided by user when updating tender';
COMMENT ON COLUMN tender_request.pre_bid_meeting_status IS 'TC_47: Status of pre-bid meeting - NOT_CONDUCTED, SCHEDULED, or CONDUCTED';
COMMENT ON COLUMN tender_request.pre_bid_meeting_discussion IS 'TC_47: Discussion points from pre-bid meeting';
COMMENT ON COLUMN tender_request.pre_bid_meeting_date IS 'TC_47: Date when pre-bid meeting was/will be held';
COMMENT ON COLUMN tender_request.is_locked IS 'TC_48: TRUE when PO is created, prevents further tender updates';
COMMENT ON COLUMN tender_request.locked_reason IS 'TC_48: Reason why tender is locked (e.g., PO created)';
COMMENT ON COLUMN tender_request.locked_for_po IS 'TC_48: PO ID that caused the tender to be locked';
COMMENT ON COLUMN tender_request.locked_date IS 'TC_48: Timestamp when tender was locked';

-- ============================================================================
-- Create indexes for performance optimization
-- ============================================================================

-- Index for searching locked tenders
CREATE INDEX IF NOT EXISTS idx_tender_is_locked
ON tender_request(is_locked)
WHERE is_locked = TRUE;

-- Index for searching by pre-bid meeting status
CREATE INDEX IF NOT EXISTS idx_tender_prebid_status
ON tender_request(pre_bid_meeting_status);

-- Index for finding tenders by version
CREATE INDEX IF NOT EXISTS idx_tender_version
ON tender_request(tender_version);

-- ============================================================================
-- Verification Queries (Run these to verify the migration)
-- ============================================================================

-- Check if all columns were added successfully
-- SELECT column_name, data_type, column_default
-- FROM information_schema.columns
-- WHERE table_name = 'tender_request'
-- AND column_name IN ('tender_version', 'update_reason', 'pre_bid_meeting_status',
--                     'pre_bid_meeting_discussion', 'pre_bid_meeting_date',
--                     'is_locked', 'locked_reason', 'locked_for_po', 'locked_date')
-- ORDER BY column_name;

-- Count records with default values
-- SELECT
--     COUNT(*) as total_tenders,
--     COUNT(CASE WHEN tender_version = 1 THEN 1 END) as with_version_1,
--     COUNT(CASE WHEN is_locked = FALSE THEN 1 END) as unlocked_tenders,
--     COUNT(CASE WHEN pre_bid_meeting_status = 'NOT_CONDUCTED' THEN 1 END) as no_prebid_meeting
-- FROM tender_request;

-- ============================================================================
-- ROLLBACK SCRIPT (Use only if you need to revert changes)
-- ============================================================================

-- WARNING: Running this will delete the columns and all data in them!
-- Uncomment only if you need to rollback:

-- ALTER TABLE tender_request DROP COLUMN IF EXISTS tender_version;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS update_reason;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS pre_bid_meeting_status;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS pre_bid_meeting_discussion;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS pre_bid_meeting_date;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS is_locked;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS locked_reason;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS locked_for_po;
-- ALTER TABLE tender_request DROP COLUMN IF EXISTS locked_date;
-- DROP INDEX IF EXISTS idx_tender_is_locked;
-- DROP INDEX IF EXISTS idx_tender_prebid_status;
-- DROP INDEX IF EXISTS idx_tender_version;

-- ============================================================================
-- END OF MIGRATION SCRIPT
-- ============================================================================
