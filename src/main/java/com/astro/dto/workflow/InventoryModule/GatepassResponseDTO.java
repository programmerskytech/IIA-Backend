package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GatepassResponseDTO {
    private String gatePassId;
    private String gatePassType;
    private String materialDetails;
    private String expectedDateOfReturn;
    private BigDecimal extendEDR;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
