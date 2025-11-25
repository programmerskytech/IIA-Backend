package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "USER_MASTER")
@Data
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "role_name")
    private String roleName;

    private String employeeId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILENUMBER")
    private String mobileNumber;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate = LocalDateTime.now();

}
