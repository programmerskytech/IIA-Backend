package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

@Data
public class IndentCancellationApprovalDto {

    private Long requestId;
    private Integer approvedBy;
    private String approvedByName;
    private String approvalStatus; // APPROVED or REJECTED
    private String approvalRemarks;
}
