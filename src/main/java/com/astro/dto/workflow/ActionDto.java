package com.astro.dto.workflow;

import lombok.Data;

import java.util.Date;

@Data
public class ActionDto {

    private Integer actionId;
    private String actionName;
    private String createdBy;
    private Date createdDate;
}
