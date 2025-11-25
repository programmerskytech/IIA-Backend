package com.astro.service;

import com.astro.dto.workflow.InventoryModule.GoodsReturnRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsReturnResponseDto;
import com.astro.entity.InventoryModule.GoodsReturn;

import java.util.List;

public interface GoodsReturnService {


    List<GoodsReturnResponseDto> getAllGoodsReturns();
    GoodsReturnResponseDto getGoodsReturnById(String goodsReturnId);
    GoodsReturnResponseDto createGoodsReturn(GoodsReturnRequestDto goodsReturnDto);
    GoodsReturnResponseDto updateGoodsReturn(String goodsReturnId, GoodsReturnRequestDto goodsReturnDto);
    void deleteGoodsReturn(String goodsReturnId);

}
