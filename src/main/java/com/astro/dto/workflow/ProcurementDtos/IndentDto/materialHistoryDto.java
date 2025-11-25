package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class materialHistoryDto {

    private String indentId;
    private Integer userId;
    private String poId;
    private String createdDate;
    private String vendorName;

}
