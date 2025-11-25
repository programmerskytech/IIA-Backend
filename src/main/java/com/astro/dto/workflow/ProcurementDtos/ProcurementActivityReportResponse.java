package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProcurementActivityReportResponse {

    private String orderId;
    private String gemOrNonGem;
    private String indentor; // Always null
    private BigDecimal value;
    private String descriptionOfGoods; // Always null
    private String vendorName;


    public ProcurementActivityReportResponse(String orderId, String gemOrNonGem, String s, BigDecimal value, String s1, String s2) {
        this.orderId = orderId;
        this.gemOrNonGem = gemOrNonGem;
        this.indentor = s;
        this.value = value;
        this.descriptionOfGoods = s1;
        this.vendorName = vendorName;

    }
}
