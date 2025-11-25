package com.astro.dto.workflow;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class VendorQuotationUpdateRequestDto {

    private String tenderId;
    private String vendorId;
    private String status;
    private String remarks;
    private Integer userId;


}
