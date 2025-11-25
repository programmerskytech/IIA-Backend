package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserDto {

    private Integer userId;
    private String password;
    private String userName;
    private String mobileNumber;
    private String roleName;
    private String createdBy;
    private LocalDateTime createdDate;
}
