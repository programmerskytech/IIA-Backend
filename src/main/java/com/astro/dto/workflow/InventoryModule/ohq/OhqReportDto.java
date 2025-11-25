package com.astro.dto.workflow.InventoryModule.ohq;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OhqReportDto {
    private String materialCode;
    private Integer assetId;
    private String assetDesc;
    private String materialDesc;
    private String uomId;
    private BigDecimal totalQuantity;
    private BigDecimal bookValue;
    private BigDecimal depriciationRate;
    private BigDecimal unitPrice;
    private List<OhqLocatorDetailDto> locatorDetails;
    private String custodianId;
    private String assetCode;
}