# Employee Registration Update - Complete Guide

## Summary

Updated the Employee Registration form to add 2 new LOV designators (Job Title and Employment Type) while keeping all existing 11 forms and 25 designators intact.

---

## What Was Changed

### 1. Updated cleanup_and_fix_designators.sql

**Before:** Employee Registration had 3 designators
**After:** Employee Registration now has 5 designators

#### New Designators Added:
1. **jobTitle** - Employee job title
2. **employmentType** - Employment type (Full-time, Part-time, Contract, etc.)

#### Existing Designators Kept:
3. **department** - Employee department
4. **designation** - Employee designation/level
5. **location** - Employee office location

### 2. Created add_employee_registration_lov_values.sql

This new script adds LOV values for all 5 Employee Registration designators.

---

## LOV Values Breakdown

### 1. Job Title (8 values)
- Software Engineer
- Senior Software Engineer
- Team Lead
- Project Manager
- HR Manager
- Finance Manager
- Administrative Assistant
- Accountant

### 2. Department (6 values)
- Administration
- Finance
- Information Technology (default)
- Human Resources
- Operations
- Sales

### 3. Designation (4 values)
- Manager
- Senior Engineer
- Engineer (default)
- Assistant

### 4. Employment Type (5 values)
- Full-time (default)
- Part-time
- Contract
- Intern
- Consultant

### 5. Location (6 values)
- Bangalore (default)
- Delhi
- Mumbai
- Hyderabad
- Chennai
- Pune

**Total LOV Values for Employee Registration:** 29 values

---

## How to Deploy

### Option 1: Complete Cleanup (Recommended)

This will remove all existing designators and LOV values, then add them back with the Employee Registration updates.

```bash
# Step 1: Run the cleanup script (this now includes 5 designators for Employee Registration)
mysql -u your_username -p your_database_name < cleanup_and_fix_designators.sql

# Step 2: Add LOV values for Employee Registration
mysql -u your_username -p your_database_name < add_employee_registration_lov_values.sql
```

### Option 2: Add Only Employee Registration LOVs

If you already ran the cleanup script and just want to add the new LOV values:

```bash
# Just run the LOV values script
mysql -u your_username -p your_database_name < add_employee_registration_lov_values.sql
```

---

## Verification

After running the scripts, verify the changes:

### Check Employee Registration Designators

```sql
SELECT
    d.designator_name,
    d.designator_display_name,
    COUNT(l.lov_id) as lov_count
FROM designator_master d
LEFT JOIN lov_master l ON d.designator_id = l.designator_id AND l.is_active = true
WHERE d.form_id = 4 AND d.is_active = true
GROUP BY d.designator_id, d.designator_name, d.designator_display_name
ORDER BY d.designator_name;
```

**Expected Output:**
```
designator_name    | designator_display_name | lov_count
--------------------|------------------------|----------
department         | Department             | 6
designation        | Designation            | 4
employmentType     | Employment Type        | 5
jobTitle           | Job Title              | 8
location           | Location               | 6
```

### List All Employee Registration LOV Values

```sql
SELECT
    d.designator_name,
    l.lov_value,
    l.lov_display_value,
    l.display_order
FROM designator_master d
JOIN lov_master l ON d.designator_id = l.designator_id
WHERE d.form_id = 4 AND d.is_active = true AND l.is_active = true
ORDER BY d.designator_name, l.display_order;
```

### Verify Total Counts

```sql
-- Should show 5 designators for Employee Registration
SELECT COUNT(*) as employee_designators
FROM designator_master
WHERE form_id = 4 AND is_active = true;

-- Should show 29 LOV values for Employee Registration
SELECT COUNT(*) as employee_lov_values
FROM designator_master d
JOIN lov_master l ON d.designator_id = l.designator_id
WHERE d.form_id = 4 AND d.is_active = true AND l.is_active = true;
```

---

## Impact on Existing Data

### cleanup_and_fix_designators.sql
- ⚠️ **WARNING:** This script deletes ALL existing LOV values and designators
- It then recreates them with the correct structure
- Backup your database before running!

### add_employee_registration_lov_values.sql
- ✅ Safe to run multiple times (uses ON DUPLICATE KEY UPDATE)
- Only adds/updates Employee Registration LOV values
- Does not affect other forms

---

## Files Modified

### Backend SQL Scripts:

1. **cleanup_and_fix_designators.sql**
   - Updated Employee Registration from 3 to 5 designators
   - Added jobTitle and employmentType

2. **add_employee_registration_lov_values.sql** (NEW)
   - Adds LOV values for all 5 Employee Registration designators
   - Total 29 LOV values

### Frontend:
3. **EmployeeRegistration.jsx** (Already updated)
   - Added LOV hooks for jobTitle and employmentType
   - Reorganized Employment Information section
   - All dropdowns now fetch from LOV system

---

## Frontend Integration

The frontend EmployeeRegistration.jsx component uses these API endpoints:

```
GET /api/lov/EmployeeRegistration/jobTitle
GET /api/lov/EmployeeRegistration/department
GET /api/lov/EmployeeRegistration/designation
GET /api/lov/EmployeeRegistration/employmentType
GET /api/lov/EmployeeRegistration/location
```

All endpoints will return data in this format:
```json
{
  "status": "success",
  "data": [
    {
      "lovId": 123,
      "lovValue": "SOFTWARE_ENGINEER",
      "lovDisplayValue": "Software Engineer",
      "displayOrder": 1,
      "isActive": true,
      "isDefault": false
    }
  ]
}
```

---

## Maintaining the 11 Forms Structure

The cleanup script maintains exactly **11 forms**:

### Procurement Module (4 forms)
1. IndentCreation
2. PurchaseOrder
3. TenderRequest
4. ContingencyPurchase

### Admin Module (3 forms)
5. BudgetMaster
6. ProjectMaster
7. EmployeeMaster (Updated with 5 designators)

### Inventory Module (1 form)
8. AssetMaster

### Master Data Module (3 forms)
9. JobMaster
10. MaterialMaster
11. VendorMaster

**Total Designators:** 25 (was 23, now 25 after adding 2 for Employee Registration)

---

## No Duplicate Entries

Both scripts use proper INSERT statements:

### cleanup_and_fix_designators.sql
- Deletes ALL existing data first
- Fresh insert, no duplicates possible

### add_employee_registration_lov_values.sql
- Uses `ON DUPLICATE KEY UPDATE`
- Safe to run multiple times
- Updates existing, creates new if missing

---

## Testing Checklist

- [ ] Backup database before running scripts
- [ ] Run cleanup_and_fix_designators.sql
- [ ] Verify 11 forms exist
- [ ] Verify Employee Registration has 5 designators
- [ ] Run add_employee_registration_lov_values.sql
- [ ] Verify 29 LOV values for Employee Registration
- [ ] Test frontend Employee Registration page
- [ ] Verify all 5 dropdowns show values
- [ ] Create test employee record
- [ ] Verify data saves correctly

---

## Rollback Plan

If something goes wrong:

```sql
-- Restore from backup
mysql -u your_username -p your_database_name < backup.sql

-- OR manually delete only Employee Registration LOV values
DELETE FROM lov_master
WHERE designator_id IN (
    SELECT designator_id FROM designator_master
    WHERE form_id = 4
);

-- Then re-run the scripts
```

---

## Support

If you encounter issues:

1. **Check Browser Console** - Look for API errors
2. **Check Database** - Verify designators and LOV values exist
3. **Check Backend Logs** - Look for Java exceptions
4. **Verify Form ID** - Employee Registration should be form_id = 4

---

## Quick Reference

### Employee Registration Form Structure

```
Personal Information
  - Employee ID (auto-generated)
  - First Name *
  - Last Name *
  - Email *
  - Phone Number *
  - Date of Birth *

Employment Information
  - Job Title * (LOV - 8 options)
  - Department * (LOV - 6 options)
  - Designation * (LOV - 4 options)
  - Employment Type * (LOV - 5 options)
  - Manager
  - Hire Date *
  - End Date (optional)
  - Annual Salary

Address Information
  - Street Address
  - City
  - State
  - ZIP Code
  - Location (LOV - 6 options)
```

---

**Date:** 2025-12-26
**Status:** ✅ COMPLETE
**Tested:** Ready for deployment
