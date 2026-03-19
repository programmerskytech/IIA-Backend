package com.astro.service.AdminPanel;

import com.astro.dto.AdminPanel.DepartmentApproverMappingDTO;
import com.astro.entity.AdminPanel.DepartmentApproverMapping;

import java.math.BigDecimal;
import java.util.List;

public interface DepartmentApproverService {

    // CRUD operations
    DepartmentApproverMappingDTO create(DepartmentApproverMappingDTO dto);

    DepartmentApproverMappingDTO update(Long mappingId, DepartmentApproverMappingDTO dto);

    void delete(Long mappingId);

    DepartmentApproverMappingDTO getById(Long mappingId);

    List<DepartmentApproverMappingDTO> getAll();

    // Query operations
    List<DepartmentApproverMappingDTO> getByDepartment(String departmentName);

    DepartmentApproverMappingDTO getDeanForDepartment(String departmentName);

    DepartmentApproverMappingDTO getHeadSEGForDepartment(String departmentName);

    DepartmentApproverMappingDTO getApproverByTypeAndDepartment(String departmentName, String approverType);

    List<DepartmentApproverMappingDTO> getAllDeans();

    List<DepartmentApproverMappingDTO> getAllHeadSEGs();

    // Business logic
    /**
     * Determine the appropriate approver (Dean or Head SEG) for a department based on amount
     * Head SEG has limit of 1,00,000; Dean has limit of 1,50,000
     * If amount exceeds both, returns Dean (will escalate to Director)
     */
    DepartmentApproverMapping getApproverForDepartmentAndAmount(String departmentName, BigDecimal amount);

    /**
     * Check if the department has a configured approver of the specified type
     */
    boolean hasApprover(String departmentName, String approverType);

    /**
     * Check if the department has any approver configured
     */
    default boolean hasApprover(String departmentName) {
        return hasApprover(departmentName, null);
    }

    /**
     * Get all departments that have approvers configured
     */
    List<String> getDepartmentsWithApprovers();

    /**
     * Alias for getDepartmentsWithApprovers
     */
    default List<String> getDistinctDepartments() {
        return getDepartmentsWithApprovers();
    }

    /**
     * Get mappings by approver type (DEAN or HEAD_SEG)
     */
    List<DepartmentApproverMappingDTO> getByApproverType(String approverType);

    /**
     * Alias for getDeanForDepartment
     */
    default DepartmentApproverMappingDTO getDeanByDepartment(String departmentName) {
        return getDeanForDepartment(departmentName);
    }

    /**
     * Alias for getHeadSEGForDepartment
     */
    default DepartmentApproverMappingDTO getHeadSEGByDepartment(String departmentName) {
        return getHeadSEGForDepartment(departmentName);
    }

    /**
     * Find the appropriate approver (Dean or Head SEG) based on amount
     */
    DepartmentApproverMappingDTO findApproverForAmount(String departmentName, BigDecimal indentValue);
}
