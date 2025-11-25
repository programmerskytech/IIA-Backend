package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TenderResponseDto {
    private String tenderId;
    private String titleOfTender;
    private String openingDate;
    private String closingDate;
    // private String indentId;
    private String indentMaterials;
    private String modeOfProcurement;
    private String bidType;
    private String lastDateOfSubmission;
    private String applicableTaxes;
    // private String consignesAndBillinngAddress;
    private String incoTerms;
    private String paymentTerms;
    private Boolean ldClause;
    // private String applicablePerformance;
    private String performanceAndWarrantySecurity;
    private Boolean bidSecurityDeclaration;
    private Boolean mllStatusDeclaration;
    private String singleAndMultipleVendors;
    private String uploadTenderDocuments;
    private String uploadGeneralTermsAndConditions;
    private String uploadSpecificTermsAndConditions;
    private String fileType;
    private String bidSecurityDeclarationFileName;
    private String mllStatusDeclarationFileName;
    private String preBidDisscussions;
    private BigDecimal totalTenderValue;
    private String projectName;
    private BigDecimal projectLimit;
    private String billinngAddress;
    private String consignes;
    private String updatedBy;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<String> indentIds;
    //private String vendorId;
     // private String quotationFileName;
    private Boolean buyBack;
    private String buyBackAmount;
    private String modelNumber;
    private String serialNumber;
    private String dateOfPurchase;
    private String uploadBuyBackFileNames;
}
