package com.astro.dto.workflow;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SubWorkflowQueueDto {
    private String indentId;
    private String indentorName;
    private String projectName;
    private BigDecimal amount;
    private String bitType;
    private String budgetName;
    private String indentTitle;
    private String modeOfProcurement;
    private String consignee;

    private Integer subWorkflowTransitionId;
    private Integer workflowId;
    private String workflowName;
    private String requestId;
    private Integer createdBy;
    private Integer modifiedBy;
    private String status;
    private String action;
    private String remarks;
    private Integer actionOn;
    private Integer workflowSequence;
    private Date modificationDate;
    private Date createdDate;
}
