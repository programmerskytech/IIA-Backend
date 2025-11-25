package com.astro.controller.InventoryModule;


import com.astro.dto.workflow.InventoryModule.GatepassRequestDTO;
import com.astro.dto.workflow.InventoryModule.GatepassResponseDTO;
import com.astro.service.GatepassService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gate-pass")
public class GatepassController {

    @Autowired
    private GatepassService gatepassService;

    @PostMapping
    public ResponseEntity<Object> createGatepassInAndOut(@RequestBody GatepassRequestDTO gatepassRequestDTO) {
        GatepassResponseDTO response= gatepassService.createGatepass(gatepassRequestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllGatepasses() {
        List<GatepassResponseDTO> gatepasses=gatepassService.getAllGatepasses();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(gatepasses), HttpStatus.OK);
    }

    @GetMapping("/{gatePassId}")
    public ResponseEntity<Object> getGatePassById(@PathVariable String gatePassId) {
        GatepassResponseDTO response =gatepassService.getGatepassById(gatePassId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("/{gatePassId}")
    public ResponseEntity<Object> updateGatePassInAndOut(
            @PathVariable String gatePassId, @RequestBody GatepassRequestDTO gatepassRequestDTO) {
        GatepassResponseDTO response =gatepassService.updateGatepass(gatePassId, gatepassRequestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{gatePassId}")
    public ResponseEntity<String> deleteGatePasInAndOut(@PathVariable String gatePassId) {
        gatepassService.deleteGatepass(gatePassId);
        return ResponseEntity.ok("Gate Pass deleted successfully!");
    }





}
