package com.astro.dto.workflow;

import lombok.Data;

import java.util.List;


@Data
public class userRequestDto {

    private String userName;
    private String password;
    private List<String> roleNames;
  //  private String roleName;
    private String email;
    private String mobileNumber;
    private String employeeId;
    private String createdBy;

}
