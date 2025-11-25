package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

@Data
public class GoodsReturnRequestDto {

    private String goodsReturnId;
    private String goodsReturnNoteNo;
    private Integer rejectedQuantity;
    private Integer returnQuantity;
    private String typeOfReturn;
    private String reasonOfReturn;
    private String updatedBy;
    private String createdBy;
}
