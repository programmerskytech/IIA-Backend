package com.astro.service;

import com.astro.dto.workflow.*;

import java.util.List;

public interface EmployeeDepartmentMasterService {

    EmployeeDepartmentMasterResponseDto createEmployeeDepartment(EmployeeDepartmentMasterRequestDto employeeRequestDto);

    EmployeeDepartmentMasterResponseDto createEmployeeDepartmentWithUser(EmployeeDepartmentMasterRequestDto employeeRequestDto);

    EmployeeDepartmentMasterResponseDto updateEmployeeDepartmentMaster(String employeeId, EmployeeDepartmentMasterRequestDto employeeRequestDto);
    
    List<EmployeeDepartmentMasterResponseDto> getAllEmployeeDepartmentMasters();
    
    List<employeedto> getAllEmployeeDepartmentMasterswithName();

    EmployeeDepartmentMasterResponseDto getEmployeeDepartmentMasterById(String employeeId);
    
    void deleteEmployeeDepartmentMasterr(String employeeId);

    List<EmployeeSearchResponseDto> searchEmployees(String keyword);
    
    // New methods for status management
    EmployeeDepartmentMasterResponseDto activateEmployee(String employeeId, String updatedBy);
    
    EmployeeDepartmentMasterResponseDto deactivateEmployee(String employeeId, String updatedBy);
    
    // LOV methods
    List<DesignationDto> getAllDesignations();
    
    List<DepartmentDto> getAllDepartments();

    // Save as draft
    EmployeeDepartmentMasterResponseDto saveAsDraft(EmployeeDepartmentMasterRequestDto requestDto);

    // Get all drafts by user
    List<EmployeeDepartmentMasterResponseDto> getDraftsByUser(String userId);

    // Get all drafts
    List<EmployeeDepartmentMasterResponseDto> getAllDrafts();

    // Submit draft (convert draft to final)
    EmployeeDepartmentMasterResponseDto submitDraft(String employeeId, EmployeeDepartmentMasterRequestDto requestDto);

    // Add this method
    List<employeedto> getEmployeesByDepartment(String departmentName);
    
    // ✅ ADD THIS: Get department by employee name
    String getDepartmentByEmployeeName(String employeeName);
}