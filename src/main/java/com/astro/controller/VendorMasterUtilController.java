package com.astro.controller;

import com.astro.dto.workflow.*;
import com.astro.service.VendorMasterService;
import com.astro.service.VendorMasterUtilService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendor-master-util")
public class VendorMasterUtilController {

    @Autowired
    private VendorMasterUtilService vendorMasterUtil;
    @Autowired
    private VendorMasterService vendorMasterService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerVendor(@RequestBody VendorRegistrationRequestDTO dto) {
        VendorRegiEmailResponseDTO vendor = vendorMasterUtil.registerVendor(dto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(vendor), HttpStatus.OK);
    }


    @GetMapping("/awaiting-approval")
    public ResponseEntity<Object>  getAllAwaitingApprovalVendors() {
        List<VendorRegistrationResponseDTO> vendors = vendorMasterUtil.getAllAwaitingApprovalVendors();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(vendors), HttpStatus.OK);

    }

    @PostMapping("/performAction")
    public ResponseEntity<Object> approveOrRejectVendor(@RequestBody ApprovalAndRejectionRequestDTO request) {
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(vendorMasterUtil.performAction(request)), HttpStatus.OK);

    }

    @PostMapping("/performBulkAction")
    public ResponseEntity<Object> approveOrRejectBulkVendor(@RequestBody List<ApprovalAndRejectionRequestDTO> request) {
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(vendorMasterUtil.performAllAction(request)), HttpStatus.OK);

    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<Object> getVendorMasterUtilById(@PathVariable String vendorId) {
        VendorRegistrationResponseDTO responseDTO = vendorMasterUtil.getVendorMasterUtilById(vendorId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Object> checkEmailExists(@PathVariable String email) {
        boolean exists = vendorMasterService.checkEmailExistsForInternational(email);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("exists", exists);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );
    }

    @GetMapping("/check-panNumber/{panNumber}")
    public ResponseEntity<Object> checkPanNumberExists(@PathVariable String panNumber) {
        boolean exists = vendorMasterService.checkPanExists(panNumber);
        Map<String, Boolean> responseData = new HashMap<>();
        responseData.put("exists", exists);
        return new ResponseEntity<>(
                ResponseBuilder.getSuccessResponse(responseData),
                HttpStatus.OK
        );
    }








}
