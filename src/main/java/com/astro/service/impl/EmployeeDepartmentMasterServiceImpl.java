package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.*;
import com.astro.entity.DepartmentMaster;
import com.astro.entity.DesignationMaster;
import com.astro.entity.EmployeeDepartmentMaster;
import com.astro.entity.EmployeeIdSequence;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.repository.DepartmentMasterRepository;
import com.astro.repository.DesignationMasterRepository;
import com.astro.repository.EmployeeDepartmentMasterRepository;
import com.astro.repository.EmployeeIdSequenceRepository;
import com.astro.service.EmployeeDepartmentMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeDepartmentMasterServiceImpl implements EmployeeDepartmentMasterService {

    @Autowired
    private EmployeeDepartmentMasterRepository employeeRepository;
    
    @Autowired
    private EmployeeIdSequenceRepository employeeIdSequenceRepository;
    
    @Autowired
    private DesignationMasterRepository designationMasterRepository;
    
    @Autowired
    private DepartmentMasterRepository departmentMasterRepository;

    @Override
    @Transactional
    public EmployeeDepartmentMasterResponseDto createEmployeeDepartment(EmployeeDepartmentMasterRequestDto employeeRequestDto) {
        
        // Validate phone number
        if (!employeeRequestDto.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Phone number must be exactly 10 digits"
                )
            );
        }

        Integer maxNumber = employeeIdSequenceRepository.findMaxEmployeeId();
        int nextNumber = (maxNumber == null) ? 1100 : maxNumber + 1;

        String employeeId = "E" + nextNumber;

        EmployeeIdSequence em = new EmployeeIdSequence();
        em.setEmployeeId(nextNumber);
        employeeIdSequenceRepository.save(em);
        
        EmployeeDepartmentMaster employee = new EmployeeDepartmentMaster();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeRequestDto.getEmployeeName());
        employee.setLocation(employeeRequestDto.getLocation());
        employee.setDepartmentName(employeeRequestDto.getDepartmentName());
        employee.setDesignation(employeeRequestDto.getDesignation());
        employee.setPhoneNumber(employeeRequestDto.getPhoneNumber());
        employee.setEmailAddress(employeeRequestDto.getEmailAddress());
        employee.setAddress(employeeRequestDto.getAddress());
        employee.setStatus(employeeRequestDto.getStatus() != null ? employeeRequestDto.getStatus() : "Active");
        employee.setCreatedBy(employeeRequestDto.getCreatedBy());
        employee.setUpdatedBy(employeeRequestDto.getUpdatedBy());
        employee.setCreatedDate(LocalDateTime.now());
        employee.setUpdatedDate(LocalDateTime.now());

        employeeRepository.save(employee);
        return mapToResponseDTO(employee);
    }

    private EmployeeDepartmentMasterResponseDto mapToResponseDTO(EmployeeDepartmentMaster employee) {
        EmployeeDepartmentMasterResponseDto responseDto = new EmployeeDepartmentMasterResponseDto();
        responseDto.setEmployeeId(employee.getEmployeeId());
        responseDto.setLocation(employee.getLocation());
        responseDto.setEmployeeName(employee.getEmployeeName());
        responseDto.setDepartmentName(employee.getDepartmentName());
        responseDto.setDesignation(employee.getDesignation());
        responseDto.setPhoneNumber(employee.getPhoneNumber());
        responseDto.setEmailAddress(employee.getEmailAddress());
        responseDto.setAddress(employee.getAddress());
        responseDto.setStatus(employee.getStatus());
        responseDto.setIsDraft(employee.getIsDraft());
        responseDto.setCreatedBy(employee.getCreatedBy());
        responseDto.setUpdatedBy(employee.getUpdatedBy());
        responseDto.setCreatedDate(employee.getCreatedDate());
        responseDto.setUpdatedDate(employee.getUpdatedDate());
        return responseDto;
    }

    @Override
    @Transactional
    public EmployeeDepartmentMasterResponseDto updateEmployeeDepartmentMaster(String employeeId, EmployeeDepartmentMasterRequestDto employeeRequestDto) {
        
        // Validate phone number
        if (!employeeRequestDto.getPhoneNumber().matches("^[0-9]{10}$")) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Phone number must be exactly 10 digits"
                )
            );
        }

        EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "Employee department not found for the provided employee id."
                )
            ));

        employee.setEmployeeName(employeeRequestDto.getEmployeeName());
        employee.setDepartmentName(employeeRequestDto.getDepartmentName());
        employee.setLocation(employeeRequestDto.getLocation());
        employee.setDesignation(employeeRequestDto.getDesignation());
        employee.setPhoneNumber(employeeRequestDto.getPhoneNumber());
        employee.setEmailAddress(employeeRequestDto.getEmailAddress());
        employee.setAddress(employeeRequestDto.getAddress());
        if (employeeRequestDto.getStatus() != null) {
            employee.setStatus(employeeRequestDto.getStatus());
        }
        employee.setUpdatedBy(employeeRequestDto.getUpdatedBy());
        employee.setUpdatedDate(LocalDateTime.now());

        employeeRepository.save(employee);
        return mapToResponseDTO(employee);
    }

    @Override
    public List<EmployeeDepartmentMasterResponseDto> getAllEmployeeDepartmentMasters() {
        List<EmployeeDepartmentMaster> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<employeedto> getAllEmployeeDepartmentMasterswithName() {
        List<EmployeeDepartmentMaster> employees = employeeRepository.findAll();
        return employees.stream()
            .map(emp -> new employeedto(emp.getEmployeeId(), emp.getEmployeeName()))
            .collect(Collectors.toList());
    }

    @Override
    public EmployeeDepartmentMasterResponseDto getEmployeeDepartmentMasterById(String employeeId) {
        EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Employee not found for the provided employee id."
                )
            ));
        return mapToResponseDTO(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeeDepartmentMasterr(String employeeId) {
        EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Employee not found for the provided employee ID."
                )
            ));
        try {
            employeeRepository.delete(employee);
        } catch (Exception ex) {
            throw new BusinessException(
                new ErrorDetails(
                    AppConstant.INTER_SERVER_ERROR,
                    AppConstant.ERROR_TYPE_CODE_INTERNAL,
                    AppConstant.ERROR_TYPE_ERROR,
                    "An error occurred while deleting the employee."
                ),
                ex
            );
        }
    }

    @Override
    public List<EmployeeSearchResponseDto> searchEmployees(String keyword) {
        List<Object[]> results = employeeRepository.searchEmployeesForDropdown(keyword);
        return results.stream()
            .map(obj -> new EmployeeSearchResponseDto(
                (String) obj[0],  // employee_id
                (String) obj[1],  // employee_name
                (String) obj[2],  // department_name
                (String) obj[3],  // designation
                (String) obj[4]   // status
            ))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EmployeeDepartmentMasterResponseDto activateEmployee(String employeeId, String updatedBy) {
        EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Employee not found for the provided employee id."
                )
            ));
        
        employee.setStatus("Active");
        employee.setUpdatedBy(updatedBy);
        employee.setUpdatedDate(LocalDateTime.now());
        employeeRepository.save(employee);
        
        return mapToResponseDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDepartmentMasterResponseDto deactivateEmployee(String employeeId, String updatedBy) {
        EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Employee not found for the provided employee id."
                )
            ));
        
        employee.setStatus("Inactive");
        employee.setUpdatedBy(updatedBy);
        employee.setUpdatedDate(LocalDateTime.now());
        employeeRepository.save(employee);
        
        return mapToResponseDTO(employee);
    }

    @Override
    public List<DesignationDto> getAllDesignations() {
        List<DesignationMaster> designations = designationMasterRepository.findByIsActiveTrue();
        return designations.stream()
            .map(d -> new DesignationDto(d.getId(), d.getDesignationName()))
            .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentMaster> departments = departmentMasterRepository.findByIsActiveTrue();
        return departments.stream()
            .map(d -> new DepartmentDto(d.getId(), d.getDepartmentName()))
            .collect(Collectors.toList());
    }

    @Override
@Transactional
public EmployeeDepartmentMasterResponseDto saveAsDraft(EmployeeDepartmentMasterRequestDto requestDto) {
    
    EmployeeDepartmentMaster employee;
    
    // Check if updating existing draft or creating new
    if (requestDto.getEmployeeId() != null && !requestDto.getEmployeeId().isEmpty()) {
        // Update existing draft
        employee = employeeRepository.findById(requestDto.getEmployeeId())
            .orElseThrow(() -> new BusinessException(
                new ErrorDetails(
                    AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_RESOURCE,
                    "Draft not found for the provided employee id."
                )
            ));
    } else {
        // Create new draft with generated ID
        Integer maxNumber = employeeIdSequenceRepository.findMaxEmployeeId();
        int nextNumber = (maxNumber == null) ? 1100 : maxNumber + 1;
        String employeeId = "E" + nextNumber;

        EmployeeIdSequence em = new EmployeeIdSequence();
        em.setEmployeeId(nextNumber);
        employeeIdSequenceRepository.save(em);

        employee = new EmployeeDepartmentMaster();
        employee.setEmployeeId(employeeId);
        employee.setCreatedDate(LocalDateTime.now());
    }

    // Set fields - allowing partial/empty values for drafts
    employee.setEmployeeName(requestDto.getEmployeeName() != null ? requestDto.getEmployeeName() : "");
    employee.setLocation(requestDto.getLocation() != null ? requestDto.getLocation() : "");
    employee.setDepartmentName(requestDto.getDepartmentName() != null ? requestDto.getDepartmentName() : "");
    employee.setDesignation(requestDto.getDesignation() != null ? requestDto.getDesignation() : "");
    employee.setPhoneNumber(requestDto.getPhoneNumber() != null ? requestDto.getPhoneNumber() : "");
    employee.setEmailAddress(requestDto.getEmailAddress() != null ? requestDto.getEmailAddress() : "");
    employee.setAddress(requestDto.getAddress() != null ? requestDto.getAddress() : "");
    employee.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : "Active");
    employee.setIsDraft(true);
    employee.setCreatedBy(requestDto.getCreatedBy());
    employee.setUpdatedBy(requestDto.getUpdatedBy());
    employee.setUpdatedDate(LocalDateTime.now());

    employeeRepository.save(employee);
    return mapToResponseDTO(employee);
}

@Override
public List<EmployeeDepartmentMasterResponseDto> getDraftsByUser(String userId) {
    List<EmployeeDepartmentMaster> drafts = employeeRepository.findByCreatedByAndIsDraftTrue(userId);
    return drafts.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
}

@Override
public List<EmployeeDepartmentMasterResponseDto> getAllDrafts() {
    List<EmployeeDepartmentMaster> drafts = employeeRepository.findByIsDraftTrue();
    return drafts.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
}

@Override
@Transactional
public EmployeeDepartmentMasterResponseDto submitDraft(String employeeId, EmployeeDepartmentMasterRequestDto requestDto) {
    
    EmployeeDepartmentMaster employee = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_RESOURCE,
                "Draft not found for the provided employee id."
            )
        ));

    // Validate all required fields before submitting
    validateRequiredFields(requestDto);

    // Update all fields
    employee.setEmployeeName(requestDto.getEmployeeName());
    employee.setLocation(requestDto.getLocation());
    employee.setDepartmentName(requestDto.getDepartmentName());
    employee.setDesignation(requestDto.getDesignation());
    employee.setPhoneNumber(requestDto.getPhoneNumber());
    employee.setEmailAddress(requestDto.getEmailAddress());
    employee.setAddress(requestDto.getAddress());
    employee.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : "Active");
    employee.setIsDraft(false); // Mark as submitted
    employee.setUpdatedBy(requestDto.getUpdatedBy());
    employee.setUpdatedDate(LocalDateTime.now());

    employeeRepository.save(employee);
    return mapToResponseDTO(employee);
}

private void validateRequiredFields(EmployeeDepartmentMasterRequestDto requestDto) {


    StringBuilder errors = new StringBuilder();
    
    if (requestDto.getEmployeeName() == null || requestDto.getEmployeeName().trim().isEmpty()) {
        errors.append("Employee name is required. ");
    }
    if (requestDto.getDepartmentName() == null || requestDto.getDepartmentName().trim().isEmpty()) {
        errors.append("Department is required. ");
    }
    if (requestDto.getDesignation() == null || requestDto.getDesignation().trim().isEmpty()) {
        errors.append("Designation is required. ");
    }
    if (requestDto.getLocation() == null || requestDto.getLocation().trim().isEmpty()) {
        errors.append("Location is required. ");
    }
    if (requestDto.getPhoneNumber() == null || !requestDto.getPhoneNumber().matches("^[0-9]{10}$")) {
        errors.append("Valid 10-digit phone number is required. ");
    }
    if (requestDto.getEmailAddress() == null || requestDto.getEmailAddress().trim().isEmpty()) {
        errors.append("Email address is required. ");
    }
    if (requestDto.getAddress() == null || requestDto.getAddress().trim().isEmpty()) {
        errors.append("Address is required. ");
    }

    if (errors.length() > 0) {
        throw new BusinessException(
            new ErrorDetails(
                AppConstant.ERROR_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                AppConstant.ERROR_TYPE_VALIDATION,
                errors.toString().trim()
            )
        );
    }
}

@Override
public List<employeedto> getEmployeesByDepartment(String departmentName) {
    List<EmployeeDepartmentMaster> employees = employeeRepository
        .findByDepartmentNameAndStatusAndIsDraftFalse(departmentName, "Active");
    return employees.stream()
        .map(emp -> new employeedto(emp.getEmployeeId(), emp.getEmployeeName()))
        .collect(Collectors.toList());
}





}