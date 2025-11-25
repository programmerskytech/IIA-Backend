package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CpMaterialResponseDto {
    private String materialCode;
    private String materialDescription;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String uom;
    private BigDecimal totalPrice;
    private String budgetCode;
    private String materialCategory;
    private String materialSubCategory;
    private String currency;
    private BigDecimal gst;
    private String countryOfOrigin;

}
