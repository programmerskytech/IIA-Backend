# LOV System - Quick Start Guide

## 🚀 Quick Start (5 Minutes)

This guide will help you get the LOV (List of Values) system up and running quickly.

---

## Step 1: Execute Database Migration (1 minute)

Open your MySQL client and run:

```sql
-- Run the migration script
source database-migrations/002_seed_all_lovs.sql;

-- Verify it worked (should show 25 rows)
SELECT fm.form_name, dm.designator_name, COUNT(lm.lov_id) as values
FROM form_master fm
JOIN designator_master dm ON fm.form_id = dm.form_id
LEFT JOIN lov_master lm ON dm.designator_id = lm.designator_id
WHERE fm.is_active = true
GROUP BY fm.form_name, dm.designator_name
ORDER BY fm.form_name;
```

**✅ Success Indicator:** You should see 25 rows with dropdown field names and their value counts.

---

## Step 2: Restart Your Application (1 minute)

```bash
# Stop your Spring Boot application
# Then restart it

mvn spring-boot:run
# OR
java -jar target/astro-service.jar
```

**✅ Success Indicator:** Application starts without errors, you should see cache initialization in logs.

---

## Step 3: Test the APIs (1 minute)

### Windows (PowerShell):
```powershell
Invoke-RestMethod -Uri "http://localhost:8081/astro-service/api/lov/MaterialMaster/category"
```

### Linux/Mac:
```bash
curl http://localhost:8081/astro-service/api/lov/MaterialMaster/category
```

**✅ Success Indicator:** You should get a JSON response with material categories.

---

## Step 4: Share with Frontend Team (2 minutes)

Send them the [`FRONTEND_LOV_INTEGRATION_GUIDE.md`](FRONTEND_LOV_INTEGRATION_GUIDE.md) file.

Key points to highlight:
- Base URL: `http://localhost:8081/astro-service/api/lov`
- Generic endpoint pattern: `/api/lov/{FormName}/{fieldName}`
- Bulk fetch: `/api/lov/form/{FormName}`

---

## 📋 Quick Reference: All Dropdown Endpoints

### Material Master
```javascript
GET /api/lov/MaterialMaster/category        // Computer, Non-Computer, etc.
GET /api/lov/MaterialMaster/subcategory     // Laptop, Desktop, etc.
GET /api/lov/MaterialMaster/uom             // Nos, Kg, Liter, etc.
GET /api/lov/MaterialMaster/currency        // INR, USD, EUR, etc.
```

### Job Master
```javascript
GET /api/lov/JobMaster/jobCategory          // Maintenance, Consulting, etc.
GET /api/lov/JobMaster/jobSubcategory       // Electrical, Plumbing, etc.
GET /api/lov/JobMaster/uom                  // Hour, Day, Month, etc.
GET /api/lov/JobMaster/currency             // INR, USD, EUR, etc.
```

### Employee Registration
```javascript
GET /api/lov/EmployeeRegistration/department    // IT, Finance, HR, etc.
GET /api/lov/EmployeeRegistration/designation   // Manager, Engineer, etc.
GET /api/lov/EmployeeRegistration/location      // Bangalore, Delhi, etc.
```

### Asset Master
```javascript
GET /api/lov/AssetMaster/locator            // Bangalore, Delhi, etc.
```

### Contingency Purchase
```javascript
GET /api/lov/ContingencyPurchase/gstPercentage      // 0%, 5%, 12%, 18%, 28%
GET /api/lov/ContingencyPurchase/paymentTo          // Vendor, Contractor, etc.
GET /api/lov/ContingencyPurchase/materialCategory   // Computer, Non-Computer, etc.
GET /api/lov/ContingencyPurchase/materialSubCategory
GET /api/lov/ContingencyPurchase/countryOfOrigin    // India, USA, China, etc.
```

### Indent Creation
```javascript
GET /api/lov/IndentCreation/consigneeLocation   // Bangalore, Delhi, etc.
```

### Purchase Order
```javascript
GET /api/lov/PurchaseOrder/deliveryPeriod       // 7 Days, 15 Days, 30 Days, etc.
GET /api/lov/PurchaseOrder/warranty             // 6 Months, 1 Year, etc.
GET /api/lov/PurchaseOrder/applicablePbgToBeSubmitted
```

### Tender Request
```javascript
GET /api/lov/TenderRequest/incoTerms            // FOB, CIF, EXW, etc.
GET /api/lov/TenderRequest/paymentTerms         // Net 30, Net 45, etc.
```

### Vendor Master
```javascript
GET /api/lov/VendorMaster/primaryBusiness       // Manufacturing, Trading, etc.
```

---

## 🎯 Common Use Cases

### Use Case 1: Populate a Dropdown

```javascript
// Frontend code (React example)
const [categories, setCategories] = useState([]);

useEffect(() => {
  fetch('/api/lov/MaterialMaster/category')
    .then(res => res.json())
    .then(data => setCategories(data.data));
}, []);

return (
  <select>
    {categories.map(cat => (
      <option key={cat.lovId} value={cat.value}>
        {cat.displayValue}
      </option>
    ))}
  </select>
);
```

### Use Case 2: Load All Form Dropdowns at Once

```javascript
// Load all Material Master dropdowns in one call
fetch('/api/lov/form/MaterialMaster')
  .then(res => res.json())
  .then(data => {
    setCategories(data.data.category);
    setSubcategories(data.data.subcategory);
    setUoms(data.data.uom);
    setCurrencies(data.data.currency);
  });
```

### Use Case 3: Add New Dropdown Value (Admin Panel)

```javascript
// Add new material category
fetch('/api/admin/lov/values', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    designatorId: 5,  // ID for MaterialMaster.category
    lovValue: 'ELECTRONICS',
    lovDisplayValue: 'Electronics',
    lovDescription: 'Electronic items',
    isActive: true,
    isDefault: false,
    displayOrder: 5
  })
});
```

---

## 🔍 Troubleshooting

### Issue: Getting 404 Error

**Problem:** `http://localhost:8081/api/lov/...` returns 404

**Solution:** Add the context path:
```
✗ Wrong: http://localhost:8081/api/lov/...
✓ Correct: http://localhost:8081/astro-service/api/lov/...
```

---

### Issue: Empty Response

**Problem:** API returns `{"status": "success", "data": []}`

**Solution:**
1. Check if migration script was executed
2. Verify form name and field name are correct (case-sensitive)
3. Check database:
```sql
SELECT * FROM lov_master lm
JOIN designator_master dm ON lm.designator_id = dm.designator_id
JOIN form_master fm ON dm.form_id = fm.form_id
WHERE fm.form_name = 'MaterialMaster' AND dm.designator_name = 'category';
```

---

### Issue: Application Won't Start

**Problem:** Error: "Cache 'lovsByFormAndField' not found"

**Solution:** Check `application.properties` has cache configuration:
```properties
spring.cache.type=simple
spring.cache.cache-names=allForms,activeForms,...
```

---

## 📚 Full Documentation

- **Frontend Integration:** [`FRONTEND_LOV_INTEGRATION_GUIDE.md`](FRONTEND_LOV_INTEGRATION_GUIDE.md)
- **Backend Summary:** [`LOV_IMPLEMENTATION_SUMMARY.md`](LOV_IMPLEMENTATION_SUMMARY.md)
- **Database Script:** [`database-migrations/002_seed_all_lovs.sql`](database-migrations/002_seed_all_lovs.sql)

---

## ✅ Verification Checklist

Before marking LOV integration as complete:

- [ ] Database migration executed successfully
- [ ] Application starts without errors
- [ ] Can fetch dropdown values via API
- [ ] Frontend team has received integration guide
- [ ] All 25 dropdown fields are working
- [ ] Caching is enabled and working
- [ ] Admin panel can add/edit/delete LOV values

---

## 🆘 Need Help?

1. Check the full documentation files listed above
2. Review the test script: `test-lov-implementation.sh`
3. Contact the backend development team

---

**Last Updated:** 2025-12-23
**Version:** 1.0.0
