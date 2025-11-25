package com.astro.controller;

import com.astro.dto.workflow.MaterialCreationRequestDto;
import com.astro.dto.workflow.MaterialCreationResponseDto;

import com.astro.service.MaterialCreationService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-creation")
public class MaterialCreationController {
    @Autowired
    private MaterialCreationService materialCreationService;

    @PostMapping
    public ResponseEntity<Object> createMaterial(@RequestBody MaterialCreationRequestDto requestDTO) {
        MaterialCreationResponseDto material = materialCreationService.createMaterial(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(material), HttpStatus.OK);
    }

    @PutMapping("/{materialCode}")
    public ResponseEntity<Object> updateMaterial(@PathVariable String materialCode,
                                                  @RequestBody MaterialCreationRequestDto requestDTO) {
        MaterialCreationResponseDto response = materialCreationService.updateMaterial(materialCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllMaterials() {
        List<MaterialCreationResponseDto> response = materialCreationService.getAllMaterials();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{materialCode}")
    public ResponseEntity<Object> getMaterialById(@PathVariable String materialCode) {
       MaterialCreationResponseDto responseDTO = materialCreationService.getMaterialById(materialCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{materialCode}")
    public ResponseEntity<String> deleteMaterial(@PathVariable String materialCode) {
        materialCreationService.deleteMaterial(materialCode);
        return ResponseEntity.ok("Work Order deleted successfully. materialCode:"+" " +materialCode);
    }


}
