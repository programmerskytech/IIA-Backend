package com.astro.service;

import com.astro.dto.workflow.*;
import com.astro.entity.VendorMasterUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorMasterUtilService {
    public VendorRegiEmailResponseDTO registerVendor(VendorRegistrationRequestDTO dto);

    public List<VendorRegistrationResponseDTO> getAllAwaitingApprovalVendors();

    public String performAction(ApprovalAndRejectionRequestDTO request);

    public String performAllAction(List<ApprovalAndRejectionRequestDTO> request);

    public VendorRegistrationResponseDTO getVendorMasterUtilById(String vendorId);



}
