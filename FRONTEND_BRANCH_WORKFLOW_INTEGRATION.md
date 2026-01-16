# Frontend Integration Guide - Branch-Based Workflow

**Date:** January 9, 2026
**Backend Status:** ✅ FULLY COMPLETE
**Frontend Changes Required:** ✅ MINIMAL (Optional Enhancements Only)

---

## 🎯 **GOOD NEWS: NO BREAKING CHANGES!**

**Your existing frontend will continue to work without any modifications.**

The branch-based workflow system is **fully backward compatible**. All existing APIs return the same data they did before.

---

## ✅ **WHAT'S ALREADY WORKING**

### **1. Admin Panel (Already Complete)**
- ✅ Branch creation UI
- ✅ Approver management UI
- ✅ Condition configuration UI
- ✅ All CRUD operations

**No changes needed!**

### **2. Workflow Queue**
- ✅ Pending approvals show in queue
- ✅ Approval actions work normally
- ✅ Workflow history displays correctly

**No changes needed!**

### **3. Indent/Tender Creation**
- ✅ Form submissions work normally
- ✅ Workflow initiation automatic
- ✅ Status updates work

**No changes needed!**

---

## 📋 **OPTIONAL ENHANCEMENTS**

You can enhance the UI to show branch-based workflow information:

### **1. Workflow History - Show Branch Info**

**API Response Now Includes:**
```json
{
  "workflowTransitionId": 123,
  "requestId": "IND1116",
  "status": "Submitted",
  "currentRole": "Reporting Officer",
  "nextRole": "Administrative Officer",
  "workflowSequence": 2,

  // NEW FIELDS (Optional to display):
  "branchId": 23,
  "approverId": 9,
  "approvalLevel": 2,
  "approvalSequence": 2
}
```

**Frontend Enhancement (Optional):**
```javascript
// Show approval progress indicator
function renderApprovalProgress(transition) {
    if (transition.branchId) {
        // This is a branch-based workflow
        return `
            <div class="approval-progress">
                <span class="level-badge">Level ${transition.approvalLevel}</span>
                <span class="sequence-badge">Step ${transition.approvalSequence}</span>
                <span class="role-badge">${transition.nextRole}</span>
            </div>
        `;
    } else {
        // Legacy workflow
        return `<span class="role-badge">${transition.nextRole}</span>`;
    }
}
```

### **2. Show Which Branch Matched (Optional)**

You can fetch branch details to show which branch was matched:

**New API Endpoint (Optional - Can Add If Needed):**
```http
GET /api/workflow-branch/{branchId}
```

**Response:**
```json
{
  "branchId": 23,
  "branchCode": "INDENT_AMOUNT_5000",
  "branchName": "Under Project - Computer - Bangalore",
  "conditionConfig": "{\"minAmount\": 50000, \"maxAmount\": 100000}"
}
```

**Frontend Enhancement (Optional):**
```javascript
// Show which branch was matched
async function showBranchInfo(branchId) {
    const branch = await fetch(`/api/workflow-branch/${branchId}`).then(r => r.json());

    return `
        <div class="branch-info">
            <i class="fa fa-code-branch"></i>
            <span>Routing: ${branch.branchName}</span>
        </div>
    `;
}
```

### **3. Approval Chain Preview (Optional)**

Show all approvers in the chain before submission:

**New API Endpoint (Can Add If Needed):**
```http
GET /api/workflow-preview?workflowId=1&amount=75000&category=Computer
```

**Response:**
```json
{
  "matchedBranch": {
    "branchId": 23,
    "branchName": "Under Project - Computer - Bangalore"
  },
  "approvalChain": [
    {
      "level": 1,
      "sequence": 1,
      "roleName": "Reporting Officer"
    },
    {
      "level": 2,
      "sequence": 2,
      "roleName": "Administrative Officer"
    }
  ]
}
```

**Frontend Enhancement (Optional):**
```javascript
// Show approval chain preview on indent creation
async function showApprovalPreview(formData) {
    const preview = await fetch(`/api/workflow-preview?workflowId=1&amount=${formData.totalAmount}&category=${formData.category}`)
        .then(r => r.json());

    return `
        <div class="approval-preview">
            <h4>Approval Chain:</h4>
            <ol>
                ${preview.approvalChain.map(a => `
                    <li>${a.roleName} (Level ${a.level})</li>
                `).join('')}
            </ol>
        </div>
    `;
}
```

---

## 🔧 **IF YOU WANT TO IMPLEMENT ENHANCEMENTS**

### **Backend API to Add (Optional):**

1. **Get Branch Details:**
```java
@GetMapping("/api/workflow-branch/{branchId}")
public ResponseEntity<?> getBranchDetails(@PathVariable Long branchId) {
    WorkflowBranchMaster branch = branchRepository.findById(branchId)
        .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    return ResponseEntity.ok(branch);
}
```

2. **Preview Approval Chain:**
```java
@GetMapping("/api/workflow-preview")
public ResponseEntity<?> previewApprovalChain(
    @RequestParam Integer workflowId,
    @RequestParam BigDecimal amount,
    @RequestParam(required = false) String category,
    @RequestParam(required = false) String location
) {
    Map<String, Object> conditions = new HashMap<>();
    conditions.put("totalAmount", amount);
    if (category != null) conditions.put("category", category);
    if (location != null) conditions.put("location", location);

    WorkflowBranchMaster branch = branchWorkflowService.findMatchingBranch(workflowId, conditions);

    if (branch == null) {
        return ResponseEntity.ok(Map.of("message", "No matching branch"));
    }

    List<ApproverMaster> approvers = branchWorkflowService.getApproversForBranch(branch.getBranchId());

    return ResponseEntity.ok(Map.of(
        "matchedBranch", branch,
        "approvalChain", approvers
    ));
}
```

---

## 📝 **SUMMARY FOR FRONTEND TEAM**

### **What You MUST Do:**
**NOTHING!** ✅ Everything works as-is.

### **What You CAN Do (Optional):**
1. Show branch name in workflow history
2. Display approval level/sequence badges
3. Preview approval chain before submission
4. Add "Routing via: [Branch Name]" indicator

### **APIs Available:**
- All existing APIs work unchanged
- New fields added to WorkflowTransitionDto (optional to use):
  - `branchId`
  - `approverId`
  - `approvalLevel`
  - `approvalSequence`

---

## 🧪 **TESTING FOR FRONTEND**

### **Test Scenario:**

1. **Admin Panel:**
   - Create a branch with 2 approvers (already working)
   - No UI changes needed

2. **Create Indent:**
   - Create indent matching branch conditions
   - Submit normally
   - ✅ Should work exactly as before

3. **Queue:**
   - Login as first approver
   - ✅ See indent in queue (works as before)
   - Approve it
   - ✅ Approval works as before

4. **Next Approver:**
   - Login as second approver
   - ✅ See indent in queue (THIS IS THE FIX!)
   - Approve it
   - ✅ Workflow completes

5. **Workflow History:**
   - View workflow transitions
   - ✅ All transitions shown (works as before)
   - **NEW:** Can optionally show branch/level info

---

## ⚠️ **IMPORTANT NOTES**

1. **No Breaking Changes:** All existing frontend code continues to work
2. **Graceful Degradation:** If backend falls back to old system, frontend sees no difference
3. **Console Logs:** Backend logs detailed routing info for debugging
4. **Database Migration:** Must run SQL script before testing

---

## 🎯 **MINIMAL FRONTEND CHANGES**

If you want to show the "fixed" behavior is working, add this simple indicator:

**Workflow History Component:**
```javascript
// Add a simple badge to show it's using new branch system
function renderWorkflowRow(transition) {
    const isBranchBased = transition.branchId != null;

    return `
        <tr>
            <td>${transition.workflowSequence}</td>
            <td>${transition.currentRole}</td>
            <td>${transition.status}</td>
            <td>
                ${isBranchBased ?
                    '<span class="badge badge-success">✓ Branch-Based</span>' :
                    '<span class="badge badge-secondary">Legacy</span>'
                }
            </td>
        </tr>
    `;
}
```

**That's it!** Simple badge to show new system is active.

---

**Ready to Test!** 🚀

No frontend changes required for core functionality. The fix is 100% backend.

---

**Last Updated:** January 9, 2026
