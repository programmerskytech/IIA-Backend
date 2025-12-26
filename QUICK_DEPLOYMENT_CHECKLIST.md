# ⚡ QUICK DEPLOYMENT CHECKLIST

**Goal:** Ensure ONLY correct designators appear for each form

---

## ✅ **3-Step Deployment (5 minutes)**

### **Step 1: Cleanup Database**
```bash
cd e:\Work 2.0\IIA\Backend-prod
mysql -u root -p astrodatabase < cleanup_and_fix_designators.sql
```
⏱️ Takes: 2 seconds

---

### **Step 2: Re-seed LOV Values**
```bash
mysql -u root -p astrodatabase < seed_all_existing_dropdown_values.sql
```
⏱️ Takes: 5 seconds

---

### **Step 3: Restart Backend**
```bash
mvn clean spring-boot:run
```
⏱️ Takes: 2-3 minutes

---

## ✅ **Quick Verification (1 minute)**

Open: Admin Panel → List of Values Management

1. **Asset Master** → Should show ONLY "Locator" ✅
2. **Contingency Purchase** → Should show 6 designators ✅
3. **Purchase Order** → Should show 3 designators (Delivery Period, Warranty, Applicable PBG) ✅
4. **Select Purchase Order → Warranty** → Should show 21 values (NA, 1 Year... 20 Years) ✅

**If all pass:** ✅ **DEPLOYMENT SUCCESSFUL!**

---

## 📊 **Expected Designator Counts**

| Form | Count |
|------|-------|
| Asset Master | 1 |
| Contingency Purchase | 6 |
| Indent Creation | 1 |
| Employee Registration | 3 |
| Job Master | 4 |
| Material Master | 4 |
| Vendor Master | 1 |
| Purchase Order | 3 |
| Tender Request | 2 |

**Total: 25 designators**

---

## 🐛 **If Something Goes Wrong**

**Problem:** Wrong designators still appearing
```bash
# Re-run cleanup script
mysql -u root -p astrodatabase < cleanup_and_fix_designators.sql
```

**Problem:** No LOV values showing
```bash
# Re-run seed script
mysql -u root -p astrodatabase < seed_all_existing_dropdown_values.sql
```

**Problem:** UI not updating
```
Hard refresh browser: Ctrl + Shift + R
```

---

## 📁 **Files You Need**

1. ✅ [cleanup_and_fix_designators.sql](e:\Work 2.0\IIA\Backend-prod\cleanup_and_fix_designators.sql)
2. ✅ [seed_all_existing_dropdown_values.sql](e:\Work 2.0\IIA\Backend-prod\seed_all_existing_dropdown_values.sql)

---

**Status:** ✅ **READY TO DEPLOY**
**Date:** 2024-12-24
