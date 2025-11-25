package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class AllVendorStatus {

    private String vendorId;
    private String vendorName;
    private String tenderId;
    private String status;
    private String po;

}
