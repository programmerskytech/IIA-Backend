package com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto;

import com.astro.dto.workflow.ProcurementDtos.purchaseOrder.PurchaseOrderAttributesResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PendingSoReportDto {

    private String soId;
    private String tenderId;
    private String indentIds;
    private BigDecimal value;
    private String vendorName;
    private String submittedDate;
    private String pendingWith;
    private String pendingFrom;
    private String status;
    private LocalDate asOnDate;
    private List<ServiceOrderMaterialResponseDTO> materials;
}
