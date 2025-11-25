package com.astro.service;

import com.astro.dto.workflow.InventoryModule.GoodsInspectionRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsInspectionResponseDto;
import com.astro.entity.InventoryModule.GoodsInspection;

import java.util.List;

public interface GoodsInspectionService {

    List<GoodsInspectionResponseDto> getAllGoodsInspections();

    GoodsInspectionResponseDto getGoodsInspectionById(String goodsInspectionNo);

    GoodsInspectionResponseDto createGoodsInspection(GoodsInspectionRequestDto goodsInspectionDTO, String uploadInstallationReportFileName);

    GoodsInspectionResponseDto updateGoodsInspection(String goodsInspectionNo, GoodsInspectionRequestDto goodsInspectionDTO, String uploadInstallationReportFileName);

    void deleteGoodsInspection(String goodsInspectionNo);

}
