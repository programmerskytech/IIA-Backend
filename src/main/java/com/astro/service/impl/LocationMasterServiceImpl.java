package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.LocationMasterRequestDto;
import com.astro.dto.workflow.LocationMasterResponseDto;
import com.astro.entity.LocationMaster;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.LocationMasterRepository;
import com.astro.service.LocationMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationMasterServiceImpl implements LocationMasterService {

    @Autowired
    private LocationMasterRepository locationMasterRepository;
    @Override
    public LocationMasterResponseDto createLocationMaster(LocationMasterRequestDto locationMasterRequestDto) {

        // Check if the indentorId already exists
        if (locationMasterRepository.existsById(locationMasterRequestDto.getLocationCode())) {
            ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate location code", "location code " + locationMasterRequestDto.getLocationCode() + " already exists.");
            throw new InvalidInputException(errorDetails);
        }
        LocationMaster locationMaster = new LocationMaster();
        locationMaster.setLocationCode(locationMasterRequestDto.getLocationCode());
        locationMaster.setLocationName(locationMasterRequestDto.getLocationName());
        locationMaster.setAddress(locationMasterRequestDto.getAddress());
        locationMaster.setCreatedBy(locationMasterRequestDto.getCreatedBy());
        locationMaster.setUpdatedBy(locationMasterRequestDto.getUpdatedBy());

        locationMasterRepository.save(locationMaster);
        return  mapToResponseDTO(locationMaster);
    }


    @Override
    public LocationMasterResponseDto updateLocationMaster(String locationCode, LocationMasterRequestDto locationMasterRequestDto) {

        LocationMaster locationMaster= locationMasterRepository.findById(locationCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "location Code not found for the provided location code.")
                ));


       // locationMaster.setLocationCode(locationMasterRequestDto.getLocationCode());
        locationMaster.setLocationName(locationMasterRequestDto.getLocationName());
        locationMaster.setAddress(locationMasterRequestDto.getAddress());
        locationMaster.setCreatedBy(locationMasterRequestDto.getCreatedBy());
        locationMaster.setUpdatedBy(locationMasterRequestDto.getUpdatedBy());
        locationMasterRepository.save(locationMaster);
        return mapToResponseDTO(locationMaster);


    }

    @Override
    public List<LocationMasterResponseDto> getAllLocationMasters() {
        List<LocationMaster> locationMasters= locationMasterRepository.findAll();
        return locationMasters.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public LocationMasterResponseDto getLocationMasterById(String locationCode) {
        LocationMaster locationMaster= locationMasterRepository.findById(locationCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Location master not found for the provided location code.")
                ));
        return mapToResponseDTO(locationMaster);
    }

    @Override
    public void deleteLocationMaster(String locationCode) {

        LocationMaster locationMaster=locationMasterRepository.findById(locationCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Location Master not found for the provided location code."
                        )
                ));
        try {
            locationMasterRepository.delete(locationMaster);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_ERROR,
                            "An error occurred while deleting the Location master."
                    ),
                    ex
            );
        }

    }

    private LocationMasterResponseDto mapToResponseDTO(LocationMaster locationMaster) {

        LocationMasterResponseDto responseDto = new LocationMasterResponseDto();
        responseDto.setLocationCode(locationMaster.getLocationCode());
        responseDto.setLocationName(locationMaster.getLocationName());
        responseDto.setAddress(locationMaster.getAddress());
        responseDto.setUpdatedBy(locationMaster.getUpdatedBy());
        responseDto.setCreatedBy(locationMaster.getCreatedBy());
        responseDto.setCreatedDate(locationMaster.getCreatedDate());
        responseDto.setUpdatedDate(locationMaster.getUpdatedDate());


        return responseDto;

    }

}
