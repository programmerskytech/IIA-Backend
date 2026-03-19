package com.astro.controller;


import com.astro.dto.workflow.MaterialMasterRequestDto;
import com.astro.dto.workflow.MaterialMasterResponseDto;

import com.astro.dto.workflow.MaterialSearchResponseDto;
import com.astro.service.MaterialMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/material-master")
public class MaterialMasterController {

    @Autowired
    private MaterialMasterService materialMasterService;

  //  @Autowired
  //  private ObjectMapper mapper;
    @PostMapping
    public ResponseEntity<Object> createMaterialMaster(
       @RequestBody MaterialMasterRequestDto materialMasterRequestDto) {

    MaterialMasterResponseDto created = materialMasterService.createMaterialMaster(materialMasterRequestDto);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);
}

    @GetMapping("/materialSearch")
    public ResponseEntity<Object> searchMaterials(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "materialCategoryType", required = false) String materialCategoryType) {
        List<MaterialSearchResponseDto> results;
        if (materialCategoryType != null && !materialCategoryType.trim().isEmpty() && !"all".equalsIgnoreCase(materialCategoryType)) {
            results = materialMasterService.searchMaterialsByCategory(keyword, materialCategoryType);
        } else {
            results = materialMasterService.searchMaterials(keyword);
        }
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }

    @GetMapping("/materialSearchByCategory")
    public ResponseEntity<Object> searchMaterialsByCategory(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "materialCategoryType", required = false, defaultValue = "all") String materialCategoryType) {
        List<MaterialSearchResponseDto> results = materialMasterService.searchMaterialsByCategory(keyword, materialCategoryType);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(results), HttpStatus.OK);
    }

    @PutMapping("/{materialCode}")
    public ResponseEntity<Object> updateMaterialMaster(
            @PathVariable String materialCode,@RequestBody MaterialMasterRequestDto materialMasterRequestDto){
        MaterialMasterResponseDto updated = materialMasterService.updateMaterialMaster(
                materialCode, materialMasterRequestDto);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllMaterialMaster() {
        List<MaterialMasterResponseDto> response = materialMasterService.getAllMaterialMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{materialCode}")
    public ResponseEntity<Object> getMaterialMasterById(@PathVariable String materialCode) {
        MaterialMasterResponseDto responseDTO = materialMasterService.getMaterialMasterById(materialCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("/base64/{materialCode}")
    public ResponseEntity<Object> getMaterialMasterBybaseId(@PathVariable String materialCode) throws IOException {
        MaterialMasterResponseDto responseDTO = materialMasterService.getMaterialMasterByIdBase64(materialCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{materialCode}")
    public ResponseEntity<String> deleteMaterialMaster(@PathVariable String materialCode) {
       materialMasterService.deleteMaterialMaster(materialCode);
        return ResponseEntity.ok("material master deleted successfully. materialCode:"+" " +materialCode);
    }

}
