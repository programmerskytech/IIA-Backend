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

    // Split name fields
    private String firstName;
    private String lastName;

    // Separate contact detail fields
    private String phoneNumber;
    private String emailAddress;
    private String address;

    // Split address fields
    private String streetAddress;
    private String city;
    private String state;
    private String pinCode;

    // Reporting Officer (replaces manager)
    private String reportingOfficerId;
    private String reportingOfficerName;

    // Status field
    private String status;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean isDraft;

    // For displaying userId in frontend
    private Integer userId;
}