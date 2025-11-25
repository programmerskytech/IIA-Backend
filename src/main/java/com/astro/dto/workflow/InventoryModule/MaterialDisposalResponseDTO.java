package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaterialDisposalResponseDTO {

    private String materialDisposalCode;
    private String disposalCategory;
    private String disposalMode;
    private String vendorDetails;
    private String disposalDate;
    private BigDecimal currentBookValue;
    private BigDecimal editReserveValue;
    private BigDecimal finalBidValue;
    private String saleNoteFileName;
    private BigDecimal editQuantity;
    private BigDecimal editValueMaterials;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
