package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.GoodsReturnRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsReturnResponseDto;
import com.astro.dto.workflow.InventoryModule.GoodsTransferRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsTransferResponseDto;
import com.astro.repository.InventoryModule.GoodsTransferRepository;
import com.astro.service.GoodsTransferService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-transfer")
public class GoodsTransferController {
    @Autowired
    private GoodsTransferService  goodsTransferService;

    @PostMapping
    public ResponseEntity<Object> createGoodsTransfer(@RequestBody GoodsTransferRequestDto goodsTransferRequestDto) {
        GoodsTransferResponseDto goodsTransfer= goodsTransferService.createGoodsTransfer(goodsTransferRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsTransfer), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllGoodsTransfers() {
        List<GoodsTransferResponseDto> goodsTransfer=goodsTransferService.getAllGoodsTransfer();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsTransfer), HttpStatus.OK);
    }

    @GetMapping("/{goodsTransferID}")
    public ResponseEntity<Object> getGoodsTransferById(@PathVariable String goodsTransferID) {
        GoodsTransferResponseDto goodsTransfer =goodsTransferService.getGoodsTransferById(goodsTransferID);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsTransfer), HttpStatus.OK);
    }

    @PutMapping("/{goodsTransferID}")
    public ResponseEntity<Object> updateGoodsTransfer(
            @PathVariable String goodsTransferID, @RequestBody GoodsTransferRequestDto goodsTransferRequestDto) {
        GoodsTransferResponseDto goodsTransfer =goodsTransferService.updateGoodsTransfer(goodsTransferID, goodsTransferRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsTransfer), HttpStatus.OK);
    }

    @DeleteMapping("/{goodsTransferID}")
    public ResponseEntity<String> deleteGoodsTransfer(@PathVariable String goodsTransferID) {
        goodsTransferService.deleteGoodsTransfer(goodsTransferID);
        return ResponseEntity.ok("Goods transfer deleted successfully!");
    }


}
