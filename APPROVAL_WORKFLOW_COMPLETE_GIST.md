# 🔄 IIA Backend - Complete Approval Workflow System Gist

## 📋 Table of Contents
1. [Overview](#overview)
2. [Workflow Types](#workflow-types)
3. [Branch System](#branch-system)
4. [Condition Types](#condition-types)
5. [How It Works End-to-End](#how-it-works-end-to-end)
6. [Admin Panel Configuration](#admin-panel-configuration)
7. [Backend Processing Flow](#backend-processing-flow)
8. [Database Architecture](#database-architecture)
9. [Key Code Locations](#key-code-locations)

---

## 🎯 Overview

The IIA Backend implements a **sophisticated multi-level approval workflow system** that routes procurement requests (Indents, Tenders, POs, etc.) through different approval chains based on configurable conditions like amount, category, location, and more.

### Key Capabilities
- ✅ **5 Primary Workflows** (Indent, Tender, Tender Evaluator, Purchase Order, Contingency Purchase)
- ✅ **Multiple Branches** per workflow (Default, High Value, Urgent, etc.)
- ✅ **8 Condition Types** for routing logic
- ✅ **Admin Panel** for non-technical configuration
- ✅ **Email Notifications** at each approval step
- ✅ **Complete Audit Trail** of all approvals

---

## 🔀 Workflow Types

The system supports **5 primary procurement workflows**:

### 1. **Indent Approval Workflow** (Workflow ID: 1)
- **Purpose**: Approve material requisition requests
- **Key Field**: `totalIntentValue` (amount)
- **Branches**: Default, High Value, Urgent
- **Example Conditions**:
  - Amount ≤ ₹50,000 → Department Manager
  - Amount > ₹50,000 → Finance Manager → Director

### 2. **Tender Approval Workflow** (Workflow ID: 2)
- **Purpose**: Approve tender requests
- **Key Field**: `totalTenderValue` (amount)
- **Branches**: Default, High Value
- **Example Conditions**:
  - Amount ≤ ₹1,00,000 → Procurement Manager
  - Amount > ₹1,00,000 → CFO

### 3. **Tender Evaluator Workflow** (Workflow ID: 7)
- **Purpose**: Evaluate vendor bids
- **Key Field**: `totalTenderValue`, `bidType`
- **Branches**: Default, High Value
- **Example Conditions**:
  - Bid Type = "Open" → Evaluation Committee A
  - Bid Type = "Sealed" → Sealed Bid Committee

### 4. **Purchase Order Workflow** (Workflow ID: 3)
- **Purpose**: Approve purchase orders
- **Key Field**: `totalValueOfPo` (amount)
- **Branches**: Default, High Value
- **Example Conditions**:
  - Amount ≤ ₹2,00,000 → Procurement Head
  - Amount > ₹2,00,000 → CFO → Director

### 5. **Contingency Purchase Workflow** (Workflow ID: TBD)
- **Purpose**: Approve emergency purchases
- **Key Field**: `totalCpValue` (amount), `projectName`
- **Branches**: Default
- **Example Conditions**:
  - Project Name = "Empty" → Route A
  - Project Name = "Not Empty" → Route B

---

## 🌿 Branch System

Each workflow can have **multiple approval branches** with different routing logic.

### Common Branches

| Branch Code | Branch Name | Description | Typical Use |
|------------|-------------|-------------|-------------|
| `DEFAULT` | Default Branch | No conditions, fallback route | All requests not matching other conditions |
| `HIGH_VALUE` | High Value Branch | Amount-based routing | Requests above certain threshold |
| `URGENT` | Urgent Branch | Priority handling | Urgent/emergency requests |
| `COMPUTER` | Computer Branch | Category-based routing | Computer-related purchases |
| `NON_COMPUTER` | Non-Computer Branch | Category-based routing | Non-computer purchases |
| `BANGALORE` | Bangalore Branch | Location-based routing | Bangalore office requests |
| `NON_BANGALORE` | Non-Bangalore Branch | Location-based routing | Other locations |

### How Branches Are Stored

**Entity**: `WorkflowBranchMaster`
```java
branchId: 5 (auto-generated)
workflowId: 1 (Indent Workflow)
branchCode: "HIGH_VALUE"
branchName: "High Value Indents"
branchDescription: "Indents over 1 Lakh rupees"
conditionType: "AMOUNT"
conditionConfig: '{"minAmount": 100000, "maxAmount": 500000}'
isActive: true
displayOrder: 2
```

---

## 🎛️ Condition Types

The system supports **8 types of conditional routing**:

### 1. **DEFAULT** - No Conditions
```
conditionType: "DEFAULT"
conditionConfig: null
Logic: Fallback route if no other condition matches
```

### 2. **AMOUNT-BASED** - Route by Value
```
conditionType: "AMOUNT"
conditionConfig: '{"minAmount": 100000, "maxAmount": 500000}'
Logic: If totalPrice >= 100000 AND <= 500000 → Use this branch
```

**Backend Condition Keys:**
- `TotalPriceOfAllMaterials` (≤ threshold)
- `TotalPriceOfAllMaterialsAnd` (> threshold)

### 3. **CATEGORY-BASED** - Route by Material Category
```
conditionType: "CATEGORY"
conditionConfig: '{"categories": ["Electronics", "Hardware", "Software"]}'
Logic: If materialCategory in list → Use this branch
```

**Backend Condition Key:**
- `MaterialCategory`

### 4. **COMPUTER vs NON-COMPUTER** - Specialized Category
```
conditionType: "CATEGORY"
conditionConfig: '{"category": "Computer"}'
Logic: If category = "Computer" → Route to IT Manager
      If category = "Non-Computer" → Route to Admin Manager
```

### 5. **LOCATION-BASED** - Route by Consignee Location
```
conditionType: "LOCATION"
conditionConfig: '{"locations": ["Bangalore", "Head Office"]}'
Logic: If consignesLocation in list → Use this branch
```

**Backend Condition Key:**
- `ConsignesLocation`

### 6. **PROJECT-BASED** - Route by Project Existence
```
conditionType: "CUSTOM"
conditionConfig: '{"projectCheck": "Not Empty"}'
Logic: If projectName != null → Route A
      If projectName == null → Route B
```

**Backend Condition Key:**
- `ProjectName`

### 7. **BID TYPE** - Route by Tender Bid Type
```
conditionType: "CUSTOM"
conditionConfig: '{"bidType": "open"}'
Logic: If bidType = "open" → Evaluation Committee
      If bidType = "sealed" → Sealed Bid Committee
```

**Backend Condition Key:**
- `bidType`

### 8. **COMBINED CONDITIONS** - Multiple Criteria
```
conditionType: "CUSTOM"
conditionConfig: '{"category": "Electronics", "location": "Head Office"}'
Logic: If category = "Electronics" AND location = "Head Office" → Route
```

**Backend Condition Keys:**
- `materialCategoryAndconsignesLocation` (e.g., "Electronics+Head Office")
- `TotalPriceOfAllMaterialsAndDept` (e.g., "100000(Engineering)")

---

## 🔄 How It Works End-to-End

### Complete Flow: Indent Approval Example

```
┌─────────────────────────────────────────────────────────────┐
│ STEP 1: USER SUBMITS INDENT                                 │
├─────────────────────────────────────────────────────────────┤
│ UI: POST /indent/create                                     │
│ Data:                                                       │
│   - Amount: ₹75,000                                         │
│   - Category: Electronics                                   │
│   - Location: Bangalore                                     │
│   - Project: Project Alpha                                  │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ BACKEND: Save to Database                                   │
├─────────────────────────────────────────────────────────────┤
│ Table: indent_creation                                      │
│ Record:                                                     │
│   indentId: "IND-2025-001"                                  │
│   totalIntentValue: 75000                                   │
│   currentStatus: "SUBMITTED"                                │
│   isEditable: true                                          │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ STEP 2: INITIATE WORKFLOW                                   │
├─────────────────────────────────────────────────────────────┤
│ UI/Auto: POST /initiateWorkflow                             │
│ Params:                                                     │
│   - requestId: "IND-2025-001"                               │
│   - workflowName: "IndentWorkflow"                          │
│   - createdBy: 123 (User ID)                                │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ BACKEND: Create First Transition                            │
├─────────────────────────────────────────────────────────────┤
│ Table: WORKFLOW_TRANSITION                                  │
│ Record:                                                     │
│   workflowTransitionId: 501                                 │
│   requestId: "IND-2025-001"                                 │
│   workflowName: "Indent Workflow"                           │
│   currentRole: "Department Manager"                         │
│   nextAction: "PENDING"                                     │
│   status: "IN_PROGRESS"                                     │
│   order: 1, subOrder: 1                                     │
│                                                             │
│ Action: Send email to Department Manager                    │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ STEP 3: DEPARTMENT MANAGER REVIEWS                          │
├─────────────────────────────────────────────────────────────┤
│ UI: GET /pendingWorkflowTransition                          │
│ Shows: Pending indent IND-2025-001                          │
│                                                             │
│ Manager clicks "Approve"                                    │
│ UI: POST /performTransitionAction                           │
│ Data:                                                       │
│   - action: "Approve"                                       │
│   - remarks: "Approved. Proceed."                           │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ BACKEND: Process Approval & Route                           │
├─────────────────────────────────────────────────────────────┤
│ Method: WorkflowServiceImpl.performTransitionAction()      │
│                                                             │
│ STEP A: Get request data                                   │
│   - Fetch IndentCreation (IND-2025-001)                    │
│   - Extract: totalIntentValue = 75000                      │
│                                                             │
│ STEP B: Evaluate conditions (nextTransitionDto())          │
│   Check condition 1:                                       │
│     "TotalPriceOfAllMaterials" <= 50000?                   │
│     75000 <= 50000 → NO                                    │
│                                                             │
│   Check condition 2:                                       │
│     "TotalPriceOfAllMaterialsAnd" > 50000?                 │
│     75000 > 50000 → YES ✓                                  │
│     → Route to "Finance Manager"                           │
│                                                             │
│ STEP C: Update current transition                          │
│   - status: "COMPLETED"                                    │
│   - actionBy: 123 (Department Manager's User ID)           │
│   - approvedDate: 2025-01-04 10:30:00                      │
│                                                             │
│ STEP D: Create next transition                             │
│   Table: WORKFLOW_TRANSITION                               │
│   Record:                                                  │
│     workflowTransitionId: 502                              │
│     requestId: "IND-2025-001"                              │
│     currentRole: "Finance Manager"                         │
│     nextAction: "PENDING"                                  │
│     status: "IN_PROGRESS"                                  │
│     order: 1, subOrder: 2                                  │
│                                                             │
│ STEP E: Send email to Finance Manager                      │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ STEP 4: FINANCE MANAGER APPROVES                            │
├─────────────────────────────────────────────────────────────┤
│ (Repeat Step 3 process)                                    │
│                                                             │
│ Condition Check:                                           │
│   Amount 75000 < 100000 → No Director approval needed      │
│   Next role: null (Final approval)                         │
│                                                             │
│ Update current transition:                                 │
│   - status: "COMPLETED"                                    │
│   - nextAction: null                                       │
│                                                             │
│ Update indent_creation:                                    │
│   - currentStatus: "APPROVED"                              │
│   - isEditable: false                                      │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ STEP 5: WORKFLOW COMPLETE                                   │
├─────────────────────────────────────────────────────────────┤
│ Indent is now APPROVED                                     │
│ User can create Tender Request based on this Indent        │
│ Workflow audit trail saved in WORKFLOW_TRANSITION table    │
└─────────────────────────────────────────────────────────────┘
```

### Alternative Flow: Rejection

```
If any approver clicks "Reject":
  ↓
Backend: rejectTransition()
  - Mark ALL transitions as "REJECTED"
  - Set indent status: "REJECTED"
  - Send rejection emails to all involved users
  - User can re-submit from scratch
```

### Alternative Flow: Change Requested

```
If any approver clicks "Request Changes":
  ↓
Backend: requestChangeTransition()
  - Set nextRole: "Indent Creator"
  - Set status: "CHANGE_REQUESTED"
  - Send email to original creator
  - Creator can edit and re-submit
  - Workflow restarts from beginning
```

---

## ⚙️ Admin Panel Configuration

### How Admin Configures Workflows (UI → Backend)

#### **Scenario: Configure "High Value Indent" Branch**

```
┌─────────────────────────────────────────────────────────────┐
│ ADMIN UI: Workflow Management Page                          │
├─────────────────────────────────────────────────────────────┤
│ 1. Select Workflow: "Indent Approval Workflow"              │
│ 2. Click "Add Branch"                                       │
│ 3. Fill Form:                                               │
│    - Branch Name: "High Value Indents"                      │
│    - Branch Code: "HIGH_VALUE"                              │
│    - Condition Type: "Amount Based"                         │
│    - Min Amount: ₹1,00,000                                  │
│    - Max Amount: ₹5,00,000                                  │
│    - Display Order: 2                                       │
│ 4. Click "Save"                                             │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ BACKEND: POST /api/admin/approvers/workflows/1/branches     │
├─────────────────────────────────────────────────────────────┤
│ Request Body:                                               │
│ {                                                           │
│   "branchCode": "HIGH_VALUE",                               │
│   "branchName": "High Value Indents",                       │
│   "branchDescription": "Indents between 1L to 5L",          │
│   "conditionType": "AMOUNT",                                │
│   "conditionConfig": "{                                     │
│     \"minAmount\": 100000,                                  │
│     \"maxAmount\": 500000                                   │
│   }",                                                       │
│   "displayOrder": 2,                                        │
│   "isActive": true                                          │
│ }                                                           │
│                                                             │
│ Controller: ApproverController.createWorkflowBranch()      │
│ Service: Save to workflow_branch_master table               │
│ Response: branchId = 5                                      │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ ADMIN UI: Add Approvers for This Branch                     │
├─────────────────────────────────────────────────────────────┤
│ 1. Click "Manage" on HIGH_VALUE branch                      │
│ 2. Click "Add Approver"                                     │
│ 3. Fill Form (Level 1):                                     │
│    - Role: "Finance Manager"                                │
│    - Approval Level: 1                                      │
│    - Approval Sequence: 1                                   │
│    - Is Mandatory: Yes                                      │
│ 4. Click "Save"                                             │
│                                                             │
│ 5. Click "Add Approver" again (Level 2):                    │
│    - Role: "CFO"                                            │
│    - Approval Level: 2                                      │
│    - Approval Sequence: 1                                   │
│    - Is Mandatory: Yes                                      │
│ 6. Click "Save"                                             │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ BACKEND: POST /api/admin/approvers (2 calls)                │
├─────────────────────────────────────────────────────────────┤
│ Call 1 (Finance Manager):                                  │
│ {                                                           │
│   "workflowId": 1,                                          │
│   "branchId": 5,                                            │
│   "roleId": 10,                                             │
│   "roleName": "Finance Manager",                            │
│   "approvalLevel": 1,                                       │
│   "approvalSequence": 1,                                    │
│   "isMandatory": true,                                      │
│   "status": "Active"                                        │
│ }                                                           │
│ → System generates: approverCode = "W1-B5-001"              │
│ → Save to approver_master table                             │
│                                                             │
│ Call 2 (CFO):                                               │
│ {                                                           │
│   "workflowId": 1,                                          │
│   "branchId": 5,                                            │
│   "roleId": 15,                                             │
│   "roleName": "CFO",                                        │
│   "approvalLevel": 2,                                       │
│   "approvalSequence": 1,                                    │
│   "isMandatory": true,                                      │
│   "status": "Active"                                        │
│ }                                                           │
│ → System generates: approverCode = "W1-B5-002"              │
│ → Save to approver_master table                             │
└──────────────────────────┬──────────────────────────────────┘
                           ↓
┌─────────────────────────────────────────────────────────────┐
│ RESULT: Configuration Active                                │
├─────────────────────────────────────────────────────────────┤
│ Now when user submits Indent with amount ₹2,50,000:        │
│   1. System checks all branches for workflow 1              │
│   2. Finds HIGH_VALUE branch condition matches:             │
│      2,50,000 >= 1,00,000 AND <= 5,00,000 ✓                │
│   3. Gets approvers for branchId = 5                        │
│   4. Routes: Finance Manager (Level 1) → CFO (Level 2)      │
│   5. Executes approval chain automatically                  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔧 Backend Processing Flow

### Key Backend Components

#### **1. Controller Layer**
```java
WorkflowController.java
  ├── POST /initiateWorkflow
  ├── POST /performTransitionAction
  ├── GET /pendingWorkflowTransition
  └── GET /completedIndentWorkflowTransition

ApproverController.java (Admin Panel)
  ├── POST /api/admin/approvers/workflows/{id}/branches
  ├── POST /api/admin/approvers
  ├── PUT /api/admin/approvers/{id}
  └── GET /api/admin/approvers/workflows/{id}/branches
```

#### **2. Service Layer**
```java
WorkflowServiceImpl.java
  ├── initiateWorkflow()         → Create first transition
  ├── performTransitionAction()  → Process approve/reject/change
  ├── nextTransitionDto()        → CORE: Conditional routing logic
  ├── approveTransition()        → Handle approval
  ├── rejectTransition()         → Handle rejection
  └── requestChangeTransition()  → Handle change request
```

#### **3. Repository Layer**
```java
WorkflowTransitionRepository.java
  ├── findByRequestIdAndWorkflowName()
  ├── findApprovedIndentRequestIds()
  └── findByNextActionAndNextRole()

WorkflowBranchMasterRepository.java
  ├── findByWorkflowIdAndIsActiveTrue()
  └── findByBranchCode()

ApproverMasterRepository.java
  ├── findByWorkflowIdAndBranchIdOrderByApprovalLevelAsc()
  └── findByApproverCode()
```

### Critical Method: `nextTransitionDto()` (Conditional Routing)

**Location**: `WorkflowServiceImpl.java` lines 1249-1468

**Purpose**: Determines which approval role comes next based on conditions

**Algorithm**:
```java
public TransitionDto nextTransitionDto(
    List<TransitionDto> nextTransitionDtoList,
    String workflowName,
    String requestId
) {
    // If only 1 option and no conditions → return it
    if (nextTransitionDtoList.size() == 1 && !hasConditions) {
        return nextTransitionDtoList.get(0);
    }

    // Switch based on workflow type
    switch (workflowName) {
        case "INDENT WORKFLOW":
            // Get indent data
            IndentCreationResponseDTO indent = indentService.getIndentById(requestId);

            // Loop through possible transitions
            for (TransitionDto dto : nextTransitionDtoList) {
                // Get condition from TRANSITION_CONDITION_MASTER
                TransitionConditionMaster condition =
                    getConditionByTransition(dto.getTransitionId());

                String conditionKey = condition.getConditionKey();
                String conditionValue = condition.getConditionValue();

                // Evaluate condition
                if ("TotalPriceOfAllMaterials".equals(conditionKey)) {
                    if (indent.getTotalPrice() <= Double.valueOf(conditionValue)) {
                        return dto; // Match found
                    }
                }

                if ("MaterialCategory".equals(conditionKey)) {
                    if (indent.getCategory().equals(conditionValue)) {
                        return dto; // Match found
                    }
                }

                // ... more conditions
            }

            // No match → return default transition
            return getDefaultTransition(nextTransitionDtoList);

        case "TENDER WORKFLOW":
            // Similar logic for tender

        // ... other workflows
    }
}
```

**Example Execution**:
```
Input:
  - nextTransitionDtoList = [
      {transitionId: 5, nextRole: "Finance Manager"},
      {transitionId: 6, nextRole: "Director"}
    ]
  - workflowName = "INDENT WORKFLOW"
  - requestId = "IND-2025-001"

Execution:
  1. Get indent data: totalPrice = 75000
  2. Check transition 5:
     - Condition: "TotalPriceOfAllMaterials" <= "50000"
     - 75000 <= 50000? NO
  3. Check transition 6:
     - Condition: "TotalPriceOfAllMaterialsAnd" > "50000"
     - 75000 > 50000? YES ✓
  4. Return: {transitionId: 6, nextRole: "Director"}

Result: Request routed to Director
```

---

## 🗄️ Database Architecture

### Core Tables

#### **1. WORKFLOW_MASTER**
```sql
CREATE TABLE WORKFLOW_MASTER (
  WORKFLOW_ID INT PRIMARY KEY AUTO_INCREMENT,
  WORKFLOW_NAME VARCHAR(100) NOT NULL
);

-- Data:
-- 1 | Indent Workflow
-- 2 | Tender Workflow
-- 3 | PO Workflow
-- 7 | Tender Evaluator Workflow
```

#### **2. TRANSITION_MASTER**
```sql
CREATE TABLE TRANSITION_MASTER (
  TRANSITION_ID INT PRIMARY KEY AUTO_INCREMENT,
  WORKFLOW_ID INT NOT NULL,
  ORDER_NUMBER INT NOT NULL,        -- Main sequence (1, 2, 3...)
  SUB_ORDER INT NOT NULL,            -- Sub-sequence within order
  CURRENT_ROLE_ID INT NOT NULL,
  NEXT_ROLE_ID INT NULL,             -- null = final step
  CREATED_BY VARCHAR(100),
  FOREIGN KEY (WORKFLOW_ID) REFERENCES WORKFLOW_MASTER(WORKFLOW_ID)
);

-- Example for Indent Workflow:
-- 1 | 1 | 1 | 1 | 5 (Dept Mgr) | 10 (Finance Mgr)
-- 2 | 1 | 1 | 2 | 10 (Finance Mgr) | 15 (Director)
-- 3 | 1 | 1 | 3 | 15 (Director) | NULL
```

#### **3. TRANSITION_CONDITION_MASTER**
```sql
CREATE TABLE TRANSITION_CONDITION_MASTER (
  CONDITION_ID INT PRIMARY KEY AUTO_INCREMENT,
  WORKFLOW_ID INT NOT NULL,
  TRANSITION_ID INT NOT NULL,
  CONDITION_KEY VARCHAR(200) NOT NULL,    -- Type of condition
  CONDITION_VALUE VARCHAR(500),           -- Threshold/value to check
  FOREIGN KEY (WORKFLOW_ID) REFERENCES WORKFLOW_MASTER(WORKFLOW_ID),
  FOREIGN KEY (TRANSITION_ID) REFERENCES TRANSITION_MASTER(TRANSITION_ID)
);

-- Example conditions:
-- 1 | 1 | 1 | TotalPriceOfAllMaterials | 50000
-- 2 | 1 | 2 | TotalPriceOfAllMaterialsAnd | 50000
-- 3 | 1 | 5 | MaterialCategory | Electronics
-- 4 | 1 | 6 | ConsignesLocation | Bangalore
```

#### **4. WORKFLOW_TRANSITION** (Runtime Tracking)
```sql
CREATE TABLE WORKFLOW_TRANSITION (
  WORKFLOW_TRANSITION_ID INT PRIMARY KEY AUTO_INCREMENT,
  REQUEST_ID VARCHAR(100) NOT NULL,      -- IND-2025-001
  WORKFLOW_NAME VARCHAR(100) NOT NULL,
  CURRENT_ROLE VARCHAR(100),
  NEXT_ACTION VARCHAR(50),               -- PENDING, null
  STATUS VARCHAR(50),                    -- IN_PROGRESS, COMPLETED, REJECTED
  ORDER_NUMBER INT,
  SUB_ORDER INT,
  ACTION_BY INT,                         -- User ID who acted
  APPROVED_DATE DATETIME,
  REMARKS TEXT,
  CREATED_DATE DATETIME DEFAULT NOW()
);

-- Example workflow execution:
-- 501 | IND-2025-001 | Indent Workflow | Dept Manager | PENDING | IN_PROGRESS | 1 | 1
-- 502 | IND-2025-001 | Indent Workflow | Finance Mgr | PENDING | IN_PROGRESS | 1 | 2
-- 503 | IND-2025-001 | Indent Workflow | Director | NULL | COMPLETED | 1 | 3
```

#### **5. workflow_branch_master** (Admin Panel)
```sql
CREATE TABLE workflow_branch_master (
  branch_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  workflow_id INT NOT NULL,
  branch_code VARCHAR(50) NOT NULL,
  branch_name VARCHAR(100) NOT NULL,
  branch_description TEXT,
  condition_type VARCHAR(50),            -- AMOUNT, CATEGORY, LOCATION, CUSTOM, DEFAULT
  condition_config JSON,                 -- Stores condition parameters
  is_active BOOLEAN DEFAULT TRUE,
  display_order INT,
  created_by VARCHAR(100),
  created_date DATETIME DEFAULT NOW(),
  UNIQUE (workflow_id, branch_code)
);

-- Example:
-- 5 | 1 | HIGH_VALUE | High Value Indents | ... | AMOUNT | {"minAmount":100000} | 1 | 2
```

#### **6. approver_master** (Admin Panel)
```sql
CREATE TABLE approver_master (
  approver_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  approver_code VARCHAR(50) UNIQUE,      -- W1-B5-001
  workflow_id INT NOT NULL,
  branch_id BIGINT NOT NULL,
  role_id INT NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  approval_level INT NOT NULL,           -- 1, 2, 3 (hierarchy)
  approval_sequence INT,                 -- Order within level
  is_parallel_approval BOOLEAN,          -- true=OR logic, false=AND logic
  is_mandatory BOOLEAN DEFAULT TRUE,
  status VARCHAR(20) DEFAULT 'Active',   -- Active, Inactive
  created_by VARCHAR(100),
  created_date DATETIME DEFAULT NOW(),
  FOREIGN KEY (workflow_id) REFERENCES WORKFLOW_MASTER(WORKFLOW_ID),
  FOREIGN KEY (branch_id) REFERENCES workflow_branch_master(branch_id)
);

-- Example:
-- 1 | W1-B5-001 | 1 | 5 | 10 | Finance Manager | 1 | 1 | 0 | 1 | Active
-- 2 | W1-B5-002 | 1 | 5 | 15 | CFO | 2 | 1 | 0 | 1 | Active
```

### Table Relationships

```
WORKFLOW_MASTER
  └── TRANSITION_MASTER
      ├── TRANSITION_CONDITION_MASTER (conditions for routing)
      └── WORKFLOW_TRANSITION (runtime execution)

  └── workflow_branch_master
      └── approver_master (approval hierarchy per branch)

INDENT_CREATION / TENDER_REQUEST / PURCHASE_ORDER
  └── WORKFLOW_TRANSITION (linked by requestId)
```

---

## 📂 Key Code Locations

### Entities

| File | Path | Purpose |
|------|------|---------|
| `WorkflowMaster.java` | `com.astro.entity` | Workflow definitions |
| `TransitionMaster.java` | `com.astro.entity` | Transition mappings |
| `TransitionConditionMaster.java` | `com.astro.entity` | Conditional routing rules |
| `WorkflowTransition.java` | `com.astro.entity` | Runtime tracking |
| `WorkflowBranchMaster.java` | `com.astro.entity.AdminPanel` | Branch config (Admin Panel) |
| `ApproverMaster.java` | `com.astro.entity.AdminPanel` | Approver hierarchy (Admin Panel) |

### Controllers

| File | Path | Key Endpoints |
|------|------|---------------|
| `WorkflowController.java` | `com.astro.controller` | `/initiateWorkflow`, `/performTransitionAction` |
| `ApproverController.java` | `com.astro.controller.AdminPanel` | `/api/admin/approvers/*` |

### Services

| File | Path | Key Methods |
|------|------|-------------|
| `WorkflowServiceImpl.java` | `com.astro.service.impl` | `nextTransitionDto()` (lines 1249-1468), `approveTransition()` |
| `IndentCreationServiceImpl.java` | `com.astro.service.impl` | `getIndentById()` (provides data for conditions) |

### Repositories

| File | Path | Key Queries |
|------|------|-------------|
| `WorkflowTransitionRepository.java` | `com.astro.repository` | `findApprovedIndentRequestIds()`, `findByNextActionAndNextRole()` |
| `WorkflowBranchMasterRepository.java` | `com.astro.repository.AdminPanel` | `findByWorkflowIdAndIsActiveTrue()` |
| `ApproverMasterRepository.java` | `com.astro.repository.AdminPanel` | `findByWorkflowIdAndBranchId()` |

---

## 🎯 Real-World Example: Purchase Order Workflow

### Admin Configuration

**Admin creates 2 branches for PO Workflow:**

**Branch 1: DEFAULT**
```
branchCode: "DEFAULT"
conditionType: "DEFAULT"
conditionConfig: null
Approvers: Procurement Manager (Level 1)
```

**Branch 2: HIGH_VALUE**
```
branchCode: "HIGH_VALUE"
conditionType: "AMOUNT"
conditionConfig: {"minAmount": 200000}
Approvers:
  - Procurement Manager (Level 1)
  - CFO (Level 2)
  - Director (Level 3)
```

### User Submits PO

**Case 1: PO Amount = ₹1,50,000**
```
1. User creates PO with totalValueOfPo = 150000
2. POST /initiateWorkflow (requestId = PO-2025-050)
3. Backend checks branches:
   - DEFAULT: No condition (fallback)
   - HIGH_VALUE: 150000 >= 200000? NO
4. Route to DEFAULT branch
5. Approval chain: Procurement Manager only
6. After 1 approval → APPROVED
```

**Case 2: PO Amount = ₹3,50,000**
```
1. User creates PO with totalValueOfPo = 350000
2. POST /initiateWorkflow (requestId = PO-2025-051)
3. Backend checks branches:
   - DEFAULT: No condition (fallback)
   - HIGH_VALUE: 350000 >= 200000? YES ✓
4. Route to HIGH_VALUE branch
5. Approval chain:
   - Procurement Manager (Level 1) → Approves
   - CFO (Level 2) → Approves
   - Director (Level 3) → Approves
6. After 3 approvals → APPROVED
```

---

## 🔍 Debugging & Monitoring

### Check Workflow Status
```sql
-- See all transitions for a request
SELECT * FROM WORKFLOW_TRANSITION
WHERE REQUEST_ID = 'IND-2025-001'
ORDER BY ORDER_NUMBER, SUB_ORDER;

-- Find pending approvals for a role
SELECT wt.*, it.totalIntentValue
FROM WORKFLOW_TRANSITION wt
JOIN indent_creation it ON wt.REQUEST_ID = it.indentId
WHERE wt.NEXT_ACTION = 'PENDING'
  AND wt.CURRENT_ROLE = 'Finance Manager';

-- Get all approved indents
SELECT REQUEST_ID FROM WORKFLOW_TRANSITION
WHERE WORKFLOW_NAME = 'Indent Workflow'
  AND STATUS = 'COMPLETED'
  AND NEXT_ACTION IS NULL;
```

### Admin Panel Queries
```sql
-- See all branches for a workflow
SELECT * FROM workflow_branch_master
WHERE workflow_id = 1
  AND is_active = 1
ORDER BY display_order;

-- See approval hierarchy for a branch
SELECT * FROM approver_master
WHERE branch_id = 5
  AND status = 'Active'
ORDER BY approval_level, approval_sequence;
```

---

## 📊 Summary Diagram

```
┌────────────────────────────────────────────────────────────────┐
│                     APPROVAL WORKFLOW SYSTEM                    │
└────────────────────────────────────────────────────────────────┘

┌─────────────┐
│   ADMIN     │ Configures workflows via Admin Panel
└──────┬──────┘
       │
       ↓
┌──────────────────────────────────────────────────────────┐
│  ADMIN PANEL (UI)                                        │
│  1. Create workflow branches (Default, High Value, etc.) │
│  2. Set conditions (Amount, Category, Location)          │
│  3. Add approvers with hierarchy (Level 1, 2, 3...)      │
└──────────────────────┬───────────────────────────────────┘
                       │
                       ↓ Saves to DB
┌──────────────────────────────────────────────────────────┐
│  DATABASE                                                │
│  • workflow_branch_master (Branch configs)               │
│  • approver_master (Approver hierarchy)                  │
│  • TRANSITION_CONDITION_MASTER (Routing rules)           │
└──────────────────────┬───────────────────────────────────┘
                       │
                       │ Used by
                       ↓
┌─────────────┐
│    USER     │ Submits request (Indent, Tender, PO)
└──────┬──────┘
       │
       ↓
┌──────────────────────────────────────────────────────────┐
│  USER FLOW                                               │
│  1. Create Indent (Amount: ₹75,000)                      │
│  2. System initiates workflow                            │
│  3. Routes to approvers based on conditions              │
└──────────────────────┬───────────────────────────────────┘
                       │
                       ↓
┌──────────────────────────────────────────────────────────┐
│  BACKEND PROCESSING                                      │
│  • WorkflowService.initiateWorkflow()                    │
│  • Create first WORKFLOW_TRANSITION                      │
│  • Send email to first approver                          │
└──────────────────────┬───────────────────────────────────┘
                       │
                       ↓
┌─────────────┐
│  APPROVER   │ Reviews & approves/rejects
└──────┬──────┘
       │
       ↓
┌──────────────────────────────────────────────────────────┐
│  APPROVAL ACTION                                         │
│  • WorkflowService.performTransitionAction()             │
│  • nextTransitionDto() evaluates conditions              │
│  • Routes to next approver or completes                  │
└──────────────────────┬───────────────────────────────────┘
                       │
                       ↓
┌──────────────────────────────────────────────────────────┐
│  FINAL STATE                                             │
│  • All approvals done → Status: APPROVED                 │
│  • Any rejection → Status: REJECTED                      │
│  • Complete audit trail in WORKFLOW_TRANSITION           │
└──────────────────────────────────────────────────────────┘
```

---

## ✅ Summary

The IIA Backend Approval Workflow System provides:

1. **5 Workflows**: Indent, Tender, Tender Evaluator, PO, CP
2. **Flexible Branching**: Multiple approval paths per workflow
3. **8 Condition Types**: Amount, Category, Location, Project, Bid Type, etc.
4. **Admin Configuration**: Non-technical workflow setup via UI
5. **Conditional Routing**: Automatic routing based on request data
6. **Audit Trail**: Complete tracking in WORKFLOW_TRANSITION table
7. **Email Notifications**: Automatic alerts at each step
8. **State Management**: Proper status tracking (Pending, Approved, Rejected)

**Key Insight**: The system uses a **transition-based architecture** where each approval step creates a new transition record, allowing flexible routing and complete visibility into the approval chain.

---

**Generated**: 2025-01-04
**Backend Version**: IIA Backend Production
**Author**: System Analysis
