-- ============================================================================
-- Tender Module Schema Updates - TC_40 to TC_52 (MySQL Version)
-- Date: January 5, 2026
-- Description: Adds versioning, pre-bid meeting, and locking fields to tender_request table
-- ============================================================================

-- TC_44: Tender Versioning
ALTER TABLE tender_request
ADD COLUMN tender_version INT DEFAULT 1;

-- TC_46: Update Reason Tracking
ALTER TABLE tender_request
ADD COLUMN update_reason VARCHAR(1000) NULL;

-- TC_47: Pre-bid Meeting Recording
ALTER TABLE tender_request
ADD COLUMN pre_bid_meeting_status VARCHAR(50) DEFAULT 'NOT_CONDUCTED';

ALTER TABLE tender_request
ADD COLUMN pre_bid_meeting_discussion TEXT NULL;

ALTER TABLE tender_request
ADD COLUMN pre_bid_meeting_date DATE NULL;

-- TC_48: Tender Lock Mechanism (after PO creation)
ALTER TABLE tender_request
ADD COLUMN is_locked BOOLEAN DEFAULT FALSE;

ALTER TABLE tender_request
ADD COLUMN locked_reason VARCHAR(500) NULL;

ALTER TABLE tender_request
ADD COLUMN locked_for_po VARCHAR(50) NULL;

ALTER TABLE tender_request
ADD COLUMN locked_date DATETIME NULL;

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
-- Add column comments for documentation (MySQL 5.5+)
-- ============================================================================

ALTER TABLE tender_request
MODIFY COLUMN tender_version INT DEFAULT 1
COMMENT 'TC_44: Auto-incremented version number, starts at 1';

ALTER TABLE tender_request
MODIFY COLUMN update_reason VARCHAR(1000) NULL
COMMENT 'TC_46: Reason provided by user when updating tender';

ALTER TABLE tender_request
MODIFY COLUMN pre_bid_meeting_status VARCHAR(50) DEFAULT 'NOT_CONDUCTED'
COMMENT 'TC_47: Status of pre-bid meeting - NOT_CONDUCTED, SCHEDULED, or CONDUCTED';

ALTER TABLE tender_request
MODIFY COLUMN pre_bid_meeting_discussion TEXT NULL
COMMENT 'TC_47: Discussion points from pre-bid meeting';

ALTER TABLE tender_request
MODIFY COLUMN pre_bid_meeting_date DATE NULL
COMMENT 'TC_47: Date when pre-bid meeting was/will be held';

ALTER TABLE tender_request
MODIFY COLUMN is_locked BOOLEAN DEFAULT FALSE
COMMENT 'TC_48: TRUE when PO is created, prevents further tender updates';

ALTER TABLE tender_request
MODIFY COLUMN locked_reason VARCHAR(500) NULL
COMMENT 'TC_48: Reason why tender is locked (e.g., PO created)';

ALTER TABLE tender_request
MODIFY COLUMN locked_for_po VARCHAR(50) NULL
COMMENT 'TC_48: PO ID that caused the tender to be locked';

ALTER TABLE tender_request
MODIFY COLUMN locked_date DATETIME NULL
COMMENT 'TC_48: Timestamp when tender was locked';

-- ============================================================================
-- Create indexes for performance optimization
-- ============================================================================

-- Index for searching locked tenders
CREATE INDEX idx_tender_is_locked
ON tender_request(is_locked);

-- Index for searching by pre-bid meeting status
CREATE INDEX idx_tender_prebid_status
ON tender_request(pre_bid_meeting_status);

-- Index for finding tenders by version
CREATE INDEX idx_tender_version
ON tender_request(tender_version);

-- ============================================================================
-- Verification Queries (Run these to verify the migration)
-- ============================================================================

-- Check if all columns were added successfully
-- SELECT COLUMN_NAME, DATA_TYPE, COLUMN_DEFAULT, COLUMN_COMMENT
-- FROM INFORMATION_SCHEMA.COLUMNS
-- WHERE TABLE_SCHEMA = DATABASE()
-- AND TABLE_NAME = 'tender_request'
-- AND COLUMN_NAME IN ('tender_version', 'update_reason', 'pre_bid_meeting_status',
--                     'pre_bid_meeting_discussion', 'pre_bid_meeting_date',
--                     'is_locked', 'locked_reason', 'locked_for_po', 'locked_date')
-- ORDER BY COLUMN_NAME;

-- Count records with default values
-- SELECT
--     COUNT(*) as total_tenders,
--     COUNT(CASE WHEN tender_version = 1 THEN 1 END) as with_version_1,
--     COUNT(CASE WHEN is_locked = 0 THEN 1 END) as unlocked_tenders,
--     COUNT(CASE WHEN pre_bid_meeting_status = 'NOT_CONDUCTED' THEN 1 END) as no_prebid_meeting
-- FROM tender_request;

-- ============================================================================
-- ROLLBACK SCRIPT (Use only if you need to revert changes)
-- ============================================================================

-- WARNING: Running this will delete the columns and all data in them!
-- Uncomment only if you need to rollback:

-- ALTER TABLE tender_request DROP COLUMN tender_version;
-- ALTER TABLE tender_request DROP COLUMN update_reason;
-- ALTER TABLE tender_request DROP COLUMN pre_bid_meeting_status;
-- ALTER TABLE tender_request DROP COLUMN pre_bid_meeting_discussion;
-- ALTER TABLE tender_request DROP COLUMN pre_bid_meeting_date;
-- ALTER TABLE tender_request DROP COLUMN is_locked;
-- ALTER TABLE tender_request DROP COLUMN locked_reason;
-- ALTER TABLE tender_request DROP COLUMN locked_for_po;
-- ALTER TABLE tender_request DROP COLUMN locked_date;
-- DROP INDEX idx_tender_is_locked ON tender_request;
-- DROP INDEX idx_tender_prebid_status ON tender_request;
-- DROP INDEX idx_tender_version ON tender_request;

-- ============================================================================
-- END OF MIGRATION SCRIPT
-- ============================================================================
