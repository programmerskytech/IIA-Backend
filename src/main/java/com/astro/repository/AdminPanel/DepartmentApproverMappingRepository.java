package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.DepartmentApproverMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentApproverMappingRepository extends JpaRepository<DepartmentApproverMapping, Long> {

    /**
     * Find all approvers for a department
     */
    List<DepartmentApproverMapping> findByDepartmentNameAndIsActiveTrue(String departmentName);

    /**
     * Find specific approver type for a department (Dean or Head SEG)
     */
    Optional<DepartmentApproverMapping> findByDepartmentNameAndApproverTypeAndIsActiveTrue(
            String departmentName, String approverType);

    /**
     * Find all approvers of a specific type (e.g., all Deans)
     */
    List<DepartmentApproverMapping> findByApproverTypeAndIsActiveTrue(String approverType);

    /**
     * Find all active mappings
     */
    List<DepartmentApproverMapping> findByIsActiveTrue();

    /**
     * Find by approver employee ID
     */
    List<DepartmentApproverMapping> findByApproverEmployeeIdAndIsActiveTrue(String approverEmployeeId);

    /**
     * Find Dean for a department
     */
    @Query("SELECT d FROM DepartmentApproverMapping d WHERE " +
           "d.departmentName = :dept AND d.approverType = 'DEAN' AND d.isActive = true")
    Optional<DepartmentApproverMapping> findDeanByDepartment(@Param("dept") String departmentName);

    /**
     * Find Head SEG for a department
     */
    @Query("SELECT d FROM DepartmentApproverMapping d WHERE " +
           "d.departmentName = :dept AND d.approverType = 'HEAD_SEG' AND d.isActive = true")
    Optional<DepartmentApproverMapping> findHeadSEGByDepartment(@Param("dept") String departmentName);

    /**
     * Find all departments that have a specific approver type configured
     */
    @Query("SELECT DISTINCT d.departmentName FROM DepartmentApproverMapping d WHERE " +
           "d.approverType = :type AND d.isActive = true")
    List<String> findDepartmentsWithApproverType(@Param("type") String approverType);

    /**
     * Check if department has any approver mapping
     */
    boolean existsByDepartmentNameAndIsActiveTrue(String departmentName);

    /**
     * Find all distinct department names with approvers configured
     */
    @Query("SELECT DISTINCT d.departmentName FROM DepartmentApproverMapping d WHERE d.isActive = true ORDER BY d.departmentName")
    List<String> findDistinctDepartmentNames();
}
