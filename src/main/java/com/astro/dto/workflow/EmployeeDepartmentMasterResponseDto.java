package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeDepartmentMasterResponseDto {

    private String employeeId;
    private String employeeName;
    private String departmentName;
    private String location;
    private String designation;
    
    // Separate contact detail fields
    private String phoneNumber;
    private String emailAddress;
    private String address;
    
    // Status field
    private String status;
    
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isDraft;
}