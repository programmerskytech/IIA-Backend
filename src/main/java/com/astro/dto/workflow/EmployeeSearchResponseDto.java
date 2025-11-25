package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeSearchResponseDto {

    private String employeeId;
    private String employeeName;
    private String departmentName;
    private String designation;
    private String status;
}