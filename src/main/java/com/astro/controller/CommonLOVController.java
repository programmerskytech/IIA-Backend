package com.astro.controller;

import com.astro.dto.AdminPanel.LOVResponseDto;
import com.astro.dto.workflow.ReportingOfficerDto;
import com.astro.entity.AdminPanel.LOVMaster;
import com.astro.service.AdminPanel.LOVService;
import com.astro.service.EmployeeDepartmentMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Common Controller for frontend to fetch LOV (dropdown) values.
 * This controller provides simple, frontend-friendly APIs for all forms.
 *
 * Base URL: /api/lov
 */
@RestController
@RequestMapping("/api/lov")
@CrossOrigin
public class CommonLOVController {

    @Autowired
    private LOVService lovService;

    @Autowired
    private EmployeeDepartmentMasterService employeeDepartmentMasterService;

    /**
     * Get dropdown values for a specific field in a form
     *
     * GET /api/lov/{formName}/{fieldName}
     *
     * Examples:
     * - GET /api/lov/MaterialMaster/category
     * - GET /api/lov/JobMaster/uom
     * - GET /api/lov/EmployeeRegistration/department
     * - GET /api/lov/AssetMaster/locator
     * - GET /api/lov/ContingencyPurchase/gstPercentage
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": [
     *     {
     *       "lovId": 1,
     *       "value": "COMPUTER",
     *       "displayValue": "Computer",
     *       "description": "Computer Equipment",
     *       "isActive": true,
     *       "isDefault": false,
     *       "displayOrder": 1,
     *       "colorCode": null,
     *       "iconName": null,
     *       "parentLovId": null
     *     }
     *   ]
     * }
     */
    @GetMapping("/{formName}/{fieldName}")
    public ResponseEntity<Object> getDropdownValues(
            @PathVariable String formName,
            @PathVariable String fieldName) {
        // FIX: Use active-only method for frontend dropdowns - deleted items should not show
        List<LOVMaster> values = lovService.getActiveLOVsByFormAndField(formName, fieldName);
        List<LOVResponseDto> response = values.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * Get all dropdowns for a specific form in a single call
     *
     * GET /api/lov/form/{formName}
     *
     * Examples:
     * - GET /api/lov/form/MaterialMaster
     * - GET /api/lov/form/JobMaster
     * - GET /api/lov/form/EmployeeRegistration
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": {
     *     "category": [{ lov objects }],
     *     "subcategory": [{ lov objects }],
     *     "uom": [{ lov objects }],
     *     "currency": [{ lov objects }]
     *   }
     * }
     */
    @GetMapping("/form/{formName}")
    public ResponseEntity<Object> getAllDropdownsForForm(@PathVariable String formName) {
        // FIX: Use active-only method for frontend dropdowns - deleted items should not show
        Map<String, List<LOVMaster>> dropdowns = lovService.getActiveDropdownsForForm(formName);

        // Convert to DTO format
        Map<String, List<LOVResponseDto>> response = new HashMap<>();
        for (Map.Entry<String, List<LOVMaster>> entry : dropdowns.entrySet()) {
            List<LOVResponseDto> lovDtos = entry.getValue().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            response.put(entry.getKey(), lovDtos);
        }

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * Get multiple dropdown values in a single call (bulk fetch)
     * Useful when you need dropdowns from different forms
     *
     * POST /api/lov/bulk
     *
     * Request Body:
     * [
     *   "MaterialMaster.category",
     *   "MaterialMaster.subcategory",
     *   "JobMaster.uom",
     *   "EmployeeRegistration.department"
     * ]
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": {
     *     "MaterialMaster.category": [{ lov objects }],
     *     "MaterialMaster.subcategory": [{ lov objects }],
     *     "JobMaster.uom": [{ lov objects }],
     *     "EmployeeRegistration.department": [{ lov objects }]
     *   }
     * }
     */
    @PostMapping("/bulk")
    public ResponseEntity<Object> getBulkDropdowns(@RequestBody List<String> formFieldPairs) {
        // FIX: Use active-only method for frontend dropdowns - deleted items should not show
        Map<String, List<LOVMaster>> bulkLOVs = lovService.getActiveBulkLOVs(formFieldPairs);

        // Convert to DTO format
        Map<String, List<LOVResponseDto>> response = new HashMap<>();
        for (Map.Entry<String, List<LOVMaster>> entry : bulkLOVs.entrySet()) {
            List<LOVResponseDto> lovDtos = entry.getValue().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            response.put(entry.getKey(), lovDtos);
        }

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * Get dependent dropdown values (for cascading dropdowns)
     *
     * GET /api/lov/dependent/{parentLovId}
     *
     * Example: If category is selected, get subcategories for that category
     * - GET /api/lov/dependent/5
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": [
     *     { lov objects that have parentLovId = 5 }
     *   ]
     * }
     */
    @GetMapping("/dependent/{parentLovId}")
    public ResponseEntity<Object> getDependentDropdowns(@PathVariable Long parentLovId) {
        // FIX: Use active-only method for frontend dropdowns - deleted items should not show
        List<LOVMaster> dependentValues = lovService.getActiveDependentLOVs(parentLovId);
        List<LOVResponseDto> response = dependentValues.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    /**
     * Quick reference endpoints for common dropdowns
     */

    @GetMapping("/asset-master/locator")
    public ResponseEntity<Object> getAssetLocators() {
        return getDropdownValues("AssetMaster", "locator");
    }

    @GetMapping("/contingency-purchase/gst")
    public ResponseEntity<Object> getGstPercentages() {
        return getDropdownValues("ContingencyPurchase", "gstPercentage");
    }

    @GetMapping("/contingency-purchase/payment-to")
    public ResponseEntity<Object> getPaymentToOptions() {
        return getDropdownValues("ContingencyPurchase", "paymentTo");
    }

    @GetMapping("/contingency-purchase/material-category")
    public ResponseEntity<Object> getContingencyMaterialCategories() {
        return getDropdownValues("ContingencyPurchase", "materialCategory");
    }

    @GetMapping("/contingency-purchase/material-subcategory")
    public ResponseEntity<Object> getContingencyMaterialSubCategories() {
        return getDropdownValues("ContingencyPurchase", "materialSubCategory");
    }

    @GetMapping("/contingency-purchase/country-of-origin")
    public ResponseEntity<Object> getCountriesOfOrigin() {
        return getDropdownValues("ContingencyPurchase", "countryOfOrigin");
    }

    @GetMapping("/indent/consignee-location")
    public ResponseEntity<Object> getConsigneeLocations() {
        return getDropdownValues("IndentCreation", "consigneeLocation");
    }

    @GetMapping("/employee/departments")
    public ResponseEntity<Object> getDepartments() {
        return getDropdownValues("EmployeeRegistration", "department");
    }

    @GetMapping("/employee/designations")
    public ResponseEntity<Object> getDesignations() {
        return getDropdownValues("EmployeeRegistration", "designation");
    }

    @GetMapping("/employee/locations")
    public ResponseEntity<Object> getEmployeeLocations() {
        return getDropdownValues("EmployeeRegistration", "location");
    }

    /**
     * Get all reporting officers for employee registration dropdown.
     * Returns list of active employees with their Employee ID and Name.
     *
     * GET /api/lov/employee/reporting-officers
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": [
     *     {
     *       "employeeId": "E1001",
     *       "employeeName": "John Doe",
     *       "designation": "Manager",
     *       "departmentName": "IT",
     *       "displayValue": "E1001 - John Doe"
     *     }
     *   ]
     * }
     */
    @GetMapping("/employee/reporting-officers")
    public ResponseEntity<Object> getReportingOfficers() {
        List<ReportingOfficerDto> reportingOfficers = employeeDepartmentMasterService.getAllReportingOfficers();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(reportingOfficers), HttpStatus.OK);
    }

    /**
     * Get all Indian states for employee registration dropdown.
     *
     * GET /api/lov/employee/states
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": [
     *     { "value": "Karnataka", "displayValue": "Karnataka" },
     *     { "value": "Maharashtra", "displayValue": "Maharashtra" }
     *   ]
     * }
     */
    @GetMapping("/employee/states")
    public ResponseEntity<Object> getStates() {
        List<Map<String, String>> states = employeeDepartmentMasterService.getAllStates();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(states), HttpStatus.OK);
    }

    /**
     * Get cities by state for employee registration dropdown (cascading dropdown).
     *
     * GET /api/lov/employee/cities?state=Karnataka
     *
     * Response Format:
     * {
     *   "status": "success",
     *   "data": [
     *     { "value": "Bangalore", "displayValue": "Bangalore" },
     *     { "value": "Mysore", "displayValue": "Mysore" }
     *   ]
     * }
     */
    @GetMapping("/employee/cities")
    public ResponseEntity<Object> getCitiesByState(@RequestParam String state) {
        List<Map<String, String>> cities = employeeDepartmentMasterService.getCitiesByState(state);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(cities), HttpStatus.OK);
    }

    @GetMapping("/job/categories")
    public ResponseEntity<Object> getJobCategories() {
        return getDropdownValues("JobMaster", "jobCategory");
    }

    @GetMapping("/job/subcategories")
    public ResponseEntity<Object> getJobSubcategories() {
        return getDropdownValues("JobMaster", "jobSubcategory");
    }

    @GetMapping("/job/uom")
    public ResponseEntity<Object> getJobUOM() {
        return getDropdownValues("JobMaster", "uom");
    }

    @GetMapping("/job/currency")
    public ResponseEntity<Object> getJobCurrency() {
        return getDropdownValues("JobMaster", "currency");
    }

    @GetMapping("/material/categories")
    public ResponseEntity<Object> getMaterialCategories() {
        return getDropdownValues("MaterialMaster", "category");
    }

    @GetMapping("/material/subcategories")
    public ResponseEntity<Object> getMaterialSubcategories() {
        return getDropdownValues("MaterialMaster", "subcategory");
    }

    @GetMapping("/material/uom")
    public ResponseEntity<Object> getMaterialUOM() {
        return getDropdownValues("MaterialMaster", "uom");
    }

    @GetMapping("/material/currency")
    public ResponseEntity<Object> getMaterialCurrency() {
        return getDropdownValues("MaterialMaster", "currency");
    }

    @GetMapping("/vendor/primary-business")
    public ResponseEntity<Object> getVendorPrimaryBusiness() {
        return getDropdownValues("VendorMaster", "primaryBusiness");
    }

    @GetMapping("/purchase-order/delivery-periods")
    public ResponseEntity<Object> getDeliveryPeriods() {
        return getDropdownValues("PurchaseOrder", "deliveryPeriod");
    }

    @GetMapping("/purchase-order/warranties")
    public ResponseEntity<Object> getWarranties() {
        return getDropdownValues("PurchaseOrder", "warranty");
    }

    @GetMapping("/purchase-order/pbg")
    public ResponseEntity<Object> getPBGOptions() {
        return getDropdownValues("PurchaseOrder", "applicablePbgToBeSubmitted");
    }

    @GetMapping("/tender/inco-terms")
    public ResponseEntity<Object> getIncoTerms() {
        return getDropdownValues("TenderRequest", "incoTerms");
    }

    @GetMapping("/tender/payment-terms")
    public ResponseEntity<Object> getPaymentTerms() {
        return getDropdownValues("TenderRequest", "paymentTerms");
    }

    /**
     * Get all employees for Project Head dropdown in Project Master form.
     * Returns list of active employees with their Employee ID and Name.
     *
     * GET /api/lov/project/heads
     */
    @GetMapping("/project/heads")
    public ResponseEntity<Object> getProjectHeads() {
        List<ReportingOfficerDto> employees = employeeDepartmentMasterService.getAllReportingOfficers();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(employees), HttpStatus.OK);
    }

    /**
     * Get department dropdown values for Project Master form.
     *
     * GET /api/lov/project/departments
     */
    @GetMapping("/project/departments")
    public ResponseEntity<Object> getProjectDepartments() {
        return getDropdownValues("ProjectMaster", "department");
    }

    /**
     * Get category dropdown values for Project Master form.
     *
     * GET /api/lov/project/categories
     */
    @GetMapping("/project/categories")
    public ResponseEntity<Object> getProjectCategories() {
        return getDropdownValues("ProjectMaster", "category");
    }

    /**
     * Get budget type dropdown values for Project Master form.
     *
     * GET /api/lov/project/budget-types
     */
    @GetMapping("/project/budget-types")
    public ResponseEntity<Object> getProjectBudgetTypes() {
        return getDropdownValues("ProjectMaster", "budgetType");
    }

    /**
     * Get status dropdown values for Project Master form.
     *
     * GET /api/lov/project/statuses
     */
    @GetMapping("/project/statuses")
    public ResponseEntity<Object> getProjectStatuses() {
        return getDropdownValues("ProjectMaster", "status");
    }

    // ========== HELPER METHOD ==========

    private LOVResponseDto convertToDto(LOVMaster lov) {
        LOVResponseDto dto = new LOVResponseDto();
        dto.setLovId(lov.getLovId());
        dto.setValue(lov.getLovValue());
        dto.setDisplayValue(lov.getLovDisplayValue());
        dto.setDescription(lov.getLovDescription());
        dto.setIsActive(lov.getIsActive());
        dto.setIsDefault(lov.getIsDefault());
        dto.setDisplayOrder(lov.getDisplayOrder());
        dto.setColorCode(lov.getColorCode());
        dto.setIconName(lov.getIconName());
        dto.setParentLovId(lov.getParentLovId());
        return dto;
    }
}
