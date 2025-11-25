package com.astro.dto.workflow.InventoryModule;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class MaterialDisposalRequestDTO {

    private String materialDisposalCode;
    private String disposalCategory;
    private String disposalMode;
    private String vendorDetails;
    private String disposalDate;
    private BigDecimal currentBookValue;
    private BigDecimal editReserveValue;
    private BigDecimal finalBidValue;
    private MultipartFile saleNote;
    private BigDecimal editQuantity;
    private BigDecimal editValueMaterials;
    private String createdBy;
    private String updatedBy;


}
