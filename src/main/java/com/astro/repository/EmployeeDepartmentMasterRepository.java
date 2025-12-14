package com.astro.repository;

import com.astro.entity.EmployeeDepartmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDepartmentMasterRepository extends JpaRepository<EmployeeDepartmentMaster, String> {
    
    Optional<EmployeeDepartmentMaster> findByEmployeeId(String employeeId);
    
    List<EmployeeDepartmentMaster> findByStatus(String status);

    @Query(value = "SELECT e.employee_id, e.employee_name, e.department_name, e.designation, e.status " +
            "FROM employee_department_master e " +
            "WHERE (LOWER(e.employee_id) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(e.employee_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(e.department_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(e.designation) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "LIMIT 10",
            nativeQuery = true)
    List<Object[]> searchEmployeesForDropdown(@Param("keyword") String keyword);
    
    // Find all drafts by created user
    List<EmployeeDepartmentMaster> findByCreatedByAndIsDraftTrue(String createdBy);

    // Find all non-draft employees
    List<EmployeeDepartmentMaster> findByIsDraftFalse();

    // Find all drafts
    List<EmployeeDepartmentMaster> findByIsDraftTrue();
    
    // Add this method
    List<EmployeeDepartmentMaster> findByDepartmentNameAndStatusAndIsDraftFalse(String departmentName, String status);
    
    // ✅ ADD THIS: Case-insensitive search for employee name
    @Query("SELECT e FROM EmployeeDepartmentMaster e WHERE LOWER(TRIM(e.employeeName)) = LOWER(TRIM(:employeeName)) AND e.status = :status AND e.isDraft = false")
    Optional<EmployeeDepartmentMaster> findByEmployeeNameIgnoreCaseAndStatusAndIsDraftFalse(
        @Param("employeeName") String employeeName, 
        @Param("status") String status
    );
}