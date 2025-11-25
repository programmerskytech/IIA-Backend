package com.astro.dto.workflow.InventoryModule.igp;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class IgpMaterialDtlDto {
    private Integer assetId;
    private String materialCode;
    private String materialDescription;
    private String assetDesc;
    private Integer locatorId;
    private BigDecimal quantity;
    private String igpProcessId;
    private Integer igpSubProcessId;
    private Integer issueNoteId;
    private String uomId;
}