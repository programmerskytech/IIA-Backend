package com.astro.controller.AdminPanel;

import com.astro.dto.AdminPanel.DepartmentApproverMappingDTO;
import com.astro.service.AdminPanel.DepartmentApproverService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for managing department-to-approver mappings.
 *
 * This determines whether indents from a specific department go to:
 * - Dean (approval limit: up to ₹1,50,000)
 * - Head SEG (approval limit: up to ₹1,00,000)
 *
 * If the indent value exceeds the approver's limit, it escalates to the Director.
 */
@RestController
@RequestMapping("/api/admin/department-approvers")
@CrossOrigin
public class DepartmentApproverController {

    @Autowired
    private DepartmentApproverService departmentApproverService;

    /**
     * Get all department-approver mappings
     */
    @GetMapping
    public ResponseEntity<Object> getAllMappings() {
        List<DepartmentApproverMappingDTO> mappings = departmentApproverService.getAll();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(mappings), HttpStatus.OK);
    }

    /**
     * Get mapping by ID
     */
    @GetMapping("/{mappingId}")
    public ResponseEntity<Object> getMappingById(@PathVariable Long mappingId) {
        DepartmentApproverMappingDTO mapping = departmentApproverService.getById(mappingId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(mapping), HttpStatus.OK);
    }

    /**
     * Get mappings by department name
     */
    @GetMapping("/department/{departmentName}")
    public ResponseEntity<Object> getMappingsByDepartment(@PathVariable String departmentName) {
        List<DepartmentApproverMappingDTO> mappings = departmentApproverService.getByDepartment(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(mappings), HttpStatus.OK);
    }

    /**
     * Get mappings by approver type (DEAN or HEAD_SEG)
     */
    @GetMapping("/type/{approverType}")
    public ResponseEntity<Object> getMappingsByApproverType(@PathVariable String approverType) {
        List<DepartmentApproverMappingDTO> mappings = departmentApproverService.getByApproverType(approverType);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(mappings), HttpStatus.OK);
    }

    /**
     * Get the appropriate approver for a department and indent value
     * This determines if Dean or Head SEG should approve based on amount
     */
    @GetMapping("/find-approver")
    public ResponseEntity<Object> findApproverForDepartment(
            @RequestParam String departmentName,
            @RequestParam BigDecimal indentValue) {
        DepartmentApproverMappingDTO approver = departmentApproverService.findApproverForAmount(departmentName, indentValue);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approver), HttpStatus.OK);
    }

    /**
     * Get Dean for a department
     */
    @GetMapping("/department/{departmentName}/dean")
    public ResponseEntity<Object> getDeanForDepartment(@PathVariable String departmentName) {
        DepartmentApproverMappingDTO dean = departmentApproverService.getDeanByDepartment(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(dean), HttpStatus.OK);
    }

    /**
     * Get Head SEG for a department
     */
    @GetMapping("/department/{departmentName}/head-seg")
    public ResponseEntity<Object> getHeadSEGForDepartment(@PathVariable String departmentName) {
        DepartmentApproverMappingDTO headSEG = departmentApproverService.getHeadSEGByDepartment(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(headSEG), HttpStatus.OK);
    }

    /**
     * Create new department-approver mapping
     */
    @PostMapping
    public ResponseEntity<Object> createMapping(@RequestBody DepartmentApproverMappingDTO dto) {
        try {
            DepartmentApproverMappingDTO created = departmentApproverService.create(dto);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create department-approver mapping: " + e.getMessage());
        }
    }

    /**
     * Update department-approver mapping
     */
    @PutMapping("/{mappingId}")
    public ResponseEntity<Object> updateMapping(
            @PathVariable Long mappingId,
            @RequestBody DepartmentApproverMappingDTO dto) {
        try {
            DepartmentApproverMappingDTO updated = departmentApproverService.update(mappingId, dto);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update department-approver mapping: " + e.getMessage());
        }
    }

    /**
     * Delete department-approver mapping (soft delete)
     */
    @DeleteMapping("/{mappingId}")
    public ResponseEntity<Object> deleteMapping(@PathVariable Long mappingId) {
        departmentApproverService.delete(mappingId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Department-approver mapping deleted successfully"), HttpStatus.OK);
    }

    /**
     * Get all distinct department names with configured approvers
     */
    @GetMapping("/departments")
    public ResponseEntity<Object> getDistinctDepartments() {
        List<String> departments = departmentApproverService.getDistinctDepartments();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(departments), HttpStatus.OK);
    }

    /**
     * Check if a department has an approver configured
     */
    @GetMapping("/department/{departmentName}/exists")
    public ResponseEntity<Object> hasApproverConfigured(@PathVariable String departmentName) {
        boolean exists = departmentApproverService.hasApprover(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(exists), HttpStatus.OK);
    }

    /**
     * Get all Deans
     */
    @GetMapping("/deans")
    public ResponseEntity<Object> getAllDeans() {
        List<DepartmentApproverMappingDTO> deans = departmentApproverService.getByApproverType("DEAN");
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(deans), HttpStatus.OK);
    }

    /**
     * Get all Head SEGs
     */
    @GetMapping("/head-segs")
    public ResponseEntity<Object> getAllHeadSEGs() {
        List<DepartmentApproverMappingDTO> headSEGs = departmentApproverService.getByApproverType("HEAD_SEG");
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(headSEGs), HttpStatus.OK);
    }
}
