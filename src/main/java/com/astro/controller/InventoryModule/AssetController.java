package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.AssetRequestDTO;
import com.astro.dto.workflow.InventoryModule.AssetResponseDto;
import com.astro.entity.InventoryModule.Asset;
import com.astro.service.AssetService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;


    // Create new asset
    @PostMapping
    public ResponseEntity<Object> createAsset(@RequestBody AssetRequestDTO assetDTO) {
       AssetResponseDto asset = assetService.createAsset(assetDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(asset), HttpStatus.OK);
    }

    // Update asset
    @PutMapping("/{assetCode}")
    public ResponseEntity<Object> updateAsset(@PathVariable String assetCode, @RequestBody AssetRequestDTO assetDTO) {
       AssetResponseDto asset = assetService.updateAsset(assetCode, assetDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(asset), HttpStatus.OK);

    }

    // Get all assets
    @GetMapping
    public ResponseEntity<Object> getAllAssets() {
        List<AssetResponseDto> assets= assetService.getAllAssets();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(assets), HttpStatus.OK);
    }

    // Get asset by id
    @GetMapping("/{assetCode}")
    public ResponseEntity<Object> getAssetById(@PathVariable String assetCode) {
        AssetResponseDto asset = assetService.getAssetById(assetCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(asset), HttpStatus.OK);
    }

    // Delete asset
    @DeleteMapping("/{assetCode}")
    public ResponseEntity<String> deleteAsset(@PathVariable String assetCode) {
        assetService.deleteAsset(assetCode);
        return ResponseEntity.ok("Asset deleted successfully!");
    }



}
