package com.astro.dto.workflow.InventoryModule;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DemandMaterialsDto {

    private Long id;
    private Long diId;

    private Integer assetId;

    private String assetDesc;

    private String materialCode;

    private String materialDesc;

    private BigDecimal quantity;

    private Integer receiverLocatorId;

    private Integer senderLocatorId;

    private BigDecimal unitPrice;

    private BigDecimal depriciationRate;

    private BigDecimal bookValue;
    private String uom;
}
