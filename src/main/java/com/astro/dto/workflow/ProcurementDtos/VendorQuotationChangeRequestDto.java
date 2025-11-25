package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class VendorQuotationChangeRequestDto {

    private String tenderId;
    private String vendorId;
    private String remarks;
    private Integer userId;


}
