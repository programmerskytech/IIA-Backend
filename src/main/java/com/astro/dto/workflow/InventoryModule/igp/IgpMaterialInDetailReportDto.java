package com.astro.dto.workflow.InventoryModule.igp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IgpMaterialInDetailReportDto {
    private Long id;
    private Integer assetId;
    private String materialCode;
    private String category;
    private String subCategory;
    private String description;
    private String uom;
    private BigDecimal quantity;
    private Double estimatedPriceWithCcy;
    private Boolean indigenousOrImported;
}
