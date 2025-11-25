package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PoFormateDto {

    // PO details
    private String poNumber;
   // private LocalDate poDate;
   private String poDate;
    private String tenderDate;
    private String quotationDate;


    private String indentIds;
    private String indentDates;

    // Tender details
    private String tenderNumber;
  //  private LocalDate tenderDate;

    // Quotation details
    private String quotationNo;
  //  private LocalDate quotationDate;
    private String deliveryPeriod;

    // Project & Budget
    private String projectName;
    private String budgetCode;

    // Vendor details
    private String vendorCode;
    private String vendorName;
    private String vendorAddress;
    private String gstin;
    private String contactNumber;
    private String email;

    private List<poFormateMaterial> materialDetails;

    // Other PO attributes
    private String warranty;
    private String incoTerms;
    private String paymentTerms;
    private BigDecimal dutiesAndTaxes;
    private Long deliveryPeriodWeeks;
    private String performanceAndWarrantySecurity;
    private String performanceAndWarranty;
    private String additionalTermsAndConditions;
    private String freightForwarderDetails;

    private BigDecimal totalAmount;
    private BigDecimal totalGst;
    private BigDecimal grandTotal;

    private BigDecimal buyBackAmount;
    private String currencyOfMaterial;

    private String consigneeLocation;

    private String officerSignatureFileName;
    private String officerSignatureBase64;


    private BigDecimal duties;






}
