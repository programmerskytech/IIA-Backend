package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.InventoryModule.GoodsInspectionResponseDto;
import com.astro.dto.workflow.InventoryModule.GoodsReceiptInspectionRequestDto;
import com.astro.dto.workflow.InventoryModule.GoodsReceiptInspectionResponseDto;
import com.astro.dto.workflow.InventoryModule.GprnDto.GetGprnDtlsDto;
import com.astro.service.GoodsInspectionService;
import com.astro.service.GoodsReceiptInspectionService;
import com.astro.service.ProcessService;
import com.astro.service.PurchaseOrderService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods-receipt-inspection")
public class GRIController {



    @Autowired
    private GoodsReceiptInspectionService service;
    @Autowired
    private PurchaseOrderService poService;

    @Autowired
    private ProcessService gprnService;


    @Autowired
    private GoodsInspectionService goodsInspectionService;
    @PostMapping
    public ResponseEntity<Object> createGoodsReceiptInspection(@RequestBody GoodsReceiptInspectionRequestDto dto) {
        GoodsReceiptInspectionResponseDto created = service.createGoodsReceiptInspection(dto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);
    }

    @PutMapping("/{receiptInspectionNo}")
    public ResponseEntity<Object> updateGoodsReceiptInspection(@PathVariable String receiptInspectionNo, @RequestBody GoodsReceiptInspectionRequestDto dto) {
        GoodsReceiptInspectionResponseDto updated = service.updateGoodsReceiptInspection(receiptInspectionNo, dto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(updated), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllGoodsReceiptInspections() {
        List<GoodsReceiptInspectionResponseDto> gri = service.getAllGoodsReceiptInspections();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(gri), HttpStatus.OK);
    }

    @GetMapping("/{receiptInspectionNo}")
    public ResponseEntity<Object> getGoodsReceiptInspectionById(@PathVariable String receiptInspectionNo) {
        GoodsReceiptInspectionResponseDto gri = service.getGoodsReceiptInspectionById(receiptInspectionNo);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(gri), HttpStatus.OK);
    }

    @DeleteMapping("/{receiptInspectionNo}")
    public ResponseEntity<String> deleteGoodsReceiptInspection(@PathVariable String receiptInspectionNo) {
        service.deleteGoodsReceiptInspection(receiptInspectionNo);
        return ResponseEntity.ok("GoodsReceiptInspection deleted successfully!");
    }

/*
    @GetMapping("/purchase-orders/{poId}")
    public ResponseEntity<Object> fetchPurchaseOrderDetails(@PathVariable String poId) {
        PurchaseOrderResponseDTO po = poService.getPurchaseOrderById(poId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(po), HttpStatus.OK);
    }

 */

  /*  @GetMapping("/gprn/{gprnId}")
    public ResponseEntity<Object> fetchGprDetails(@PathVariable String gprnId) {
        getGprnDtlsDto gprnDetails = gprnService.getGprnById(gprnId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(gprnDetails), HttpStatus.OK);
    }

   */

    @GetMapping("/goodsInspection/{goodsInspectionNo}")
    public ResponseEntity<Object> fetchGoodsInspectionDetails(@PathVariable String goodsInspectionNo) {
        GoodsInspectionResponseDto inspection = goodsInspectionService.getGoodsInspectionById(goodsInspectionNo);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(inspection), HttpStatus.OK);
    }




}
