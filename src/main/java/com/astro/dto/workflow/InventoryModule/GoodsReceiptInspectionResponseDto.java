package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GoodsReceiptInspectionResponseDto {


    private String griId;

    private String installationDate;

    private String commissioningDate;

    private String assetCode;

    private String additionalMaterialDescription;

    private String locator;
    private double bookValue;

    private boolean printLabelOption;

    private double depreciationRate;



    private String attachComponentPopup;

    private String updatedBy;

    private String createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
