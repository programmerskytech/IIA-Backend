package com.astro.dto.workflow.InventoryModule.ogp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OgpRejectedGiDtlDto {
    private String materialCode;
    private String materialDesc;
    private Integer assetId;
    private String assetCode;
    private String assetDesc;
    private BigDecimal rejectedQuantity;
    private String rejectionType;
}
