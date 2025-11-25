package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

@Data
public class CancelIndentRequestDto {

    private String indentId;
    private Boolean cancelStatus;
    private String cancelRemarks;
}
