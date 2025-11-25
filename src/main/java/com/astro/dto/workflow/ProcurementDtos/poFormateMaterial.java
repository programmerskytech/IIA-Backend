package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class poFormateMaterial {

    private String materialDescription;
    private BigDecimal quantity;
    private String uom;
    private BigDecimal unitPrice;
    private BigDecimal gstRate;
    private BigDecimal totalMaterialPrice;
    private String currency;
}
