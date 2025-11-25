package com.astro.dto.workflow;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaterialCreationResponseDto {

    private String materialCode;
    private String materialName;
    private String materialDescription;
    private String modeOfProcurement;
    private String materialCategory;
    private String materialSubCategory;
    private String uom;
    private BigDecimal endOfLife;
    private BigDecimal bookValue;
    private BigDecimal depricationRate;
    private String IndegeniousOrImported;
    private BigDecimal minLevel;
    private BigDecimal maxLevel;
    private BigDecimal reOrderLevel;
    private String conditionOfMaterial;
    private String locator;
    private String shelf;
    private String rank;
    private String zone;
    private String building;

    private byte[] uploadImage;
    private BigDecimal shelfLife;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
