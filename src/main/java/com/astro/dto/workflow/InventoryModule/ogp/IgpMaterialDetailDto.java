package com.astro.dto.workflow.InventoryModule.ogp;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgpMaterialDetailDto {
    private Long id;
    private Integer assetId;
    private String materialCode;
    private String category;
    private String subCategory;
    private String description;
    private String uom;
    private BigDecimal quantity;
   // private Double estimatedPriceWithCcy; // or BigDecimal if preferred
    private BigDecimal unitPrice;
    private Boolean indigenousOrImported;
}