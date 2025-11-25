package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class GoodsReturnResponseDto {

    private String goodsReturnId;


    private String goodsReturnNoteNo;


    private Integer rejectedQuantity;


    private Integer returnQuantity;


    private String typeOfReturn;


    private String reasonOfReturn;

    private String updatedBy;
    private String createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
