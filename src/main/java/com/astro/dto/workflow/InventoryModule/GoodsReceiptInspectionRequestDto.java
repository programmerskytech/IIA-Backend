package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

@Data
public class GoodsReceiptInspectionRequestDto {

    private String gprId;
    private String installationDate;
    private String commissioningDate;
    private String assetCode;
    private String additionalMaterialDescription;
    private String locator;
    private boolean printLabelOption;
    private double depreciationRate;
    private double bookValue; //calculated automatically
    private String attachComponentPopup;
    private String updatedBy;
    private String createdBy;


}
