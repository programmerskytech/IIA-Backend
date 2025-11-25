package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class ApprovedTenderIdDtos {
    private String tenderId;
    private String title;

    public ApprovedTenderIdDtos(String tenderId, String title) {
        this.tenderId = tenderId;
        this.title = title;
    }
}
