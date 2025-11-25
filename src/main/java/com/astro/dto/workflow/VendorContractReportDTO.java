package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VendorContractReportDTO {

    private String orderId;
    private String modeOfProcurement;
    private String underAMC;
    //private String amcExpiryDate;
    private String amcFor;
    private String endUser;
    private Integer noOfParticipants;
    private Double value;
    private String location;
    private String vendorName;
    private String previouslyRenewedAMCs;
    private String categoryOfSecurity;
    private String validityOfSecurity;

    public VendorContractReportDTO(String orderId, String modeOfProcurement, String underAMC,
                             String amcFor, String endUser, Integer noOfParticipants, Double value, String location,
                             String vendorName, String previouslyRenewedAMCs, String categoryOfSecurity,
                                   String validityOfSecurity) {
        // LocalDate amcExpiryDate,
        this.orderId = orderId;
        this.modeOfProcurement = modeOfProcurement;
        this.underAMC = underAMC;
      //  this.amcExpiryDate = amcExpiryDate;
        this.amcFor = amcFor;
        this.endUser = endUser;
        this.noOfParticipants = noOfParticipants;
        this.value = value;
        this.location = location;
        this.vendorName = vendorName;
        this.previouslyRenewedAMCs = previouslyRenewedAMCs;
        this.categoryOfSecurity = categoryOfSecurity;
        this.validityOfSecurity = validityOfSecurity;
    }


}
