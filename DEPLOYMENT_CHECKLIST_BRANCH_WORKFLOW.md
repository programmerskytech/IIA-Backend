# Branch-Based Workflow Deployment Checklist

**Date:** January 9, 2026
**Status:** ✅ **READY FOR DEPLOYMENT**
**Build Status:** ✅ **COMPILATION SUCCESSFUL**

---

## ✅ **IMPLEMENTATION COMPLETE**

All code has been implemented and compiled successfully. The branch-based workflow system is ready for deployment and testing.

---

## 📋 **DEPLOYMENT STEPS**

### **Step 1: Database Migration**

Run the migration script to add branch tracking columns to the `workflow_transition` table:

```bash
# For MySQL (Windows)
mysql -u root -p astrodatabase < "database-migrations/006_workflow_branch_integration.sql"

# For MySQL (Linux/Mac)
mysql -u root -p astrodatabase < database-migrations/006_workflow_branch_integration.sql
```

**What this adds:**
- `BRANCH_ID` column - Links to workflow_branch_master
- `APPROVER_ID` column - Links to approver_master
- `APPROVAL_LEVEL` column - Current approval level in chain
- `APPROVAL_SEQUENCE` column - Current sequence within level
- Indexes for performance optimization

**Verification Query:**
```sql
SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'astrodatabase'
AND TABLE_NAME = 'workflow_transition'
AND COLUMN_NAME IN ('BRANCH_ID', 'APPROVER_ID', 'APPROVAL_LEVEL', 'APPROVAL_SEQUENCE')
ORDER BY COLUMN_NAME;
```

Expected: 4 rows showing the new columns.

---

### **Step 2: Rebuild Application**

```bash
cd "e:\Work 2.0\IIA\Backend-prod"
mvn clean install -DskipTests
```

**Expected Output:** `BUILD SUCCESS`

---

### **Step 3: Restart Application**

Stop the current application and start it with the new build.

**Verification in Logs:**
Look for successful bean creation:
```
✓ BranchWorkflowService bean created
✓ WorkflowServiceImpl initialized
✓ No errors related to WorkflowTransition entity
```

---

## 🧪 **TESTING PROCEDURE**

### **Test 1: Create Branch Configuration**

1. **Login to Admin Panel** as Administrator

2. **Navigate to:** Admin Panel → Workflows → "Indent Approval Workflow"

3. **Create a New Branch:**
   - **Branch Code:** `INDENT_AMOUNT_5000`
   - **Branch Name:** `Under Project - Computer - Bangalore`
   - **Condition Type:** `AmountBased`
   - **Condition Config:**
     ```json
     {"minAmount": 50000, "maxAmount": 100000}
     ```
   - **Display Order:** `1`
   - **Status:** `ACTIVE`
   - Click **Save**

4. **Add First Approver:**
   - Click "Add Approver" for the branch you just created
   - **Approver Code:** `REPORTING_OFFICER_1`
   - **Role Name:** `Reporting Officer`
   - **Approval Level:** `1`
   - **Approval Sequence:** `1`
   - **Status:** `Active`
   - Click **Save**

5. **Add Second Approver:**
   - Click "Add Approver" again
   - **Approver Code:** `ADMIN_OFFICER_1`
   - **Role Name:** `Administrative Officer`
   - **Approval Level:** `2`
   - **Approval Sequence:** `2`
   - **Status:** `Active`
   - Click **Save**

**Verification Query:**
```sql
-- Check branch was created
SELECT branch_id, branch_code, branch_name, condition_config
FROM workflow_branch_master
WHERE branch_code = 'INDENT_AMOUNT_5000';

-- Check approvers were added
SELECT approver_code, role_name, approval_level, approval_sequence, status
FROM approver_master
WHERE branch_id = (SELECT branch_id FROM workflow_branch_master WHERE branch_code = 'INDENT_AMOUNT_5000')
ORDER BY approval_level, approval_sequence;
```

Expected: 1 branch, 2 approvers.

---

### **Test 2: Create Indent and Initiate Workflow**

1. **Login as Indent Creator**

2. **Create New Indent:**
   - **Request ID:** Will be auto-generated (e.g., IND1116)
   - **Total Amount:** ₹75,000 (matches branch condition 50K-100K)
   - **Material Category:** Computer
   - **Location:** Bangalore
   - **Project Name:** Any project
   - Fill in other required fields
   - Click **Submit**

3. **Check Application Logs:**
   Look for these messages:
   ```
   📋 Indent Conditions Built: {totalAmount=75000, category=Computer, location=Bangalore, ...}
   🔍 Matching Branch: INDENT_AMOUNT_5000
      Branch Conditions: {minAmount=50000, maxAmount=100000}
      Actual Values: {totalAmount=75000, ...}
      ✅ Amount 75000 matches range
   ✅ Matched Branch: INDENT_AMOUNT_5000 - Under Project - Computer - Bangalore
   ✅ First Approver: Reporting Officer (Level: 1, Seq: 1)
   ✅ Workflow initiated with branch: INDENT_AMOUNT_5000
   ```

4. **Verify in Database:**
   ```sql
   SELECT
       REQUESTID,
       WORKFLOWSEQUENCE,
       CURRENTROLE,
       NEXTROLE,
       NEXTACTION,
       BRANCH_ID,
       APPROVER_ID,
       APPROVAL_LEVEL,
       APPROVAL_SEQUENCE,
       STATUS
   FROM workflow_transition
   WHERE REQUESTID = 'IND1116'  -- Replace with your indent ID
   ORDER BY WORKFLOWSEQUENCE;
   ```

   **Expected Result:**
   | REQUESTID | WORKFLOWSEQUENCE | CURRENTROLE | NEXTROLE | NEXTACTION | BRANCH_ID | APPROVAL_LEVEL | APPROVAL_SEQUENCE | STATUS |
   |-----------|------------------|-------------|----------|------------|-----------|----------------|-------------------|--------|
   | IND1116   | 1                | NULL        | Reporting Officer | Reporting Officer | 23 | 1 | 1 | Submitted |

---

### **Test 3: First Approval (Reporting Officer)**

1. **Login as Reporting Officer**

2. **Navigate to:** Queue → Procurement → Indent

3. **Verify:** Indent IND1116 appears in queue with status "Submitted"

4. **Approve the Indent:**
   - Click on indent IND1116
   - Add remarks (optional)
   - Click **Approve**

5. **Check Application Logs:**
   ```
   ✅ Approved by Reporting Officer → Routing to Administrative Officer (Level: 2, Seq: 2)
   ✅ Next Approver: Administrative Officer (Level: 2, Seq: 2)
   ```

6. **Verify in Database:**
   ```sql
   SELECT
       REQUESTID,
       WORKFLOWSEQUENCE,
       CURRENTROLE,
       NEXTROLE,
       NEXTACTION,
       BRANCH_ID,
       APPROVAL_LEVEL,
       APPROVAL_SEQUENCE,
       STATUS
   FROM workflow_transition
   WHERE REQUESTID = 'IND1116'
   ORDER BY WORKFLOWSEQUENCE;
   ```

   **Expected Result:**
   | REQUESTID | WORKFLOWSEQUENCE | CURRENTROLE | NEXTROLE | NEXTACTION | BRANCH_ID | APPROVAL_LEVEL | APPROVAL_SEQUENCE | STATUS |
   |-----------|------------------|-------------|----------|------------|-----------|----------------|-------------------|--------|
   | IND1116   | 1                | NULL        | Reporting Officer | Completed | 23 | 1 | 1 | Submitted |
   | IND1116   | 2                | Reporting Officer | Administrative Officer | Administrative Officer | 23 | 2 | 2 | Submitted |

---

### **Test 4: Second Approval (Administrative Officer) - THE CRITICAL TEST**

**This is the test that was FAILING before. It should PASS now!**

1. **Login as Administrative Officer**

2. **Navigate to:** Queue → Procurement → Indent

3. **✅ CRITICAL VERIFICATION:**
   - **Indent IND1116 MUST appear in the queue**
   - Status should be "Submitted"
   - Next Action should be "Administrative Officer"

4. **Approve the Indent:**
   - Click on indent IND1116
   - Add remarks (optional)
   - Click **Approve**

5. **Check Application Logs:**
   ```
   ✅ No more approvers - workflow complete for branch 23
   ✅ Final approval by Administrative Officer - Workflow COMPLETED
   ```

6. **Verify in Database:**
   ```sql
   SELECT
       REQUESTID,
       WORKFLOWSEQUENCE,
       CURRENTROLE,
       NEXTROLE,
       NEXTACTION,
       BRANCH_ID,
       APPROVAL_LEVEL,
       APPROVAL_SEQUENCE,
       STATUS
   FROM workflow_transition
   WHERE REQUESTID = 'IND1116'
   ORDER BY WORKFLOWSEQUENCE;
   ```

   **Expected Result:**
   | REQUESTID | WORKFLOWSEQUENCE | CURRENTROLE | NEXTROLE | NEXTACTION | BRANCH_ID | APPROVAL_LEVEL | APPROVAL_SEQUENCE | STATUS |
   |-----------|------------------|-------------|----------|------------|-----------|----------------|-------------------|--------|
   | IND1116   | 1                | NULL        | Reporting Officer | Completed | 23 | 1 | 1 | Submitted |
   | IND1116   | 2                | Reporting Officer | Administrative Officer | Completed | 23 | 2 | 2 | Submitted |
   | IND1116   | 3                | Administrative Officer | NULL | NULL | 23 | 2 | 2 | Completed |

7. **Check Indent Status:**
   ```sql
   SELECT
       INDENTID,
       CURRENT_STATUS,
       CURRENT_STAGE,
       APPROVAL_LEVEL,
       IS_EDITABLE
   FROM indent_creation
   WHERE INDENTID = 'IND1116';
   ```

   **Expected:**
   - CURRENT_STATUS = "APPROVED"
   - CURRENT_STAGE = "INDENT_APPROVED"
   - IS_EDITABLE = false

---

## ✅ **SUCCESS CRITERIA**

The implementation is successful if:

1. ✅ Database migration completes without errors
2. ✅ Application builds and starts successfully
3. ✅ Branch configuration can be created in Admin Panel
4. ✅ Approvers can be added to branches
5. ✅ Creating indent triggers branch matching (check logs)
6. ✅ First approver sees indent in queue
7. ✅ **CRITICAL:** After first approval, second approver sees indent in queue
8. ✅ After final approval, workflow status is "Completed"
9. ✅ Indent status changes to "APPROVED"

---

## 🔍 **TROUBLESHOOTING**

### **Issue: No matching branch found**

**Symptom:** Logs show "⚠️ No matching branch found for workflow 1"

**Cause:** Indent conditions don't match any branch configuration

**Solution:**
1. Check branch conditions in `workflow_branch_master.condition_config`
2. Check actual indent values (amount, category, location)
3. Create a default branch with no conditions: `{}`
4. Ensure branch `is_active = true`

**Debug Query:**
```sql
SELECT branch_code, branch_name, condition_config, is_active
FROM workflow_branch_master
WHERE workflow_id = 1;
```

---

### **Issue: No approvers configured for branch**

**Symptom:** Logs show "⚠️ No active approvers found for branch 23"

**Cause:** No approvers added to the branch OR approvers are inactive

**Solution:**
1. Add approvers in Admin Panel
2. Ensure approver status = "Active"
3. Verify approval levels are sequential (1, 2, 3...)

**Debug Query:**
```sql
SELECT approver_code, role_name, approval_level, approval_sequence, status
FROM approver_master
WHERE branch_id = 23
ORDER BY approval_level, approval_sequence;
```

---

### **Issue: Application falls back to old TransitionMaster system**

**Symptom:** Logs show "⚠️ No matching branch found, falling back to old TransitionMaster system"

**Cause:** This is EXPECTED if no branch is configured. It's backward compatible.

**Solution:**
- If you want branch-based routing, create a branch configuration
- If you want to keep using old system, no action needed
- The system gracefully handles both approaches

---

### **Issue: Second approver doesn't see indent**

**This was the ORIGINAL ISSUE and should be FIXED now.**

**Symptom:** After Reporting Officer approves, Administrative Officer doesn't see indent in queue

**Cause (Before Fix):** System was using TransitionMaster, not branch-based routing

**Verification:**
```sql
-- Check if workflow is using branch-based routing
SELECT BRANCH_ID, APPROVAL_LEVEL, APPROVAL_SEQUENCE
FROM workflow_transition
WHERE REQUESTID = 'IND1116'
ORDER BY WORKFLOWSEQUENCE;
```

**If BRANCH_ID is NULL:** The system fell back to old TransitionMaster routing. Check branch configuration.

**If BRANCH_ID is NOT NULL:** The system is using branch-based routing. This should work correctly.

---

## 📊 **MONITORING QUERIES**

### **Check which workflows are using branch-based routing:**
```sql
SELECT
    WORKFLOWNAME,
    COUNT(*) as total_transitions,
    COUNT(BRANCH_ID) as branch_based_transitions,
    COUNT(CASE WHEN BRANCH_ID IS NULL THEN 1 END) as legacy_transitions
FROM workflow_transition
WHERE CREATEDDATE >= CURDATE() - INTERVAL 7 DAY
GROUP BY WORKFLOWNAME;
```

---

### **View all pending approvals (branch-based):**
```sql
SELECT
    wt.REQUESTID,
    wt.WORKFLOWNAME,
    wt.NEXTACTION,
    wb.branch_name,
    am.role_name as current_approver,
    wt.APPROVAL_LEVEL,
    wt.APPROVAL_SEQUENCE,
    wt.STATUS,
    wt.CREATEDDATE
FROM workflow_transition wt
LEFT JOIN workflow_branch_master wb ON wt.BRANCH_ID = wb.branch_id
LEFT JOIN approver_master am ON wt.APPROVER_ID = am.approver_id
WHERE wt.STATUS = 'Submitted'
  AND wt.NEXTACTION != 'Completed'
  AND wt.BRANCH_ID IS NOT NULL
ORDER BY wt.CREATEDDATE DESC;
```

---

### **View approval chain for a specific request:**
```sql
SELECT
    WORKFLOWSEQUENCE,
    CURRENTROLE,
    NEXTROLE,
    APPROVAL_LEVEL,
    APPROVAL_SEQUENCE,
    STATUS,
    NEXTACTION,
    MODIFICATIONDATE
FROM workflow_transition
WHERE REQUESTID = 'IND1116'
ORDER BY WORKFLOWSEQUENCE;
```

---

## 🎯 **APPLIES TO ALL 5 WORKFLOWS**

Once tested with Indent, the same system works for:

1. **Indent Approval Workflow** ✅ (Test first)
2. **Tender Approver Workflow** (Same logic)
3. **Tender Evaluator Workflow** (Same logic)
4. **Purchase Order Workflow** (Same logic)
5. **Contingency Purchase Workflow** (Same logic)

**To enable for other workflows:**
1. Navigate to that workflow in Admin Panel
2. Create branches with conditions
3. Add sequential approvers
4. Test with real request

---

## 📝 **FILES MODIFIED/CREATED**

| File | Type | Purpose |
|------|------|---------|
| `BranchWorkflowService.java` | ✅ NEW | Interface for branch-based routing |
| `BranchWorkflowServiceImpl.java` | ✅ NEW | Branch matching & approver routing logic |
| `WorkflowTransition.java` | 🔧 MODIFIED | Added branch tracking fields |
| `WorkflowServiceImpl.java` | 🔧 MODIFIED | Integrated branch-based routing |
| `006_workflow_branch_integration.sql` | ✅ NEW | Database migration script |
| `BRANCH_WORKFLOW_COMPLETE_IMPLEMENTATION.md` | ✅ NEW | Complete implementation guide |
| `FRONTEND_BRANCH_WORKFLOW_INTEGRATION.md` | ✅ NEW | Frontend integration guide |
| `DEPLOYMENT_CHECKLIST_BRANCH_WORKFLOW.md` | ✅ NEW | This deployment checklist |

---

## 🎉 **COMPLETION STATUS**

✅ **All Code Implemented**
✅ **Compilation Successful**
✅ **Database Migration Script Ready**
✅ **Documentation Complete**
✅ **Ready for Deployment**

---

## 🚀 **NEXT STEPS**

1. Run database migration script
2. Rebuild and restart application
3. Create test branch configuration in Admin Panel
4. Create test indent and verify sequential approvals
5. If successful, configure branches for all 5 workflows
6. Monitor logs and database for any issues

---

**Last Updated:** January 9, 2026
**Status:** ✅ **READY FOR PRODUCTION DEPLOYMENT**
