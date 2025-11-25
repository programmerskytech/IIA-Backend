package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class IndentDataResponseDto {

    private String indentorName;
    private String indentId;
    private String indentorMobileNo;
    private String indentorEmailAddress;
    private String consignesLocation;
    private List<String> uploadingPriorApprovalsFileName;
    private List<String> technicalSpecificationsFileName;
    private List<String> draftEOIOrRFPFileName;
    private List<String> uploadPACOrBrandPACFileName;
    private String fileType;
    private String projectName;
    private Boolean isPreBidMeetingRequired;
    private String preBidMeetingDate;
    private String preBidMeetingVenue;
    private Boolean isItARateContractIndent;
    private BigDecimal estimatedRate;
    private BigDecimal periodOfContract;
    private String singleAndMultipleJob;
    private String materialCategory;
    private BigDecimal totalPriceOfAllMaterials;
    private BigDecimal projectLimit;
    private List<MaterialDetailsResponseDTO> materialDetails;
    private String brandAndModel;
    private String justification;
    private Boolean brandPac;
    private String quarter;
    private String purpose;
    private String reason;
    private String modelNumber;
    private String serialNumber;
    private String dateOfPurchase;
    private String proprietaryJustification;
    private Boolean buyBack;
    private Boolean proprietaryAndLimitedDeclaration;
    private List<String> uploadBuyBackFileNames;
    private Integer createdBy;
    private String updatedBy;
    private String approvedBy;
    private String date;
    private String remarks;
    private String priorApprovalsFileName;
    private String technicalSpecificationsFile;
    private String draftFileName;
    private String buyBackFileName;
    private String pacAndBrandFileName;
    private String processStage;
    private String status;

}
