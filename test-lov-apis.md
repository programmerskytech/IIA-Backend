# TEST LOV APIs

## Quick Diagnostic Test

Run these curl commands to test if the backend APIs are working:

### 1. Test GET All Forms
```bash
curl http://localhost:8081/astro-service/api/admin/lov/forms
```

**Expected Response:**
```json
{
  "responseStatus": {
    "statusCode": 0,
    "message": null,
    "errorCode": null,
    "errorType": null
  },
  "responseData": [
    {
      "formId": 1,
      "formName": "IndentCreation",
      "formDisplayName": "Indent/Requisition",
      "moduleName": "Procurement",
      "isActive": true,
      "displayOrder": 1
    },
    // ... more forms
  ]
}
```

### 2. Test GET Designators for Employee Form (formId = 7)
```bash
curl http://localhost:8081/astro-service/api/admin/lov/forms/7/designators
```

**Expected Response:**
```json
{
  "responseStatus": {
    "statusCode": 0,
    "message": null,
    "errorCode": null,
    "errorType": null
  },
  "responseData": [
    {
      "designatorId": 1,
      "formId": 7,
      "designatorName": "status",
      "designatorDisplayName": "Status",
      "isActive": true,
      "displayOrder": 1
    },
    {
      "designatorId": 4,
      "formId": 7,
      "designatorName": "employmentType",
      "designatorDisplayName": "Employment Type",
      "isActive": true,
      "displayOrder": 2
    }
  ]
}
```

### 3. Test GET LOV Values for Status Designator
```bash
curl http://localhost:8081/astro-service/api/admin/lov/designators/1/values
```

**Expected Response:**
```json
{
  "responseStatus": {
    "statusCode": 0,
    "message": null,
    "errorCode": null,
    "errorType": null
  },
  "responseData": [
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

---

## Diagnostic Steps:

### If you get empty arrays `[]`:

**Issue:** Database migration wasn't run or data wasn't inserted

**Solution:** Run the migration:
```bash
cd "e:\Work 2.0\IIA\Backend-prod"
mysql -u root -p astrodatabase < database-migrations/001_admin_panel_schema.sql
```

### If you get 404 errors:

**Issue:** Application not running or wrong URL

**Solution:**
1. Check if app is running: `mvn spring-boot:run`
2. Verify URL includes context path: `/astro-service/`

### If you get 200 OK with data:

**Issue:** Backend is working! Problem is in the frontend

**Solution:** Check these in frontend:

1. **Check the API endpoint URL:**
   ```javascript
   // ❌ WRONG - Missing context path
   const url = '/api/admin/lov/forms';

   // ✅ CORRECT - Has context path
   const url = '/astro-service/api/admin/lov/forms';

   // ✅ ALSO CORRECT - Full URL
   const url = 'http://localhost:8081/astro-service/api/admin/lov/forms';
   ```

2. **Check response data extraction:**
   ```javascript
   // ❌ WRONG - Accessing data directly
   const forms = response.data;

   // ✅ CORRECT - Accessing responseData property
   const forms = response.data.responseData;
   ```

3. **Check if forms are filtered:**
   ```javascript
   // The API returns only active forms
   // If frontend has additional filters, it might filter out all data
   ```

---

## All LOV API Endpoints:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/admin/lov/forms` | GET | Get all active forms |
| `/api/admin/lov/forms/{formId}` | GET | Get single form |
| `/api/admin/lov/forms/{formId}/designators` | GET | Get designators for form |
| `/api/admin/lov/designators/{designatorId}/values` | GET | Get LOV values |
| `/api/admin/lov/values/{lovId}` | GET | Get single LOV |
| `/api/admin/lov/values` | POST | Create LOV |
| `/api/admin/lov/values/{lovId}` | PUT | Update LOV |
| `/api/admin/lov/values/{lovId}` | DELETE | Delete LOV |
| `/api/admin/lov/forms` | POST | Create form |
| `/api/admin/lov/designators` | POST | Create designator |
| `/api/admin/lov/designators/{designatorId}` | PUT | Update designator |

---

## Quick Database Check:

Run these SQL queries to verify data exists:

```sql
-- Check forms
SELECT * FROM form_master WHERE is_active = TRUE;

-- Check designators
SELECT * FROM designator_master WHERE is_active = TRUE;

-- Check LOVs
SELECT * FROM lov_master WHERE is_active = TRUE;

-- Count records
SELECT
    (SELECT COUNT(*) FROM form_master WHERE is_active = TRUE) as active_forms,
    (SELECT COUNT(*) FROM designator_master WHERE is_active = TRUE) as active_designators,
    (SELECT COUNT(*) FROM lov_master WHERE is_active = TRUE) as active_lovs;
```

**Expected counts:**
- Forms: 12
- Designators: 4 (status x3 + employmentType x1)
- LOVs: 11 (Employee status: 2, Project status: 3, Budget status: 3, Employment type: 3)
