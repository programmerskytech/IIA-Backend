package com.astro.dto.workflow.InventoryModule.ogp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OgpPoDtlDto {
    private String materialCode;
    private String materialDescription;
    private String uom;
    private BigDecimal quantity;
}
