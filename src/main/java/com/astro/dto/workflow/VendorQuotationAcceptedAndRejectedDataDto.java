package com.astro.dto.workflow;

import lombok.Data;

import java.util.List;

@Data
public class VendorQuotationAcceptedAndRejectedDataDto {
    private List<VendorQuotationAgainstTenderDto> vendor;
    private String uploadQualifiedVendorsFileName;
}
