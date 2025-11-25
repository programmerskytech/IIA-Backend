package com.astro.controller;

import com.astro.dto.workflow.JobMasterRequestDto;
import com.astro.dto.workflow.JobMasterResponseDto;
import com.astro.dto.workflow.UomMasterRequestDto;
import com.astro.dto.workflow.UomMasterResponseDto;
import com.astro.service.UomMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uom-master")
public class UomMasterController {

    @Autowired
    private UomMasterService uomMasterService;

    @PostMapping
    public ResponseEntity<Object> createUOMMaster(@RequestBody UomMasterRequestDto requestDTO) {
        UomMasterResponseDto material = uomMasterService.createUomMaster(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(material), HttpStatus.OK);
    }

    @PutMapping("/{uomCode}")
    public ResponseEntity<Object> updateUOMMaster(@PathVariable String uomCode,
                                                  @RequestBody UomMasterRequestDto  requestDTO) {
       UomMasterResponseDto response =uomMasterService.updateUomMaster(uomCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllUOMMaster() {
        List<UomMasterResponseDto> response = uomMasterService.getAllUomMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{uomCode}")
    public ResponseEntity<Object> getUOMMasterById(@PathVariable String uomCode) {
        UomMasterResponseDto responseDTO = uomMasterService.getUomMasterById(uomCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{uomCode}")
    public ResponseEntity<String> deleteUOmMaster(@PathVariable String uomCode) {
        uomMasterService.deleteUomMaster(uomCode);
        return ResponseEntity.ok("job master deleted successfully. jobCode:"+" " +uomCode);
    }

}
