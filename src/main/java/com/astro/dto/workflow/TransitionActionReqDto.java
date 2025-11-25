package com.astro.dto.workflow;

import lombok.Data;

@Data
public class TransitionActionReqDto {

    private Integer workflowTransitionId;
    private String requestId;
    private Integer actionBy;
    private String action;
    private String remarks;
    private String assignmentRole;
    private String roleName;
}
