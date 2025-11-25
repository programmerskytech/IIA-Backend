package com.astro.dto.workflow.InventoryModule;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PoGrnInfoDto {
    private String poId;
    private String vendorName;
    private String projectName;
    private LocalDateTime createdDate;
    private List<String> materialDescriptions;
}
