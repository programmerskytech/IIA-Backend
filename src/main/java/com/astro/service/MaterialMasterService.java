package com.astro.service;

import com.astro.dto.workflow.MaterialMasterRequestDto;
import com.astro.dto.workflow.MaterialMasterResponseDto;
import com.astro.dto.workflow.MaterialSearchResponseDto;

import java.io.IOException;
import java.util.List;

public interface MaterialMasterService {

    public MaterialMasterResponseDto createMaterialMaster(MaterialMasterRequestDto materialMasterRequestDto);
    public MaterialMasterResponseDto updateMaterialMaster(String materialCode, MaterialMasterRequestDto materialMasterRequestDto);
    public List<MaterialMasterResponseDto> getAllMaterialMasters();

    public MaterialMasterResponseDto getMaterialMasterById(String materialCode);
    public void deleteMaterialMaster(String materialCode);

    List<MaterialSearchResponseDto> searchMaterials(String keyword);
    List<MaterialSearchResponseDto> searchMaterialsByCategory(String keyword, String materialCategoryType);
    public MaterialMasterResponseDto getMaterialMasterByIdBase64(String materialCode) throws IOException;

}
