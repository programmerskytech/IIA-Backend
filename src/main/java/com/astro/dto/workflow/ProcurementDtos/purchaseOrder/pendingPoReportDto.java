package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class pendingPoReportDto {

    private String poId;
    private String tenderId;
    private String indentIds;
    private Double value;
    private String vendorName;
    private String submittedDate;
    private String pendingWith;
    private String pendingFrom;
    private String status;
    private LocalDate asOnDate;
    private List<PurchaseOrderAttributesResponseDTO> purchaseOrderAttributes;
}
