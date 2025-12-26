# 📱 FRONTEND INTEGRATION PROMPT - ADMIN PANEL

**Copy this ENTIRE prompt and send to your frontend AI/developer**

---

## 🎯 PROJECT CONTEXT

We need to build a complete Admin Panel frontend that integrates with the backend APIs. The backend is ready with all endpoints tested and working.

---

## 📋 ADMIN PANEL MODULES

Build these 6 modules:

### 1. **List of Values Management**
### 2. **Approval Workflow & Approver Management**
### 3. **Budget Management**
### 4. **Project Management**
### 5. **Employee Registration**
### 6. **User Creation**

---

## 🔌 BACKEND API DOCUMENTATION

### **BASE URL:** `http://localhost:8080`

---

## MODULE 1: LIST OF VALUES

### API Endpoints:

```
GET    /api/admin/lov/forms
GET    /api/admin/lov/forms/{formId}/designators
GET    /api/admin/lov/designators/{designatorId}/values
POST   /api/admin/lov/values
PUT    /api/admin/lov/values/{lovId}
DELETE /api/admin/lov/values/{lovId}
GET    /api/admin/lov/forms/{formName}/field/{fieldName}/values
```

### UI Requirements:

**Page:** List of Values Management

**Components:**
1. **Form Dropdown**
   - Load options from: `GET /api/admin/lov/forms`
   - Display: `formDisplayName`
   - Value: `formId`

2. **Designator Dropdown** (populated after Form selection)
   - Load options from: `GET /api/admin/lov/forms/{formId}/designators`
   - Display: `designatorDisplayName`
   - Value: `designatorId`

3. **LOV Values Table** (displayed after both selections)
   - Load from: `GET /api/admin/lov/designators/{designatorId}/values`
   - Columns: Value, Display Value, Color Code, Display Order, Status, Actions
   - Actions: Edit, Delete

4. **Add LOV Button** - Opens modal to add new value

### Sample API Responses:

**GET /api/admin/lov/forms:**
```json
{
  "status": "success",
  "data": [
    {
      "formId": 1,
      "formName": "Employee",
      "formDisplayName": "Employee Master",
      "moduleName": "Admin",
      "isActive": true
    },
    {
      "formId": 2,
      "formName": "Project",
      "formDisplayName": "Project Master",
      "moduleName": "Admin",
      "isActive": true
    }
  ]
}
```

**GET /api/admin/lov/forms/1/designators:**
```json
{
  "status": "success",
  "data": [
    {
      "designatorId": 1,
      "formId": 1,
      "designatorName": "status",
      "designatorDisplayName": "Status",
      "dataType": "STRING"
    },
    {
      "designatorId": 2,
      "formId": 1,
      "designatorName": "employmentType",
      "designatorDisplayName": "Employment Type",
      "dataType": "STRING"
    }
  ]
}
```

**GET /api/admin/lov/designators/1/values:**
```json
{
  "status": "success",
  "data": [
    {
      "lovId": 1,
      "designatorId": 1,
      "lovValue": "Active",
      "lovDisplayValue": "Active",
      "colorCode": "#28a745",
      "displayOrder": 1,
      "isActive": true
    },
    {
      "lovId": 2,
      "designatorId": 1,
      "lovValue": "Inactive",
      "lovDisplayValue": "Inactive",
      "colorCode": "#dc3545",
      "displayOrder": 2,
      "isActive": true
    }
  ]
}
```

**POST /api/admin/lov/values (Request Body):**
```json
{
  "designatorId": 1,
  "lovValue": "On-Leave",
  "lovDisplayValue": "On Leave",
  "colorCode": "#ffc107",
  "displayOrder": 3,
  "isActive": true,
  "createdBy": "admin"
}
```

---

## MODULE 2: WORKFLOW & APPROVER MANAGEMENT

### API Endpoints:

```
GET    /getWorkflowByName?workflowName={name}
GET    /api/admin/approvers/workflows/{workflowId}/branches
GET    /api/admin/approvers
GET    /api/admin/approvers/{workflowId}/{branchId}
POST   /api/admin/approvers
PUT    /api/admin/approvers/{approverId}
DELETE /api/admin/approvers/{approverId}
PUT    /api/admin/approvers/{approverId}/status?status={status}&updatedBy={user}
```

### UI Requirements:

**Page:** Approval Workflow Management

**Components:**
1. **Workflow Dropdown**
   - Hardcoded options: Indent Workflow, PO Workflow, SO Workflow, WO Workflow, Payment Voucher Workflow, Tender Approver Workflow, Tender Evaluator Workflow
   - On selection, load branches

2. **Branch Dropdown**
   - Load from: `GET /api/admin/approvers/workflows/{workflowId}/branches`
   - Display: `branchName`
   - Value: `branchId`

3. **Approvers Table**
   - Load from: `GET /api/admin/approvers/{workflowId}/{branchId}`
   - Columns: Code, Approver (Role Name), Level, Sequence, Status, Actions
   - Sort by: Level ASC, Sequence ASC
   - Actions: Edit, Delete

4. **Add Approver Button** - Opens modal

**Add/Edit Approver Modal Fields:**
- Role (dropdown - load from `GET /api/employee-department-master/roles`)
- Level (number input)
- Sequence (number input)
- Status (dropdown: Active, Inactive)

### Sample API Responses:

**GET /api/admin/approvers/workflows/2/branches:**
```json
{
  "status": "success",
  "data": [
    {
      "branchId": 1,
      "workflowId": 2,
      "branchCode": "COMPUTER",
      "branchName": "Computer Category",
      "conditionType": "CATEGORY",
      "isActive": true
    },
    {
      "branchId": 2,
      "workflowId": 2,
      "branchCode": "NON_COMPUTER",
      "branchName": "Non-Computer Category",
      "conditionType": "CATEGORY",
      "isActive": true
    }
  ]
}
```

**GET /api/admin/approvers/2/1:**
```json
{
  "status": "success",
  "data": [
    {
      "approverId": 1,
      "approverCode": "W2-B1-001",
      "workflowId": 2,
      "branchId": 1,
      "roleId": 5,
      "roleName": "Finance Manager",
      "approvalLevel": 1,
      "approvalSequence": 1,
      "status": "Active"
    },
    {
      "approverId": 2,
      "approverCode": "W2-B1-002",
      "workflowId": 2,
      "branchId": 1,
      "roleId": 8,
      "roleName": "HOD",
      "approvalLevel": 2,
      "approvalSequence": 1,
      "status": "Active"
    }
  ]
}
```

**POST /api/admin/approvers (Request Body):**
```json
{
  "workflowId": 2,
  "branchId": 1,
  "roleId": 10,
  "roleName": "Director",
  "approvalLevel": 3,
  "approvalSequence": 1,
  "status": "Active",
  "createdBy": "admin"
}
```

**Response:** Approver code is auto-generated by backend (e.g., "W2-B1-003")

---

## MODULE 3: BUDGET MANAGEMENT

### API Endpoints:

```
GET    /api/admin/budget
GET    /api/admin/budget/{budgetCode}
POST   /api/admin/budget
PUT    /api/admin/budget/{budgetCode}
DELETE /api/admin/budget/{budgetCode}
GET    /api/admin/budget/summary
```

### UI Requirements:

**Page:** Budget Management

**Components:**
1. **Summary Cards** (3 cards in a row)
   - Load from: `GET /api/admin/budget/summary`
   - Total Allocated
   - Total Spent
   - Total Remaining

2. **Budget Table**
   - Load from: `GET /api/admin/budget`
   - Columns: Budget Code, Category, Allocated, Spent, Remaining, Fiscal Year, Status, Actions
   - Actions: Edit, Delete
   - Search/Filter capability

3. **Add New Budget Button** - Opens modal

**Add/Edit Budget Modal Fields:**
- Budget Code (required, text input)
- Budget Name (required, text input)
- Category (dropdown - load from LOV or hardcoded list)
- Allocated Amount (required, number input)
- Fiscal Year (required, text input, e.g., "2024")
- Start Date (date picker)
- End Date (date picker)
- Status (dropdown: Active, Closed, Exhausted)
- Department (optional, dropdown)
- Project Code (optional, dropdown from projects)

### Sample API Responses:

**GET /api/admin/budget/summary:**
```json
{
  "status": "success",
  "data": {
    "totalAllocated": 1275000.00,
    "totalSpent": 957000.00,
    "totalRemaining": 318000.00
  }
}
```

**GET /api/admin/budget:**
```json
{
  "status": "success",
  "data": [
    {
      "budgetId": 1,
      "budgetCode": "BUD-2024-001",
      "budgetName": "IT Infrastructure Budget",
      "category": "IT Infrastructure",
      "allocatedAmount": 250000.00,
      "onHoldAmount": 70000.00,
      "spentAmount": 180000.00,
      "remainingAmount": 0.00,
      "fiscalYear": "2024",
      "startDate": "2024-01-01",
      "endDate": "2024-12-31",
      "status": "Exhausted",
      "departmentName": "IT",
      "projectCode": null
    },
    {
      "budgetId": 2,
      "budgetCode": "BUD-2024-002",
      "budgetName": "Software Development Budget",
      "category": "Software Development",
      "allocatedAmount": 500000.00,
      "onHoldAmount": 0.00,
      "spentAmount": 320000.00,
      "remainingAmount": 180000.00,
      "fiscalYear": "2024",
      "status": "Active"
    }
  ]
}
```

**POST /api/admin/budget (Request Body):**
```json
{
  "budgetCode": "BUD-2024-003",
  "budgetName": "Marketing Budget",
  "category": "Marketing",
  "allocatedAmount": 150000.00,
  "fiscalYear": "2024",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "status": "Active",
  "createdBy": "admin"
}
```

---

## MODULE 4: PROJECT MANAGEMENT

### API Endpoints:

```
GET    /api/project-master
GET    /api/project-master/{projectCode}
POST   /api/project-master
PUT    /api/project-master/{projectCode}
DELETE /api/project-master/{projectCode}
```

### UI Requirements:

**Page:** Project Management

**Components:**
1. **Projects Table**
   - Load from: `GET /api/project-master`
   - Columns: Project Name, Budget Code (projectCode), Manager (projectHead), Start Date, End Date, Status, Actions
   - Status Badge Colors: Active (green), Completed (blue), Closed (gray)
   - Actions: Edit, Delete

2. **Add New Project Button** - Opens modal

3. **Search/Filter Bar**

**Add/Edit Project Modal Fields:**
- Project Code (Budget Code) (required, text input)
- Project Name (required, text input)
- Project Head (Manager) (required, text input or dropdown of employees)
- Department/Division (dropdown)
- Budget Type (text input)
- Allocated Amount (number input)
- Available Project Limit (number input)
- Start Date (date picker)
- End Date (date picker)
- Status (dropdown: Active, Completed, Closed)
- Category (text input)

### Sample API Response:

**GET /api/project-master:**
```json
{
  "status": "success",
  "data": [
    {
      "projectCode": "BUD-2024-001",
      "projectNameDescription": "Website Redesign",
      "projectHead": "John Smith",
      "startDate": "2024-01-15",
      "endDate": "2024-06-30",
      "status": "Active",
      "allocatedAmount": 250000.00,
      "availableProjectLimit": 70000.00,
      "departmentDivision": "IT",
      "budgetType": "Capital",
      "category": "IT Infrastructure"
    }
  ]
}
```

**POST /api/project-master (Request Body):**
```json
{
  "projectCode": "BUD-2024-006",
  "projectNameDescription": "Cloud Migration",
  "projectHead": "Michael Chen",
  "departmentDivision": "IT",
  "budgetType": "Operational",
  "category": "IT Infrastructure",
  "allocatedAmount": 300000.00,
  "availableProjectLimit": 300000.00,
  "startDate": "2024-03-10",
  "endDate": "2024-09-15",
  "status": "Active",
  "createdBy": "admin"
}
```

---

## MODULE 5: EMPLOYEE REGISTRATION

### API Endpoints:

```
POST   /api/employee-department-master
PUT    /api/employee-department-master/{employeeId}
GET    /api/employee-department-master
GET    /api/employee-department-master/{employeeId}
GET    /api/employee-department-master/departments
GET    /api/employee-department-master/designations
GET    /api/employee-department-master/employeeSearch?keyword={keyword}
```

### UI Requirements:

**Page:** Employee Registration

**Form Sections:**

**1. Personal Information:**
- Employee ID (required, text input)
- First Name (required, text input) - **NEW FIELD**
- Last Name (required, text input) - **NEW FIELD**
- Email (required, email input)
- Phone Number (required, tel input)
- Date of Birth (date picker) - **NEW FIELD**

**2. Employment Information:**
- Job Title (Designation) (required, dropdown from `GET /api/employee-department-master/designations`)
- Department (required, dropdown from `GET /api/employee-department-master/departments`)
- Manager (optional, text input or autocomplete) - **NEW FIELD**
- Employment Type (dropdown: Full-time, Part-time, Contract) - **NEW FIELD**
- Hire Date (required, date picker) - **NEW FIELD**
- End Date (optional, date picker) - **NEW FIELD** (for resignation/termination)

**3. Address Information:**
- Street Address (text input) - **NEW FIELD**
- City (text input) - **NEW FIELD**
- State (text input) - **NEW FIELD**
- ZIP Code (text input) - **NEW FIELD**

**Buttons:** Clear Form, Register Employee

### Sample API Response:

**POST /api/employee-department-master (Request Body):**
```json
{
  "employeeId": "EMP-2024-001",
  "firstName": "Sarah",
  "lastName": "Wilson",
  "emailAddress": "sarah.wilson@company.com",
  "phoneNumber": "9876543210",
  "dateOfBirth": "1990-05-15",
  "designation": "Software Engineer",
  "departmentName": "IT",
  "manager": "John Smith",
  "employmentType": "Full-time",
  "hireDate": "2024-01-10",
  "streetAddress": "123 Main Street",
  "city": "Bangalore",
  "state": "Karnataka",
  "zipCode": "560001",
  "location": "Bangalore Office",
  "status": "Active",
  "createdBy": "admin"
}
```

**Note:** Backend still has `employeeName` and `address` fields for backward compatibility, but frontend should use split fields.

---

## MODULE 6: USER CREATION

### API Endpoints:

```
POST   /api/userMaster
GET    /api/userMaster
PUT    /api/userMaster/{userId}
DELETE /api/userMaster/{userId}
GET    /api/employee-department-master/roles
GET    /api/employee-department-master/user-exists/{employeeId}
```

### UI Requirements:

**Page:** User Creation

**Form Fields:**
- Username* (required, text input)
- Email Address* (required, email input)
- Password* (required, password input)
- Confirm Password* (required, password input, must match password)
- Employee ID (optional, text input with autocomplete)
- User Role* (required, dropdown from `GET /api/employee-department-master/roles`)
  - Default options: User, Admin, Manager
- Department field **REMOVED** (derived from Employee ID if provided)

**Buttons:** Clear Form, Create User

**Recently Created Users Section:**
- Table showing last 10 created users
- Columns: Username, Email, Role, Created Date

### Sample API Response:

**POST /api/userMaster (Request Body):**
```json
{
  "userName": "sarah.wilson",
  "email": "sarah.wilson@company.com",
  "password": "encrypted_password_here",
  "employeeId": "EMP-2024-001",
  "roleName": "User",
  "createdBy": "admin"
}
```

**GET /api/employee-department-master/roles:**
```json
{
  "status": "success",
  "data": [
    {"roleId": 1, "roleName": "User"},
    {"roleId": 2, "roleName": "Admin"},
    {"roleId": 3, "roleName": "Manager"}
  ]
}
```

---

## 🎨 UI/UX GUIDELINES

### Design System:
- Use a modern component library (Material-UI, Ant Design, or Chakra UI)
- Color scheme:
  - Primary: Blue (#007bff)
  - Success: Green (#28a745)
  - Warning: Yellow (#ffc107)
  - Danger: Red (#dc3545)
  - Gray: (#6c757d)

### Navigation:
Create sidebar/top nav with these menu items:
- List of Values
- Approval Workflow
- Projects
- Budget
- Employee
- User Creation

### Tables:
- Pagination (10/20/50 items per page)
- Search functionality
- Sort columns
- Responsive design

### Forms:
- Client-side validation before API calls
- Show loading spinners during API calls
- Success/Error toasts after operations
- Required field indicators (*)

### Error Handling:
```javascript
// Example error handling
try {
  const response = await axios.post('/api/admin/budget', budgetData);
  showToast('Budget created successfully', 'success');
} catch (error) {
  if (error.response?.data?.message) {
    showToast(error.response.data.message, 'error');
  } else {
    showToast('An error occurred. Please try again.', 'error');
  }
}
```

---

## 📦 IMPLEMENTATION TASKS

### Phase 1: Setup (1 hour)
- [ ] Set up React/Angular/Vue project
- [ ] Install component library
- [ ] Configure Axios/Fetch for API calls
- [ ] Set up routing
- [ ] Create base layout with navigation

### Phase 2: List of Values Module (2-3 hours)
- [ ] Create LOV page component
- [ ] Implement Form dropdown
- [ ] Implement Designator dropdown with cascading load
- [ ] Create LOV values table
- [ ] Add/Edit/Delete LOV modal
- [ ] Test all CRUD operations

### Phase 3: Approval Workflow Module (3-4 hours)
- [ ] Create Workflow page component
- [ ] Implement Workflow dropdown (hardcoded)
- [ ] Implement Branch dropdown (API-driven)
- [ ] Create Approvers table with Level/Sequence sorting
- [ ] Add/Edit/Delete Approver modal
- [ ] Test approver code auto-generation

### Phase 4: Budget Module (3-4 hours)
- [ ] Create Budget page component
- [ ] Implement summary cards
- [ ] Create Budget table with search/filter
- [ ] Add/Edit/Delete Budget modal
- [ ] Implement calculated "Remaining Amount" display
- [ ] Test budget CRUD operations

### Phase 5: Project Module (2-3 hours)
- [ ] Create Project page component
- [ ] Create Project table with status badges
- [ ] Add/Edit/Delete Project modal
- [ ] Implement status dropdown (Active, Completed, Closed)
- [ ] Test project CRUD operations

### Phase 6: Employee Registration Module (3-4 hours)
- [ ] Create Employee Registration page
- [ ] Implement 3-section form layout
- [ ] Add split name fields (firstName, lastName)
- [ ] Add split address fields (street, city, state, zip)
- [ ] Add new employment fields (DOB, manager, employment type, hire date)
- [ ] Implement department and designation dropdowns
- [ ] Test employee creation with all new fields

### Phase 7: User Creation Module (2-3 hours)
- [ ] Create User Creation page
- [ ] Implement form with all fields
- [ ] Add password confirmation validation
- [ ] Implement role dropdown (dynamic from API)
- [ ] Remove department field (per requirements)
- [ ] Add "Recently Created Users" table
- [ ] Test user creation

### Phase 8: Integration Testing (2-3 hours)
- [ ] End-to-end testing of all modules
- [ ] Cross-browser testing
- [ ] Responsive design testing
- [ ] Error handling testing
- [ ] Performance optimization

---

## 🔐 AUTHENTICATION

Add Authorization header to all API calls:

```javascript
axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
```

Or configure interceptor:

```javascript
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('authToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

---

## ✅ COMPLETION CHECKLIST

- [ ] All 6 modules implemented
- [ ] All API endpoints integrated
- [ ] Forms have validation
- [ ] Tables have pagination
- [ ] Search/filter working
- [ ] Add/Edit/Delete working for all modules
- [ ] Error handling implemented
- [ ] Success/Error toasts displayed
- [ ] Responsive design
- [ ] Cross-browser tested
- [ ] Code documented
- [ ] Git repository updated

---

## 📞 BACKEND CONTACT

If any API endpoint doesn't work as expected:
1. Check API response in browser console
2. Verify request payload format
3. Check backend logs
4. Contact backend team with specific error details

---

**ESTIMATED TOTAL TIME: 20-25 hours**

**Generate complete, production-ready code for all 6 modules with proper error handling, validation, and user feedback.**
