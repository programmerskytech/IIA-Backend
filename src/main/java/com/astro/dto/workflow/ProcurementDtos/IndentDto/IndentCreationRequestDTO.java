package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import com.astro.util.Base64ToByteArrayConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class IndentCreationRequestDTO {

    private String indentorName;

  //  private String indentId;

    private String indentorMobileNo;

    private String indentorEmailAddress;
    private String consignesLocation;
    private String fileType;
    private List<String> uploadingPriorApprovalsFileName;
    private List<String> technicalSpecificationsFileName;
    private List<String> draftEOIOrRFPFileName;
    private List<String> uploadPACOrBrandPACFileName;
    //private MultipartFile uploadingPriorApprovals;
  //  private MultipartFile uploadTenderDocuments;
  //  private MultipartFile uploadGOIOrRFP;
   // private MultipartFile uploadPACOrBrandPAC;


    private String projectName;
    private Boolean isPreBidMeetingRequired;
    private String preBidMeetingDate;
    private String preBidMeetingVenue;
    private Boolean isItARateContractIndent;
    private BigDecimal estimatedRate;
    private BigDecimal periodOfContract;
    private String singleAndMultipleJob;
    private String brandAndModel;
    private String justification;
    private Boolean brandPac;
    private String quarter;
    private String purpose;
    private String reason;
    private String proprietaryJustification;
    private Boolean buyBack;
    private String buyBackAmount;
    private String modelNumber;
    private String employeeDepartment;
    private String serialNumber;
    private String dateOfPurchase;
    private Boolean proprietaryAndLimitedDeclaration;
    private List<String> uploadBuyBackFileNames;
    private List<MaterialDetailsRequestDTO> materialDetails;

    private String updatedBy;
    private Integer createdBy;

}
