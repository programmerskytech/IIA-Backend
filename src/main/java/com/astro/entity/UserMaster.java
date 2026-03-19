package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_master")
@Data
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "employee_id")
    private String employeeId;

    private String password;

    private String email;

    private String mobileNumber;

    private String createdBy;

    private LocalDateTime createdDate = LocalDateTime.now();

    // TC_14 FIX: Track if user has changed password after first login
    @Column(name = "is_first_login", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isFirstLogin = true;

    @Column(name = "last_password_change_date")
    private LocalDateTime lastPasswordChangeDate;

    // User active/inactive status (soft delete instead of hard delete)
    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

}
