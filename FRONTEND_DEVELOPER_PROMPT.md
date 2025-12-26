# Frontend Integration Prompt for LOV Dropdowns

**To:** Frontend Developer
**From:** Backend Team
**Date:** 2025-12-23
**Subject:** Centralized Dropdown Management System - Integration Required

---

## 📋 Executive Summary

We have implemented a **centralized List of Values (LOV) management system** that controls all dropdown values across the application. This means **all dropdowns** in your forms can now fetch their values from a unified backend API, and business users can manage these values through the Admin Panel without requiring code changes.

**Your action required:** Update frontend forms to fetch dropdown values from the new LOV APIs instead of hardcoded values.

---

## 🎯 What You Need to Do

### 1. Replace Hardcoded Dropdown Values

**Before (Old Way):**
```javascript
// ❌ Don't do this anymore
const categories = [
  { value: 'COMPUTER', label: 'Computer' },
  { value: 'NON_COMPUTER', label: 'Non-Computer' }
];
```

**After (New Way):**
```javascript
// ✅ Do this instead
const [categories, setCategories] = useState([]);

useEffect(() => {
  fetch('http://localhost:8081/astro-service/api/lov/MaterialMaster/category')
    .then(res => res.json())
    .then(data => setCategories(data.data));
}, []);
```

---

### 2. Update Forms According to This Mapping

I've highlighted the dropdowns you need to update in the screenshots you provided. Here's the complete mapping:

#### **Screenshot 1: Asset Master**
```javascript
// Locator dropdown
GET /api/lov/AssetMaster/locator
```

#### **Screenshot 2: Contingency Purchase**
```javascript
// GST (%) dropdown
GET /api/lov/ContingencyPurchase/gstPercentage

// Payment To dropdown
GET /api/lov/ContingencyPurchase/paymentTo

// Budget Code dropdown
GET /api/lov/ContingencyPurchase/budgetCode

// Material Category dropdown
GET /api/lov/ContingencyPurchase/materialCategory

// Material Sub Category dropdown
GET /api/lov/ContingencyPurchase/materialSubCategory

// Country of Origin dropdown
GET /api/lov/ContingencyPurchase/countryOfOrigin
```

#### **Screenshot 3: Indent Creation**
```javascript
// Consignee Location dropdown
GET /api/lov/IndentCreation/consigneeLocation
```

#### **Screenshot 4: Employee Registration**
```javascript
// Department dropdown
GET /api/lov/EmployeeRegistration/department

// Designation dropdown
GET /api/lov/EmployeeRegistration/designation

// Location dropdown
GET /api/lov/EmployeeRegistration/location
```

#### **Screenshot 5: Job Master**
```javascript
// Job Category dropdown
GET /api/lov/JobMaster/jobCategory

// Job Subcategory dropdown
GET /api/lov/JobMaster/jobSubcategory

// UOM dropdown
GET /api/lov/JobMaster/uom

// Currency dropdown
GET /api/lov/JobMaster/currency
```

#### **Screenshot 6: Material Master**
```javascript
// Category dropdown
GET /api/lov/MaterialMaster/category

// Subcategory dropdown
GET /api/lov/MaterialMaster/subcategory

// UOM dropdown
GET /api/lov/MaterialMaster/uom

// Currency dropdown
GET /api/lov/MaterialMaster/currency
```

#### **Screenshot 7: Vendor Master**
```javascript
// Primary Business dropdown
GET /api/lov/VendorMaster/primaryBusiness
```

#### **Screenshot 8: Purchase Order**
```javascript
// Delivery Period dropdown
GET /api/lov/PurchaseOrder/deliveryPeriod

// Warranty dropdown
GET /api/lov/PurchaseOrder/warranty

// Applicable PBG to be Submitted dropdown
GET /api/lov/PurchaseOrder/applicablePbgToBeSubmitted
```

#### **Screenshot 9: Tender Request**
```javascript
// INCO Terms dropdown
GET /api/lov/TenderRequest/incoTerms

// Payment Terms dropdown
GET /api/lov/TenderRequest/paymentTerms
```

---

## 🚀 Quick Implementation Guide

### Option 1: Fetch Dropdowns Individually (Simple)

```javascript
import React, { useState, useEffect } from 'react';

const MaterialMasterForm = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8081/astro-service/api/lov/MaterialMaster/category')
      .then(res => res.json())
      .then(data => setCategories(data.data))
      .catch(err => console.error('Error loading categories:', err));
  }, []);

  return (
    <select>
      <option value="">Select Category</option>
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

### Option 2: Fetch All Form Dropdowns at Once (Recommended)

```javascript
import React, { useState, useEffect } from 'react';

const MaterialMasterForm = () => {
  const [dropdowns, setDropdowns] = useState({
    categories: [],
    subcategories: [],
    uoms: [],
    currencies: []
  });

  useEffect(() => {
    // Fetch all dropdowns for Material Master in one API call
    fetch('http://localhost:8081/astro-service/api/lov/form/MaterialMaster')
      .then(res => res.json())
      .then(data => {
        setDropdowns({
          categories: data.data.category || [],
          subcategories: data.data.subcategory || [],
          uoms: data.data.uom || [],
          currencies: data.data.currency || []
        });
      })
      .catch(err => console.error('Error loading dropdowns:', err));
  }, []);

  return (
    <form>
      <div>
        <label>Category</label>
        <select>
          <option value="">Select Category</option>
          {dropdowns.categories.map(cat => (
            <option key={cat.lovId} value={cat.value}>
              {cat.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>Subcategory</label>
        <select>
          <option value="">Select Subcategory</option>
          {dropdowns.subcategories.map(sub => (
            <option key={sub.lovId} value={sub.value}>
              {sub.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>UOM</label>
        <select>
          <option value="">Select UOM</option>
          {dropdowns.uoms.map(uom => (
            <option key={uom.lovId} value={uom.value}>
              {uom.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>Currency</label>
        <select>
          <option value="">Select Currency</option>
          {dropdowns.currencies.map(curr => (
            <option key={curr.lovId} value={curr.value}>
              {curr.displayValue}
            </option>
          ))}
        </select>
      </div>
    </form>
  );
};

export default MaterialMasterForm;
```

---

## 📊 API Response Format

All LOV endpoints return data in this format:

```json
{
  "status": "success",
  "data": [
    {
      "lovId": 1,
      "value": "COMPUTER",              // ← Store this in your form data
      "displayValue": "Computer",        // ← Show this to the user
      "description": "Computer Equipment",
      "isActive": true,
      "isDefault": false,                // ← Use this to set default selection
      "displayOrder": 1,
      "colorCode": null,                 // ← Future: for UI styling
      "iconName": null,                  // ← Future: for icons
      "parentLovId": null                // ← For cascading dropdowns
    },
    {
      "lovId": 2,
      "value": "NON_COMPUTER",
      "displayValue": "Non-Computer",
      "description": "Non-Computer Equipment",
      "isActive": true,
      "isDefault": false,
      "displayOrder": 2,
      "colorCode": null,
      "iconName": null,
      "parentLovId": null
    }
  ]
}
```

**Important:**
- **Store** the `value` field in your form state (e.g., "COMPUTER")
- **Display** the `displayValue` field to users (e.g., "Computer")
- When submitting forms, send the `value` to the backend, not `displayValue`

---

## 🔗 Complete API Endpoint List

### Base URL
```
http://localhost:8081/astro-service/api/lov
```

### Endpoint Pattern
```
GET /api/lov/{FormName}/{fieldName}
```

### Quick Reference Endpoints

We've also created shortcut endpoints for convenience:

```javascript
// Material Master
GET /api/lov/material/categories
GET /api/lov/material/subcategories
GET /api/lov/material/uom
GET /api/lov/material/currency

// Job Master
GET /api/lov/job/categories
GET /api/lov/job/subcategories
GET /api/lov/job/uom
GET /api/lov/job/currency

// Employee
GET /api/lov/employee/departments
GET /api/lov/employee/designations
GET /api/lov/employee/locations

// Vendor
GET /api/lov/vendor/primary-business

// Purchase Order
GET /api/lov/purchase-order/delivery-periods
GET /api/lov/purchase-order/warranties
GET /api/lov/purchase-order/pbg

// Tender
GET /api/lov/tender/inco-terms
GET /api/lov/tender/payment-terms

// And more... (see full documentation)
```

---

## 📱 Testing Your Integration

### Step 1: Test API Directly

Open browser or Postman and test:
```
http://localhost:8081/astro-service/api/lov/MaterialMaster/category
```

You should see a JSON response with category values.

### Step 2: Integrate into Your Component

Copy the code examples above into your components.

### Step 3: Verify Dropdowns Populate

- Load your form
- Check that dropdowns show values from the API
- Verify that selecting a value works correctly

---

## 💡 Best Practices

### 1. Cache Dropdown Data

Since dropdown values don't change often, cache them:

```javascript
// Create a context or Redux slice for dropdowns
const DropdownContext = React.createContext();

export const DropdownProvider = ({ children }) => {
  const [dropdowns, setDropdowns] = useState({});

  const loadDropdowns = async (formName) => {
    if (!dropdowns[formName]) {
      const response = await fetch(`/api/lov/form/${formName}`);
      const data = await response.json();
      setDropdowns(prev => ({ ...prev, [formName]: data.data }));
    }
  };

  return (
    <DropdownContext.Provider value={{ dropdowns, loadDropdowns }}>
      {children}
    </DropdownContext.Provider>
  );
};
```

### 2. Handle Loading States

```javascript
const [loading, setLoading] = useState(true);

useEffect(() => {
  setLoading(true);
  fetch('/api/lov/MaterialMaster/category')
    .then(res => res.json())
    .then(data => {
      setCategories(data.data);
      setLoading(false);
    });
}, []);

if (loading) return <div>Loading...</div>;
```

### 3. Handle Errors

```javascript
const [error, setError] = useState(null);

useEffect(() => {
  fetch('/api/lov/MaterialMaster/category')
    .then(res => res.json())
    .then(data => setCategories(data.data))
    .catch(err => {
      setError('Failed to load categories');
      console.error(err);
    });
}, []);

if (error) return <div className="error">{error}</div>;
```

### 4. Set Default Values

```javascript
useEffect(() => {
  const defaultCategory = categories.find(cat => cat.isDefault);
  if (defaultCategory && !formData.category) {
    setFormData(prev => ({
      ...prev,
      category: defaultCategory.value
    }));
  }
}, [categories]);
```

---

## 📚 Full Documentation

For detailed documentation, examples for Angular and Vue.js, troubleshooting, and advanced features, see:

**[`FRONTEND_LOV_INTEGRATION_GUIDE.md`](FRONTEND_LOV_INTEGRATION_GUIDE.md)**

This 50-page guide includes:
- ✅ Complete API documentation
- ✅ React, Angular, and Vue.js examples
- ✅ Cascading dropdown implementation
- ✅ Best practices
- ✅ Troubleshooting guide
- ✅ Database schema reference

---

## ⏱️ Estimated Integration Time

- **Per form:** 15-30 minutes
- **Total (9 forms):** 3-5 hours
- **Testing:** 1-2 hours

---

## ✅ Integration Checklist

For each form, complete these steps:

- [ ] Identify all dropdown fields (refer to highlighted screenshots)
- [ ] Note the API endpoint for each dropdown
- [ ] Update component to fetch dropdown values
- [ ] Remove hardcoded dropdown arrays
- [ ] Test that dropdowns populate correctly
- [ ] Test form submission with dropdown values
- [ ] Verify loading and error states

### Forms to Update:

- [ ] Asset Master (1 dropdown)
- [ ] Contingency Purchase (6 dropdowns)
- [ ] Indent Creation (1 dropdown)
- [ ] Employee Registration (3 dropdowns)
- [ ] Job Master (4 dropdowns)
- [ ] Material Master (4 dropdowns)
- [ ] Vendor Master (1 dropdown)
- [ ] Purchase Order (3 dropdowns)
- [ ] Tender Request (2 dropdowns)

**Total: 25 dropdowns across 9 forms**

---

## 🆘 Need Help?

### Quick Questions?
- Check the full integration guide: [`FRONTEND_LOV_INTEGRATION_GUIDE.md`](FRONTEND_LOV_INTEGRATION_GUIDE.md)
- Review the troubleshooting section

### API Not Working?
- Verify backend is running on port 8081
- Check the base URL includes `/astro-service`
- Verify database migration was executed

### Integration Issues?
- Contact the backend team
- Share your code snippet and the error message

---

## 🎉 Benefits of This System

Once integrated, you'll enjoy:

- ✅ **No more hardcoded dropdowns** - All values managed centrally
- ✅ **Business users can update** - No code changes needed
- ✅ **Instant updates** - Changes reflect immediately across the app
- ✅ **Consistent data** - Same dropdown values everywhere
- ✅ **Better performance** - Values are cached on backend
- ✅ **Future-proof** - Easy to add new dropdowns or values

---

## 📞 Questions?

Feel free to reach out if you have any questions or need clarification on any part of this integration.

**Backend Team**
**Date:** 2025-12-23

---

**Next Steps:**
1. Read this document ✅
2. Review the full integration guide
3. Start with one form (e.g., Material Master)
4. Test thoroughly
5. Repeat for remaining forms
6. Mark as complete when all 9 forms are updated

Good luck! 🚀
