-- =====================================================
-- ADMIN PANEL - SEED WORKFLOW BRANCHES
-- Version: 1.2
-- Description: Create default branches for all workflows
-- =====================================================

-- =====================================================
-- 1. INDENT WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(1, 'INDENT-DEFAULT', 'Default Branch', 'Default approval path for all indents', 'DEFAULT', NULL, 1, true),
(1, 'INDENT-HIGH-VALUE', 'High Value Branch', 'For indents with total amount above 100000', 'AMOUNT_BASED', '{"minAmount": 100000}', 2, true),
(1, 'INDENT-URGENT', 'Urgent Branch', 'For urgent/emergency indents', 'PRIORITY_BASED', '{"priority": "URGENT"}', 3, true);

-- =====================================================
-- 2. CONTINGENCY PURCHASE WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(2, 'CP-DEFAULT', 'Default Branch', 'Default approval path for contingency purchases', 'DEFAULT', NULL, 1, true),
(2, 'CP-HIGH-VALUE', 'High Value Branch', 'For CP above 50000', 'AMOUNT_BASED', '{"minAmount": 50000}', 2, true);

-- =====================================================
-- 3. PURCHASE ORDER WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(3, 'PO-DEFAULT', 'Default Branch', 'Default approval path for purchase orders', 'DEFAULT', NULL, 1, true),
(3, 'PO-HIGH-VALUE', 'High Value Branch', 'For PO above 200000', 'AMOUNT_BASED', '{"minAmount": 200000}', 2, true),
(3, 'PO-CAPITAL', 'Capital PO Branch', 'For capital equipment purchases', 'TYPE_BASED', '{"type": "CAPITAL"}', 3, true);

-- =====================================================
-- 4. SERVICE ORDER WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(4, 'SO-DEFAULT', 'Default Branch', 'Default approval path for service orders', 'DEFAULT', NULL, 1, true),
(4, 'SO-HIGH-VALUE', 'High Value Branch', 'For SO above 100000', 'AMOUNT_BASED', '{"minAmount": 100000}', 2, true);

-- =====================================================
-- 5. WORK ORDER WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(5, 'WO-DEFAULT', 'Default Branch', 'Default approval path for work orders', 'DEFAULT', NULL, 1, true),
(5, 'WO-CONSTRUCTION', 'Construction Branch', 'For construction/infrastructure work orders', 'TYPE_BASED', '{"type": "CONSTRUCTION"}', 2, true);

-- =====================================================
-- 6. TENDER APPROVER WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(6, 'TENDER-APPR-DEFAULT', 'Default Branch', 'Default approval path for tender approvals', 'DEFAULT', NULL, 1, true),
(6, 'TENDER-APPR-HIGH-VALUE', 'High Value Branch', 'For tenders above 500000', 'AMOUNT_BASED', '{"minAmount": 500000}', 2, true);

-- =====================================================
-- 7. TENDER EVALUATOR WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(7, 'TENDER-EVAL-DEFAULT', 'Default Branch', 'Default evaluation path for tenders', 'DEFAULT', NULL, 1, true),
(7, 'TENDER-EVAL-TECHNICAL', 'Technical Evaluation Branch', 'For technical evaluation phase', 'PHASE_BASED', '{"phase": "TECHNICAL"}', 2, true),
(7, 'TENDER-EVAL-FINANCIAL', 'Financial Evaluation Branch', 'For financial evaluation phase', 'PHASE_BASED', '{"phase": "FINANCIAL"}', 3, true);

-- =====================================================
-- 8. PAYMENT VOUCHER WORKFLOW BRANCHES
-- =====================================================

INSERT INTO workflow_branch_master
(workflow_id, branch_code, branch_name, branch_description, condition_type, condition_config, display_order, is_active)
VALUES
(8, 'PV-DEFAULT', 'Default Branch', 'Default approval path for payment vouchers', 'DEFAULT', NULL, 1, true),
(8, 'PV-HIGH-VALUE', 'High Value Branch', 'For payments above 50000', 'AMOUNT_BASED', '{"minAmount": 50000}', 2, true),
(8, 'PV-ADVANCE', 'Advance Payment Branch', 'For advance payment vouchers', 'TYPE_BASED', '{"type": "ADVANCE"}', 3, true);

-- =====================================================
-- SUMMARY
-- =====================================================
-- Total Branches Created: 26 branches across 8 workflows
-- Each workflow has at least one DEFAULT branch
-- Additional conditional branches based on amount, type, or phase
