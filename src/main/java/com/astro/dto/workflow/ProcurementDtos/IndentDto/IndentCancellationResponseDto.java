package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IndentCancellationResponseDto {

    private Long id;
    private String indentId;
    private Integer requestedBy;
    private String requestedByName;
    private String cancellationReason;
    private String requestStatus;
    private Integer approvedBy;
    private String approvedByName;
    private String approvalRemarks;
    private LocalDateTime approvalDate;
    private LocalDateTime createdDate;
}
