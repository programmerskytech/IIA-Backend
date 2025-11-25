package com.astro.service;



import com.astro.dto.workflow.*;
import com.astro.dto.workflow.ProcurementDtos.approvedTenderIdWithTitle;
import com.astro.entity.VendorMaster;

import java.util.List;

public interface VendorMasterService {

    public VendorMasterResponseDto createVendorMaster(VendorMasterRequestDto vendorMasterRequestDto);
    public VendorMasterResponseDto updateVendorMaster(String vendorId, VendorMasterRequestDto vendorMasterRequestDto);
    public List<VendorMasterResponseDto> getAllVendorMasters();

    public VendorMasterResponseDto getVendorMasterById(String vendorId);
    public void deleteVendorMaster(String vendorId);

   public List<VendorMasterResponseDto> getAllNotApprovedVendors();

    public RegisteredVendorsDataDto getVendorPurchaseOrders(String tenderId);

    public List<approvedTenderIdWithTitle> getTenderIds(String vendorId);
    public boolean checkPanExists(String panNumber);
    public boolean checkEmailExistsForInternational(String email);
    public List<VendorIdNameDTO> getAllVendorIdAndName();
    public VendorMaster getVendorByVendorId(String vendorId);
    public VendorMaster updateVendor(String vendorId, VendorMasterUpdateDto dto);


 //   List<VendorContractReportDTO> getVendorContracts(String startDate, String endDate);
}
