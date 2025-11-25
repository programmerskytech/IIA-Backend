package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class SearchTenderIdDto {
    private String tenderId;
    public SearchTenderIdDto(String tenderId) {
        this.tenderId = tenderId;
    }
}
