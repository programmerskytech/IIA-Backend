package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchResponseDto {
    private Integer userId;
    private String userName;
    private String email;
    private String mobileNumber;
    private String employeeId;
    private String employeeName;
    private String roleNames;
    private String createdBy;
    private String createdDate;
    private Boolean isActive;
}
