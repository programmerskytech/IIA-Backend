#!/bin/bash

# LOV Implementation Test Script
# This script tests all the key API endpoints to verify the LOV implementation

BASE_URL="http://localhost:8081/astro-service/api/lov"
ADMIN_URL="http://localhost:8081/astro-service/api/admin/lov"

echo "=========================================="
echo "LOV Implementation Test Script"
echo "=========================================="
echo ""

# Color codes for output
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counter
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# Function to test an endpoint
test_endpoint() {
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    local url=$1
    local description=$2

    echo -e "${BLUE}Test $TOTAL_TESTS: $description${NC}"
    echo "Endpoint: $url"

    response=$(curl -s -o /dev/null -w "%{http_code}" "$url")

    if [ "$response" -eq 200 ]; then
        echo -e "${GREEN}✓ PASSED${NC} (HTTP $response)"
        PASSED_TESTS=$((PASSED_TESTS + 1))
    else
        echo -e "${RED}✗ FAILED${NC} (HTTP $response)"
        FAILED_TESTS=$((FAILED_TESTS + 1))
    fi
    echo ""
}

echo "=========================================="
echo "Testing Common LOV Endpoints"
echo "=========================================="
echo ""

# Test Material Master dropdowns
test_endpoint "$BASE_URL/MaterialMaster/category" "Get Material Categories"
test_endpoint "$BASE_URL/MaterialMaster/subcategory" "Get Material Subcategories"
test_endpoint "$BASE_URL/MaterialMaster/uom" "Get Material UOM"
test_endpoint "$BASE_URL/MaterialMaster/currency" "Get Material Currency"

# Test Job Master dropdowns
test_endpoint "$BASE_URL/JobMaster/jobCategory" "Get Job Categories"
test_endpoint "$BASE_URL/JobMaster/jobSubcategory" "Get Job Subcategories"
test_endpoint "$BASE_URL/JobMaster/uom" "Get Job UOM"
test_endpoint "$BASE_URL/JobMaster/currency" "Get Job Currency"

# Test Employee Registration dropdowns
test_endpoint "$BASE_URL/EmployeeRegistration/department" "Get Departments"
test_endpoint "$BASE_URL/EmployeeRegistration/designation" "Get Designations"
test_endpoint "$BASE_URL/EmployeeRegistration/location" "Get Locations"

# Test Asset Master dropdowns
test_endpoint "$BASE_URL/AssetMaster/locator" "Get Asset Locators"

# Test Contingency Purchase dropdowns
test_endpoint "$BASE_URL/ContingencyPurchase/gstPercentage" "Get GST Percentages"
test_endpoint "$BASE_URL/ContingencyPurchase/paymentTo" "Get Payment To Options"
test_endpoint "$BASE_URL/ContingencyPurchase/materialCategory" "Get Material Categories (CP)"
test_endpoint "$BASE_URL/ContingencyPurchase/countryOfOrigin" "Get Countries of Origin"

# Test Indent Creation dropdowns
test_endpoint "$BASE_URL/IndentCreation/consigneeLocation" "Get Consignee Locations"

# Test Purchase Order dropdowns
test_endpoint "$BASE_URL/PurchaseOrder/deliveryPeriod" "Get Delivery Periods"
test_endpoint "$BASE_URL/PurchaseOrder/warranty" "Get Warranties"
test_endpoint "$BASE_URL/PurchaseOrder/applicablePbgToBeSubmitted" "Get PBG Options"

# Test Tender Request dropdowns
test_endpoint "$BASE_URL/TenderRequest/incoTerms" "Get INCO Terms"
test_endpoint "$BASE_URL/TenderRequest/paymentTerms" "Get Payment Terms"

# Test Vendor Master dropdowns
test_endpoint "$BASE_URL/VendorMaster/primaryBusiness" "Get Primary Business Types"

echo "=========================================="
echo "Testing Quick Reference Endpoints"
echo "=========================================="
echo ""

test_endpoint "$BASE_URL/material/categories" "Quick: Material Categories"
test_endpoint "$BASE_URL/job/uom" "Quick: Job UOM"
test_endpoint "$BASE_URL/employee/departments" "Quick: Departments"
test_endpoint "$BASE_URL/vendor/primary-business" "Quick: Vendor Primary Business"

echo "=========================================="
echo "Testing Bulk Endpoints"
echo "=========================================="
echo ""

test_endpoint "$BASE_URL/form/MaterialMaster" "Get All Material Master Dropdowns"
test_endpoint "$BASE_URL/form/JobMaster" "Get All Job Master Dropdowns"
test_endpoint "$BASE_URL/form/EmployeeRegistration" "Get All Employee Dropdowns"

echo "=========================================="
echo "Testing Admin Panel Endpoints"
echo "=========================================="
echo ""

test_endpoint "$ADMIN_URL/forms" "Get All Forms (Admin)"

echo "=========================================="
echo "Test Summary"
echo "=========================================="
echo ""
echo "Total Tests: $TOTAL_TESTS"
echo -e "${GREEN}Passed: $PASSED_TESTS${NC}"
echo -e "${RED}Failed: $FAILED_TESTS${NC}"
echo ""

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "${GREEN}=========================================="
    echo "All Tests Passed! ✓"
    echo -e "==========================================${NC}"
    exit 0
else
    echo -e "${RED}=========================================="
    echo "Some Tests Failed! ✗"
    echo -e "==========================================${NC}"
    exit 1
fi
