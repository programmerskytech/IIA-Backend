package com.astro.dto.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class WorkflowDto {

    private Integer workflowId;
    private String workflowName;
    private String createdBy;
    private Date createdDate;
}
