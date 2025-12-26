# 🎯 DESIGNATOR CLEANUP - Complete Deployment Guide

**Date:** 2024-12-24
**Status:** ✅ **READY TO DEPLOY**

---

## 📋 **What This Does**

This cleanup ensures that when you select a form in the List of Values Management page, you see **ONLY** the correct designators for that form.

### **Current Problem:**
- Forms might have extra/incorrect designators showing in the dropdown
- Database might have leftover test data or incorrect mappings

### **Solution:**
- Delete ALL existing designators and LOV values
- Insert ONLY the correct designators (25 total across 9 forms)
- Re-seed all LOV values

---

## 🎯 **Correct Designators Per Form**

| Form | Designators | Count |
|------|------------|-------|
| Asset Master | locator | 1 |
| Contingency Purchase | gstPercentage, paymentTo, budgetCode, materialCategory, materialSubCategory, countryOfOrigin | 6 |
| Indent Creation | consigneeLocation | 1 |
| Employee Registration | department, designation, location | 3 |
| Job Master | jobCategory, jobSubcategory, uom, currency | 4 |
| Material Master | category, subcategory, uom, currency | 4 |
| Vendor Master | primaryBusiness | 1 |
| Purchase Order | deliveryPeriod, warranty, applicablePbgToBeSubmitted | 3 |
| Tender Request | incoTerms, paymentTerms | 2 |

**Total: 25 designators**

---

## 🚀 **DEPLOYMENT STEPS**

### **Step 1: Run Cleanup Script** ⚠️ **CRITICAL - DO THIS FIRST**

```bash
cd e:\Work 2.0\IIA\Backend-prod

# This will DELETE all existing designators and LOVs, then insert ONLY correct ones
mysql -u root -p astrodatabase < cleanup_and_fix_designators.sql
```

**Expected Output:**
```
Query OK, X rows affected (DELETE FROM lov_master)
Query OK, X rows affected (DELETE FROM designator_master)
Query OK, 1 row affected (INSERT locator)
Query OK, 6 rows affected (INSERT Contingency Purchase designators)
...
Query OK, 25 rows affected (Total designators inserted)
```

**Verification Query:**
```sql
SELECT
    fm.form_id,
    fm.form_display_name,
    COUNT(dm.designator_id) as designator_count
FROM form_master fm
LEFT JOIN designator_master dm ON fm.form_id = dm.form_id AND dm.is_active = true
WHERE fm.is_active = true
GROUP BY fm.form_id, fm.form_display_name
ORDER BY fm.form_id;
```

**Expected Result:**
```
form_id | form_display_name       | designator_count
1       | Asset Master            | 1
2       | Contingency Purchase    | 6
3       | Indent Creation         | 1
4       | Employee Registration   | 3
5       | Job Master              | 4
6       | Material Master         | 4
7       | Vendor Master           | 1
8       | Purchase Order          | 3
9       | Tender Request          | 2
```

---

### **Step 2: Re-seed LOV Values** ⚠️ **CRITICAL**

```bash
# Still in e:\Work 2.0\IIA\Backend-prod
mysql -u root -p astrodatabase < seed_all_existing_dropdown_values.sql
```

**What this does:**
- Seeds all 200+ LOV values for the 25 designators
- Includes ALL existing hardcoded dropdown values

**Expected Output:**
```
Query OK, 4 rows affected (Asset Master - Locator)
Query OK, 5 rows affected (Contingency Purchase - GST)
Query OK, 2 rows affected (Contingency Purchase - Payment To)
...
Query OK, 200+ rows affected (Total LOV values inserted)
```

---

### **Step 3: Restart Backend Server** ⚠️ **REQUIRED**

```bash
# Stop your Spring Boot application
# Then restart:
cd e:\Work 2.0\IIA\Backend-prod
mvn clean spring-boot:run
```

**Why:** Backend caching might need refresh after database changes

---

### **Step 4: Hard Refresh Frontend**

```
Press: Ctrl + Shift + R (Windows)
Or: Cmd + Shift + R (Mac)
```

**Why:** Clear browser cache to load fresh data

---

## ✅ **VERIFICATION TESTING**

### **Test 1: Asset Master - Should Show ONLY 1 Designator**

1. **Open:** Admin Panel → List of Values Management
2. **Select Form:** Asset Master
3. **Check Designator Dropdown:** Should show ONLY "Locator" ✅
4. **If you see other designators:** ❌ FAIL - Cleanup script didn't run correctly

---

### **Test 2: Contingency Purchase - Should Show ONLY 6 Designators**

1. **Select Form:** Contingency Purchase
2. **Check Designator Dropdown:** Should show EXACTLY these 6:
   - GST Percentage ✅
   - Payment To ✅
   - Budget Code ✅
   - Material Category ✅
   - Material Sub Category ✅
   - Country of Origin ✅
3. **No other designators should appear**

---

### **Test 3: Indent Creation - Should Show ONLY 1 Designator**

1. **Select Form:** Indent Creation
2. **Check Designator Dropdown:** Should show ONLY "Consignee Location" ✅

---

### **Test 4: Purchase Order - Should Show ONLY 3 Designators**

1. **Select Form:** Purchase Order
2. **Check Designator Dropdown:** Should show EXACTLY these 3:
   - Delivery Period ✅
   - Warranty ✅
   - Applicable PBG to be Submitted ✅

---

### **Test 5: Verify LOV Values Are Present**

1. **Select Form:** Purchase Order
2. **Select Designator:** Warranty
3. **Expected:** Table shows 21 warranty values (NA, 1 Year, 2 Years... 20 Years) ✅
4. **If empty:** ❌ FAIL - Re-seed script didn't run

---

### **Test 6: Complete Form Coverage**

Test each form to ensure only correct designators appear:

- [ ] **Asset Master** → 1 designator (locator)
- [ ] **Contingency Purchase** → 6 designators
- [ ] **Indent Creation** → 1 designator (consigneeLocation)
- [ ] **Employee Registration** → 3 designators (department, designation, location)
- [ ] **Job Master** → 4 designators (jobCategory, jobSubcategory, uom, currency)
- [ ] **Material Master** → 4 designators (category, subcategory, uom, currency)
- [ ] **Vendor Master** → 1 designator (primaryBusiness)
- [ ] **Purchase Order** → 3 designators (deliveryPeriod, warranty, applicablePbgToBeSubmitted)
- [ ] **Tender Request** → 2 designators (incoTerms, paymentTerms)

---

## 🐛 **TROUBLESHOOTING**

### **Issue: Still seeing wrong designators after cleanup**

**Diagnosis:**
```sql
-- Check if cleanup script ran successfully
SELECT COUNT(*) FROM designator_master WHERE is_active = true;
-- Should return: 25

-- Check which designators exist
SELECT
    fm.form_display_name,
    dm.designator_name,
    dm.designator_display_name
FROM form_master fm
JOIN designator_master dm ON fm.form_id = dm.form_id
WHERE fm.is_active = true AND dm.is_active = true
ORDER BY fm.form_id, dm.designator_id;
```

**Solution:**
```bash
# Re-run cleanup script
mysql -u root -p astrodatabase < cleanup_and_fix_designators.sql
```

---

### **Issue: Designators correct but LOV values missing**

**Diagnosis:**
```sql
-- Check LOV count
SELECT COUNT(*) FROM lov_master WHERE is_active = true;
-- Should return: 200+

-- Check specific designator
SELECT
    lm.lov_value,
    lm.lov_display_value
FROM lov_master lm
JOIN designator_master dm ON lm.designator_id = dm.designator_id
WHERE dm.designator_name = 'warranty' AND lm.is_active = true
ORDER BY lm.display_order;
-- Should return 21 rows
```

**Solution:**
```bash
# Re-run seed script
mysql -u root -p astrodatabase < seed_all_existing_dropdown_values.sql
```

---

### **Issue: Changes not reflecting in UI**

**Solutions:**
1. **Hard refresh browser:** Ctrl + Shift + R
2. **Clear browser cache completely**
3. **Restart backend server**
4. **Check browser console for API errors**
5. **Check Network tab - verify API is returning correct data**

---

## 📊 **Expected Results After Deployment**

### **UI Behavior:**

**Before Cleanup:**
```
Form: Asset Master
Designator dropdown shows:
- locator
- category          ← WRONG! (belongs to Material Master)
- warranty          ← WRONG! (belongs to Purchase Order)
- consigneeLocation ← WRONG! (belongs to Indent Creation)
```

**After Cleanup:**
```
Form: Asset Master
Designator dropdown shows:
- locator           ← CORRECT! ✅
(Only this one!)
```

---

### **Database Counts:**

```sql
-- Forms: 9
SELECT COUNT(*) FROM form_master WHERE is_active = true;

-- Designators: 25 (ONLY the correct ones)
SELECT COUNT(*) FROM designator_master WHERE is_active = true;

-- LOV Values: 200+ (all existing dropdown values)
SELECT COUNT(*) FROM lov_master WHERE is_active = true;
```

---

## ✅ **SUCCESS CRITERIA**

Your deployment is successful when:

1. **Designator Counts Match:**
   - Asset Master: 1 designator
   - Contingency Purchase: 6 designators
   - Indent Creation: 1 designator
   - Employee Registration: 3 designators
   - Job Master: 4 designators
   - Material Master: 4 designators
   - Vendor Master: 1 designator
   - Purchase Order: 3 designators
   - Tender Request: 2 designators

2. **No Extra Designators:**
   - Each form shows ONLY its designated designators
   - No "wrong" designators appear in any form's dropdown

3. **LOV Values Present:**
   - All 200+ values are seeded
   - Warranty shows 21 values
   - Delivery Period shows 20 values
   - Applicable PBG shows 21 values
   - All other designators have their values

4. **Bidirectional Sync Works:**
   - Add LOV in Admin Panel → Appears in Management list immediately
   - Add LOV in Admin Panel → Appears in form dropdown (after refresh)
   - Edit/Delete LOV → Changes reflect everywhere

---

## 📝 **What Was Changed**

### **Files Created:**
1. `cleanup_and_fix_designators.sql` - Cleanup script
2. `DESIGNATOR_CLEANUP_DEPLOYMENT_GUIDE.md` - This guide

### **Database Changes:**
1. **DELETE** all existing designators and LOV values (clean slate)
2. **INSERT** 25 correct designators across 9 forms
3. **Re-seed** 200+ LOV values

### **No Code Changes Required:**
- Frontend code already supports dynamic designators
- Backend code already has all fixes (flush/clear)
- useLOVValues hook already working correctly
- ListOfValues.jsx already has data normalization

---

## 🎉 **YOU'RE READY!**

**Next Steps:**

1. ✅ Run cleanup script (Step 1)
2. ✅ Run seed script (Step 2)
3. ✅ Restart backend (Step 3)
4. ✅ Hard refresh frontend (Step 4)
5. ✅ Test all 9 forms (Verification)

**After deployment, each form will show ONLY its correct designators!**

---

**Deployment Date:** 2024-12-24
**Status:** ✅ **READY TO DEPLOY**
**Time to Deploy:** ~5 minutes
**Restart Required:** Yes (backend only)

---

## 🔗 **Related Documentation**

- [BIDIRECTIONAL_SYNC_COMPLETE_GUIDE.md](./BIDIRECTIONAL_SYNC_COMPLETE_GUIDE.md) - LOV sync testing
- [ISSUE_FIXED_SUMMARY.md](./ISSUE_FIXED_SUMMARY.md) - Field name mismatch fix
- [cleanup_and_fix_designators.sql](./cleanup_and_fix_designators.sql) - Cleanup script
- [seed_all_existing_dropdown_values.sql](./seed_all_existing_dropdown_values.sql) - Seed script
