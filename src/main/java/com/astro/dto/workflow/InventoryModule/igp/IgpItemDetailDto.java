package com.astro.dto.workflow.InventoryModule.igp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgpItemDetailDto {
    private Integer detailId;
    private String materialCode;
    private String materialDesc;
    private Integer assetId;
    private String assetDesc;
    private Integer locatorId;
    private String uomId;
    private BigDecimal quantity;
    private String type;
}