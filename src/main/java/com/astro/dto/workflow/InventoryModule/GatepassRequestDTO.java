package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GatepassRequestDTO {
    private String gatePassId;
    private String gatePassType;
    private String materialDetails;
    private String expectedDateOfReturn;
    private BigDecimal extendEDR;
    private String createdBy;
    private String updatedBy;


}
