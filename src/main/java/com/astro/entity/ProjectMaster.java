package com.astro.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_master")
@Data
public class ProjectMaster {

    @Id
    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "project_name_description")
    private String projectNameDescription;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "allocated_amount")
    private BigDecimal allocatedAmount;

    @Column(name = "department_division")
    private String departmentDivision;

    @Column(name = "budget_type")
    private String budgetType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "remarks_notes")
    private String remarksNotes;

    @Column(name = "project_head")
    private String projectHead;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();


}
