package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class PaymentVoucherPoSearchDto {

    private String poId;
    private String vendorId;
    private String vendorName;
    private LocalDateTime createdDate;
    private String materialDescription;

    public PaymentVoucherPoSearchDto(
            String poId,
            String vendorId,
            String vendorName,
            LocalDateTime createdDate,
            String materialDescription) {
        this.poId = poId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.createdDate = createdDate;
        this.materialDescription = materialDescription;
    }
}
