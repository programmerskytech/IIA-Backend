package com.astro.service;

import com.astro.dto.workflow.MaterialCreationRequestDto;
import com.astro.dto.workflow.MaterialCreationResponseDto;

import java.util.List;

public interface MaterialCreationService {
    public MaterialCreationResponseDto createMaterial(MaterialCreationRequestDto materialCreationRequestDto);
    public MaterialCreationResponseDto updateMaterial(String materialCode, MaterialCreationRequestDto materialCreationRequestDto);
    public List<MaterialCreationResponseDto> getAllMaterials();

    public MaterialCreationResponseDto getMaterialById(String materialCode);
    public void deleteMaterial(String materialCode);
}
