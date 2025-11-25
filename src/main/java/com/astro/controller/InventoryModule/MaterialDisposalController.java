package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.MaterialDisposalRequestDTO;
import com.astro.dto.workflow.InventoryModule.MaterialDisposalResponseDTO;
import com.astro.service.MaterialDisposalService;
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
@RequestMapping("/api/material-disposal")
public class MaterialDisposalController {
    @Autowired
    private MaterialDisposalService materialDisposalService;


    @Autowired
    private ObjectMapper mapper;
    @PostMapping
    public ResponseEntity<Object> createMaterialDisposal(
            @RequestPart("materialDisposalRequestDTO") String materialDisposalRequestDTO,
            @RequestPart(value = "saleNote") MultipartFile saleNote) throws JsonProcessingException {

      MaterialDisposalRequestDTO  materialMasterRequestDto= mapper.readValue(materialDisposalRequestDTO, MaterialDisposalRequestDTO.class);
        materialMasterRequestDto.setSaleNote(saleNote);

        String saleNoteFileName = saleNote.getOriginalFilename();

        MaterialDisposalResponseDTO created = materialDisposalService.createMaterialDisposal(
                materialMasterRequestDto, saleNoteFileName);

        // Return success response
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);
    }
    @PutMapping("/{materialDisposalCode}")
    public ResponseEntity<Object> updateMaterialDisposal(
            @PathVariable String materialDisposalCode,
            @RequestPart("materialDisposalRequestDTO") String materialDisposalRequestDTO,
            @RequestPart(value = "saleNote") MultipartFile saleNote) throws JsonProcessingException {
        MaterialDisposalRequestDTO materialDisposalRequestDto = mapper.readValue(materialDisposalRequestDTO,MaterialDisposalRequestDTO.class);
        materialDisposalRequestDto.setSaleNote(saleNote);

        String saleNoteFileName = saleNote != null ? saleNote.getOriginalFilename() : null;

        MaterialDisposalResponseDTO updated = materialDisposalService.updateMaterialDisposal(
                materialDisposalCode, materialDisposalRequestDto, saleNoteFileName);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllMaterialDisposal() {
        List<MaterialDisposalResponseDTO> response = materialDisposalService.getAllMaterialDisposals();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{materialDisposalCode}")
    public ResponseEntity<Object> getMaterialDisposalById(@PathVariable String materialDisposalCode) {
        MaterialDisposalResponseDTO responseDTO = materialDisposalService.getMaterialDisposalById(materialDisposalCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{materialDisposalCode}")
    public ResponseEntity<String> deleteMaterialMaster(@PathVariable String materialDisposalCode) {
        materialDisposalService.deleteMaterialDisposal(materialDisposalCode);
        return ResponseEntity.ok("material master deleted successfully. materialCode:"+" " +materialDisposalCode);
    }




}
