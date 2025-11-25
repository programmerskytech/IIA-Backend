package com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceOrderMaterialRequestDTO {

    private String materialCode;
    private String materialDescription;
    private BigDecimal quantity;
    private BigDecimal rate;
    private BigDecimal exchangeRate;
    private String currency;
    private BigDecimal gst;
    private BigDecimal duties;
    private String budgetCode;


}
