package com.astro.dto.workflow.InventoryModule;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class GoodsTransferResponseDto {

    private String goodsTransferID;
    private String consignorDetails;
    private String consigneeDetails;
    private String fieldStationName;
    private String materialCode;
    private String uom;
    private Integer quantity;
    private String locator;
    private String note;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
