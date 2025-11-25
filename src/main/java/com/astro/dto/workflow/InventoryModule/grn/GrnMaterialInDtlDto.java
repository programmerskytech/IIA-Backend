package com.astro.dto.workflow.InventoryModule.grn;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnMaterialInDtlDto {
    private String materialCode;
    private String category;
    private BigDecimal bookValue;
    private String subCategory;
    private Integer assetId;
    private String assetDesc;
    private Integer locatorId;
    private BigDecimal quantity;
    private String estimatedPriceWithCcy;
    private Boolean indigenousOrImported;
    private String materialDesc;
    private String uomId;
    private BigDecimal depriciationRate;
    private BigDecimal unitPrice;
}
