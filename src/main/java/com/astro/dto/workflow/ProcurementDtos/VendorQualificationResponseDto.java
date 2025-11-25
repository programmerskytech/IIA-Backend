package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.util.List;

@Data
public class VendorQualificationResponseDto {
    private String vendorId;
    private Boolean qualified;
    private String remarks;
    private Boolean changeRequest;

   // private Integer actionTakenBy;
    private String actionTakenBy;
    private String actionStatus;
    private List<String> vendorIds;
    private String POVendorId;
    private String actionStatusAfterPoGenerated;
    private String approvedVendorPoData;


}
