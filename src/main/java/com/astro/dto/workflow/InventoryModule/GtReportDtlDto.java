package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GtReportDtlDto {
    private Integer assetId;
    private String assetDesc;
    private String materialCode;
    private String materialDesc;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal depriciationRate;
    private BigDecimal bookValue;
    private Integer receiverLocatorId;
    private Integer senderLocatorId;
}
