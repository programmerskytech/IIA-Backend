package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.GoodsInspectionRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsInspectionResponseDto;
import com.astro.dto.workflow.InventoryModule.GprnDto.GetGprnDtlsDto;
import com.astro.service.GoodsInspectionService;
import com.astro.service.ProcessService;
import com.astro.util.ResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/goods-inspections")
public class GoodsInspectionController {

    @Autowired
    private GoodsInspectionService goodsInspectionService;

    @Autowired
    private ProcessService gprnService;
    @Autowired
    private ObjectMapper mapper;


    // Create a new Goods Inspection
    @PostMapping
    public ResponseEntity<Object> createGoodsInspection(@RequestPart("goodsInspectionDto") String goodsInspectionDto,
                                                        @RequestPart(value ="uploadInstallationReport") MultipartFile uploadInstallationReport ) throws JsonProcessingException {
        GoodsInspectionRequestDto goodsInspectionDTO = mapper.readValue(goodsInspectionDto,GoodsInspectionRequestDto.class);
        goodsInspectionDTO.setUploadInstallationReport(uploadInstallationReport);
        String uploadInstallationReportFileName =uploadInstallationReport.getOriginalFilename();

        GoodsInspectionResponseDto createdInspection = goodsInspectionService.createGoodsInspection(goodsInspectionDTO,uploadInstallationReportFileName);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(createdInspection), HttpStatus.OK);
    }
    // Get all Goods Inspections
    @GetMapping
    public ResponseEntity<Object> getAllGoodsInspections() {
        List<GoodsInspectionResponseDto> inspections = goodsInspectionService.getAllGoodsInspections();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(inspections), HttpStatus.OK);
    }



    @GetMapping("/{goodsInspectionNo}")
    public ResponseEntity<Object> getGoodsInspectionById(@PathVariable String goodsInspectionNo) {
        GoodsInspectionResponseDto inspection = goodsInspectionService.getGoodsInspectionById(goodsInspectionNo);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(inspection), HttpStatus.OK);
    }


    // Update an existing Goods Inspection by ID
    @PutMapping("/{goodsInspectionNo}")
    public ResponseEntity<Object> updateGoodsInspection(@PathVariable String goodsInspectionNo,
                                                        @RequestPart("goodsInspectionDto") String goodsInspectionDto,
                                                        @RequestPart(value ="uploadInstallationReport") MultipartFile uploadInstallationReport) throws JsonProcessingException {
        GoodsInspectionRequestDto goodsInspectionDTO = mapper.readValue(goodsInspectionDto,GoodsInspectionRequestDto.class);
        goodsInspectionDTO.setUploadInstallationReport(uploadInstallationReport);
        String uploadInstallationReportFileName =uploadInstallationReport.getOriginalFilename();
        GoodsInspectionResponseDto updatedInspection = goodsInspectionService.updateGoodsInspection(goodsInspectionNo, goodsInspectionDTO,uploadInstallationReportFileName);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(updatedInspection), HttpStatus.OK);
    }

    // Delete a Goods Inspection by ID
    @DeleteMapping("/{goodsInspectionNo}")
    public ResponseEntity<String> deleteGoodsInspection(@PathVariable String goodsInspectionNo) {
        goodsInspectionService.deleteGoodsInspection(goodsInspectionNo);
        return ResponseEntity.ok("GoodsInspection deleted successfully."+" " +goodsInspectionNo);
    }
    //fetch all details from the gprn
 /*   @GetMapping("/gprn/{gprnId}")
    public ResponseEntity<Object> fetchGprDetails(@PathVariable String gprnId) {
        getGprnDtlsDto gprnDetails = gprnService.getGprnById(gprnId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(gprnDetails), HttpStatus.OK);
    }

  */

}
