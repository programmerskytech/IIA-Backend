package com.astro.dto.workflow.ProcurementDtos.WorkOrderDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkOrderMaterialRequestDTO {
    private String workCode;
    private String workDescription;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal exchangeRate;
    private String currency;
    private BigDecimal gst;
    private BigDecimal duties;
    private String budgetCode;
}
