package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

import java.util.List;

@Data
public class ApprovedPoListReportDto {
    private String approvedDate;
    private String poId;
    private String vendorName;
    private Double value;
    private String tenderId;
    private String project;
    private String vendorId;
    private String indentIds;
    private String modeOfProcurement;
    private List<PurchaseOrderAttributesResponseDTO> purchaseOrderAttributes;


}
