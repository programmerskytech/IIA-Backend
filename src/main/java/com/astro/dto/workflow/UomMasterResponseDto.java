package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UomMasterResponseDto {

    private String uomCode;
    private String uomName;
    private String updatedBy;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
