package com.astro.service;

import com.astro.dto.workflow.InventoryModule.GatepassRequestDTO;
import com.astro.dto.workflow.InventoryModule.GatepassResponseDTO;
import com.astro.dto.workflow.InventoryModule.MaterialDisposalRequestDTO;
import com.astro.dto.workflow.InventoryModule.MaterialDisposalResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GatepassService {

    public GatepassResponseDTO createGatepass(GatepassRequestDTO requestDTO);
    public List<GatepassResponseDTO> getAllGatepasses();
    public GatepassResponseDTO getGatepassById(String gatePassId);
    public GatepassResponseDTO updateGatepass(String gatePassId, GatepassRequestDTO requestDTO);
    public void deleteGatepass(String gatePassId);



}
