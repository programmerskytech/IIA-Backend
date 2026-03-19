package com.astro.dto.workflow;

import lombok.Data;

@Data
public class AssignEmployeeToIndentDto {

    private String indentId;
    private String employeeId;
    private String employeeName;
    private String assignedBy; // added by abhinav
}
