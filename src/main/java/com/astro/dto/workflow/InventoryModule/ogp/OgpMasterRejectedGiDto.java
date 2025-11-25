package com.astro.dto.workflow.InventoryModule.ogp;

import java.util.List;

import lombok.Data;

@Data
public class OgpMasterRejectedGiDto {
        private String ogpDate;
    private String giId;
    private String locationId;
    private String createdBy;
    private String senderName;
    private String receiverName;
    private String receiverLocation;
    private String dateOfReturn;
    private String ogpId;
    private String ogpType;

    private List<OgpRejectedGiDtlDto> materialDtlList;
}
