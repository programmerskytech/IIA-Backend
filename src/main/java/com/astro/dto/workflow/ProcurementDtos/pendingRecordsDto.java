package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class pendingRecordsDto {
    private String status;
    private String requestId;
    private String workflowName;
    public pendingRecordsDto(String status, String requestId, String workflowName) {
        this.status = status;
        this.requestId = requestId;
        this.workflowName = workflowName;
    }

}
