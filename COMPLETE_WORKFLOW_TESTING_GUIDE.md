# Complete Branch-Based Workflow Testing Guide

## Overview
This guide provides a step-by-step testing plan for all workflows in the procurement system with branch-based approval routing.

---

## 1. INDENT WORKFLOW (✅ Working - Already Tested)

### Current Branch Configuration

**Query to check existing branches:**
```sql
SELECT branch_id, branch_code, branch_name, condition_config, display_order, is_active
FROM workflow_branch_master
WHERE workflow_id = 1 AND is_active = 1
ORDER BY display_order;
```

**Query to check approvers:**
```sql
SELECT b.branch_code, a.approval_level, a.approval_sequence, a.role_name, a.approver_code
FROM approver_master a
JOIN workflow_branch_master b ON a.branch_id = b.branch_id
WHERE b.workflow_id = 1 AND b.is_active = 1
ORDER BY b.branch_id, a.approval_level, a.approval_sequence;
```

### Test Cases

#### Test 1: Amount-Based Routing (50k-100k)
**Branch 23: INDENT_AMOUNT_5000**
- Condition: `{"minAmount": 50000, "maxAmount": 100000}`
- Expected Flow:
  1. Indent Creator → Creates indent with amount 68,000
  2. Reporting Officer → Approves
  3. Administrative Officer → Approves (Final)

**How to Test:**
1. Login as Indent Creator (User ID: 95)
2. Create indent: Amount = 68,000, Location = BANGALORE, Category = Computer
3. Login as Reporting Officer → Check queue → Approve
4. Login as Administrative Officer → Check queue → Approve
5. Verify email notifications received

**Expected Result:** ✅ Already working

#### Test 2: Location-Based Routing (BANGALORE)
**Branch 24: BR_IND_LOC_BNG**
- Condition: `{"location": "BANGALORE"}`
- Expected Flow:
  1. Indent Creator → Creates indent
  2. Reporting Officer → Approves
  3. Store Purchase Officer → Approves (Final)

**How to Test:**
1. Create indent: Amount = 30,000 (below 50k), Location = BANGALORE, Category = Computer
2. Verify it routes to: Reporting Officer → Store Purchase Officer

**Query to verify routing:**
```sql
SELECT wt.requestid, wt.branch_id, wt.currentrole, wt.nextrole, wt.status, wt.approval_level, wt.approval_sequence
FROM workflow_transition wt
WHERE wt.requestid = 'IND[NEW_ID]'
ORDER BY wt.workflowtransitionid;
```

---

## 2. TENDER WORKFLOW

### Step 1: Check Existing Branch Configuration

```sql
-- Check if tender branches exist
SELECT branch_id, branch_code, branch_name, condition_config, workflow_id
FROM workflow_branch_master
WHERE workflow_id = 4 AND is_active = 1;

-- Check workflow_id for Tender
SELECT * FROM workflow_master WHERE workflowname LIKE '%Tender%';
```

### Step 2: Create Tender Branches (if not exist)

```sql
-- Branch 1: Tender Amount < 1 Lakh
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_TND_AMT_LOW', 'Tender Amount Below 1L', '{"maxAmount": 100000}', 'AMOUNT', 4, 1, 1, 1, NOW());

-- Branch 2: Tender Amount 1L - 10L
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_TND_AMT_MED', 'Tender Amount 1L-10L', '{"minAmount": 100000, "maxAmount": 1000000}', 'AMOUNT', 4, 2, 1, 1, NOW());

-- Branch 3: Tender Amount > 10L
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_TND_AMT_HIGH', 'Tender Amount Above 10L', '{"minAmount": 1000000}', 'AMOUNT', 4, 3, 1, 1, NOW());
```

### Step 3: Configure Approvers for Each Branch

```sql
-- Get branch IDs
SELECT branch_id, branch_code FROM workflow_branch_master WHERE workflow_id = 4;

-- For Branch: BR_TND_AMT_LOW (< 1L)
-- Approver 1: Tender Creator → Reporting Officer
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES ([BRANCH_ID_LOW], 4, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW());

-- Approver 2: Reporting Officer → Tender Approver
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES ([BRANCH_ID_LOW], 4, 'Tender Approver', 2, 2, 1, 'Active', 1, NOW());

-- For Branch: BR_TND_AMT_MED (1L-10L)
-- Add additional approval layer
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_MED], 4, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_MED], 4, 'Administrative Officer', 2, 2, 1, 'Active', 1, NOW()),
([BRANCH_ID_MED], 4, 'Tender Approver', 3, 3, 1, 'Active', 1, NOW());
```

### Step 4: Test Tender Workflow

**Prerequisites:**
1. Must have an APPROVED indent (IND1148 is approved)
2. Create tender from approved indent

**Test Case:**
1. Login as Tender Creator
2. Navigate to: Create Tender from Indent IND1148
3. Fill tender details:
   - Opening Date: Future date
   - Closing Date: Future date
   - Mode of Procurement: Open Tender
4. Submit tender
5. Check routing based on total indent value

**Expected Flow (if < 1L):**
- Tender Creator → Reporting Officer → Tender Approver

**Verification Queries:**
```sql
-- Check tender creation
SELECT * FROM tender_request WHERE indent_materials LIKE '%IND1148%';

-- Check workflow routing
SELECT wt.requestid, wt.branch_id, wt.currentrole, wt.nextrole, wt.status
FROM workflow_transition wt
WHERE wt.workflowname = 'Tender Workflow'
ORDER BY wt.createddate DESC LIMIT 5;
```

---

## 3. TENDER EVALUATOR WORKFLOW

### Current Setup
This workflow is triggered AFTER Tender Approver approves the tender and vendors submit quotations.

### Branch Configuration

```sql
-- Branch 1: Standard Evaluation (< 5 vendors)
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_EVAL_STD', 'Standard Evaluation', '{"maxVendors": 5}', 'VENDOR_COUNT', 7, 1, 1, 1, NOW());

-- Branch 2: Complex Evaluation (>= 5 vendors)
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_EVAL_COMPLEX', 'Complex Evaluation', '{"minVendors": 5}', 'VENDOR_COUNT', 7, 2, 1, 1, NOW());
```

### Approver Configuration

```sql
-- For Standard Evaluation
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_STD], 7, 'Tender Evaluator', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_STD], 7, 'Purchase Committee Chairman', 2, 2, 1, 'Active', 1, NOW());
```

### Test Case

1. After tender is approved, vendors submit quotations
2. Login as Tender Evaluator
3. Evaluate vendor quotations
4. Submit evaluation
5. Verify routing to Purchase Committee Chairman

---

## 4. PURCHASE ORDER (PO) WORKFLOW

### Branch Configuration

```sql
-- Check PO workflow ID
SELECT * FROM workflow_master WHERE workflowname LIKE '%Purchase%' OR workflowname LIKE '%PO%';

-- Branch 1: PO Amount < 50k
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_PO_LOW', 'PO Below 50K', '{"maxAmount": 50000}', 'AMOUNT', 3, 1, 1, 1, NOW());

-- Branch 2: PO Amount 50k-5L
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_PO_MED', 'PO 50K-5L', '{"minAmount": 50000, "maxAmount": 500000}', 'AMOUNT', 3, 2, 1, 1, NOW());

-- Branch 3: PO Amount > 5L
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_PO_HIGH', 'PO Above 5L', '{"minAmount": 500000}', 'AMOUNT', 3, 3, 1, 1, NOW());
```

### Approver Configuration

```sql
-- For PO < 50K
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_LOW], 3, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_LOW], 3, 'PO Approver', 2, 2, 1, 'Active', 1, NOW());

-- For PO 50K-5L
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_MED], 3, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_MED], 3, 'Administrative Officer', 2, 2, 1, 'Active', 1, NOW()),
([BRANCH_ID_MED], 3, 'PO Approver', 3, 3, 1, 'Active', 1, NOW());
```

### Test Case

1. After tender evaluation, create PO from selected vendor
2. Fill PO details
3. Submit PO
4. Verify routing based on PO amount

---

## 5. CONTINGENCY PURCHASE WORKFLOW

### Branch Configuration

```sql
-- Check CP workflow ID
SELECT * FROM workflow_master WHERE workflowname LIKE '%Contingency%';

-- Branch 1: CP Amount < 25k (Emergency)
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_CP_EMERG', 'CP Emergency Below 25K', '{"maxAmount": 25000}', 'AMOUNT', 2, 1, 1, 1, NOW());

-- Branch 2: CP Amount 25k-1L
INSERT INTO workflow_branch_master (branch_code, branch_name, condition_config, condition_type, workflow_id, display_order, is_active, created_by, created_date)
VALUES ('BR_CP_NORM', 'CP Normal 25K-1L', '{"minAmount": 25000, "maxAmount": 100000}', 'AMOUNT', 2, 2, 1, 1, NOW());
```

### Approver Configuration

```sql
-- For Emergency CP (< 25K)
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_EMERG], 2, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_EMERG], 2, 'CP Approver', 2, 2, 1, 'Active', 1, NOW());

-- For Normal CP (25K-1L)
INSERT INTO approver_master (branch_id, workflow_id, role_name, approval_level, approval_sequence, is_mandatory, status, created_by, created_date)
VALUES
([BRANCH_ID_NORM], 2, 'Reporting Officer', 1, 1, 1, 'Active', 1, NOW()),
([BRANCH_ID_NORM], 2, 'Administrative Officer', 2, 2, 1, 'Active', 1, NOW()),
([BRANCH_ID_NORM], 2, 'CP Approver', 3, 3, 1, 'Active', 1, NOW());
```

---

## COMPLETE END-TO-END TESTING WORKFLOW

### Scenario: Purchase 68,000 worth of Computer Equipment

#### Phase 1: Indent Creation & Approval ✅ (Already Done)
1. **Indent Creator** creates IND1148 (Amount: 68,000, Location: BANGALORE)
2. **System** routes to Branch 23 (Amount 50k-100k)
3. **Reporting Officer** approves → Routes to Administrative Officer
4. **Administrative Officer** approves → Indent COMPLETED

#### Phase 2: Tender Creation & Approval
1. **Tender Creator** creates tender from IND1148
2. **System** routes based on amount (68k < 1L) → Branch: BR_TND_AMT_LOW
3. **Reporting Officer** approves
4. **Tender Approver** approves → Tender COMPLETED & sent to vendors

#### Phase 3: Vendor Quotation & Evaluation
1. Vendors receive email notifications
2. Vendors submit quotations (3 vendors submit)
3. **Tender Evaluator** evaluates quotations
4. **System** routes to Branch: BR_EVAL_STD (< 5 vendors)
5. **Purchase Committee Chairman** approves evaluation
6. Winner vendor selected

#### Phase 4: Purchase Order Creation & Approval
1. **PO Creator** creates PO for winning vendor (Amount: 68,000)
2. **System** routes to Branch: BR_PO_MED (50k-5L)
3. **Reporting Officer** approves
4. **Administrative Officer** approves
5. **PO Approver** approves → PO COMPLETED

#### Phase 5: Goods Receipt & Payment
1. Goods received and inspected
2. Payment processed
3. Workflow complete!

---

## VERIFICATION QUERIES

### Check All Active Branches
```sql
SELECT
    wm.workflowname,
    wb.branch_code,
    wb.branch_name,
    wb.condition_config,
    wb.display_order,
    COUNT(am.approver_id) as approver_count
FROM workflow_branch_master wb
JOIN workflow_master wm ON wb.workflow_id = wm.workflowid
LEFT JOIN approver_master am ON wb.branch_id = am.branch_id
WHERE wb.is_active = 1
GROUP BY wb.branch_id
ORDER BY wm.workflowname, wb.display_order;
```

### Check Workflow Transitions for a Request
```sql
SELECT
    wt.workflowtransitionid,
    wt.requestid,
    wt.workflowname,
    wt.branch_id,
    wb.branch_code,
    wt.currentrole,
    wt.nextrole,
    wt.status,
    wt.nextaction,
    wt.approval_level,
    wt.approval_sequence,
    wt.createddate
FROM workflow_transition wt
LEFT JOIN workflow_branch_master wb ON wt.branch_id = wb.branch_id
WHERE wt.requestid = 'IND1148'
ORDER BY wt.workflowtransitionid;
```

### Check Email Notifications Sent
```sql
-- If you have an email log table
SELECT * FROM email_log WHERE request_id = 'IND1148' ORDER BY sent_date DESC;
```

---

## TROUBLESHOOTING

### Issue 1: Branch Not Matching
**Symptoms:** Workflow falls back to old TransitionMaster system

**Check:**
```sql
-- Verify branch is active
SELECT * FROM workflow_branch_master WHERE branch_id = [BRANCH_ID];

-- Verify conditions match
-- Check indent values match branch conditions
```

**Solution:** Ensure condition_config JSON matches actual request data

### Issue 2: Approver Not Found
**Symptoms:** Error "No approvers configured for branch"

**Check:**
```sql
SELECT * FROM approver_master WHERE branch_id = [BRANCH_ID] AND status = 'Active';
```

**Solution:** Add missing approvers with correct approval_level and approval_sequence

### Issue 3: User Cannot Approve
**Symptoms:** User doesn't see indent in their queue

**Check:**
```sql
-- Verify user has correct role
SELECT user_id, user_name, role_name FROM user_master WHERE user_id = [USER_ID];

-- Verify role matches nextRole in workflow_transition
SELECT nextrole FROM workflow_transition WHERE requestid = 'IND1148' AND nextaction = 'Pending';
```

---

## SUMMARY CHECKLIST

- [ ] Indent Workflow - Amount-based routing (50k-100k) ✅ Working
- [ ] Indent Workflow - Location-based routing (BANGALORE)
- [ ] Tender Workflow - Configure branches for amount ranges
- [ ] Tender Workflow - Test with different amounts
- [ ] Tender Evaluator - Configure branches for vendor count
- [ ] Tender Evaluator - Test evaluation flow
- [ ] PO Workflow - Configure branches for amount ranges
- [ ] PO Workflow - Test PO creation and approval
- [ ] Contingency Purchase - Configure emergency/normal branches
- [ ] Contingency Purchase - Test both scenarios
- [ ] Email notifications working for all workflows
- [ ] All role-based access working correctly

---

## NEXT STEPS

1. **Run the verification queries** to see current branch setup
2. **Create missing branches** using the INSERT scripts above
3. **Configure approvers** for each branch
4. **Test each workflow** following the test cases
5. **Document any issues** found during testing
6. **Adjust branch conditions** based on business requirements
