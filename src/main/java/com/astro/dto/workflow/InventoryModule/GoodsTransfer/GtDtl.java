package com.astro.dto.workflow.InventoryModule.GoodsTransfer;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class GtDtl {
    private Integer assetId;
    private String assetDesc;
    private String assetCode;
    private String materialCode;
    private String materialDesc;
    private BigDecimal quantity;
    private Integer receiverLocatorId;
    private Integer senderLocatorId;
    private BigDecimal unitPrice;
    private BigDecimal depriciationRate;
    private BigDecimal bookValue;
    private String uom;
    private String poId;
    private String modelNo;
    private String serialNo;
    private String reasonForTransfer;
}
