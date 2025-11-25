package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AssetDisposalReportDto {

    private String auctionId;
    private String auctionCode;
    private String auctionDate;
    private BigDecimal reservePrice;
    private BigDecimal auctionPrice;
    private String vendorName;
    private List<AutionAssetDisposalReportDto> disposals;
}
