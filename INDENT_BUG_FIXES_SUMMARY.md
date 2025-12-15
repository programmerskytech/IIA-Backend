# Indent Bug Fixes - Backend Changes Summary

**Date:** December 15, 2025
**Developer:** Backend Team
**Target:** UI/Frontend Team

---

## Overview

This document summarizes all backend changes made to resolve 4 critical bugs in the Indent Management System. The UI team should update their code accordingly to utilize the new features and enforce business rules on the frontend.

---

## Bug Fixes Implemented

### Bug Fix 1: Indent Edit Restriction Based on Workflow
**Problem:** Indent creator could update indent at any time, even after sending for approval.
**Solution:** Indent can only be edited when:
- Initially created (before submission)
- Sent back by approver with "Change Requested" action

### Bug Fix 2: Lock Indent After Tender Creation
**Problem:** Users could update indent even after tender was generated.
**Solution:** Indent is permanently locked once a tender is created from it.

### Bug Fix 3: Indent Version Tracking
**Problem:** No version history when indent is updated.
**Solution:** Each update increments the version number automatically.

### Bug Fix 4: Comprehensive Status Tracking
**Problem:** Status only showed "Completed", not showing current workflow stage.
**Solution:** Detailed status tracking across entire procurement lifecycle.

---

## New Fields Added to IndentCreationResponseDTO

The backend now returns these additional fields in all indent responses:

```json
{
  "indentId": "IND1001",
  "indentorName": "John Doe",

  // NEW FIELDS - Use these in your UI
  "isEditable": true,                    // Controls if Edit button should be enabled
  "isLockedForTender": false,            // Indicates if tender has been created
  "lockedReason": null,                  // Why indent is locked (if locked)
  "version": 1,                          // Current version number
  "parentIndentId": null,                // Original indent ID (if revised)
  "currentStatus": "DRAFT",              // Current status (see status list below)
  "currentStage": "INDENT_CREATION",     // Current workflow stage (see stage list below)
  "approvalLevel": 0,                    // Current approval level (0 = not submitted)

  // ... existing fields ...
}
```

---

## Current Status Values

The `currentStatus` field can have these values:

| Status | Meaning | When Set |
|--------|---------|----------|
| `DRAFT` | Indent is being created | Initial creation |
| `IN_APPROVAL` | Indent is in approval workflow | After submission, moving through approvals |
| `APPROVED` | Indent fully approved | Final approval completed |
| `CHANGE_REQUESTED` | Sent back for revision | Approver requested changes |
| `TENDER_CREATED` | Tender has been generated | After tender creation |
| `CANCELLED` | Indent cancelled | Cancellation approved |

---

## Current Stage Values

The `currentStage` field shows detailed workflow position:

| Stage | Description |
|-------|-------------|
| `INDENT_CREATION` | Initial creation phase |
| `INDENT_REVISION` | Sent back for changes |
| `INDENT_APPROVAL_LEVEL_1` | At first approval level |
| `INDENT_APPROVAL_LEVEL_2` | At second approval level |
| `INDENT_APPROVAL_LEVEL_n` | At nth approval level |
| `INDENT_APPROVED` | Fully approved |
| `TENDER_GENERATION` | Tender created |

---

## UI Implementation Guidelines

### 1. Edit Button Control

**OLD Logic:**
```javascript
// Don't use this anymore
<button onClick={editIndent}>Edit</button>
```

**NEW Logic:**
```javascript
// Use isEditable and isLockedForTender to control edit functionality
const canEdit = indent.isEditable && !indent.isLockedForTender;

<button
  onClick={editIndent}
  disabled={!canEdit}
  title={!canEdit ? getDisabledReason() : "Edit Indent"}
>
  Edit
</button>

function getDisabledReason() {
  if (indent.isLockedForTender) {
    return indent.lockedReason || "Indent locked - Tender already created";
  }
  if (!indent.isEditable) {
    return "Indent is in approval. Wait for it to be sent back for revision.";
  }
  return "";
}
```

### 2. Status Display

**Show comprehensive status information:**

```javascript
// Status badge component
function IndentStatusBadge({ indent }) {
  const statusColors = {
    'DRAFT': 'gray',
    'IN_APPROVAL': 'blue',
    'APPROVED': 'green',
    'CHANGE_REQUESTED': 'orange',
    'TENDER_CREATED': 'purple',
    'CANCELLED': 'red'
  };

  return (
    <div>
      <Badge color={statusColors[indent.currentStatus]}>
        {indent.currentStatus}
      </Badge>
      <div className="text-sm text-gray-600">
        Stage: {indent.currentStage}
      </div>
      {indent.approvalLevel > 0 && (
        <div className="text-xs text-gray-500">
          Approval Level: {indent.approvalLevel}
        </div>
      )}
    </div>
  );
}
```

### 3. Version Display

**Show version information:**

```javascript
function IndentVersionInfo({ indent }) {
  return (
    <div className="version-info">
      <span>Version: {indent.version}</span>
      {indent.parentIndentId && (
        <a href={`/indents/${indent.parentIndentId}`}>
          View Original (v1)
        </a>
      )}
    </div>
  );
}
```

### 4. Locked Status Indicator

**Show when indent is locked:**

```javascript
function IndentLockIndicator({ indent }) {
  if (!indent.isLockedForTender) return null;

  return (
    <div className="alert alert-warning">
      <Icon name="lock" />
      <div>
        <strong>Indent Locked</strong>
        <p>{indent.lockedReason}</p>
      </div>
    </div>
  );
}
```

### 5. Workflow Progress Tracker

**Implement a visual workflow tracker:**

```javascript
function WorkflowProgressTracker({ indent }) {
  const stages = [
    { key: 'INDENT_CREATION', label: 'Created' },
    { key: 'INDENT_APPROVAL', label: 'In Approval' },
    { key: 'INDENT_APPROVED', label: 'Approved' },
    { key: 'TENDER_GENERATION', label: 'Tender Created' },
  ];

  const currentStageIndex = stages.findIndex(
    s => indent.currentStage.includes(s.key)
  );

  return (
    <div className="workflow-progress">
      {stages.map((stage, index) => (
        <div
          key={stage.key}
          className={`stage ${index <= currentStageIndex ? 'completed' : 'pending'}`}
        >
          <div className="stage-icon">{index + 1}</div>
          <div className="stage-label">{stage.label}</div>
        </div>
      ))}
    </div>
  );
}
```

---

## API Endpoint Changes

### GET /api/indents/{indentId}
**Response now includes new fields (see above)**

### PUT /api/indents/{indentId}
**New Error Responses:**

```json
// When indent is locked for tender
{
  "errorCode": 400,
  "errorType": "VALIDATION",
  "message": "Indent is locked for editing as tender has been created. Reason: Tender T1001 has been created for this indent"
}

// When indent is not editable (in approval)
{
  "errorCode": 400,
  "errorType": "VALIDATION",
  "message": "Indent is not editable. It can only be edited when sent back by an approver for revision."
}
```

**Handle these errors in UI:**
```javascript
try {
  await updateIndent(indentId, data);
  showSuccess("Indent updated successfully");
} catch (error) {
  if (error.response?.data?.message?.includes("locked for editing")) {
    showError("Cannot edit: Tender already created for this indent");
  } else if (error.response?.data?.message?.includes("not editable")) {
    showError("Cannot edit: Indent is in approval workflow");
  } else {
    showError("Failed to update indent");
  }
}
```

---

## Workflow State Transitions

### State Diagram

```
DRAFT (isEditable: true)
  |
  | (Submit for approval)
  v
IN_APPROVAL (isEditable: false)
  |
  +---(Approve)---> APPROVED (isEditable: false)
  |                    |
  |                    | (Create Tender)
  |                    v
  |                 TENDER_CREATED (isEditable: false, isLockedForTender: true)
  |
  +---(Change Request)---> CHANGE_REQUESTED (isEditable: true)
                             |
                             | (Re-submit)
                             v
                          IN_APPROVAL (isEditable: false, version++)
```

---

## Form Validation on Frontend

### Before Allowing Edit

```javascript
function handleEditClick(indent) {
  // Validation 1: Check if locked for tender
  if (indent.isLockedForTender) {
    showAlert({
      type: 'error',
      title: 'Cannot Edit',
      message: indent.lockedReason || 'Indent is locked as tender has been created'
    });
    return;
  }

  // Validation 2: Check if editable
  if (!indent.isEditable) {
    showAlert({
      type: 'warning',
      title: 'Cannot Edit',
      message: `Indent is currently in ${indent.currentStage}.
                It can only be edited when sent back for revision.`
    });
    return;
  }

  // All checks passed - allow edit
  navigateToEditPage(indent.indentId);
}
```

---

## Status Colors and Icons (Recommended)

```javascript
const STATUS_CONFIG = {
  'DRAFT': {
    color: '#6B7280',      // Gray
    icon: 'edit',
    label: 'Draft'
  },
  'IN_APPROVAL': {
    color: '#3B82F6',      // Blue
    icon: 'clock',
    label: 'In Approval'
  },
  'APPROVED': {
    color: '#10B981',      // Green
    icon: 'check-circle',
    label: 'Approved'
  },
  'CHANGE_REQUESTED': {
    color: '#F59E0B',      // Orange
    icon: 'arrow-left',
    label: 'Revision Required'
  },
  'TENDER_CREATED': {
    color: '#8B5CF6',      // Purple
    icon: 'document',
    label: 'Tender Created'
  },
  'CANCELLED': {
    color: '#EF4444',      // Red
    icon: 'x-circle',
    label: 'Cancelled'
  }
};
```

---

## Checklist for UI Team

- [ ] Update indent detail page to show new status fields
- [ ] Implement edit button disable logic based on `isEditable` and `isLockedForTender`
- [ ] Add locked indicator when `isLockedForTender` is true
- [ ] Display version number on indent cards/list
- [ ] Show workflow progress tracker using `currentStage`
- [ ] Update indent list filtering to support new status values
- [ ] Add tooltips explaining why edit is disabled
- [ ] Update error handling for edit API calls
- [ ] Add visual indicators for different statuses
- [ ] Test workflow: Create → Submit → Request Change → Edit → Resubmit → Approve → Create Tender → Try to Edit (should be blocked)
- [ ] Update status dashboard to show indents by `currentStatus` and `currentStage`

---

## Testing Scenarios

### Scenario 1: Normal Workflow
1. Create new indent → Check `currentStatus = 'DRAFT'`, `isEditable = true`
2. Submit for approval → Check `currentStatus = 'IN_APPROVAL'`, `isEditable = false`
3. Approve indent → Check `currentStatus = 'APPROVED'`
4. Create tender → Check `isLockedForTender = true`, edit button disabled
5. Try to edit → Should show error message

### Scenario 2: Change Request Workflow
1. Create and submit indent → `isEditable = false`
2. Request change as approver → Check `currentStatus = 'CHANGE_REQUESTED'`, `isEditable = true`
3. Edit indent → Should work, `version` should increment
4. Resubmit → `isEditable = false` again

### Scenario 3: Version Tracking
1. Create indent → `version = 1`
2. Request change and edit → `version = 2`
3. Request change and edit again → `version = 3`
4. Display version history in UI

---

## Database Migration

Run the SQL migration script: [database_migration_indent_bug_fixes.sql](database_migration_indent_bug_fixes.sql)

This script:
- Adds all new columns with defaults
- Updates existing records with appropriate default values
- Creates indexes for performance
- Adds documentation comments

---

## Backend Files Modified

### Entity Layer
- [IndentCreation.java](src/main/java/com/astro/entity/ProcurementModule/IndentCreation.java)
  - Added 8 new fields for tracking

### DTO Layer
- [IndentCreationResponseDTO.java](src/main/java/com/astro/dto/workflow/ProcurementDtos/IndentDto/IndentCreationResponseDTO.java)
  - Added 8 new response fields

### Service Layer
- [IndentCreationServiceImpl.java](src/main/java/com/astro/service/impl/IndentCreationServiceImpl.java)
  - Added edit validation logic
  - Added version increment on update
  - Added field initialization on create
  - Updated mapping method

- [WorkflowServiceImpl.java](src/main/java/com/astro/service/impl/WorkflowServiceImpl.java)
  - Added status update on approval
  - Added isEditable flag management
  - Added status tracking in workflow transitions

- [TenderRequestServiceImpl.java](src/main/java/com/astro/service/impl/TenderRequestServiceImpl.java)
  - Added indent locking when tender is created

---

## Support and Questions

If you have any questions or need clarification on any of these changes, please reach out to the backend team.

### Common Questions

**Q: What happens to existing indents in the database?**
A: The migration script sets default values. All existing indents will have `isEditable = true`, `version = 1`, `currentStatus = 'DRAFT'`

**Q: Can a locked indent ever be unlocked?**
A: No, once a tender is created, the indent is permanently locked. This is by design to maintain data integrity.

**Q: Does version number reset?**
A: No, version always increments. It's a historical counter of how many times the indent has been updated.

**Q: What if indent is cancelled?**
A: Cancelled indents have `currentStatus = 'CANCELLED'` and `isEditable = false`

---

## Summary of Key Changes

1. **Edit Control**: Edit button must check `isEditable && !isLockedForTender`
2. **Status Display**: Use `currentStatus` and `currentStage` for rich status information
3. **Version Tracking**: Display `version` number on UI
4. **Lock Indicator**: Show warning when `isLockedForTender = true`
5. **Workflow Tracker**: Implement visual progress using `currentStage` and `approvalLevel`
6. **Error Handling**: Handle new validation error messages from backend

---

**End of Document**
