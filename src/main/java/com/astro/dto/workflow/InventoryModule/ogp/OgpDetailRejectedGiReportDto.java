package com.astro.dto.workflow.InventoryModule.ogp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OgpDetailRejectedGiReportDto {
    private Integer detailId;
    private String materialCode;
    private String materialDesc;
    private Integer assetId;
    private String assetDesc;
    private String rejectionType;
    private BigDecimal rejectedQuantity;
}
