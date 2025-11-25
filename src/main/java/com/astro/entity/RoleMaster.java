package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "ROLE_MASTER")
@Data
public class RoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLEID")
    private Integer roleId;

    @Column(name = "ROLENAME")
    private String roleName;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate = LocalDateTime.now();
  //  private Date createdDate;
}
