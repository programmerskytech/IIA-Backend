package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

@Data
public class IndentCancellationRequestDto {

    private String indentId;
    private Integer requestedBy;
    private String requestedByName;
    private String cancellationReason;
}
