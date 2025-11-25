package com.astro.dto.workflow.InventoryModule.isn;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class IsnReportDetailDto {
    private Integer detailId;
    private Integer assetId;
    private Integer locatorId;
    private BigDecimal quantity;
    private String materialDesc;
    private String assetDesc;
    private String uomId;
}