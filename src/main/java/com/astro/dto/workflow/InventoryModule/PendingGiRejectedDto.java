package com.astro.dto.workflow.InventoryModule;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PendingGiRejectedDto {

    private Integer subProcessId;
    private String giNo;
    private String poId;
    private String vendorId;
    private List<String> materialDescriptions;
}
