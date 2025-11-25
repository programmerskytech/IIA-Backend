package com.astro.dto.workflow.InventoryModule.ogp;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OgpDetailReportDto {
    private Integer detailId;
    private Integer assetId;
    private String assetDesc;
    private String materialDesc;
    private Integer locatorId;
    private String locatorDesc;
    private BigDecimal quantity;
    private String uomId;
}