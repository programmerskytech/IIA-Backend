package com.astro.service;

import com.astro.dto.workflow.InventoryModule.MaterialDisposalRequestDTO;
import com.astro.dto.workflow.InventoryModule.MaterialDisposalResponseDTO;
import com.astro.dto.workflow.MaterialCreationRequestDto;
import com.astro.dto.workflow.MaterialCreationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialDisposalService {

    public MaterialDisposalResponseDTO createMaterialDisposal(MaterialDisposalRequestDTO materialDisposalRequestDTO, String saleNoteFileName);
    public MaterialDisposalResponseDTO updateMaterialDisposal(String materialDisposalCode, MaterialDisposalRequestDTO materialDisposalRequestDTO,String saleNoteFileName);
    public List<MaterialDisposalResponseDTO> getAllMaterialDisposals();
    public MaterialDisposalResponseDTO getMaterialDisposalById(String materialDisposalCode);
    public void deleteMaterialDisposal(String materialDisposalCode);

    




}
