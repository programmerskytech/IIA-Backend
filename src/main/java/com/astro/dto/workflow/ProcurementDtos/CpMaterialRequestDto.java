package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CpMaterialRequestDto {

    private String materialCode;
    private String materialDescription;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String uom;
    private String budgetCode;
    private BigDecimal gst;
    private String materialCategory;
    private String materialSubCategory;
    private String currency;
    private String countryOfOrigin;
    private BigDecimal totalPrice;


}
