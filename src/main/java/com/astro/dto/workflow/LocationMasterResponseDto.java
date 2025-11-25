package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationMasterResponseDto {

    private String locationCode;
    private String locationName;
    private String address;
    private String updatedBy;
    private String createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
