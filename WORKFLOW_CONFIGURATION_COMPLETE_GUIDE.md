# Complete Workflow Configuration Guide - Backend Analysis

## ✅ BACKEND STATUS: FULLY READY

The backend **ALREADY HAS** complete workflow and approval management system with:
- ✅ Dynamic workflow branches
- ✅ Price/amount-based routing
- ✅ Category-based routing (Computer/Non-Computer)
- ✅ Location-based routing (Bangalore/Non-Bangalore)
- ✅ Project-based routing (Under Project/Not Under Project)
- ✅ Multi-level approvals
- ✅ Role-based approvers
- ✅ Parallel/Sequential approval logic

---

## Database Tables (Already Created):

### 1. **workflow_branch_master**
Stores all branches for each workflow (conditions/rules)

```sql
CREATE TABLE workflow_branch_master (
  branch_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  workflow_id INT NOT NULL,
  branch_code VARCHAR(50) NOT NULL,
  branch_name VARCHAR(200) NOT NULL,
  branch_description TEXT,
  condition_type VARCHAR(50),  -- CATEGORY, LOCATION, AMOUNT, PROJECT, CUSTOM
  condition_config JSON,        -- Stores branch conditions
  is_active BOOLEAN DEFAULT TRUE,
  display_order INT DEFAULT 0,
  created_by VARCHAR(100),
  created_date TIMESTAMP,
  UNIQUE KEY (workflow_id, branch_code)
);
```

### 2. **approver_master**
Stores approvers for each branch with hierarchy

```sql
CREATE TABLE approver_master (
  approver_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  approver_code VARCHAR(50) UNIQUE NOT NULL,
  workflow_id INT NOT NULL,
  branch_id BIGINT NOT NULL,
  role_id INT NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  approval_level INT DEFAULT 1,
  approval_sequence INT DEFAULT 1,
  is_parallel_approval BOOLEAN DEFAULT FALSE,
  is_mandatory BOOLEAN DEFAULT TRUE,
  status VARCHAR(50) DEFAULT 'Active',
  created_by VARCHAR(100),
  updated_by VARCHAR(100),
  created_date TIMESTAMP,
  updated_date TIMESTAMP
);
```

---

## Workflow IDs (Reference):

| Workflow Name | Workflow ID | Constant |
|---------------|-------------|----------|
| Indent Approval Workflow | 1 | INDENT |
| Tender Approver Workflow | 2 | TENDER_APPROVER |
| Tender Evaluator Workflow | 3 | TENDER_EVALUATOR |
| Purchase Order Workflow | 4 | PO |
| Contingency Purchase Workflow | 5 | CP |

---

## Workflow 1: INDENT APPROVAL WORKFLOW

### **Branches Required:**

#### **Branch 1: Under Project - Computer Category - Non-Bangalore Location**
```json
{
  "branchCode": "INDENT_PROJECT_COMPUTER_NON_BANGALORE",
  "branchName": "Under Project - Computer Category - Non-Bangalore Location",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": true,
    "materialCategory": "COMPUTER",
    "location": "NON_BANGALORE"
  }
}
```
**Approvers:**
1. Project Head (Level 1)
2. Computer Committee Chairman (Level 2)
3. Field Station Incharge (Level 3)

#### **Branch 2: Under Project - Computer Category - Bangalore Location**
```json
{
  "branchCode": "INDENT_PROJECT_COMPUTER_BANGALORE",
  "branchName": "Under Project - Computer Category - Bangalore Location",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": true,
    "materialCategory": "COMPUTER",
    "location": "BANGALORE"
  }
}
```
**Approvers:**
1. Project Head (Level 1)
2. Computer Committee Chairman (Level 2)
3. Administrative Officer (Level 3)

#### **Branch 3: Under Project - Non-Computer Category - Non-Bangalore Location**
```json
{
  "branchCode": "INDENT_PROJECT_NONCOMPUTER_NON_BANGALORE",
  "branchName": "Under Project - Non-Computer - Non-Bangalore Location",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": true,
    "materialCategory": "NON_COMPUTER",
    "location": "NON_BANGALORE"
  }
}
```
**Approvers:**
1. Project Head (Level 1)
2. Field Station Incharge (Level 2)

#### **Branch 4: Under Project - Non-Computer Category - Bangalore Location**
```json
{
  "branchCode": "INDENT_PROJECT_NONCOMPUTER_BANGALORE",
  "branchName": "Under Project - Non-Computer - Bangalore Location",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": true,
    "materialCategory": "NON_COMPUTER",
    "location": "BANGALORE"
  }
}
```
**Approvers:**
1. Project Head (Level 1)
2. Administrative Officer (Level 2)

#### **Branch 5: Not Under Project - Computer Category - Non-Bangalore Location**
```json
{
  "branchCode": "INDENT_NONPROJECT_COMPUTER_NON_BANGALORE",
  "branchName": "Not Under Project - Computer - Non-Bangalore",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": false,
    "materialCategory": "COMPUTER",
    "location": "NON_BANGALORE"
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Computer Committee Chairman (Level 2)
3. Field Station Incharge (Level 3)

#### **Branch 6: Not Under Project - Computer Category - Bangalore Location**
```json
{
  "branchCode": "INDENT_NONPROJECT_COMPUTER_BANGALORE",
  "branchName": "Not Under Project - Computer - Bangalore",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": false,
    "materialCategory": "COMPUTER",
    "location": "BANGALORE"
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Computer Committee Chairman (Level 2)
3. Administrative Officer (Level 3)

#### **Branch 7: Not Under Project - Non-Computer Category - Non-Bangalore Location**
```json
{
  "branchCode": "INDENT_NONPROJECT_NONCOMPUTER_NON_BANGALORE",
  "branchName": "Not Under Project - Non-Computer - Non-Bangalore",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": false,
    "materialCategory": "NON_COMPUTER",
    "location": "NON_BANGALORE"
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Field Station Incharge (Level 2)

#### **Branch 8: Not Under Project - Non-Computer Category - Bangalore Location**
```json
{
  "branchCode": "INDENT_NONPROJECT_NONCOMPUTER_BANGALORE",
  "branchName": "Not Under Project - Non-Computer - Bangalore",
  "conditionType": "COMPOSITE",
  "conditionConfig": {
    "projectBased": false,
    "materialCategory": "NON_COMPUTER",
    "location": "BANGALORE"
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Administrative Officer (Level 2)

#### **Branch 9: Amount-Based Routing (Above Sanction Limit) - From Project Head**
```json
{
  "branchCode": "INDENT_ABOVE_SANCTION_FROM_PROJECT",
  "branchName": "Above Sanction Limit - From Project Head",
  "conditionType": "AMOUNT_WITH_ROLE",
  "conditionConfig": {
    "fromRole": "Project Head",
    "aboveSanctionLimit": true
  }
}
```
**Approvers:**
1. Director (Level 4)

#### **Branch 10: Amount Under 50,000**
```json
{
  "branchCode": "INDENT_UNDER_50000",
  "branchName": "Amount Under 50,000",
  "conditionType": "AMOUNT",
  "conditionConfig": {
    "maxAmount": 50000
  }
}
```
**Approvers:**
- Ends at Administrative Officer (no further approval)

#### **Branch 11: Amount Above 50,000**
```json
{
  "branchCode": "INDENT_ABOVE_50000",
  "branchName": "Amount Above 50,000",
  "conditionType": "AMOUNT",
  "conditionConfig": {
    "minAmount": 50000
  }
}
```
**Approvers:**
1. Purchase Head (Level After Administrative Officer)

#### **Branch 12: Dean/Head SEG - Under 1,00,000 (Head SEG) or Under 1,50,000 (Dean)**
```json
{
  "branchCode": "INDENT_DEAN_HEADSEG_RANGE",
  "branchName": "Dean/Head SEG Amount Range",
  "conditionType": "AMOUNT_WITH_ROLE",
  "conditionConfig": {
    "role": ["Dean", "Head SEG"],
    "maxAmountHeadSEG": 100000,
    "maxAmountDean": 150000
  }
}
```
**Approvers:**
1. Purchase Head (Next Level)

#### **Branch 13: Dean/Head SEG - More than 1,50,000 (Dean) or More than 1,00,000 (Head SEG)**
```json
{
  "branchCode": "INDENT_DEAN_HEADSEG_HIGH",
  "branchName": "Dean/Head SEG High Amount",
  "conditionType": "AMOUNT_WITH_ROLE",
  "conditionConfig": {
    "role": ["Dean", "Head SEG"],
    "minAmountHeadSEG": 100000,
    "minAmountDean": 150000
  }
}
```
**Approvers:**
1. Director (Level After Purchase Head)

#### **Branch 14: Within Project Limit - From Director**
```json
{
  "branchCode": "INDENT_WITHIN_PROJECT_LIMIT",
  "branchName": "Within Project Limit",
  "conditionType": "AMOUNT_WITH_PROJECT",
  "conditionConfig": {
    "withinProjectLimit": true
  }
}
```
**Approvers:**
- Ends at Director (Approved)

---

## Workflow 2: TENDER APPROVAL WORKFLOW

### **Branches Required:**

#### **Branch 1: Default - All Tenders**
```json
{
  "branchCode": "TENDER_DEFAULT",
  "branchName": "Default Tender Approval",
  "conditionType": "DEFAULT",
  "conditionConfig": null
}
```
**Approvers:**
1. Tender Approver (Level 1)

---

## Workflow 3: TENDER EVALUATION WORKFLOW

### **Branches Required:**

#### **Branch 1: Single Indent**
```json
{
  "branchCode": "TENDER_EVAL_SINGLE_INDENT",
  "branchName": "Single Indent Evaluation",
  "conditionType": "INDENT_COUNT",
  "conditionConfig": {
    "indentCount": 1
  }
}
```
**Approvers:**
1. Indent Creator (Level 1)

#### **Branch 2: More Than 1 Indent**
```json
{
  "branchCode": "TENDER_EVAL_MULTIPLE_INDENT",
  "branchName": "Multiple Indents Evaluation",
  "conditionType": "INDENT_COUNT",
  "conditionConfig": {
    "minIndentCount": 2
  }
}
```
**Approvers:**
1. Purchase Personnel (Level 1)

#### **Branch 3: Double Bid (Financial Bid)**
```json
{
  "branchCode": "TENDER_EVAL_DOUBLE_BID",
  "branchName": "Double Bid Evaluation",
  "conditionType": "BID_TYPE",
  "conditionConfig": {
    "bidType": "DOUBLE_BID"
  }
}
```
**Approvers:**
1. Purchase Department (Level 1)
2. Purchase Department (Financial Bid Opening) (Level 2)
3. Head Purchase Personnel (Level 3)
4. Purchase Department (Final) (Level 4)

#### **Branch 4: Single Bid**
```json
{
  "branchCode": "TENDER_EVAL_SINGLE_BID_PURCHASE",
  "branchName": "Single Bid - Purchase Department",
  "conditionType": "BID_TYPE",
  "conditionConfig": {
    "bidType": "SINGLE_BID",
    "department": "PURCHASE"
  }
}
```
**Approvers:**
1. Purchase Department (Level 1)

#### **Branch 5: Single Bid - Financial Bid**
```json
{
  "branchCode": "TENDER_EVAL_SINGLE_BID_FINANCIAL",
  "branchName": "Single Bid - Financial Committee",
  "conditionType": "BID_TYPE",
  "conditionConfig": {
    "bidType": "SINGLE_BID",
    "committee": "TECHNO_FINANCIAL"
  }
}
```
**Approvers:**
1. Techno-Financial Committee Member (Level 1)
2. Techno-Financial Committee Member (Level 2)
3. Purchase Department (Level 3)

#### **Branch 6: Techno-Financial Committee Track**
```json
{
  "branchCode": "TENDER_EVAL_TECHNO_FINANCIAL",
  "branchName": "Techno-Financial Committee",
  "conditionType": "COMMITTEE",
  "conditionConfig": {
    "committee": "TECHNO_FINANCIAL"
  }
}
```
**Approvers:**
1. Director (Level 1)
2. Techno-Financial Committee Member (Level 2)
3. Techno-Financial Committee Member (Level 3)
4. Purchase Department (Level 4)

---

## Workflow 4: PURCHASE ORDER APPROVAL WORKFLOW

### **Branches Required:**

#### **Branch 1: Under 50,000**
```json
{
  "branchCode": "PO_UNDER_50000",
  "branchName": "PO Amount Under 50,000",
  "conditionType": "AMOUNT",
  "conditionConfig": {
    "maxAmount": 50000
  }
}
```
**Approvers:**
1. Store Purchase Officer (Level 1)
2. Accounts Officer (Level 2)
3. Administrative Officer (Level 3)
4. Approved (Final)

#### **Branch 2: Above 50,000 - Under Project**
```json
{
  "branchCode": "PO_ABOVE_50000_PROJECT",
  "branchName": "PO Above 50,000 - Under Project",
  "conditionType": "AMOUNT_WITH_PROJECT",
  "conditionConfig": {
    "minAmount": 50000,
    "projectBased": true
  }
}
```
**Approvers:**
1. Store Purchase Officer (Level 1)
2. Accounts Officer (Level 2)
3. Administrative Officer (Level 3)
4. Project Head (Level 4)

#### **Branch 3: Above 50,000 - Not Under Project**
```json
{
  "branchCode": "PO_ABOVE_50000_NONPROJECT",
  "branchName": "PO Above 50,000 - Not Under Project",
  "conditionType": "AMOUNT_WITH_PROJECT",
  "conditionConfig": {
    "minAmount": 50000,
    "projectBased": false
  }
}
```
**Approvers:**
1. Store Purchase Officer (Level 1)
2. Accounts Officer (Level 2)
3. Administrative Officer (Level 3)
4. Dean/Head SEG (Level 4)

#### **Branch 4: Above Sanction Limit of Project**
```json
{
  "branchCode": "PO_ABOVE_PROJECT_SANCTION",
  "branchName": "PO Above Project Sanction Limit",
  "conditionType": "AMOUNT_WITH_PROJECT",
  "conditionConfig": {
    "aboveProjectSanctionLimit": true
  }
}
```
**Approvers:**
1. Director (Level 5 - After Project Head)

#### **Branch 5: Above 1,00,000 (Head SEG) or Above 1,50,000 (Dean)**
```json
{
  "branchCode": "PO_DEAN_HEADSEG_HIGH",
  "branchName": "PO High Amount - Dean/Head SEG",
  "conditionType": "AMOUNT_WITH_ROLE",
  "conditionConfig": {
    "role": ["Dean", "Head SEG"],
    "minAmountHeadSEG": 100000,
    "minAmountDean": 150000
  }
}
```
**Approvers:**
1. Director (Level 5 - After Dean/Head SEG)

---

## Workflow 5: CONTINGENCY PURCHASE APPROVAL WORKFLOW

### **Branches Required:**

#### **Branch 1: Under Project**
```json
{
  "branchCode": "CP_UNDER_PROJECT",
  "branchName": "Contingency Purchase - Under Project",
  "conditionType": "PROJECT",
  "conditionConfig": {
    "projectBased": true
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Project Head (Level 2)
3. Store Purchase Officer (Level 3)
4. Billing Section Personnel (Level 4)
5. Accounts Officer (Level 5)
6. Administrative Officer (Level 6)

#### **Branch 2: Not Under Project**
```json
{
  "branchCode": "CP_NOT_UNDER_PROJECT",
  "branchName": "Contingency Purchase - Not Under Project",
  "conditionType": "PROJECT",
  "conditionConfig": {
    "projectBased": false
  }
}
```
**Approvers:**
1. Reporting Officer (Level 1)
2. Store Purchase Officer (Level 2)
3. Billing Section Personnel (Level 3)
4. Accounts Officer (Level 4)
5. Administrative Officer (Level 5)

---

## Backend APIs Available:

### **Workflow Branch Management:**

#### Get Branches for Workflow
```
GET /api/admin/approvers/workflows/{workflowId}/branches
```

#### Create Branch
```
POST /api/admin/approvers/workflows/{workflowId}/branches
Body:
{
  "branchCode": "INDENT_PROJECT_COMPUTER",
  "branchName": "Under Project - Computer Category",
  "branchDescription": "Branch for indents under project with computer category",
  "conditionType": "COMPOSITE",
  "conditionConfig": "{\"projectBased\":true,\"materialCategory\":\"COMPUTER\"}",
  "isActive": true,
  "displayOrder": 1,
  "createdBy": "admin"
}
```

#### Update Branch
```
PUT /api/admin/approvers/branches/{branchId}
```

#### Delete Branch
```
DELETE /api/admin/approvers/branches/{branchId}
```

---

### **Approver Management:**

#### Get Approvers for Branch
```
GET /api/admin/approvers/workflow/{workflowId}/branch/{branchId}
```

#### Create Approver
```
POST /api/admin/approvers
Body:
{
  "workflowId": 1,
  "branchId": 1,
  "roleId": 5,
  "roleName": "Project Head",
  "approvalLevel": 1,
  "approvalSequence": 1,
  "isParallelApproval": false,
  "isMandatory": true,
  "status": "Active",
  "createdBy": "admin"
}
```

#### Update Approver
```
PUT /api/admin/approvers/{approverId}
```

#### Delete Approver
```
DELETE /api/admin/approvers/{approverId}
```

#### Change Approver Status
```
PUT /api/admin/approvers/{approverId}/status?status=Active&updatedBy=admin
```

---

## Summary:

✅ **Backend is 100% READY** with complete workflow management system

✅ **All 5 Workflows Can Be Configured** via Admin Panel APIs

✅ **Supports:**
- Category-based routing (Computer/Non-Computer)
- Location-based routing (Bangalore/Non-Bangalore)
- Project-based routing (Under Project/Not Under Project)
- Amount-based routing (Price thresholds)
- Multi-level approvals
- Role-based approvers
- Parallel/Sequential approval logic

✅ **Admin Panel Control:**
- Create/Update/Delete branches
- Create/Update/Delete approvers
- Activate/Deactivate branches
- Activate/Deactivate approvers
- Configure conditions via JSON

---
