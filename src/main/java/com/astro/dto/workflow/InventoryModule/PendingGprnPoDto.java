package com.astro.dto.workflow.InventoryModule;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PendingGprnPoDto {
    private String poId;
    private String vendorName;
    private String projectName;
    private LocalDateTime createdDate;
    private List<String> indentIds;
    private List<MaterialDto> materials;
}
