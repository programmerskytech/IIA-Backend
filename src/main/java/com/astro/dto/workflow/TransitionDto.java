package com.astro.dto.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class TransitionDto {

    private Integer transitionId;
    private String transitionName;
    private Integer workflowId;
    private String workflowName;
    private Integer currentRoleId;
    private String currentRoleName;
    private Integer nextRoleId;
    private String nextRoleName;
    private Integer previousRoleId;
    private String previousRoleName;
    private Integer conditionId;
    private String conditionKey;
    private String conditionValue;
    private Integer transitionOrder;
    private Integer transitionSubOrder;
    private String createdBy;
    private Date createdDate;
}
