package com.astro.controller;

import com.astro.dto.workflow.JobMasterRequestDto;
import com.astro.dto.workflow.JobMasterResponseDto;
import com.astro.dto.workflow.LocationMasterRequestDto;
import com.astro.dto.workflow.LocationMasterResponseDto;
import com.astro.service.LocationMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-master")
public class LocationMasterController {

    @Autowired
    private LocationMasterService locationMasterService;


    @PostMapping
    public ResponseEntity<Object> createLocationMaster(@RequestBody LocationMasterRequestDto requestDTO) {
        LocationMasterResponseDto material = locationMasterService.createLocationMaster(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(material), HttpStatus.OK);
    }

    @PutMapping("/{locationCode}")
    public ResponseEntity<Object> updateLocationMaster(@PathVariable String locationCode,
                                                  @RequestBody LocationMasterRequestDto requestDTO) {
        LocationMasterResponseDto response =locationMasterService.updateLocationMaster(locationCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllLocationMaster() {
        List<LocationMasterResponseDto> response = locationMasterService.getAllLocationMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{locationCode}")
    public ResponseEntity<Object> getLocationMasterById(@PathVariable String locationCode) {
        LocationMasterResponseDto responseDTO = locationMasterService.getLocationMasterById(locationCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{locationCode}")
    public ResponseEntity<String> deleteLocationMaster(@PathVariable String locationCode) {
        locationMasterService.deleteLocationMaster(locationCode);
        return ResponseEntity.ok("location master deleted successfully. locationCode:"+" " +locationCode);
    }




}
