package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GtDtlDto {
    private Long id;
    private Integer assetId;
    private String assetDesc;
    private String materialCode;
    private String materialDesc;
    private BigDecimal unitPrice;
    private BigDecimal depriciationRate;
    private BigDecimal bookValue;
    private BigDecimal quantity;
    private String receiverLocatorId;
    private String senderLocatorId;
}
