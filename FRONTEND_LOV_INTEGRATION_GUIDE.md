# Frontend LOV (Dropdown) Integration Guide

## Overview

This guide explains how to integrate the centralized List of Values (LOV) management system into your frontend application. All dropdowns across the application are now managed through a unified admin panel, making it easy to add, update, or remove dropdown values without code changes.

---

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Available API Endpoints](#available-api-endpoints)
3. [Integration Examples](#integration-examples)
4. [Form-Specific Dropdown Mappings](#form-specific-dropdown-mappings)
5. [Best Practices](#best-practices)
6. [Troubleshooting](#troubleshooting)

---

## Architecture Overview

### Three-Tier LOV Structure

1. **Form Master**: Represents application pages (e.g., MaterialMaster, JobMaster, EmployeeRegistration)
2. **Designator Master**: Represents dropdown fields within forms (e.g., category, uom, department)
3. **LOV Master**: Stores actual dropdown values for each designator

### Key Features

- ✅ **Centralized Management**: All dropdowns controlled from Admin Panel
- ✅ **Caching**: Dropdown values are cached for performance
- ✅ **Active/Inactive**: Control visibility without deletion
- ✅ **Default Values**: Set default selections
- ✅ **Display Order**: Control the order of dropdown options
- ✅ **Cascading Dropdowns**: Support for dependent dropdowns (via parentLovId)
- ✅ **Bulk Fetch**: Get multiple dropdowns in a single API call
- ✅ **UI Enhancements**: Optional color codes and icons

---

## Available API Endpoints

### Base URL
```
http://localhost:8081/astro-service/api/lov
```

### 1. Get Dropdown Values for a Specific Field

**Endpoint Pattern:**
```
GET /api/lov/{formName}/{fieldName}
```

**Examples:**
```javascript
// Material Category dropdown
GET /api/lov/MaterialMaster/category

// Job UOM dropdown
GET /api/lov/JobMaster/uom

// Employee Department dropdown
GET /api/lov/EmployeeRegistration/department

// Asset Locator dropdown
GET /api/lov/AssetMaster/locator

// GST Percentage dropdown
GET /api/lov/ContingencyPurchase/gstPercentage
```

**Response Format:**
```json
{
  "status": "success",
  "data": [
    {
      "lovId": 1,
      "value": "COMPUTER",
      "displayValue": "Computer",
      "description": "Computer Equipment",
      "isActive": true,
      "isDefault": false,
      "displayOrder": 1,
      "colorCode": null,
      "iconName": null,
      "parentLovId": null
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

---

### 2. Get All Dropdowns for a Form (Bulk Fetch)

**Endpoint Pattern:**
```
GET /api/lov/form/{formName}
```

**Example:**
```javascript
GET /api/lov/form/MaterialMaster
```

**Response Format:**
```json
{
  "status": "success",
  "data": {
    "category": [
      { "lovId": 1, "value": "COMPUTER", "displayValue": "Computer", ... }
    ],
    "subcategory": [
      { "lovId": 5, "value": "LAPTOP", "displayValue": "Laptop", ... }
    ],
    "uom": [
      { "lovId": 10, "value": "NOS", "displayValue": "Nos", ... }
    ],
    "currency": [
      { "lovId": 15, "value": "INR", "displayValue": "INR (₹)", ... }
    ]
  }
}
```

---

### 3. Quick Reference Endpoints

For convenience, we've created shorthand endpoints:

```javascript
// Asset Master
GET /api/lov/asset-master/locator

// Contingency Purchase
GET /api/lov/contingency-purchase/gst
GET /api/lov/contingency-purchase/payment-to
GET /api/lov/contingency-purchase/material-category
GET /api/lov/contingency-purchase/material-subcategory
GET /api/lov/contingency-purchase/country-of-origin

// Indent Creation
GET /api/lov/indent/consignee-location

// Employee Registration
GET /api/lov/employee/departments
GET /api/lov/employee/designations
GET /api/lov/employee/locations

// Job Master
GET /api/lov/job/categories
GET /api/lov/job/subcategories
GET /api/lov/job/uom
GET /api/lov/job/currency

// Material Master
GET /api/lov/material/categories
GET /api/lov/material/subcategories
GET /api/lov/material/uom
GET /api/lov/material/currency

// Vendor Master
GET /api/lov/vendor/primary-business

// Purchase Order
GET /api/lov/purchase-order/delivery-periods
GET /api/lov/purchase-order/warranties
GET /api/lov/purchase-order/pbg

// Tender Request
GET /api/lov/tender/inco-terms
GET /api/lov/tender/payment-terms
```

---

### 4. Bulk Fetch from Multiple Forms

**Endpoint:**
```
POST /api/lov/bulk
```

**Request Body:**
```json
[
  "MaterialMaster.category",
  "MaterialMaster.subcategory",
  "JobMaster.uom",
  "EmployeeRegistration.department"
]
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "MaterialMaster.category": [ { lov objects } ],
    "MaterialMaster.subcategory": [ { lov objects } ],
    "JobMaster.uom": [ { lov objects } ],
    "EmployeeRegistration.department": [ { lov objects } ]
  }
}
```

---

### 5. Cascading/Dependent Dropdowns

**Endpoint:**
```
GET /api/lov/dependent/{parentLovId}
```

**Example Use Case:**
When a user selects a category, fetch subcategories for that category:

```javascript
// User selects category with lovId = 5
GET /api/lov/dependent/5
```

**Response:**
```json
{
  "status": "success",
  "data": [
    {
      "lovId": 10,
      "value": "LAPTOP",
      "displayValue": "Laptop",
      "parentLovId": 5
    }
  ]
}
```

---

## Integration Examples

### React Example

```jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const MaterialMasterForm = () => {
  const [categories, setCategories] = useState([]);
  const [subcategories, setSubcategories] = useState([]);
  const [uoms, setUoms] = useState([]);
  const [currencies, setCurrencies] = useState([]);

  const [formData, setFormData] = useState({
    category: '',
    subcategory: '',
    uom: '',
    currency: ''
  });

  // Option 1: Fetch dropdowns individually
  useEffect(() => {
    const fetchDropdowns = async () => {
      try {
        const [catRes, subcatRes, uomRes, currRes] = await Promise.all([
          axios.get('/api/lov/MaterialMaster/category'),
          axios.get('/api/lov/MaterialMaster/subcategory'),
          axios.get('/api/lov/MaterialMaster/uom'),
          axios.get('/api/lov/MaterialMaster/currency')
        ]);

        setCategories(catRes.data.data);
        setSubcategories(subcatRes.data.data);
        setUoms(uomRes.data.data);
        setCurrencies(currRes.data.data);
      } catch (error) {
        console.error('Error fetching dropdowns:', error);
      }
    };

    fetchDropdowns();
  }, []);

  // Option 2: Fetch all dropdowns for the form at once
  useEffect(() => {
    const fetchAllDropdowns = async () => {
      try {
        const response = await axios.get('/api/lov/form/MaterialMaster');
        const data = response.data.data;

        setCategories(data.category || []);
        setSubcategories(data.subcategory || []);
        setUoms(data.uom || []);
        setCurrencies(data.currency || []);
      } catch (error) {
        console.error('Error fetching dropdowns:', error);
      }
    };

    fetchAllDropdowns();
  }, []);

  return (
    <form>
      <div>
        <label>Category</label>
        <select
          value={formData.category}
          onChange={(e) => setFormData({...formData, category: e.target.value})}
        >
          <option value="">Select Category</option>
          {categories.map(cat => (
            <option key={cat.lovId} value={cat.value}>
              {cat.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>Subcategory</label>
        <select
          value={formData.subcategory}
          onChange={(e) => setFormData({...formData, subcategory: e.target.value})}
        >
          <option value="">Select Subcategory</option>
          {subcategories.map(subcat => (
            <option key={subcat.lovId} value={subcat.value}>
              {subcat.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>UOM</label>
        <select
          value={formData.uom}
          onChange={(e) => setFormData({...formData, uom: e.target.value})}
        >
          <option value="">Select UOM</option>
          {uoms.map(uom => (
            <option key={uom.lovId} value={uom.value}>
              {uom.displayValue}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>Currency</label>
        <select
          value={formData.currency}
          onChange={(e) => setFormData({...formData, currency: e.target.value})}
        >
          <option value="">Select Currency</option>
          {currencies.map(curr => (
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

### Angular Example

```typescript
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface LOVItem {
  lovId: number;
  value: string;
  displayValue: string;
  description: string;
  isActive: boolean;
  isDefault: boolean;
  displayOrder: number;
}

@Component({
  selector: 'app-material-master',
  templateUrl: './material-master.component.html'
})
export class MaterialMasterComponent implements OnInit {
  categories: LOVItem[] = [];
  subcategories: LOVItem[] = [];
  uoms: LOVItem[] = [];
  currencies: LOVItem[] = [];

  formData = {
    category: '',
    subcategory: '',
    uom: '',
    currency: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchAllDropdowns();
  }

  fetchAllDropdowns(): void {
    this.http.get<any>('/api/lov/form/MaterialMaster').subscribe(
      response => {
        const data = response.data;
        this.categories = data.category || [];
        this.subcategories = data.subcategory || [];
        this.uoms = data.uom || [];
        this.currencies = data.currency || [];
      },
      error => {
        console.error('Error fetching dropdowns:', error);
      }
    );
  }
}
```

```html
<!-- material-master.component.html -->
<form>
  <div>
    <label>Category</label>
    <select [(ngModel)]="formData.category" name="category">
      <option value="">Select Category</option>
      <option *ngFor="let cat of categories" [value]="cat.value">
        {{ cat.displayValue }}
      </option>
    </select>
  </div>

  <div>
    <label>Subcategory</label>
    <select [(ngModel)]="formData.subcategory" name="subcategory">
      <option value="">Select Subcategory</option>
      <option *ngFor="let subcat of subcategories" [value]="subcat.value">
        {{ subcat.displayValue }}
      </option>
    </select>
  </div>

  <div>
    <label>UOM</label>
    <select [(ngModel)]="formData.uom" name="uom">
      <option value="">Select UOM</option>
      <option *ngFor="let uom of uoms" [value]="uom.value">
        {{ uom.displayValue }}
      </option>
    </select>
  </div>

  <div>
    <label>Currency</label>
    <select [(ngModel)]="formData.currency" name="currency">
      <option value="">Select Currency</option>
      <option *ngFor="let curr of currencies" [value]="curr.value">
        {{ curr.displayValue }}
      </option>
    </select>
  </div>
</form>
```

---

### Vue.js Example

```vue
<template>
  <form>
    <div>
      <label>Category</label>
      <select v-model="formData.category">
        <option value="">Select Category</option>
        <option
          v-for="cat in categories"
          :key="cat.lovId"
          :value="cat.value"
        >
          {{ cat.displayValue }}
        </option>
      </select>
    </div>

    <div>
      <label>Subcategory</label>
      <select v-model="formData.subcategory">
        <option value="">Select Subcategory</option>
        <option
          v-for="subcat in subcategories"
          :key="subcat.lovId"
          :value="subcat.value"
        >
          {{ subcat.displayValue }}
        </option>
      </select>
    </div>

    <div>
      <label>UOM</label>
      <select v-model="formData.uom">
        <option value="">Select UOM</option>
        <option
          v-for="uom in uoms"
          :key="uom.lovId"
          :value="uom.value"
        >
          {{ uom.displayValue }}
        </option>
      </select>
    </div>

    <div>
      <label>Currency</label>
      <select v-model="formData.currency">
        <option value="">Select Currency</option>
        <option
          v-for="curr in currencies"
          :key="curr.lovId"
          :value="curr.value"
        >
          {{ curr.displayValue }}
        </option>
      </select>
    </div>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  name: 'MaterialMasterForm',
  data() {
    return {
      categories: [],
      subcategories: [],
      uoms: [],
      currencies: [],
      formData: {
        category: '',
        subcategory: '',
        uom: '',
        currency: ''
      }
    };
  },
  mounted() {
    this.fetchAllDropdowns();
  },
  methods: {
    async fetchAllDropdowns() {
      try {
        const response = await axios.get('/api/lov/form/MaterialMaster');
        const data = response.data.data;

        this.categories = data.category || [];
        this.subcategories = data.subcategory || [];
        this.uoms = data.uom || [];
        this.currencies = data.currency || [];
      } catch (error) {
        console.error('Error fetching dropdowns:', error);
      }
    }
  }
};
</script>
```

---

## Form-Specific Dropdown Mappings

### Asset Master

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| locator | `/api/lov/AssetMaster/locator` | Locator dropdown |

### Contingency Purchase

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| gstPercentage | `/api/lov/ContingencyPurchase/gstPercentage` | GST (%) dropdown |
| paymentTo | `/api/lov/ContingencyPurchase/paymentTo` | Payment To dropdown |
| materialCategory | `/api/lov/ContingencyPurchase/materialCategory` | Material Category dropdown |
| materialSubCategory | `/api/lov/ContingencyPurchase/materialSubCategory` | Material Sub Category dropdown |
| countryOfOrigin | `/api/lov/ContingencyPurchase/countryOfOrigin` | Country of Origin dropdown |

### Indent Creation

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| consigneeLocation | `/api/lov/IndentCreation/consigneeLocation` | Consignee Location dropdown |

### Employee Registration

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| department | `/api/lov/EmployeeRegistration/department` | Department dropdown |
| designation | `/api/lov/EmployeeRegistration/designation` | Designation dropdown |
| location | `/api/lov/EmployeeRegistration/location` | Location dropdown |

### Job Master

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| jobCategory | `/api/lov/JobMaster/jobCategory` | Job Category dropdown |
| jobSubcategory | `/api/lov/JobMaster/jobSubcategory` | Job Subcategory dropdown |
| uom | `/api/lov/JobMaster/uom` | UOM dropdown |
| currency | `/api/lov/JobMaster/currency` | Currency dropdown |

### Material Master

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| category | `/api/lov/MaterialMaster/category` | Category dropdown |
| subcategory | `/api/lov/MaterialMaster/subcategory` | Subcategory dropdown |
| uom | `/api/lov/MaterialMaster/uom` | UOM dropdown |
| currency | `/api/lov/MaterialMaster/currency` | Currency dropdown |

### Vendor Master

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| primaryBusiness | `/api/lov/VendorMaster/primaryBusiness` | Primary Business dropdown |

### Purchase Order

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| deliveryPeriod | `/api/lov/PurchaseOrder/deliveryPeriod` | Delivery Period dropdown |
| warranty | `/api/lov/PurchaseOrder/warranty` | Warranty dropdown |
| applicablePbgToBeSubmitted | `/api/lov/PurchaseOrder/applicablePbgToBeSubmitted` | Applicable PBG to be Submitted dropdown |

### Tender Request

| Field Name | API Endpoint | Form Field |
|------------|--------------|------------|
| incoTerms | `/api/lov/TenderRequest/incoTerms` | INCO Terms dropdown |
| paymentTerms | `/api/lov/TenderRequest/paymentTerms` | Payment Terms dropdown |

---

## Best Practices

### 1. **Cache Dropdown Data on Frontend**

Since dropdown values don't change frequently, cache them in your state management (Redux, Vuex, NgRx):

```javascript
// Redux Example
const dropdownSlice = createSlice({
  name: 'dropdowns',
  initialState: {},
  reducers: {
    setDropdowns: (state, action) => {
      state[action.payload.formName] = action.payload.dropdowns;
    }
  }
});
```

### 2. **Use Bulk Fetch for Form Initialization**

Instead of making multiple API calls, fetch all dropdowns at once:

```javascript
// ✅ Good - Single API call
const response = await axios.get('/api/lov/form/MaterialMaster');

// ❌ Bad - Multiple API calls
const cat = await axios.get('/api/lov/MaterialMaster/category');
const subcat = await axios.get('/api/lov/MaterialMaster/subcategory');
const uom = await axios.get('/api/lov/MaterialMaster/uom');
const curr = await axios.get('/api/lov/MaterialMaster/currency');
```

### 3. **Handle Loading and Error States**

```javascript
const [loading, setLoading] = useState(false);
const [error, setError] = useState(null);

const fetchDropdowns = async () => {
  setLoading(true);
  setError(null);
  try {
    const response = await axios.get('/api/lov/form/MaterialMaster');
    setDropdowns(response.data.data);
  } catch (err) {
    setError('Failed to load dropdown values');
    console.error(err);
  } finally {
    setLoading(false);
  }
};
```

### 4. **Store the `value`, Display the `displayValue`**

When submitting forms, send the `value` field to the backend:

```javascript
// ✅ Correct
const submitData = {
  category: formData.category, // "COMPUTER"
  subcategory: formData.subcategory // "LAPTOP"
};

// ❌ Wrong
const submitData = {
  category: selectedCategoryObject.displayValue // "Computer"
};
```

### 5. **Set Default Values**

Use the `isDefault` flag to set initial form values:

```javascript
useEffect(() => {
  const defaultCategory = categories.find(cat => cat.isDefault);
  if (defaultCategory) {
    setFormData(prev => ({ ...prev, category: defaultCategory.value }));
  }
}, [categories]);
```

### 6. **Implement Cascading Dropdowns**

```javascript
// When category changes, fetch dependent subcategories
const handleCategoryChange = async (categoryLovId) => {
  setFormData(prev => ({ ...prev, category: categoryLovId, subcategory: '' }));

  const response = await axios.get(`/api/lov/dependent/${categoryLovId}`);
  setSubcategories(response.data.data);
};
```

---

## Troubleshooting

### Issue: Dropdowns are Empty

**Solution:**
1. Check if the database migration script has been executed
2. Verify the API response in browser dev tools
3. Ensure the form name and field name match exactly (case-sensitive)

### Issue: Getting 404 Error

**Solution:**
- Verify the base URL includes the context path: `/astro-service/api/lov`
- Check that the backend server is running on port 8081

### Issue: Dropdowns Not Updating After Admin Changes

**Solution:**
- Clear the browser cache
- The backend cache will auto-refresh when LOV values are modified through the admin panel

### Issue: Cascading Dropdown Not Working

**Solution:**
- Ensure the parent dropdown has a selected value
- Check that child LOV values have `parentLovId` set correctly in the database
- Verify you're using the correct `lovId` (not `value`) for the parent

---

## Admin Panel Management

To add, edit, or remove dropdown values:

1. **Login to Admin Panel**
2. **Navigate to**: List of Values Management
3. **Select Form**: Choose the form (e.g., MaterialMaster)
4. **Select Field**: Choose the field (e.g., category)
5. **Manage Values**:
   - **Add**: Click "Add Value" button
   - **Edit**: Click edit icon next to a value
   - **Delete**: Click delete icon (soft delete - sets isActive = false)
   - **Reorder**: Drag and drop to change display order

---

## Database Schema Reference

### form_master
- `form_id` (PK)
- `form_name` (e.g., "MaterialMaster")
- `form_display_name` (e.g., "Material Master")
- `is_active`

### designator_master
- `designator_id` (PK)
- `form_id` (FK)
- `designator_name` (e.g., "category")
- `designator_display_name` (e.g., "Category")
- `data_type` (STRING, NUMBER, DATE, BOOLEAN)
- `is_active`

### lov_master
- `lov_id` (PK)
- `designator_id` (FK)
- `lov_value` (stored value, e.g., "COMPUTER")
- `lov_display_value` (display text, e.g., "Computer")
- `lov_description` (tooltip/help text)
- `is_active`
- `is_default`
- `display_order`
- `color_code` (optional, for UI styling)
- `icon_name` (optional, for UI icons)
- `parent_lov_id` (optional, for cascading dropdowns)

---

## Summary

This LOV system provides a centralized, maintainable way to manage all dropdown values across your application. By following this guide, you can:

- ✅ Quickly integrate dropdowns into any form
- ✅ Reduce API calls with bulk fetching
- ✅ Implement cascading dropdowns
- ✅ Allow business users to manage dropdown values without code changes
- ✅ Maintain consistency across the application

For any issues or questions, please contact the backend development team.

---

**Last Updated**: 2025-12-23
**Backend Version**: 1.0.0
**Contact**: Backend Development Team
