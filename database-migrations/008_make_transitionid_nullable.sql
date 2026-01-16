-- Make transitionId nullable to support branch-based workflow
-- Branch-based workflows don't use the old transition_master system,
-- so transitionId can be NULL when branch_id is set

ALTER TABLE workflow_transition
MODIFY COLUMN transitionId INT NULL;

-- Add comment to explain the change
-- Branch-based workflows use branch_id instead of transitionId
-- Legacy workflows continue to use transitionId
