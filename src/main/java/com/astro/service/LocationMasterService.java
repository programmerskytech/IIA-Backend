package com.astro.service;

import com.astro.dto.workflow.LocationMasterRequestDto;
import com.astro.dto.workflow.LocationMasterResponseDto;


import java.util.List;

public interface LocationMasterService {

    public LocationMasterResponseDto createLocationMaster(LocationMasterRequestDto locationMasterRequestDto);
    public LocationMasterResponseDto updateLocationMaster(String locationCode, LocationMasterRequestDto locationMasterRequestDto);
    public List<LocationMasterResponseDto> getAllLocationMasters();

    public LocationMasterResponseDto getLocationMasterById(String locationCode);
    public void deleteLocationMaster(String locationCode);
}
