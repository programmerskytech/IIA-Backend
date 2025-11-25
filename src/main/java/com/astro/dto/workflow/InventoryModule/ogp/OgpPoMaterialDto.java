package com.astro.dto.workflow.InventoryModule.ogp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OgpPoMaterialDto {
    private String materialCode;
    private String materialDescription;
    private String uomId;
    private BigDecimal quantity;
}