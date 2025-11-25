package com.astro.service;

import com.astro.dto.workflow.InventoryModule.AssetDisposalDto;
import com.astro.dto.workflow.InventoryModule.AssetRequestDTO;
import com.astro.dto.workflow.InventoryModule.AssetResponseDto;
import com.astro.entity.InventoryModule.Asset;

import java.util.List;

public interface AssetService {

    public AssetResponseDto createAsset(AssetRequestDTO assetDTO);
    public AssetResponseDto updateAsset(String assetCode, AssetRequestDTO assetDTO);
    public List<AssetResponseDto> getAllAssets();

    public AssetResponseDto getAssetById(String assetCode);


    public void deleteAsset(String assetCode);


}
