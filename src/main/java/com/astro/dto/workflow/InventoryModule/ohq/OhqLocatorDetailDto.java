package com.astro.dto.workflow.InventoryModule.ohq;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OhqLocatorDetailDto {
    private Integer locatorId;
    private String locatorDesc;
    private BigDecimal quantity;
    private List<String> serialNos;
}