# LOV System - Testing & Verification Guide

## ✅ STEP 1: Run the Database Script

### Execute in MySQL Workbench:
1. Open MySQL Workbench
2. Connect to your database
3. File → Open SQL Script
4. Select: `database-migrations/003_seed_complete_11_forms_lovs_MYSQL_FIXED.sql`
5. Click Execute (⚡ icon)

### Expected Result:
```
✓ 11 rows inserted/updated in form_master
✓ 25 rows inserted/updated in designator_master
✓ 200+ rows inserted/updated in lov_master
```

---

## ✅ STEP 2: Verify Database

### Run these verification queries in MySQL Workbench:

```sql
-- Check Forms (should return 11 rows)
SELECT form_id, form_name, form_display_name, module_name, is_active
FROM form_master
WHERE is_active = true
ORDER BY display_order;

-- Check Designators (should return 25 rows)
SELECT d.designator_id, f.form_name, d.designator_name, d.designator_display_name, d.is_active
FROM designator_master d
JOIN form_master f ON d.form_id = f.form_id
WHERE d.is_active = true
ORDER BY f.form_name, d.display_order;

-- Check LOV Values (should return 200+ rows)
SELECT
    f.form_name,
    d.designator_name,
    COUNT(*) as value_count
FROM lov_master l
JOIN designator_master d ON l.designator_id = d.designator_id
JOIN form_master f ON d.form_id = f.form_id
WHERE l.is_active = true
GROUP BY f.form_name, d.designator_name
ORDER BY f.form_name, d.designator_name;
```

### Expected Output for Forms:
```
IndentCreation       → Indent Creation
PurchaseOrder        → Purchase Order
TenderRequest        → Tender Request
BudgetMaster         → Budget Master
ProjectMaster        → Project Master
AssetMaster          → Asset Master
ContingencyPurchase  → Contingency Purchase
EmployeeMaster       → Employee Master
JobMaster            → Job Master
MaterialMaster       → Material Master
VendorMaster         → Vendor Master
```

### Expected Designator Counts per Form:
```
IndentCreation       → 1 designator  (consigneeLocation)
PurchaseOrder        → 3 designators (deliveryPeriod, warranty, applicablePbgToBeSubmitted)
TenderRequest        → 2 designators (incoTerms, paymentTerms)
BudgetMaster         → 1 designator  (status)
ProjectMaster        → 2 designators (status, budgetType)
AssetMaster          → 1 designator  (locator)
ContingencyPurchase  → 2 designators (gstPercentage, paymentTo)
EmployeeMaster       → 3 designators (department, designation, location)
JobMaster            → 4 designators (jobCategory, jobSubCategory, uom, currency)
MaterialMaster       → 4 designators (category, subCategory, uom, currency)
VendorMaster         → 1 designator  (primaryBusiness)
```

---

## ✅ STEP 3: Test Backend APIs

### Prerequisites:
- Ensure backend is running: `mvn spring-boot:run`
- Backend should be accessible at: `http://localhost:8081`

### Test API #1: Get Dropdown Values for Single Field

```bash
# Test Indent Creation - Consignee Location
curl http://localhost:8081/astro-service/api/lov/IndentCreation/consigneeLocation
```

**Expected Response:**
```json
{
  "status": "success",
  "data": [
    {
      "lovId": 1,
      "value": "MUMBAI",
      "displayValue": "Mumbai"
    },
    {
      "lovId": 2,
      "value": "DELHI",
      "displayValue": "Delhi"
    },
    {
      "lovId": 3,
      "value": "BANGALORE",
      "displayValue": "Bangalore"
    },
    {
      "lovId": 4,
      "value": "CHENNAI",
      "displayValue": "Chennai"
    },
    {
      "lovId": 5,
      "value": "KOLKATA",
      "displayValue": "Kolkata"
    }
  ]
}
```

### Test API #2: Get All Dropdowns for a Form

```bash
# Test Purchase Order - All Fields
curl http://localhost:8081/astro-service/api/lov/form/PurchaseOrder
```

**Expected Response:**
```json
{
  "status": "success",
  "data": {
    "deliveryPeriod": [
      { "lovId": 6, "value": "WITHIN_7_DAYS", "displayValue": "Within 7 Days" },
      { "lovId": 7, "value": "WITHIN_15_DAYS", "displayValue": "Within 15 Days" },
      ...
    ],
    "warranty": [
      { "lovId": 13, "value": "NO_WARRANTY", "displayValue": "No Warranty" },
      { "lovId": 14, "value": "1_YEAR", "displayValue": "1 Year" },
      ...
    ],
    "applicablePbgToBeSubmitted": [
      { "lovId": 18, "value": "YES", "displayValue": "Yes" },
      { "lovId": 19, "value": "NO", "displayValue": "No" },
      ...
    ]
  }
}
```

### Test API #3: Get All Active Forms (for Admin Dropdown)

```bash
curl http://localhost:8081/astro-service/api/lov/forms
```

**Expected Response:**
```json
{
  "status": "success",
  "data": [
    { "formId": 1, "formName": "IndentCreation", "displayName": "Indent Creation" },
    { "formId": 2, "formName": "PurchaseOrder", "displayName": "Purchase Order" },
    ...
  ]
}
```

---

## ✅ STEP 4: Quick Browser Tests

Open these URLs in your browser:

1. **Test Indent Creation:**
   ```
   http://localhost:8081/astro-service/api/lov/IndentCreation/consigneeLocation
   ```

2. **Test Purchase Order:**
   ```
   http://localhost:8081/astro-service/api/lov/PurchaseOrder/deliveryPeriod
   ```

3. **Test Employee Master:**
   ```
   http://localhost:8081/astro-service/api/lov/EmployeeMaster/department
   ```

4. **Test All Forms List:**
   ```
   http://localhost:8081/astro-service/api/lov/forms
   ```

---

## 🐛 Troubleshooting

### Issue: "Form not found"
**Solution:** Check form_master table, ensure form_name matches exactly (case-sensitive)

### Issue: "Field not found"
**Solution:** Check designator_master table, ensure designator_name matches exactly

### Issue: Empty array returned
**Solution:** Check lov_master table, ensure is_active = true for the values

### Issue: 404 Error
**Solution:**
- Verify backend is running
- Check if URL path is correct
- Ensure base URL is: `/astro-service/api/lov`

### Issue: Database connection error
**Solution:** Check application.properties for correct database credentials

---

## 📊 Complete Testing Checklist

### Database:
- [ ] Script executed without errors
- [ ] 11 forms created in form_master
- [ ] 25 designators created in designator_master
- [ ] 200+ LOV values created in lov_master
- [ ] Verification queries return expected counts

### Backend APIs:
- [ ] Single field API works (tested 3 different forms)
- [ ] Form-level API works (tested 2 different forms)
- [ ] Forms list API returns all 11 forms
- [ ] All responses have correct JSON structure
- [ ] Cache is working (second request is faster)

### Ready for Frontend:
- [ ] All tests passed
- [ ] FRONTEND_DEV_NOTE.txt shared with frontend team
- [ ] Frontend has access to FRONTEND_INTEGRATION_GUIDE_11_FORMS.md
- [ ] Base URL confirmed: `http://localhost:8081/astro-service/api/lov`

---

## 📝 Next Steps

Once all tests pass:

1. ✅ **Share with Frontend Team:**
   - Send them: `FRONTEND_DEV_NOTE.txt`
   - Point them to: `FRONTEND_INTEGRATION_GUIDE_11_FORMS.md`

2. ✅ **Frontend Tasks (4-6 hours):**
   - Create `useLOV.js` hook
   - Update 11 form files to use LOV APIs
   - Test each form's dropdowns

3. ✅ **Admin Panel:**
   - Frontend to build LOV Management UI
   - Admins can add/edit dropdown values
   - Changes reflect immediately in forms

---

## 🎉 Success Criteria

✓ All API calls return valid JSON with dropdown values
✓ Dropdowns contain meaningful sample data
✓ Frontend can integrate without backend changes
✓ Admin can manage values through API

**Status:** Backend 100% Complete ✅
**Next:** Frontend Integration (4-6 hours) ⏳
