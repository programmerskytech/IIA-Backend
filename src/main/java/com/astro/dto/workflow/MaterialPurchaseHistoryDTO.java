package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialPurchaseHistoryDTO {

    private String poId;

    private String indentId;

    private String vendorName;

    private String vendorId;

    private BigDecimal quantity;

    private BigDecimal rate;

    private String currency;

    private BigDecimal exchangeRate;

    private LocalDateTime createdDate;

    private String materialCode;

    private String materialDescription;

    private BigDecimal gst;

    private BigDecimal totalPoMaterialPriceInInr;
}
