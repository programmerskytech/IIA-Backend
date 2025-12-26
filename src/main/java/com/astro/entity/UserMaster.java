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

}
