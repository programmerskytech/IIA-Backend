package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RoleDto {

    private Integer roleId;
    private String roleName;
    private String createdBy;
    private LocalDateTime createdDate;
}
