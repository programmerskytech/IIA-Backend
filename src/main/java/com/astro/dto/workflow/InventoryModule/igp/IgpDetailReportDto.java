package com.astro.dto.workflow.InventoryModule.igp;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IgpDetailReportDto {
    private Integer detailId;
    private Integer igpSubProcessId;
    private String materialCode;
    private String materialDesc;
    private Integer assetId;
    private Integer locatorId;
    private String uomId;
    private BigDecimal quantity;
    private String type; // "MATERIAL" or "ASSET"
}