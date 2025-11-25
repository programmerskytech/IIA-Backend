package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

@Data
public class MonthlyProcurementReportDto {

    private String month;
    private String poNumber;
    private String date;
    private String indentIds;
    private Double value;
    private String vendorName;
    private String modeOfProcurement;

}
