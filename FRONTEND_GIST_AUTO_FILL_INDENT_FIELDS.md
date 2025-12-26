# FRONTEND GIST: Auto-Fill Indent Creation Fields Based on Logged-In User

## Requirement:
When a user logs in and opens the Indent Creation page, automatically populate:
- **Indentor Name** (from employee table)
- **Indentor Mobile Number** (from employee table)
- **Indentor Email Address** (from employee table)
- **Department** (from employee table)

All fields should be auto-filled based on the logged-in user's employee details.

---

## Backend API (ALREADY IMPLEMENTED):

### Endpoint:
```
GET /api/employee-department-master/by-user/{userId}
```

### Request Example:
```javascript
GET /api/employee-department-master/by-user/83
```

### Response Example:
```json
{
  "responseStatus": {
    "statusCode": 0,
    "message": null
  },
  "responseData": {
    "employeeId": "E1114",
    "employeeName": "Mohit Kumar",
    "departmentName": "Engineering",
    "phoneNumber": "9801722265",
    "emailAddress": "ritwiksinghkkc@gmail.com",
    "location": "Haryana",
    "designation": "Indent Creator",
    "status": "Active"
  }
}
```

---

## Frontend Implementation:

### 1. **Get User ID from Login Response**

When user logs in, store the `userId`:

```javascript
// Login API call
const handleLogin = async (credentials) => {
  try {
    const response = await axios.post('/api/userMaster/login', credentials);

    const userData = response.data.responseData;

    // Store userId in localStorage or state management
    localStorage.setItem('userId', userData.userId);
    localStorage.setItem('userName', userData.userName);

    // Navigate to dashboard
    navigate('/dashboard');
  } catch (error) {
    console.error('Login failed:', error);
  }
};
```

---

### 2. **Fetch Employee Details on Indent Creation Page Load**

When Indent Creation page loads, fetch employee details:

```javascript
import { useEffect, useState } from 'react';
import axios from 'axios';

const IndentCreation = () => {
  const [formData, setFormData] = useState({
    indentorName: '',
    indentorDepartment: '',
    indentorMobileNo: '',
    indentorEmailAddress: '',
    // ... other fields
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch employee details when component mounts
  useEffect(() => {
    fetchEmployeeDetails();
  }, []);

  const fetchEmployeeDetails = async () => {
    try {
      setLoading(true);
      setError(null);

      // Get userId from localStorage
      const userId = localStorage.getItem('userId');

      if (!userId) {
        setError('User not logged in');
        setLoading(false);
        return;
      }

      // Call backend API
      const response = await axios.get(
        `/api/employee-department-master/by-user/${userId}`
      );

      const employeeData = response.data?.responseData;

      if (employeeData) {
        // Auto-fill form fields
        setFormData(prev => ({
          ...prev,
          indentorName: employeeData.employeeName || '',
          indentorDepartment: employeeData.departmentName || '',
          indentorMobileNo: employeeData.phoneNumber || '',
          indentorEmailAddress: employeeData.emailAddress || '',
        }));
      }

      setLoading(false);
    } catch (error) {
      console.error('Error fetching employee details:', error);
      setError(error.response?.data?.responseStatus?.message || 'Failed to fetch employee details');
      setLoading(false);
    }
  };

  // Rest of your component...
  return (
    <div>
      {loading && <div>Loading employee details...</div>}
      {error && <div className="error">{error}</div>}

      {!loading && (
        <form>
          {/* Indentor Name - Auto-filled, Read-only */}
          <TextField
            name="indentorName"
            label="Indentor Name"
            value={formData.indentorName}
            disabled={true}  // ✅ Read-only
            required
            fullWidth
          />

          {/* Department - Auto-filled, Read-only */}
          <TextField
            name="indentorDepartment"
            label="Department"
            value={formData.indentorDepartment}
            disabled={true}  // ✅ Read-only
            required
            fullWidth
          />

          {/* Mobile Number - Auto-filled, Read-only */}
          <TextField
            name="indentorMobileNo"
            label="Mobile Number"
            value={formData.indentorMobileNo}
            disabled={true}  // ✅ Read-only
            required
            fullWidth
          />

          {/* Email Address - Auto-filled, Read-only */}
          <TextField
            name="indentorEmailAddress"
            label="Email Address"
            value={formData.indentorEmailAddress}
            disabled={true}  // ✅ Read-only
            required
            fullWidth
          />

          {/* Other editable fields */}
          {/* ... */}
        </form>
      )}
    </div>
  );
};

export default IndentCreation;
```

---

### 3. **Alternative: Using React Context/Redux**

If you're using state management:

```javascript
// In your Auth Context/Redux
const [userDetails, setUserDetails] = useState({
  userId: null,
  userName: null,
  employeeId: null,
  employeeName: null,
  department: null,
  mobile: null,
  email: null,
});

// After login, fetch and store employee details
const fetchAndStoreEmployeeDetails = async (userId) => {
  const response = await axios.get(`/api/employee-department-master/by-user/${userId}`);
  const employeeData = response.data?.responseData;

  setUserDetails(prev => ({
    ...prev,
    userId: userId,
    employeeId: employeeData.employeeId,
    employeeName: employeeData.employeeName,
    department: employeeData.departmentName,
    mobile: employeeData.phoneNumber,
    email: employeeData.emailAddress,
  }));
};

// Then in Indent Creation page
const IndentCreation = () => {
  const { userDetails } = useAuth(); // or useSelector for Redux

  const [formData, setFormData] = useState({
    indentorName: userDetails.employeeName,
    indentorDepartment: userDetails.department,
    indentorMobileNo: userDetails.mobile,
    indentorEmailAddress: userDetails.email,
  });

  // ...
};
```

---

### 4. **Error Handling**

Handle different error scenarios:

```javascript
const fetchEmployeeDetails = async () => {
  try {
    const userId = localStorage.getItem('userId');

    if (!userId) {
      // User not logged in - redirect to login
      navigate('/login');
      return;
    }

    const response = await axios.get(
      `/api/employee-department-master/by-user/${userId}`
    );

    const employeeData = response.data?.responseData;

    if (!employeeData) {
      setError('No employee record found for this user. Please contact administrator.');
      return;
    }

    // Auto-fill fields
    setFormData(prev => ({
      ...prev,
      indentorName: employeeData.employeeName || '',
      indentorDepartment: employeeData.departmentName || '',
      indentorMobileNo: employeeData.phoneNumber || '',
      indentorEmailAddress: employeeData.emailAddress || '',
    }));

  } catch (error) {
    const errorMessage = error.response?.data?.responseStatus?.message;

    if (error.response?.status === 404) {
      setError('Employee record not found. Please contact administrator.');
    } else if (errorMessage?.includes('inactive')) {
      setError('Your employee account is inactive. Please contact administrator.');
    } else {
      setError('Failed to load employee details. Please try again.');
    }
  }
};
```

---

### 5. **Save Indent with Auto-Filled Data**

When saving indent:

```javascript
const handleSaveIndent = async () => {
  const userId = localStorage.getItem('userId');

  const payload = {
    // Auto-filled fields (from employee table)
    indentorName: formData.indentorName,
    employeeDepartment: formData.indentorDepartment,
    indentorMobileNo: formData.indentorMobileNo,
    indentorEmailAddress: formData.indentorEmailAddress,

    // User-entered fields
    projectName: formData.projectName,
    consignesLocation: formData.consignesLocation,
    materialDetails: formData.materialDetails,
    // ... other fields

    createdBy: parseInt(userId),  // ✅ Send userId as createdBy
  };

  try {
    const response = await axios.post('/api/indents', payload);
    console.log('Indent created successfully:', response.data);
    // Show success message and redirect
  } catch (error) {
    console.error('Error creating indent:', error);
    // Show error message
  }
};
```

---

## Complete Data Flow Diagram:

```
┌─────────────────────────────────────────────────────────────┐
│                         LOGIN FLOW                           │
└─────────────────────────────────────────────────────────────┘
User Login (Mohit81)
         ↓
POST /api/userMaster/login
         ↓
Response: { userId: 83, userName: "Mohit81" }
         ↓
Store userId in localStorage

┌─────────────────────────────────────────────────────────────┐
│                  INDENT CREATION PAGE FLOW                   │
└─────────────────────────────────────────────────────────────┘
User opens Indent Creation page
         ↓
useEffect hook triggers
         ↓
Get userId from localStorage (83)
         ↓
GET /api/employee-department-master/by-user/83
         ↓
Backend queries:
  1. user_master → employeeId = "E1114"
  2. employee_department_master → employee details
         ↓
Response:
{
  employeeName: "Mohit Kumar",
  departmentName: "Engineering",
  phoneNumber: "9801722265",
  emailAddress: "ritwiksinghkkc@gmail.com"
}
         ↓
Auto-fill form fields:
  - Indentor Name: "Mohit Kumar" (disabled)
  - Department: "Engineering" (disabled)
  - Mobile: "9801722265" (disabled)
  - Email: "ritwiksinghkkc@gmail.com" (disabled)
         ↓
User fills remaining fields (Project, Location, Materials, etc.)
         ↓
User clicks "Save"
         ↓
POST /api/indents with all data
         ↓
Indent created in database
```

---

## Key Points:

### ✅ **What Gets Auto-Filled:**
1. ✅ Indentor Name (from `employee_department_master.employee_name`)
2. ✅ Department (from `employee_department_master.department_name`)
3. ✅ Mobile Number (from `employee_department_master.phone_number`)
4. ✅ Email Address (from `employee_department_master.email_address`)

### ✅ **These Fields Should Be:**
- **READ-ONLY** (`disabled={true}`)
- **Auto-filled on page load**
- **Based on logged-in user's employee record**

### ✅ **User Still Enters Manually:**
- Project Name
- Consignee Location
- Material Details
- All other indent-specific fields

### ✅ **Business Logic:**
1. **Employee Created First** → with Name, Department, Phone, Email
2. **User Account Linked** → employeeId in user_master
3. **User Logs In** → gets userId
4. **Opens Indent Page** → auto-fills employee details
5. **Creates Indent** → saves with auto-filled employee info

---

## Testing Checklist:

- [ ] User "Mohit81" logs in successfully
- [ ] userId "83" is stored in localStorage
- [ ] Navigate to Indent Creation page
- [ ] Page calls `/api/employee-department-master/by-user/83`
- [ ] Indentor Name auto-fills: "Mohit Kumar"
- [ ] Department auto-fills: "Engineering"
- [ ] Mobile auto-fills: "9801722265"
- [ ] Email auto-fills: "ritwiksinghkkc@gmail.com"
- [ ] All 4 fields are disabled (read-only)
- [ ] User can fill other fields normally
- [ ] Save indent successfully
- [ ] Verify indent saved with correct employee details

---

## Error Scenarios to Handle:

| Scenario | Backend Response | Frontend Action |
|----------|------------------|-----------------|
| User not logged in | N/A | Redirect to login page |
| userId not found | 404 - User not found | Show error: "User not found" |
| User not linked to employee | 404 - Not linked to employee | Show error: "Contact admin to link employee" |
| Employee inactive | 400 - Employee inactive | Show error: "Account inactive, contact admin" |
| Network error | Network timeout | Show error: "Connection failed, try again" |

---

## Summary:

✅ **Backend API:** `GET /api/employee-department-master/by-user/{userId}` (Already implemented!)

✅ **Frontend Changes Needed:**
1. On Indent Creation page load, call the API with logged-in userId
2. Auto-fill 4 fields: Name, Department, Mobile, Email
3. Make these 4 fields read-only (disabled)
4. User fills remaining fields and saves

✅ **Data Source:** `employee_department_master` table (NOT user table)

✅ **User Has No Department:** Correct! Department comes from employee record, not user record.

---
