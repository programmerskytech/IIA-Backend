package com.astro.controller;

import com.astro.dto.AdminPanel.LOVResponseDto;
import com.astro.entity.AdminPanel.LOVMaster;
import com.astro.service.AdminPanel.LOVService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<LOVMaster> values = lovService.getLOVsByFormAndField(formName, fieldName);
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
        Map<String, List<LOVMaster>> dropdowns = lovService.getAllDropdownsForForm(formName);

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
        Map<String, List<LOVMaster>> bulkLOVs = lovService.getBulkLOVs(formFieldPairs);

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
        List<LOVMaster> dependentValues = lovService.getDependentLOVs(parentLovId);
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
