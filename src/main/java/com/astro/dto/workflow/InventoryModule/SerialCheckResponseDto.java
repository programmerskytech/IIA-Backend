package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.util.List;

@Data
public class SerialCheckResponseDto {
    private Integer assetId;
    private String assetCode;
    private String custodianId;
    private Integer locatorId;
    private int existingCount;
    private int remainingToEnter;
    private List<String> existingSerials;
}
