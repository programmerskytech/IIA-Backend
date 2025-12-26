-- =====================================================
-- ADMIN PANEL - FIX BUDGET FOREIGN KEY
-- Version: 1.1
-- Description: Remove foreign key constraint on budget_master.project_code
--              to allow budgets to be created independently of projects
-- =====================================================

-- Drop the foreign key constraint
ALTER TABLE budget_master
DROP FOREIGN KEY fk_budget_project;

-- Keep the index for performance
-- Index already exists: idx_budget_code
