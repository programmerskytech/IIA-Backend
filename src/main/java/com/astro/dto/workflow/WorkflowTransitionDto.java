package com.astro.dto.workflow;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class WorkflowTransitionDto {

    private Integer workflowTransitionId;
    private Integer workflowId;
    private String workflowName;
    private Integer transitionId;
    private String requestId;
    private Integer createdBy;
    private String createdRole;
    private Integer modifiedBy;
    private String modifiedRole;
    private String status;
    private String nextAction;
    private String action;
    private String remarks;
    private Integer nextActionId;
    private String nextActionRole;
    private Integer transitionOrder;
    private Integer transitionSubOrder;
    private String currentRole;
    private String nextRole;
    private Integer workflowSequence;
    private Date modificationDate;
    private Date createdDate;
}
