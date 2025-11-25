package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

import java.util.List;

@Data
public class ShortClosedCancelledOrderReportDto {

    private String poId;
    private String tenderId;
    private String indentIds;
    private Double value;
    private String vendorName;
    private String submittedDate;
    private String reason;
    private List<PoMaterialReport> materials;
}
