package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

@Data
public class SearchPOIdDto {

    private String poId;
    public SearchPOIdDto(String poId) {
        this.poId = poId;
    }
}
