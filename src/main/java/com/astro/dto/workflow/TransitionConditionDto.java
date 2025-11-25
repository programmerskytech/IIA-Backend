package com.astro.dto.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class TransitionConditionDto {

    private Integer conditionId;
    private Integer workflowId;
    private String conditionKey;
    private String conditionValue;
    private String createdBy;
    private Date createdDate;
}
