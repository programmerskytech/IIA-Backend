package com.astro.controller;

import com.astro.dto.workflow.DepartmentComputerPriceLimitRequestDTO;
import com.astro.dto.workflow.DepartmentComputerPriceLimitResponseDTO;
import com.astro.service.DepartmentComputerPriceLimitService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/department-computer-price-limit")
public class DepartmentComputerPriceLimitController {

    @Autowired
    private DepartmentComputerPriceLimitService priceLimitService;

    @PostMapping
    public ResponseEntity<Object> createPriceLimit(@Valid @RequestBody DepartmentComputerPriceLimitRequestDTO requestDTO) {
        DepartmentComputerPriceLimitResponseDTO response = priceLimitService.createPriceLimit(requestDTO);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePriceLimit(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentComputerPriceLimitRequestDTO requestDTO) {
        DepartmentComputerPriceLimitResponseDTO response = priceLimitService.updatePriceLimit(id, requestDTO);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPriceLimitById(@PathVariable Long id) {
        DepartmentComputerPriceLimitResponseDTO response = priceLimitService.getPriceLimitById(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentName}")
    public ResponseEntity<Object> getPriceLimitByDepartment(@PathVariable String departmentName) {
        DepartmentComputerPriceLimitResponseDTO response = priceLimitService.getPriceLimitByDepartment(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<Object> getAllActivePriceLimits() {
        List<DepartmentComputerPriceLimitResponseDTO> response = priceLimitService.getAllActivePriceLimits();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllPriceLimits() {
        List<DepartmentComputerPriceLimitResponseDTO> response = priceLimitService.getAllPriceLimits();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePriceLimit(@PathVariable Long id) {
        priceLimitService.deletePriceLimit(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Price limit deleted successfully"), HttpStatus.OK);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Object> deactivatePriceLimit(@PathVariable Long id) {
        priceLimitService.deactivatePriceLimit(id);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Price limit deactivated successfully"), HttpStatus.OK);
    }
}
