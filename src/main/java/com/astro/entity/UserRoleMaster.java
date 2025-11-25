package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_ROLE_MASTER")
@Data
public class UserRoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERROLEID")
    private Integer userRoleId;

    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "ROLEID")
    private Integer roleId;

    @Column(name = "READPERMISSION")
    private Boolean readPermission;

    @Column(name = "WRITEPERMISSION")
    private Boolean writePermission;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;
}
