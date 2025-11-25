package com.astro.entity.InventoryModule;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class IsnAssetOhqDtlsDto {
    private Integer ohqId;
    private Integer assetId;
    private String assetDesc;
    private String uomId;
    private BigDecimal unitPrice;
    private String poId;
    private BigDecimal depriciationRate;

    private String makeNo;
    private String serialNo;
    private String modelNo;

    private List<IsnOhqDtlsDto> qtyList;
}
