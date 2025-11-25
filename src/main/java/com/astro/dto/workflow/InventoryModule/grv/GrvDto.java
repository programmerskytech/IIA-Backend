package com.astro.dto.workflow.InventoryModule.grv;

import java.util.List;

import lombok.Data;

@Data
public class GrvDto {
    private String grvNo;
    private String giNo;
    private String date;
    private String createdBy;
    private String locationId;

    private List<GrvMaterialDtlDto> materialDtlList;

}
