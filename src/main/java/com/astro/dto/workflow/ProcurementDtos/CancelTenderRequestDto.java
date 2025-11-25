package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class CancelTenderRequestDto {

    private String tenderId;
    private int actionBy;
    private Boolean cancelStatus;
    private String cancelRemarks;
}
