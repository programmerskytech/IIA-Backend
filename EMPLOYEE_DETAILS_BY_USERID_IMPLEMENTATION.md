# Implementation: Get Employee Details by User ID for Indent Creation Auto-Fill

## Requirement:
When a user logs in and goes to Indent Creation page, auto-populate:
- Indentor Name
- Indentor Mobile Number
- Indentor Email Address
- Department

All fields should be fetched from `employee_department_master` table based on the logged-in user's ID.

---

## Backend Implementation:

### 1. Add Method to Service Interface

**File:** `src/main/java/com/astro/service/EmployeeDepartmentMasterService.java`

**Add this method:**
```java
// Get employee details by user ID (for indent creation auto-fill)
EmployeeDepartmentMasterResponseDto getEmployeeDetailsByUserId(Integer userId);
```

**Full interface after adding:**
```java
package com.astro.service;

import com.astro.dto.workflow.*;

import java.util.List;

public interface EmployeeDepartmentMasterService {

    EmployeeDepartmentMasterResponseDto createEmployeeDepartment(EmployeeDepartmentMasterRequestDto employeeRequestDto);

    EmployeeDepartmentMasterResponseDto createEmployeeDepartmentWithUser(EmployeeDepartmentMasterRequestDto employeeRequestDto);

    EmployeeDepartmentMasterResponseDto updateEmployeeDepartmentMaster(String employeeId, EmployeeDepartmentMasterRequestDto employeeRequestDto);

    List<EmployeeDepartmentMasterResponseDto> getAllEmployeeDepartmentMasters();

    List<employeedto> getAllEmployeeDepartmentMasterswithName();

    EmployeeDepartmentMasterResponseDto getEmployeeDepartmentMasterById(String employeeId);

    void deleteEmployeeDepartmentMasterr(String employeeId);

    List<EmployeeSearchResponseDto> searchEmployees(String keyword);

    // New methods for status management
    EmployeeDepartmentMasterResponseDto activateEmployee(String employeeId, String updatedBy);

    EmployeeDepartmentMasterResponseDto deactivateEmployee(String employeeId, String updatedBy);

    // LOV methods
    List<DesignationDto> getAllDesignations();

    List<DepartmentDto> getAllDepartments();

    // Save as draft
    EmployeeDepartmentMasterResponseDto saveAsDraft(EmployeeDepartmentMasterRequestDto requestDto);

    // Get all drafts by user
    List<EmployeeDepartmentMasterResponseDto> getDraftsByUser(String userId);

    // Get all drafts
    List<EmployeeDepartmentMasterResponseDto> getAllDrafts();

    // Submit draft (convert draft to final)
    EmployeeDepartmentMasterResponseDto submitDraft(String employeeId, EmployeeDepartmentMasterRequestDto requestDto);

    // Add this method
    List<employeedto> getEmployeesByDepartment(String departmentName);

    // Get department by employee name
    String getDepartmentByEmployeeName(String employeeName);

    // ✅ NEW: Get employee details by user ID (for indent creation auto-fill)
    EmployeeDepartmentMasterResponseDto getEmployeeDetailsByUserId(Integer userId);
}
```

---

### 2. Implement Method in Service Implementation

**File:** `src/main/java/com/astro/service/impl/EmployeeDepartmentMasterServiceImpl.java`

**Add this method at the end of the class (before closing brace):**

```java
@Override
public EmployeeDepartmentMasterResponseDto getEmployeeDetailsByUserId(Integer userId) {
    // Step 1: Get user by userId to get employeeId
    UserMaster user = userMasterRepository.findById(userId)
        .orElseThrow(() -> new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_RESOURCE,
                "User not found for the provided user ID: " + userId
            )
        ));

    // Step 2: Get employee by employeeId
    String employeeId = user.getEmployeeId();

    if (employeeId == null || employeeId.trim().isEmpty()) {
        throw new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_RESOURCE,
                "This user is not linked to any employee record."
            )
        );
    }

    EmployeeDepartmentMaster employee = employeeRepository.findByEmployeeId(employeeId)
        .orElseThrow(() -> new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_RESOURCE,
                "Employee not found for employee ID: " + employeeId
            )
        ));

    // Step 3: Check if employee is active
    if (!"Active".equalsIgnoreCase(employee.getStatus())) {
        throw new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_VALIDATION,
                "Employee account is inactive. Please contact administrator."
            )
        );
    }

    // Step 4: Return employee details
    return mapToResponseDTO(employee);
}
```

**Required Import (add at top of file if not already present):**
```java
import com.astro.repository.UserMasterRepository;
```

**Autowire UserMasterRepository (add if not already present):**
```java
@Autowired
private UserMasterRepository userMasterRepository;
```

---

### 3. Add Controller Endpoint

**File:** `src/main/java/com/astro/controller/EmployeeDepartmentMasterController.java`

**Add this endpoint (after existing @GetMapping methods):**

```java
// Get employee details by user ID (for indent creation auto-fill)
@GetMapping("/by-user/{userId}")
public ResponseEntity<Object> getEmployeeDetailsByUserId(@PathVariable Integer userId) {
    EmployeeDepartmentMasterResponseDto responseDTO = employeeService.getEmployeeDetailsByUserId(userId);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
}
```

**Insert location:** Around line 91 (after the `getEmployeeMasterById` method)

---

## API Specification:

### Endpoint:
```
GET /api/employee-department-master/by-user/{userId}
```

### Request Example:
```
GET /api/employee-department-master/by-user/83
```

### Response Example (Success):
```json
{
  "responseStatus": {
    "statusCode": 0,
    "message": null,
    "errorCode": null,
    "errorType": null
  },
  "responseData": {
    "employeeId": "E1114",
    "employeeName": "Mohit Kumar",
    "departmentName": "Engineering",
    "location": "Haryana",
    "designation": "Indent Creator",
    "phoneNumber": "9801722265",
    "emailAddress": "ritwiksinghkkc@gmail.com",
    "address": "Jakkanpur d.v.c gate p.o g.p.o Right side gali house no. 2 Bandana Niwas, PATNA, Bihar 800001",
    "status": "Active",
    "createdBy": "admin",
    "updatedBy": null,
    "createdDate": "2025-12-20T21:27:05.247454",
    "updatedDate": "2025-12-20T21:27:05.247454",
    "isDraft": false,
    "userId": null
  }
}
```

### Response Example (Error - User Not Found):
```json
{
  "responseStatus": {
    "statusCode": 404,
    "message": "User not found for the provided user ID: 999",
    "errorCode": 404,
    "errorType": "RESOURCE"
  },
  "responseData": null
}
```

### Response Example (Error - User Not Linked to Employee):
```json
{
  "responseStatus": {
    "statusCode": 404,
    "message": "This user is not linked to any employee record.",
    "errorCode": 404,
    "errorType": "RESOURCE"
  },
  "responseData": null
}
```

### Response Example (Error - Employee Inactive):
```json
{
  "responseStatus": {
    "statusCode": 400,
    "message": "Employee account is inactive. Please contact administrator.",
    "errorCode": 400,
    "errorType": "VALIDATION"
  },
  "responseData": null
}
```

---

## Data Flow:

```
User logs in → Get userId (e.g., 83)
         ↓
Frontend: On Indent Creation page load
         ↓
Frontend calls: GET /api/employee-department-master/by-user/83
         ↓
Backend: user_master table → Get employeeId = "E1114"
         ↓
Backend: employee_department_master table → Get employee details
         ↓
Backend returns:
{
  employeeName: "Mohit Kumar",
  departmentName: "Engineering",
  phoneNumber: "9801722265",
  emailAddress: "ritwiksinghkkc@gmail.com"
}
         ↓
Frontend auto-fills:
- Indentor Name: "Mohit Kumar"
- Department: "Engineering"
- Mobile: "9801722265"
- Email: "ritwiksinghkkc@gmail.com"
```

---

## Testing Steps:

1. **Create Employee:**
   - POST `/api/employee-department-master`
   - Employee Name: "Mohit Kumar"
   - Department: "Engineering"
   - Phone: "9801722265"
   - Email: "ritwiksinghkkc@gmail.com"
   - Response: `employeeId = "E1114"`

2. **Create User:**
   - POST `/api/userMaster`
   - employeeId: "E1114"
   - userName: "Mohit81"
   - Response: `userId = 83`

3. **Test New API:**
   - GET `/api/employee-department-master/by-user/83`
   - Response should contain all employee details

4. **Frontend Integration:**
   - User logs in as "Mohit81"
   - Gets `userId = 83` from login response
   - Opens Indent Creation page
   - Frontend calls: `GET /api/employee-department-master/by-user/83`
   - Auto-fills all fields

---

## Security Note:

The API fetches employee details only for the userId provided. In production, you should add validation to ensure:
1. The logged-in user can only fetch their own details
2. Or add proper authorization checks

Optional enhancement:
```java
// Get userId from JWT token or session
Integer loggedInUserId = getLoggedInUserId();

// Verify the requested userId matches logged-in user
if (!userId.equals(loggedInUserId)) {
    throw new UnauthorizedException("You can only access your own employee details");
}
```

---

## Summary:

✅ **New API Created:** `GET /api/employee-department-master/by-user/{userId}`

✅ **Purpose:** Auto-fill indent creation fields based on logged-in user

✅ **Returns:** Employee Name, Department, Phone, Email

✅ **Validation:** Checks user exists, employee exists, and employee is active

---
