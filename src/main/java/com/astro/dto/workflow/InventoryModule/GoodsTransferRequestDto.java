package com.astro.dto.workflow.InventoryModule;

import lombok.Data;



@Data
public class GoodsTransferRequestDto {

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
}
