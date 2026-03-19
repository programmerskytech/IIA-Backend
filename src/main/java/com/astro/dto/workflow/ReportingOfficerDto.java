package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Reporting Officer dropdown values.
 * Contains employee ID and name for selection in employee registration form.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportingOfficerDto {

    private String employeeId;
    private String employeeName;
    private String designation;
    private String departmentName;

    // Display value for dropdown: "Employee ID - Employee Name"
    public String getDisplayValue() {
        return employeeId + " - " + employeeName;
    }
}
