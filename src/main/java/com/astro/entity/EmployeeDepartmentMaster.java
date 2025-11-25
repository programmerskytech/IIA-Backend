package com.astro.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "employee_department_master")
public class EmployeeDepartmentMaster {

    @Id
    @Column(name = "employee_id")
    private String employeeId;
    
    @Column(name = "employee_name", nullable = false)
    private String employeeName;
    
    @Column(name = "department_name", nullable = false)
    private String departmentName;
    
    @Column(name = "location", nullable = false)
    private String location;
    
    @Column(name = "designation", nullable = false)
    private String designation;
    
    // Separate contact detail fields
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;
    
    @Column(name = "email_address", nullable = false)
    private String emailAddress;
    
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
    
    // Status field - Active or Inactive
    @Column(name = "status", nullable = false)
    private String status = "Active";
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    
    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @Column(name = "is_draft")
    private Boolean isDraft = false;
}