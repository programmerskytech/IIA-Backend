package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="vendor_login_details")
public class VendorLoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendorId;
    private String emailAddress;
    private String password;
    private Boolean emailSent;

    @Column(name = "is_first_login")
    private Boolean isFirstLogin = true;

    @Column(name = "is_temp_password")
    private Boolean isTempPassword = true;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    private LocalDateTime createdDate = LocalDateTime.now();
}