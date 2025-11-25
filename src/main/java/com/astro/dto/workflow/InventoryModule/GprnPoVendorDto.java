package com.astro.dto.workflow.InventoryModule;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GprnPoVendorDto {
    private String poId;
    private String vendorId;
}
