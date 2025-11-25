package com.astro.dto.workflow.InventoryModule.ogp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OgpMaterialDtlDto {
    private Integer assetId;
    private Integer locatorId;
    private BigDecimal quantity;
    private String assetDesc;
    private String uomId;
}
