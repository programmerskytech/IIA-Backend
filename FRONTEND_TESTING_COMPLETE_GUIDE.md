# Frontend LOV Integration - Complete Testing Guide

**To:** Frontend Developer
**From:** Backend Team
**Date:** 2025-12-24
**Status:** 🔧 Backend Fixed - Frontend Integration Required

---

## 🎯 What Happened

We identified and **fixed critical bugs** in the LOV (List of Values) system that were preventing dropdown values from appearing after being added. All backend issues are now resolved.

**Your Action Required:** Test and verify frontend integration for ALL 25 dropdowns across 9 forms.

---

## ✅ Backend Fixes Applied

1. ✅ Fixed entity lifecycle hooks (@PrePersist)
2. ✅ Added database flush after save operations
3. ✅ Fixed cache coherency issues
4. ✅ Fixed transaction boundary problems

**Result:** LOV values now appear immediately after being added/updated/deleted.

---

## 📋 Complete Frontend Integration Checklist

### FORM 1: Asset Master (1 dropdown)

#### Dropdown: Locator

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/AssetMaster/locator
// OR
GET http://localhost:8081/astro-service/api/lov/asset-master/locator
```

**Expected Values:**
- Bangalore (default)
- Delhi
- Mumbai
- Kolkata

**Integration Code:**
```javascript
const [locators, setLocators] = useState([]);

useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/AssetMaster/locator')
    .then(res => res.json())
    .then(data => setLocators(data.data));
}, []);

// In your JSX:
<select name="locator">
  <option value="">Select Locator</option>
  {locators.map(loc => (
    <option key={loc.lovId} value={loc.value}>
      {loc.displayValue}
    </option>
  ))}
</select>
```

**Test Steps:**
- [ ] Load Asset Master form
- [ ] Check Locator dropdown populates
- [ ] Verify all 4 values appear
- [ ] Add new locator via Admin Panel
- [ ] Refresh form - verify new value appears

---

### FORM 2: Contingency Purchase (6 dropdowns)

#### Dropdown 1: GST (%)

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/gstPercentage
// OR
GET http://localhost:8081/astro-service/api/lov/contingency-purchase/gst
```

**Expected Values:**
- 0%, 5%, 12%, 18% (default), 28%

**Test Steps:**
- [ ] Load Contingency Purchase form
- [ ] Check GST dropdown populates
- [ ] Verify 18% is selected by default (isDefault: true)

#### Dropdown 2: Payment To

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/paymentTo
```

**Expected Values:**
- Vendor (default), Contractor, Service Provider

#### Dropdown 3: Material Category

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/materialCategory
```

**Expected Values:**
- Computer, Non-Computer, Office Supplies

#### Dropdown 4: Material Sub Category

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/materialSubCategory
```

#### Dropdown 5: Country of Origin

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/countryOfOrigin
```

**Expected Values:**
- India (default), USA, China, Japan, Germany

#### Dropdown 6: Budget Code

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/ContingencyPurchase/budgetCode
```

**Bulk Fetch (Recommended):**
```javascript
const [dropdowns, setDropdowns] = useState({});

useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/ContingencyPurchase')
    .then(res => res.json())
    .then(data => {
      setDropdowns({
        gst: data.data.gstPercentage || [],
        paymentTo: data.data.paymentTo || [],
        materialCategory: data.data.materialCategory || [],
        materialSubCategory: data.data.materialSubCategory || [],
        countryOfOrigin: data.data.countryOfOrigin || [],
        budgetCode: data.data.budgetCode || []
      });
    });
}, []);
```

**Test Steps:**
- [ ] Load Contingency Purchase form
- [ ] Verify all 6 dropdowns populate
- [ ] Test adding new values via Admin Panel
- [ ] Verify new values appear after page refresh

---

### FORM 3: Indent Creation (1 dropdown)

#### Dropdown: Consignee Location

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/IndentCreation/consigneeLocation
// OR
GET http://localhost:8081/astro-service/api/lov/indent/consignee-location
```

**Expected Values:**
- Bangalore (default), Delhi, Mumbai

**Test Steps:**
- [ ] Load Indent Creation form
- [ ] Check Consignee Location dropdown populates
- [ ] Verify 3 location values appear

---

### FORM 4: Employee Registration (3 dropdowns)

#### Dropdown 1: Department

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/EmployeeRegistration/department
// OR
GET http://localhost:8081/astro-service/api/lov/employee/departments
```

**Expected Values:**
- Administration, Finance, IT, HR

#### Dropdown 2: Designation

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/EmployeeRegistration/designation
// OR
GET http://localhost:8081/astro-service/api/lov/employee/designations
```

**Expected Values:**
- Manager, Senior Engineer, Engineer, Assistant

#### Dropdown 3: Location

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/EmployeeRegistration/location
// OR
GET http://localhost:8081/astro-service/api/lov/employee/locations
```

**Expected Values:**
- Bangalore (default), Delhi, Mumbai

**Bulk Fetch (Recommended):**
```javascript
useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/EmployeeRegistration')
    .then(res => res.json())
    .then(data => {
      setDepartments(data.data.department || []);
      setDesignations(data.data.designation || []);
      setLocations(data.data.location || []);
    });
}, []);
```

**Test Steps:**
- [ ] Load Employee Registration form
- [ ] Verify all 3 dropdowns populate
- [ ] Test adding new department via Admin Panel
- [ ] Verify new department appears in dropdown

---

### FORM 5: Job Master (4 dropdowns)

#### Dropdown 1: Job Category

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/JobMaster/jobCategory
// OR
GET http://localhost:8081/astro-service/api/lov/job/categories
```

**Expected Values:**
- Maintenance, Consulting, Installation, Support

#### Dropdown 2: Job Subcategory

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/JobMaster/jobSubcategory
// OR
GET http://localhost:8081/astro-service/api/lov/job/subcategories
```

**Expected Values:**
- Electrical, Plumbing, Carpentry, IT Support

#### Dropdown 3: UOM

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/JobMaster/uom
// OR
GET http://localhost:8081/astro-service/api/lov/job/uom
```

**Expected Values:**
- Hour (default), Day, Month, Job

#### Dropdown 4: Currency

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/JobMaster/currency
// OR
GET http://localhost:8081/astro-service/api/lov/job/currency
```

**Expected Values:**
- INR (₹) (default), USD ($), EUR (€), GBP (£)

**Bulk Fetch (Recommended):**
```javascript
useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/JobMaster')
    .then(res => res.json())
    .then(data => {
      setJobCategories(data.data.jobCategory || []);
      setJobSubcategories(data.data.jobSubcategory || []);
      setUoms(data.data.uom || []);
      setCurrencies(data.data.currency || []);
    });
}, []);
```

**Test Steps:**
- [ ] Load Job Master form
- [ ] Verify all 4 dropdowns populate
- [ ] Verify default values (Hour for UOM, INR for Currency)

---

### FORM 6: Material Master (4 dropdowns)

#### Dropdown 1: Category

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/MaterialMaster/category
// OR
GET http://localhost:8081/astro-service/api/lov/material/categories
```

**Expected Values:**
- Computer, Non-Computer, Office Supplies, Furniture

#### Dropdown 2: Subcategory

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/MaterialMaster/subcategory
// OR
GET http://localhost:8081/astro-service/api/lov/material/subcategories
```

**Expected Values:**
- Laptop, Desktop, Printer, Stationery

#### Dropdown 3: UOM

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/MaterialMaster/uom
// OR
GET http://localhost:8081/astro-service/api/lov/material/uom
```

**Expected Values:**
- Nos (default), Kg, Liter, Meter, Box

#### Dropdown 4: Currency

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/MaterialMaster/currency
// OR
GET http://localhost:8081/astro-service/api/lov/material/currency
```

**Expected Values:**
- INR (₹) (default), USD ($), EUR (€), GBP (£)

**Bulk Fetch (Recommended):**
```javascript
useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/MaterialMaster')
    .then(res => res.json())
    .then(data => {
      setCategories(data.data.category || []);
      setSubcategories(data.data.subcategory || []);
      setUoms(data.data.uom || []);
      setCurrencies(data.data.currency || []);
    });
}, []);
```

**Test Steps:**
- [ ] Load Material Master form
- [ ] Verify all 4 dropdowns populate
- [ ] Verify default values (Nos for UOM, INR for Currency)
- [ ] Test full cycle: Add category → See in dropdown

---

### FORM 7: Vendor Master (1 dropdown)

#### Dropdown: Primary Business

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/VendorMaster/primaryBusiness
// OR
GET http://localhost:8081/astro-service/api/lov/vendor/primary-business
```

**Expected Values:**
- Manufacturing, Trading, Service Provider, Distributor

**Test Steps:**
- [ ] Load Vendor Master form
- [ ] Check Primary Business dropdown populates
- [ ] Verify all 4 business types appear

---

### FORM 8: Purchase Order (3 dropdowns)

#### Dropdown 1: Delivery Period

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/PurchaseOrder/deliveryPeriod
// OR
GET http://localhost:8081/astro-service/api/lov/purchase-order/delivery-periods
```

**Expected Values:**
- 7 Days, 15 Days, 30 Days (default), 60 Days, 90 Days

#### Dropdown 2: Warranty

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/PurchaseOrder/warranty
// OR
GET http://localhost:8081/astro-service/api/lov/purchase-order/warranties
```

**Expected Values:**
- No Warranty, 6 Months, 1 Year (default), 2 Years, 3 Years

#### Dropdown 3: Applicable PBG to be Submitted

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/PurchaseOrder/applicablePbgToBeSubmitted
// OR
GET http://localhost:8081/astro-service/api/lov/purchase-order/pbg
```

**Expected Values:**
- Not Applicable (default), Bank Guarantee, Security Deposit, Performance Bond

**Bulk Fetch (Recommended):**
```javascript
useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/PurchaseOrder')
    .then(res => res.json())
    .then(data => {
      setDeliveryPeriods(data.data.deliveryPeriod || []);
      setWarranties(data.data.warranty || []);
      setPbgOptions(data.data.applicablePbgToBeSubmitted || []);
    });
}, []);
```

**Test Steps:**
- [ ] Load Purchase Order form
- [ ] Verify all 3 dropdowns populate
- [ ] Verify default values

---

### FORM 9: Tender Request (2 dropdowns)

#### Dropdown 1: INCO Terms

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/TenderRequest/incoTerms
// OR
GET http://localhost:8081/astro-service/api/lov/tender/inco-terms
```

**Expected Values:**
- FOB (Free on Board)
- CIF (Cost, Insurance and Freight) (default)
- EXW (Ex Works)
- DDP (Delivered Duty Paid)
- CFR (Cost and Freight)

#### Dropdown 2: Payment Terms

**API Endpoint:**
```javascript
GET http://localhost:8081/astro-service/api/lov/TenderRequest/paymentTerms
// OR
GET http://localhost:8081/astro-service/api/lov/tender/payment-terms
```

**Expected Values:**
- 100% Advance
- 50% Advance, 50% on Delivery
- Net 30 Days (default)
- Net 45 Days
- On Delivery

**Bulk Fetch (Recommended):**
```javascript
useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/form/TenderRequest')
    .then(res => res.json())
    .then(data => {
      setIncoTerms(data.data.incoTerms || []);
      setPaymentTerms(data.data.paymentTerms || []);
    });
}, []);
```

**Test Steps:**
- [ ] Load Tender Request form
- [ ] Verify both dropdowns populate
- [ ] Verify default values (CIF for INCO, Net 30 for Payment)

---

## 🧪 Complete Integration Testing Procedure

### Phase 1: Individual Dropdown Testing

For **each of the 25 dropdowns** above:

1. **Open the form** in your browser
2. **Inspect the dropdown**:
   - Open browser DevTools (F12)
   - Go to Network tab
   - Check if API call is made
   - Verify API returns data
3. **Verify values appear** in the dropdown
4. **Check expected values** match the list above
5. **Test default selection** (if applicable)

### Phase 2: Admin Panel Integration Testing

For **each form**:

1. **Navigate to Admin Panel** → List of Values Management
2. **Select the form** (e.g., Material Master)
3. **Select a designator** (e.g., category)
4. **Click "Add New"**
5. **Enter test data**:
   - Code: `TEST_VALUE_123`
   - Name: `Test Value for ${designatorName}`
   - Description: `Testing LOV integration`
   - Display Order: `99`
   - Status: Active
6. **Click "Save"**
7. **Expected Result:**
   - ✅ Value appears in LOV list immediately
   - ✅ No need to refresh page
8. **Refresh the form page**
9. **Open the dropdown**
10. **Expected Result:**
    - ✅ New test value appears in dropdown

### Phase 3: Full Cycle Testing

1. **Add** a new LOV value via Admin Panel
2. **Verify** it appears in Admin Panel list
3. **Verify** it appears in frontend dropdown (after refresh)
4. **Update** the LOV value via Admin Panel
5. **Verify** changes appear in dropdown (after refresh)
6. **Delete** (soft delete) the LOV value via Admin Panel
7. **Verify** it disappears from dropdown (after refresh)

---

## 🎨 Recommended Implementation Pattern

### Option 1: Context Provider (Recommended for Large Apps)

```javascript
// contexts/LOVContext.js
import React, { createContext, useState, useEffect } from 'react';

const LOVContext = createContext();

export const LOVProvider = ({ children }) => {
  const [dropdowns, setDropdowns] = useState({});
  const [loading, setLoading] = useState(false);

  const loadDropdowns = async (formName) => {
    if (dropdowns[formName]) {
      return dropdowns[formName]; // Already loaded
    }

    setLoading(true);
    try {
      const response = await fetch(
        `http://localhost:8081/astro-service/api/lov/form/${formName}`
      );
      const data = await response.json();
      setDropdowns(prev => ({
        ...prev,
        [formName]: data.data
      }));
      return data.data;
    } catch (error) {
      console.error('Error loading dropdowns:', error);
      return {};
    } finally {
      setLoading(false);
    }
  };

  return (
    <LOVContext.Provider value={{ dropdowns, loadDropdowns, loading }}>
      {children}
    </LOVContext.Provider>
  );
};

export const useLOV = () => useContext(LOVContext);
```

**Usage:**
```javascript
// MaterialMasterForm.jsx
import { useLOV } from '../contexts/LOVContext';

const MaterialMasterForm = () => {
  const { dropdowns, loadDropdowns, loading } = useLOV();
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    loadDropdowns('MaterialMaster').then(data => {
      setCategories(data.category || []);
    });
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <select>
      {categories.map(cat => (
        <option key={cat.lovId} value={cat.value}>
          {cat.displayValue}
        </option>
      ))}
    </select>
  );
};
```

### Option 2: Custom Hook (Recommended for Medium Apps)

```javascript
// hooks/useLOVDropdowns.js
import { useState, useEffect } from 'react';

export const useLOVDropdowns = (formName) => {
  const [dropdowns, setDropdowns] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDropdowns = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch(
          `http://localhost:8081/astro-service/api/lov/form/${formName}`
        );
        const data = await response.json();
        setDropdowns(data.data);
      } catch (err) {
        setError('Failed to load dropdowns');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchDropdowns();
  }, [formName]);

  return { dropdowns, loading, error };
};
```

**Usage:**
```javascript
// MaterialMasterForm.jsx
import { useLOVDropdowns } from '../hooks/useLOVDropdowns';

const MaterialMasterForm = () => {
  const { dropdowns, loading, error } = useLOVDropdowns('MaterialMaster');

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <>
      <select>
        {dropdowns.category?.map(cat => (
          <option key={cat.lovId} value={cat.value}>
            {cat.displayValue}
          </option>
        ))}
      </select>

      <select>
        {dropdowns.subcategory?.map(sub => (
          <option key={sub.lovId} value={sub.value}>
            {sub.displayValue}
          </option>
        ))}
      </select>
    </>
  );
};
```

### Option 3: Simple Fetch (OK for Small Apps)

```javascript
const MaterialMasterForm = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8081/astro-service/api/lov/MaterialMaster/category')
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
};
```

---

## 🔍 Debugging Tips

### Issue: Dropdown is empty

**Steps:**
1. Open browser DevTools → Network tab
2. Look for API call to `/api/lov/...`
3. Check response:
   - Status 200? ✅
   - Status 404? ❌ Check URL
   - Status 500? ❌ Check backend logs
4. Check response data:
   ```json
   {
     "status": "success",
     "data": [ /* array should not be empty */ ]
   }
   ```

### Issue: API returns empty array

**Cause:** Database migration not executed or values not seeded

**Solution:**
```bash
# Execute migration script
mysql -u root -p astrodatabase < database-migrations/002_seed_all_lovs.sql
```

### Issue: CORS error

**Solution:** Backend has `@CrossOrigin` - should work. If not, check backend logs.

### Issue: 404 Not Found

**Common Mistakes:**
```javascript
// ❌ Wrong - missing context path
fetch('http://localhost:8081/api/lov/...')

// ✅ Correct
fetch('http://localhost:8081/astro-service/api/lov/...')
```

---

## ✅ Final Checklist

Before marking integration as complete:

### Backend Verification
- [ ] Application is running (port 8081)
- [ ] Database migration executed
- [ ] All 25 designators have LOV values
- [ ] Test API endpoints in browser/Postman

### Frontend Implementation
- [ ] All 9 forms updated
- [ ] All 25 dropdowns integrated
- [ ] No hardcoded dropdown values remain
- [ ] Loading states handled
- [ ] Error states handled
- [ ] Default values set correctly

### Testing
- [ ] Each dropdown populates with expected values
- [ ] Add new LOV via Admin Panel works
- [ ] Update LOV via Admin Panel works
- [ ] Delete LOV via Admin Panel works
- [ ] Changes reflect in frontend after refresh
- [ ] All default values work correctly

### Code Quality
- [ ] No console errors
- [ ] No network errors
- [ ] Code follows project patterns
- [ ] Proper error handling
- [ ] Loading indicators shown

---

## 📞 Need Help?

**Backend Issues:**
- API not responding → Check backend logs
- 500 errors → Check database connection
- Empty responses → Check database has LOV values

**Frontend Issues:**
- CORS errors → Check browser console
- Component not updating → Check state management
- Values not showing → Check API response in Network tab

**Quick Test:**
```bash
# Test API directly in browser
http://localhost:8081/astro-service/api/lov/MaterialMaster/category

# Should return:
{
  "status": "success",
  "data": [
    { "lovId": 1, "value": "COMPUTER", "displayValue": "Computer", ... },
    ...
  ]
}
```

---

## 🎉 Success Criteria

Your integration is **complete and successful** when:

✅ All 25 dropdowns load values from API
✅ No hardcoded dropdown arrays remain
✅ Adding LOV via Admin Panel → appears in dropdown
✅ Updating LOV via Admin Panel → changes reflect
✅ Deleting LOV via Admin Panel → value disappears
✅ Default values work correctly
✅ No console errors
✅ All forms tested end-to-end

---

**Testing Started:** [Your Date]
**Expected Completion:** [Your Date]
**Status:** ⏳ In Progress

Good luck with the integration! 🚀
