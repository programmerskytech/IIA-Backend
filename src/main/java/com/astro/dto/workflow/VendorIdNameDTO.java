package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorIdNameDTO {
    private String vendorId;
    private String vendorName;
    private String primaryBusiness;
    private String purchaseHistory;                   
    private String statusOfVendorActiveOrDebar; 
}
