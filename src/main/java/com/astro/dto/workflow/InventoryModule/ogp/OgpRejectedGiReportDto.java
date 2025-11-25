package com.astro.dto.workflow.InventoryModule.ogp;

import lombok.Data;

import java.util.List;

@Data
public class OgpRejectedGiReportDto {
    private Integer ogpSubProcessId;
    private String ogpType;
    private String status;
    private String giId;
    private String locationId;
    private String createdBy;
    private String senderName;
    private String receiverName;
    private String receiverLocation;
    private String ogpDate;
    private String returnDate;
    private List<OgpDetailRejectedGiReportDto> rejectedDetails;
}
