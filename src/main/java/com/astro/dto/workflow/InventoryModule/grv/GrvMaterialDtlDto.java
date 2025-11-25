package com.astro.dto.workflow.InventoryModule.grv;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GrvMaterialDtlDto {
    private BigDecimal rejectedQuantity;
    private BigDecimal returnQuantity;
    private String returnType;
    private String uomId;
    private String rejectReason;
    private String materialCode;
    private String materialDesc;
    
}
