package com.astro.dto.workflow;

import lombok.Data;

@Data
public class LoginRoleDto {
    private Integer userRoleId;
    private Integer roleId;
    private String roleName;
    private Boolean readPermission;
    private Boolean writePermission;
}
