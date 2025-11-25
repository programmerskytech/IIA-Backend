package com.astro.dto.workflow;

import lombok.Data;

@Data
public class CompletedVendorsDto {
    private String vendorId;
    private String vendorName;

    public CompletedVendorsDto(String vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }
}
