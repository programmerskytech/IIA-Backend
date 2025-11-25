package com.astro.dto.workflow.InventoryModule.GprnDto;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class GetGprnDtlsDto {

    private String gprnNo;
    private String poId;
    private String date;
    private String deliveryChallanNo;
    private String deliveryChallanDate;
    private String vendorId;
    private String vendorName;
    private String vendorEmail;
    private Long vendorContactNo;
    private String fieldStation;
    private String indentorName;
    private String expectedSupplyDate;
    private String consigneeDetail;
    private Integer warrantyYears;
    private String project;
    private String receivedQty;
  //  private String pendingQty;
  //  private String acceptedQty;
 //   private String provisionalReceiptCertificateFileName;
    private String receivedBy;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<GprnMaterialsResponseDto> gprnMaterialsResponsetDtos;
}
