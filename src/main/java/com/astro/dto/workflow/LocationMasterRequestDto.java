package com.astro.dto.workflow;

import lombok.Data;

@Data
public class LocationMasterRequestDto {

    private String locationCode;
    private String locationName;
    private String address;
    private String updatedBy;
    private String createdBy;
}
