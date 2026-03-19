package com.astro.dto.workflow.ProjectMaster;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProjectMasterResponseDto {
    private String projectCode;
    private String projectNameDescription;
    private String financialYear;
    private BigDecimal allocatedAmount;
    private BigDecimal availableProjectLimit;
    private String departmentDivision;
    private String budgetType;
    private String startDate;
    private String endDate;
    private String remarksNotes;
    private String projectHead; // Employee ID of the project head
    private String projectHeadName; // Name of the project head

    // New Admin Panel fields
    private String status;
    private String category;
    private String budgetCode;

    private String updatedBy;
    private String createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
