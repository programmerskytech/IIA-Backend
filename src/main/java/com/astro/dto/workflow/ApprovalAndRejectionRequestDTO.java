package com.astro.dto.workflow;

import lombok.Data;

@Data
public class ApprovalAndRejectionRequestDTO {

    private String action;
    private Integer actionBy;
    private String remarks;
    private String requestId;


}
