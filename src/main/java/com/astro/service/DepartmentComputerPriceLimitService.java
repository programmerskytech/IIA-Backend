package com.astro.service;

import com.astro.dto.workflow.DepartmentComputerPriceLimitRequestDTO;
import com.astro.dto.workflow.DepartmentComputerPriceLimitResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentComputerPriceLimitService {

    DepartmentComputerPriceLimitResponseDTO createPriceLimit(DepartmentComputerPriceLimitRequestDTO requestDTO);

    DepartmentComputerPriceLimitResponseDTO updatePriceLimit(Long id, DepartmentComputerPriceLimitRequestDTO requestDTO);

    DepartmentComputerPriceLimitResponseDTO getPriceLimitById(Long id);

    DepartmentComputerPriceLimitResponseDTO getPriceLimitByDepartment(String departmentName);

    List<DepartmentComputerPriceLimitResponseDTO> getAllActivePriceLimits();

    List<DepartmentComputerPriceLimitResponseDTO> getAllPriceLimits();

    void deletePriceLimit(Long id);

    void deactivatePriceLimit(Long id);

    BigDecimal validateComputerItemPriceForDepartment(String departmentName, BigDecimal unitPrice, String materialSubCategory);
}
