package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class paymentVoucherMaterials {

    private String materialCode;
    private String materialDescription;
    private BigDecimal quantity;
    private String uom;
    private BigDecimal unitPrice;
    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal gst;
    private BigDecimal amount;

}
