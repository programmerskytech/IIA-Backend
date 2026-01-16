-- ============================================================================
-- Workflow Branch Integration - Add Branch Tracking to WorkflowTransition
-- Date: January 9, 2026
-- Description: Adds branch-based approval fields to workflow_transition table
-- ============================================================================

USE astrodatabase;

-- ============================================================================
-- Add columns to track branch-based approvals
-- ============================================================================

-- Add branch_id to link to workflow_branch_master
ALTER TABLE workflow_transition
ADD COLUMN BRANCH_ID BIGINT NULL COMMENT 'Links to workflow_branch_master for branch-based workflows';

-- Add approver_id to link to approver_master
ALTER TABLE workflow_transition
ADD COLUMN APPROVER_ID BIGINT NULL COMMENT 'Links to approver_master for current approver';

-- Add approval_level to track which level in the approval hierarchy
ALTER TABLE workflow_transition
ADD COLUMN APPROVAL_LEVEL INT NULL COMMENT 'Current approval level in the branch approval chain';

-- Add approval_sequence to track sequence within the same level
ALTER TABLE workflow_transition
ADD COLUMN APPROVAL_SEQUENCE INT NULL COMMENT 'Approval sequence within the same level';

-- ============================================================================
-- Create indexes for performance
-- ============================================================================

CREATE INDEX idx_workflow_transition_branch_id
ON workflow_transition(BRANCH_ID);

CREATE INDEX idx_workflow_transition_approver_id
ON workflow_transition(APPROVER_ID);

CREATE INDEX idx_workflow_transition_approval_level
ON workflow_transition(APPROVAL_LEVEL, APPROVAL_SEQUENCE);

-- ============================================================================
-- Verification Queries
-- ============================================================================

-- Check if columns were added successfully
-- SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT
-- FROM INFORMATION_SCHEMA.COLUMNS
-- WHERE TABLE_SCHEMA = 'astrodatabase'
-- AND TABLE_NAME = 'workflow_transition'
-- AND COLUMN_NAME IN ('BRANCH_ID', 'APPROVER_ID', 'APPROVAL_LEVEL', 'APPROVAL_SEQUENCE')
-- ORDER BY COLUMN_NAME;

-- ============================================================================
-- Test Data Check (After Implementation)
-- ============================================================================

-- Check which workflows are using branch-based routing
-- SELECT
--     WORKFLOWNAME,
--     COUNT(*) as total_transitions,
--     COUNT(BRANCH_ID) as branch_based_transitions,
--     COUNT(CASE WHEN BRANCH_ID IS NULL THEN 1 END) as legacy_transitions
-- FROM workflow_transition
-- GROUP BY WORKFLOWNAME;

-- View a sample branch-based workflow approval chain
-- SELECT
--     REQUESTID,
--     WORKFLOWNAME,
--     WORKFLOWSEQUENCE,
--     CURRENTROLE,
--     NEXTROLE,
--     BRANCH_ID,
--     APPROVAL_LEVEL,
--     APPROVAL_SEQUENCE,
--     STATUS,
--     NEXTACTION
-- FROM workflow_transition
-- WHERE BRANCH_ID IS NOT NULL
-- ORDER BY REQUESTID, WORKFLOWSEQUENCE;

-- ============================================================================
-- ROLLBACK SCRIPT (Use only if needed to revert changes)
-- ============================================================================

-- WARNING: Running this will delete the columns!
-- Uncomment only if you need to rollback:

-- ALTER TABLE workflow_transition DROP COLUMN BRANCH_ID;
-- ALTER TABLE workflow_transition DROP COLUMN APPROVER_ID;
-- ALTER TABLE workflow_transition DROP COLUMN APPROVAL_LEVEL;
-- ALTER TABLE workflow_transition DROP COLUMN APPROVAL_SEQUENCE;
-- DROP INDEX idx_workflow_transition_branch_id ON workflow_transition;
-- DROP INDEX idx_workflow_transition_approver_id ON workflow_transition;
-- DROP INDEX idx_workflow_transition_approval_level ON workflow_transition;

-- ============================================================================
-- END OF MIGRATION SCRIPT
-- ============================================================================
