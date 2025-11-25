package com.astro.controller;

import com.astro.dto.workflow.*;
import com.astro.dto.workflow.ProcurementDtos.AllVendorStatus;
import com.astro.dto.workflow.ProcurementDtos.QuotationViewHistoryDto;
import com.astro.dto.workflow.ProcurementDtos.VendorQuotationChangeRequestDto;
import com.astro.service.VendorQuotationAgainstTenderService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendor-quotation")
public class VendorQuotationController {

    @Autowired
    private VendorQuotationAgainstTenderService vqService;
    @PostMapping
    public ResponseEntity<Object> createVendorQuotation(@RequestBody VendorQuotationAgainstTenderDto requestDTO) {
        VendorQuotationAgainstTenderDto vq = vqService.saveQuotation(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(vq), HttpStatus.OK);
    }

    @GetMapping("/{tenderId}")
    public ResponseEntity<Object> getVendorQuotationByTenderId(@PathVariable String tenderId,@RequestParam String userRole) {
        List<VendorQuotationAgainstTenderDto> responseDTO = vqService.getQuotationsByTenderId(tenderId, userRole);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("/getAllVendorQuotations/{tenderId}")
    public ResponseEntity<Object> getVendorAllQuotationsByTenderId(@PathVariable String tenderId) {
        VendorQuotationAcceptedAndRejectedDataDto responseDTO = vqService.getAllVendorQuotationsByTenderId(tenderId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("NotSubmitVendors/{tenderId}")
    public ResponseEntity<Object> getVendorNotSubmittedQuotationByTenderId(@PathVariable String tenderId) {
        List<String> responseDTO = vqService.getVendorsWhoDidNotSubmitQuotation(tenderId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @GetMapping("VendorStatus/{vendorId}")
    public ResponseEntity<Object> getVendorStatus(@PathVariable String vendorId) {
        VendorStatusDto responseDTO = vqService.getVendorStatus(vendorId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @GetMapping("vendorHistory/{tenderId}/{vendorId}")
    public ResponseEntity<Object> getVendorHistory(@PathVariable String tenderId,@PathVariable String vendorId) {
        List<QuotationViewHistoryDto> responseDTO = vqService.getVendorHistory(tenderId,vendorId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("tenderEvaluationHistory/{tenderId}/{vendorId}")
    public ResponseEntity<Object> getTenderEvaluationHistory(@PathVariable String tenderId,@PathVariable String vendorId) {
        List<TenderEvaluationHistory> responseDTO = vqService.getFullQuotationHistory(tenderId,vendorId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }


    @PutMapping("/updateVendorQuotation-status")
    public ResponseEntity<Object> updateVendorQuotationStatus(@RequestBody VendorQuotationUpdateRequestDto request) {
        Boolean message = vqService.updateStatusAndRemarks(request);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("status",  message);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );

    }
    @PostMapping("/change-request")
    public ResponseEntity<Object> requestQuotationChange(@RequestBody VendorQuotationChangeRequestDto request) {
        boolean success = vqService.markQuotationForChangeRequest(request);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("status",  success);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );




    }

    @PutMapping("/quotations/accept")
    public ResponseEntity<Object> acceptVendorQuotation(@RequestParam String tenderId,
                                                        @RequestParam String vendorId, @RequestParam Integer userId) {
        boolean result = vqService.acceptVendorQuotation(tenderId, vendorId, userId);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("accepted vendor",  result);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );
    }

    @PostMapping("/spo-review")
    public ResponseEntity<Object> storeOfficerReviewQuotation(@RequestBody spoDto spo) {

            boolean result = vqService.storeOfficerReviewQuotation(spo.getTenderId(), spo.getVendorId(), spo.getAction(), spo.getRemarks(), spo.getUserId());
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("accepted vendor",  result);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );
    }

    @PutMapping("/reject")
    public ResponseEntity<Object> rejectVendorQuotation(@RequestBody
            VendorQuotationUpdateRequestDto  vm) {
        boolean result = vqService.rejectVendorQuotation(vm.getTenderId(),vm.getVendorId(), vm.getRemarks(), vm.getUserId());
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("accepted vendor",  result);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );
    }

    @GetMapping("/completed-vendors/{tenderId}")
    public ResponseEntity<Object> getVendorsWithCompletedStatus(@PathVariable String tenderId) {
        List<String> vendorIds = vqService.getVendorsWithCompletedQuotation(tenderId);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(vendorIds),
                HttpStatus.OK
        );
    }
    @GetMapping("/all-vendors/Status/{tenderId}")
    public ResponseEntity<Object> getAllVendorsStatusOfTender(@PathVariable String tenderId) {
        List<AllVendorStatus> vendor = vqService.getAllVendorStatusOnTenderid(tenderId);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(vendor),
                HttpStatus.OK
        );
    }
    @GetMapping("/all-gem-vendors/Status/{tenderId}")
    public ResponseEntity<Object> getAllGemVendorsStatusOfTender(@PathVariable String tenderId) {
        List<AllVendorStatus> vendor = vqService.getAllVendorStatusOnTenderidsForGem(tenderId);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(vendor),
                HttpStatus.OK
        );
    }

    @GetMapping("/completed-vendorNames/{tenderId}")
    public ResponseEntity<Object> getVendorsNamesWithCompletedStatus(@PathVariable String tenderId) {
        List<CompletedVendorsDto> vendorIds = vqService.getVendorsNamesWithCompletedQuotation(tenderId);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(vendorIds),
                HttpStatus.OK
        );
    }

    @PostMapping("/change-password")
public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequestDto request) {
    ChangePasswordResponseDto response = vqService.changePassword(request);
    return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
}


    }
