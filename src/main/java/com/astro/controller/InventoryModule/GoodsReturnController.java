package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.GoodsReturnRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsReturnResponseDto;
import com.astro.entity.InventoryModule.GoodsReturn;
import com.astro.service.GoodsReturnService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-returns")
public class GoodsReturnController {

    @Autowired
    private GoodsReturnService service;


    @PostMapping
    public ResponseEntity<Object> createGoodsReturn(@RequestBody GoodsReturnRequestDto goodsReturnDto) {
       GoodsReturnResponseDto goodsReturn= service.createGoodsReturn(goodsReturnDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsReturn), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllGoodsReturns() {
        List<GoodsReturnResponseDto> goodsReturn=service.getAllGoodsReturns();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsReturn), HttpStatus.OK);
    }

    @GetMapping("/{goodsReturnId}")
    public ResponseEntity<Object> getGoodsReturnById(@PathVariable String goodsReturnId) {
        GoodsReturnResponseDto goodsReturn =service.getGoodsReturnById(goodsReturnId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsReturn), HttpStatus.OK);
    }

    @PutMapping("/{goodsReturnId}")
    public ResponseEntity<Object> updateGoodsReturn(
            @PathVariable String goodsReturnId, @RequestBody GoodsReturnRequestDto goodsReturnDto) {
        GoodsReturnResponseDto goodsReturn =service.updateGoodsReturn(goodsReturnId, goodsReturnDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(goodsReturn), HttpStatus.OK);
    }

    @DeleteMapping("/{goodsReturnId}")
    public ResponseEntity<String> deleteGoodsReturn(@PathVariable String goodsReturnId) {
        service.deleteGoodsReturn(goodsReturnId);
        return ResponseEntity.ok("Goods Return deleted successfully!");
    }


}
