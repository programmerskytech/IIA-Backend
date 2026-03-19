package com.astro.controller.AdminPanel;

import com.astro.dto.AdminPanel.FieldStationApproverDTO;
import com.astro.service.AdminPanel.FieldStationApproverService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Field Station In-Charge configurations.
 *
 * For non-Bangalore locations, indents are routed through:
 * - Engineer In-Charge (formerly Field Station In-Charge)
 * - Professor In-Charge (new role with same access as Engineer In-Charge)
 *
 * Both roles have the same approval responsibilities for their respective field stations.
 */
@RestController
@RequestMapping("/api/admin/field-station-approvers")
@CrossOrigin
public class FieldStationApproverController {

    @Autowired
    private FieldStationApproverService fieldStationApproverService;

    /**
     * Get all field station approvers (Engineer and Professor In-Charges)
     */
    @GetMapping
    public ResponseEntity<Object> getAllApprovers() {
        List<FieldStationApproverDTO> approvers = fieldStationApproverService.getAll();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    /**
     * Get approver by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getApproverById(@PathVariable Long id) {
        FieldStationApproverDTO approver = fieldStationApproverService.getById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approver), HttpStatus.OK);
    }

    /**
     * Get approvers by field station name
     */
    @GetMapping("/station/{fieldStationName}")
    public ResponseEntity<Object> getApproversByStation(@PathVariable String fieldStationName) {
        FieldStationApproverDTO approver = fieldStationApproverService.getByFieldStationName(fieldStationName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approver), HttpStatus.OK);
    }

    /**
     * Get approver by field station and in-charge type
     */
    @GetMapping("/station/{fieldStationName}/type/{inchargeType}")
    public ResponseEntity<Object> getApproverByStationAndType(
            @PathVariable String fieldStationName,
            @PathVariable String inchargeType) {
        FieldStationApproverDTO approver = fieldStationApproverService.getByFieldStationAndType(fieldStationName, inchargeType);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approver), HttpStatus.OK);
    }

    /**
     * Get all Engineer In-Charges
     */
    @GetMapping("/engineer-incharges")
    public ResponseEntity<Object> getAllEngineerInCharges() {
        List<FieldStationApproverDTO> approvers = fieldStationApproverService.getEngineerInCharges();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    /**
     * Get all Professor In-Charges
     */
    @GetMapping("/professor-incharges")
    public ResponseEntity<Object> getAllProfessorInCharges() {
        List<FieldStationApproverDTO> approvers = fieldStationApproverService.getProfessorInCharges();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    /**
     * Get approvers by in-charge type (ENGINEER_INCHARGE or PROFESSOR_INCHARGE)
     */
    @GetMapping("/type/{inchargeType}")
    public ResponseEntity<Object> getApproversByType(@PathVariable String inchargeType) {
        List<FieldStationApproverDTO> approvers = fieldStationApproverService.getByInchargeType(inchargeType);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    /**
     * Create new field station approver (Engineer or Professor In-Charge)
     */
    @PostMapping
    public ResponseEntity<Object> createApprover(@RequestBody FieldStationApproverDTO dto) {
        try {
            FieldStationApproverDTO created = fieldStationApproverService.create(dto);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create field station approver: " + e.getMessage());
        }
    }

    /**
     * Update field station approver
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateApprover(
            @PathVariable Long id,
            @RequestBody FieldStationApproverDTO dto) {
        try {
            FieldStationApproverDTO updated = fieldStationApproverService.update(id, dto);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update field station approver: " + e.getMessage());
        }
    }

    /**
     * Delete field station approver (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApprover(@PathVariable Long id) {
        fieldStationApproverService.delete(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Field station approver deleted successfully"), HttpStatus.OK);
    }

    /**
     * Get all distinct field station names
     */
    @GetMapping("/stations")
    public ResponseEntity<Object> getAllFieldStations() {
        List<String> stations = fieldStationApproverService.getDistinctFieldStations();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(stations), HttpStatus.OK);
    }

    /**
     * Check if a location is a field station (non-Bangalore)
     */
    @GetMapping("/is-field-station")
    public ResponseEntity<Object> checkIfFieldStation(@RequestParam String location) {
        boolean isFieldStation = fieldStationApproverService.isFieldStation(location);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(isFieldStation), HttpStatus.OK);
    }

    /**
     * Check if a field station has an in-charge configured
     */
    @GetMapping("/station/{fieldStationName}/has-incharge")
    public ResponseEntity<Object> hasInCharge(@PathVariable String fieldStationName) {
        boolean hasInCharge = fieldStationApproverService.hasInCharge(fieldStationName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(hasInCharge), HttpStatus.OK);
    }

    /**
     * Get the in-charge for a location (returns null for Bangalore)
     */
    @GetMapping("/location/{location}/incharge")
    public ResponseEntity<Object> getInChargeForLocation(@PathVariable String location) {
        var inCharge = fieldStationApproverService.getFieldStationInCharge(location);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(inCharge), HttpStatus.OK);
    }
}
