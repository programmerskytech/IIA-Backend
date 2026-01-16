# Admin Panel Test Case Fixes - Frontend Integration Guide

## Overview
This document outlines the backend fixes for 4 admin panel test cases (TC_13 through TC_16) and provides integration guidance for the frontend team.

All backend changes have been implemented and are ready for frontend integration.

---

## TC_13: LOV Visibility (Active/Inactive Items)

### Issue
Deactivated LOV (List of Values) items were being filtered out by the backend, making them invisible in the admin panel. This prevented administrators from viewing and managing inactive dropdown values.

### Backend Fix
**Modified Files:**
- [LOVServiceImpl.java:173](src/main/java/com/astro/service/impl/AdminPanel/LOVServiceImpl.java#L173)
- [LOVServiceImpl.java:259](src/main/java/com/astro/service/impl/AdminPanel/LOVServiceImpl.java#L259)

**Changes:**
- Changed `findByDesignatorIdAndIsActiveTrueOrderByDisplayOrderAsc()` to `findByDesignatorIdOrderByDisplayOrderAsc()`
- Backend now returns ALL LOV items regardless of active/inactive status

### Frontend Integration

#### API Endpoints (No Changes)
```
GET /api/lov/form/{formName}/field/{fieldName}
GET /api/lov/form/{formName}/all
```

#### Response Format
```json
[
  {
    "lovId": 123,
    "lovValue": "BANGALORE",
    "lovDisplayValue": "Bangalore",
    "isActive": true,
    "displayOrder": 1
  },
  {
    "lovId": 124,
    "lovValue": "MUMBAI",
    "lovDisplayValue": "Mumbai",
    "isActive": false,  // ⚠️ Inactive items now included
    "displayOrder": 2
  }
]
```

#### Frontend Requirements

1. **Admin Panel LOV Management:**
   - Display ALL LOV items (both active and inactive)
   - Show visual indicator for inactive items (e.g., gray text, strikethrough, or "Inactive" badge)
   - Allow admins to toggle active/inactive status
   - Allow admins to edit/delete inactive items

2. **Regular Dropdowns (Forms):**
   - Filter out inactive items on the frontend when displaying in regular form dropdowns
   - Only show `isActive: true` items to end users
   - Example filtering code:
     ```javascript
     const activeOptions = lovData.filter(item => item.isActive === true);
     ```

3. **Visual Design Suggestion:**
   ```jsx
   // Admin Panel Example
   {lovData.map(item => (
     <div className={item.isActive ? '' : 'inactive-item'}>
       {item.lovDisplayValue}
       {!item.isActive && <span className="badge">Inactive</span>}
     </div>
   ))}

   // Regular Dropdown Example
   {lovData.filter(item => item.isActive).map(item => (
     <option value={item.lovValue}>{item.lovDisplayValue}</option>
   ))}
   ```

---

## TC_14: First Login Password Change Prompt

### Issue
System needed to force users to change their password on first login for security purposes.

### Backend Fix
**Modified Files:**
- [UserMaster.java:38-42](src/main/java/com/astro/entity/UserMaster.java#L38-L42) - Added fields
- [UserRoleDto.java:26](src/main/java/com/astro/dto/workflow/UserRoleDto.java#L26) - Added DTO field
- [UserServiceImpl.java:106](src/main/java/com/astro/service/impl/UserServiceImpl.java#L106) - Login response
- [UserServiceImpl.java:373-374](src/main/java/com/astro/service/impl/UserServiceImpl.java#L373-L374) - Password change logic
- [UserMasterController.java:58-70](src/main/java/com/astro/controller/UserMasterController.java#L58-L70) - New endpoint

**Database Changes:**
- Added `is_first_login` column (BOOLEAN, default TRUE)
- Added `last_password_change_date` column (TIMESTAMP)

### Frontend Integration

#### API Changes

**1. Login Response (Modified)**
```
POST /login
```

Request:
```json
{
  "userId": 1,
  "password": "password123"
}
```

Response (NEW FIELD):
```json
{
  "success": true,
  "data": {
    "userId": 1,
    "userName": "John Doe",
    "email": "john@example.com",
    "isFirstLogin": true,  // ⭐ NEW FIELD
    "roles": [...]
  }
}
```

**2. Password Change Endpoint (NEW)**
```
POST /api/userMaster/change-password
```

Request:
```json
{
  "userId": 1,
  "oldPassword": "currentPassword123",
  "newPassword": "newSecurePassword456"
}
```

Response:
```json
{
  "success": true,
  "data": "Password changed successfully"
}
```

Error Response (if old password is wrong):
```json
{
  "success": false,
  "error": {
    "message": "Old password is incorrect."
  }
}
```

#### Frontend Requirements

1. **After Login:**
   - Check `isFirstLogin` field in login response
   - If `true`, immediately redirect to "Change Password" screen
   - Block access to all other pages until password is changed

2. **Change Password Screen:**
   - Show form with 3 fields:
     - Old Password (current password)
     - New Password
     - Confirm New Password
   - Validate new password strength (min 8 chars, uppercase, lowercase, number recommended)
   - Validate that new password matches confirm password
   - Call `/api/userMaster/change-password` endpoint
   - After successful change, allow user to proceed to dashboard

3. **Implementation Example:**
   ```javascript
   // After login
   const loginResponse = await api.post('/login', credentials);

   if (loginResponse.data.isFirstLogin) {
     // Redirect to change password page
     router.push('/change-password');
     // Store userId in session for password change
     sessionStorage.setItem('tempUserId', loginResponse.data.userId);
   } else {
     // Proceed to dashboard normally
     router.push('/dashboard');
   }

   // In change password page
   const handlePasswordChange = async () => {
     const response = await api.post('/api/userMaster/change-password', {
       userId: sessionStorage.getItem('tempUserId'),
       oldPassword: oldPassword,
       newPassword: newPassword
     });

     if (response.success) {
       // Clear temp storage
       sessionStorage.removeItem('tempUserId');
       // Redirect to dashboard
       router.push('/dashboard');
     }
   };
   ```

4. **Optional: Password Change from Settings:**
   - Add "Change Password" option in user settings/profile
   - Use same endpoint and form
   - Don't block user if initiated from settings (not first login)

---

## TC_15: Employee Search Functionality

### Issue
Admin panel needed comprehensive employee search capabilities by multiple criteria (department, name, employee ID, location).

### Backend Fix
**Modified Files:**
- [EmployeeDepartmentMasterRepository.java:48-63](src/main/java/com/astro/repository/EmployeeDepartmentMasterRepository.java#L48-L63) - Search queries
- [EmployeeDepartmentMasterService.java:57](src/main/java/com/astro/service/EmployeeDepartmentMasterService.java#L57) - Service interface
- [EmployeeDepartmentMasterServiceImpl.java:569-595](src/main/java/com/astro/service/impl/EmployeeDepartmentMasterServiceImpl.java#L569-L595) - Search implementation
- [EmployeeDepartmentMasterController.java:188-195](src/main/java/com/astro/controller/EmployeeDepartmentMasterController.java#L188-L195) - New endpoint

### Frontend Integration

#### API Endpoint (NEW)
```
GET /api/employee-department-master/advanced-search?searchTerm={term}&department={dept}&location={loc}
```

**Query Parameters (All Optional):**
- `searchTerm` - General search across employee ID, name, designation, department, location
- `department` - Filter by specific department name
- `location` - Filter by specific location

**Examples:**

1. Search by general term:
   ```
   GET /api/employee-department-master/advanced-search?searchTerm=john
   ```
   Returns employees with "john" in ID, name, department, location, or designation

2. Search by department:
   ```
   GET /api/employee-department-master/advanced-search?department=IT
   ```
   Returns all employees in IT department

3. Search by location:
   ```
   GET /api/employee-department-master/advanced-search?location=bangalore
   ```
   Returns all employees in Bangalore location

4. Get all employees (no parameters):
   ```
   GET /api/employee-department-master/advanced-search
   ```
   Returns all active employees

**Response Format:**
```json
{
  "success": true,
  "data": [
    {
      "employeeId": "EMP001",
      "employeeName": "John Doe",
      "departmentName": "IT",
      "location": "BANGALORE",
      "designation": "Software Engineer",
      "emailAddress": "john@example.com",
      "phoneNumber": "9876543210",
      "status": "Active"
    }
  ]
}
```

#### Frontend Requirements

1. **Search UI Components:**
   - Search input box for general search (searches across all fields)
   - Department dropdown filter
   - Location dropdown filter
   - Search button (or search on keypress)
   - Clear filters button

2. **Search Behavior:**
   - Case-insensitive search
   - Partial matching (e.g., "ban" matches "bangalore", "bangalore office")
   - Real-time search (optional) or button-triggered
   - Show "No results found" message when empty

3. **Implementation Example:**
   ```javascript
   const searchEmployees = async (searchTerm, department, location) => {
     const params = new URLSearchParams();
     if (searchTerm) params.append('searchTerm', searchTerm);
     if (department) params.append('department', department);
     if (location) params.append('location', location);

     const response = await api.get(
       `/api/employee-department-master/advanced-search?${params.toString()}`
     );

     setEmployees(response.data);
   };

   // In component
   <input
     type="text"
     placeholder="Search by name, ID, department, or location"
     onChange={(e) => setSearchTerm(e.target.value)}
   />

   <select onChange={(e) => setDepartment(e.target.value)}>
     <option value="">All Departments</option>
     <option value="IT">IT</option>
     <option value="HR">HR</option>
     ...
   </select>

   <select onChange={(e) => setLocation(e.target.value)}>
     <option value="">All Locations</option>
     <option value="BANGALORE">Bangalore</option>
     <option value="MUMBAI">Mumbai</option>
     ...
   </select>

   <button onClick={() => searchEmployees(searchTerm, department, location)}>
     Search
   </button>
   ```

4. **Results Display:**
   - Show results in a table or card grid
   - Display: Employee ID, Name, Department, Location, Designation, Status
   - Add action buttons (View, Edit, Activate/Deactivate)
   - Implement pagination if results exceed 50 employees

5. **Existing Search Endpoint:**
   - Old endpoint: `GET /api/employee-department-master/employeeSearch?keyword={term}`
   - Still works for simple dropdown searches
   - Use new advanced-search endpoint for admin panel's full search page

---

## TC_16: Employee ID Validation Before User Creation

### Issue
Users could be created with non-existent employee IDs, causing data inconsistency. System should validate that employee exists before creating user account.

### Backend Fix
**Modified Files:**
- [UserServiceImpl.java:196-206](src/main/java/com/astro/service/impl/UserServiceImpl.java#L196-L206) - Validation logic

**Validation Added:**
1. Check if employee ID exists in `employee_department_master` table
2. If not found, throw error: "Employee ID does not exist in the system. Please register the employee first."
3. If found, proceed to check if user already exists for that employee ID
4. If user exists, throw error: "User already exists for this employee ID"

### Frontend Integration

#### API Endpoint (No Changes)
```
POST /api/userMaster
```

Request:
```json
{
  "userName": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "employeeId": "EMP001",  // ⚠️ Must exist in employee_department_master
  "mobileNumber": "9876543210",
  "roleNames": ["Indent Creator"],
  "createdBy": "ADMIN"
}
```

**Success Response (200 OK):**
```json
{
  "success": true,
  "data": {
    "userId": 123,
    "userName": "John Doe",
    "email": "john@example.com",
    "employeeId": "EMP001"
  }
}
```

**Error Response 1 - Employee Not Found (400 Bad Request):**
```json
{
  "success": false,
  "error": {
    "message": "Employee ID does not exist in the system. Please register the employee first.",
    "errorType": "VALIDATION"
  }
}
```

**Error Response 2 - User Already Exists (400 Bad Request):**
```json
{
  "success": false,
  "error": {
    "message": "User already exists for this employee ID",
    "errorType": "VALIDATION"
  }
}
```

#### Frontend Requirements

1. **User Creation Form:**
   - Add employee ID search/autocomplete field
   - Validate employee ID exists before allowing form submission
   - Show helpful error messages

2. **Employee ID Input Options:**

   **Option A: Autocomplete Search**
   ```javascript
   // Use existing employee search endpoint
   const searchEmployees = async (keyword) => {
     const response = await api.get(
       `/api/employee-department-master/employeeSearch?keyword=${keyword}`
     );
     return response.data; // Returns matching employees
   };

   // In component
   <Autocomplete
     placeholder="Search employee by ID or name"
     onSearch={searchEmployees}
     onSelect={(employee) => {
       setEmployeeId(employee.employeeId);
       setEmployeeName(employee.employeeName); // Auto-fill name
     }}
   />
   ```

   **Option B: Validate on Blur**
   ```javascript
   const validateEmployeeId = async (employeeId) => {
     try {
       const response = await api.get(
         `/api/employee-department-master/${employeeId}`
       );
       // Employee exists
       setEmployeeValid(true);
       setEmployeeName(response.data.employeeName);
     } catch (error) {
       // Employee not found
       setEmployeeValid(false);
       setError('Employee ID does not exist');
     }
   };

   <input
     value={employeeId}
     onChange={(e) => setEmployeeId(e.target.value)}
     onBlur={() => validateEmployeeId(employeeId)}
   />
   ```

3. **Check if User Already Exists:**
   ```javascript
   // Before creating user, check if user exists
   const checkUserExists = async (employeeId) => {
     const response = await api.get(
       `/api/employee-department-master/user-exists/${employeeId}`
     );
     return response.data.exists; // true/false
   };

   const handleCreateUser = async () => {
     // Check if user already exists
     const exists = await checkUserExists(employeeId);
     if (exists) {
       alert('User already exists for this employee');
       return;
     }

     // Proceed with user creation
     await api.post('/api/userMaster', userData);
   };
   ```

4. **Error Handling:**
   - Display backend error messages clearly
   - Suggest user to "Register Employee First" if employee ID doesn't exist
   - Provide link to employee registration page

5. **User Flow Improvement:**
   ```
   Step 1: Admin goes to "Create User" page
   Step 2: Admin searches for employee (autocomplete shows matching employees)
   Step 3: Admin selects employee from dropdown
   Step 4: Form auto-fills employee name, department, location
   Step 5: Admin enters email, password, role
   Step 6: Submit → Backend validates and creates user

   Alternative Flow (if employee not found):
   - Show message: "Employee not found. Register employee first?"
   - Button: "Register New Employee" → Redirects to employee registration
   ```

---

## Database Migration

### Required SQL Script
Run the appropriate SQL script based on your database:

**PostgreSQL:**
```bash
psql -U username -d database_name -f database-migrations/009_admin_panel_test_case_fixes.sql
```

**MySQL:**
```bash
mysql -u username -p database_name < database-migrations/009_admin_panel_test_case_fixes_MYSQL.sql
```

### Migration Files
- [009_admin_panel_test_case_fixes.sql](database-migrations/009_admin_panel_test_case_fixes.sql) - PostgreSQL
- [009_admin_panel_test_case_fixes_MYSQL.sql](database-migrations/009_admin_panel_test_case_fixes_MYSQL.sql) - MySQL

### Changes Applied
```sql
-- TC_14: First login tracking
ALTER TABLE user_master
ADD COLUMN is_first_login BOOLEAN DEFAULT TRUE;

ALTER TABLE user_master
ADD COLUMN last_password_change_date TIMESTAMP;

-- Set existing users to not first login
UPDATE user_master SET is_first_login = FALSE WHERE is_first_login IS NULL;
```

---

## Testing Checklist

### TC_13: LOV Visibility
- [ ] Admin panel shows both active and inactive LOV items
- [ ] Inactive items have visual indicator (grayed out, badge, etc.)
- [ ] Regular form dropdowns only show active items
- [ ] Admin can edit/delete inactive items
- [ ] Admin can toggle active/inactive status

### TC_14: First Login Password Change
- [ ] New users have `isFirstLogin: true` in login response
- [ ] First login redirects to password change page
- [ ] User cannot access other pages until password changed
- [ ] Password change validates old password correctly
- [ ] After successful password change, `isFirstLogin` becomes `false`
- [ ] Subsequent logins go directly to dashboard
- [ ] Password change from settings also works

### TC_15: Employee Search
- [ ] General search finds employees by ID, name, department, location
- [ ] Department filter returns only employees in that department
- [ ] Location filter returns only employees in that location
- [ ] Search is case-insensitive
- [ ] Partial matching works (e.g., "ban" finds "bangalore")
- [ ] Empty search returns all employees
- [ ] "No results found" message displays when no matches

### TC_16: Employee ID Validation
- [ ] Creating user with valid employee ID succeeds
- [ ] Creating user with non-existent employee ID shows error
- [ ] Error message suggests registering employee first
- [ ] Creating user with existing employee ID (user exists) shows error
- [ ] Employee ID autocomplete/search works
- [ ] Employee name auto-fills after selecting ID

---

## API Summary Table

| Test Case | Method | Endpoint | Changes |
|-----------|--------|----------|---------|
| TC_13 | GET | `/api/lov/form/{formName}/field/{fieldName}` | Response now includes inactive items |
| TC_13 | GET | `/api/lov/form/{formName}/all` | Response now includes inactive items |
| TC_14 | POST | `/login` | Response includes new `isFirstLogin` field |
| TC_14 | POST | `/api/userMaster/change-password` | **NEW ENDPOINT** |
| TC_15 | GET | `/api/employee-department-master/advanced-search` | **NEW ENDPOINT** |
| TC_16 | POST | `/api/userMaster` | Enhanced validation, new error messages |

---

## Quick Start for Frontend

1. **Run Database Migration:**
   ```bash
   # PostgreSQL
   psql -U your_username -d your_database -f database-migrations/009_admin_panel_test_case_fixes.sql

   # OR MySQL
   mysql -u your_username -p your_database < database-migrations/009_admin_panel_test_case_fixes_MYSQL.sql
   ```

2. **Update Frontend Components:**
   - **LOV Dropdowns:** Add filtering logic to show only active items in forms
   - **Admin LOV Management:** Display all items with visual inactive indicators
   - **Login Flow:** Check `isFirstLogin` and redirect to password change
   - **Password Change Page:** Implement form with old/new password fields
   - **Employee Search:** Implement advanced search UI with filters
   - **User Creation:** Add employee ID validation and autocomplete

3. **Test Each Feature:**
   - Use the testing checklist above
   - Verify error messages display correctly
   - Test edge cases (empty searches, non-existent IDs, etc.)

---

## Support & Questions

If you encounter any issues or have questions about the integration:

1. Check the test cases and examples in this document
2. Verify database migration was applied successfully
3. Check backend console logs for detailed error messages
4. Test API endpoints directly using Postman/Insomnia

**Backend Files Reference:**
- LOV Service: [LOVServiceImpl.java](src/main/java/com/astro/service/impl/AdminPanel/LOVServiceImpl.java)
- User Service: [UserServiceImpl.java](src/main/java/com/astro/service/impl/UserServiceImpl.java)
- Employee Service: [EmployeeDepartmentMasterServiceImpl.java](src/main/java/com/astro/service/impl/EmployeeDepartmentMasterServiceImpl.java)

---

## Summary of Changes

✅ **TC_13 Fixed:** LOV items (both active/inactive) are now returned by backend. Frontend must filter for regular dropdowns.

✅ **TC_14 Fixed:** First login password change implemented with new `isFirstLogin` field and `/change-password` endpoint.

✅ **TC_15 Fixed:** Advanced employee search with multiple filters added via `/advanced-search` endpoint.

✅ **TC_16 Fixed:** Employee ID validation added to user creation - validates employee exists before creating user.

**All backend changes are complete and tested. Ready for frontend integration!** 🚀
