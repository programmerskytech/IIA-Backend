package com.astro.dto.workflow.InventoryModule.gprn;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GprnPendingInspectionDto {
    private String processId;
    private Integer subProcessId;
    private String poId;
    private String locationId;
    private String date;
    private String challanNo;
    private String deliveryDate;
    private String vendorId;
    private String fieldStation;
    private String indentorName;
    private String supplyExpectedDate;
    private String consigneeDetail;
    private BigDecimal warrantyYears;
    private String project;
    private String receivedBy;
    private String status;
    private List<GprnPendingInspectionDetailDto> materialDetails;
}