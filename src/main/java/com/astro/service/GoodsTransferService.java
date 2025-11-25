package com.astro.service;

import com.astro.dto.workflow.InventoryModule.GoodsTransferRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsTransferResponseDto;


import java.util.List;

public interface GoodsTransferService {

    public GoodsTransferResponseDto createGoodsTransfer(GoodsTransferRequestDto goodsTransferRequestDto);
    GoodsTransferResponseDto updateGoodsTransfer(String goodsTransferID, GoodsTransferRequestDto goodsTransferRequestDto);


    List<GoodsTransferResponseDto> getAllGoodsTransfer();
    GoodsTransferResponseDto getGoodsTransferById(String goodsTransferID);
    void deleteGoodsTransfer(String goodsTransferID);

}
