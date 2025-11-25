package com.astro.dto.workflow.InventoryModule.isn;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class IsnMaterialDtlDto {
    private Integer assetId;
    private Integer locatorId;
    private BigDecimal quantity;
    private String uomId;
    private String assetDesc;  // Added field
}