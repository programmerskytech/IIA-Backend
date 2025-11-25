package com.astro.dto.workflow;

import lombok.Data;

@Data
public class VendorMasterUpdateDto {

    private String vendorName;
    private String vendorType;
    private String contactNo;
    private String emailAddress;
    private Boolean registeredPlatform;
    private String pfmsVendorCode;
    private String primaryBusiness;
    private String address;
    private String alternateEmailOrPhoneNumber;
    private String panNo;
    private String gstNo;
    private String bankName;
    private String accountNo;
    private String ifscCode;
    private String purchaseHistory;
    private String swiftCode;
    private String bicCode;
    private String ibanAbaNumber;
    private String sortCode;
    private String bankRoutingNumber;
    private String bankAddress;
    private String country;
    private String state;
    private String place;
    private String updatedBy;

    private String status;
    private String reasonForDebar;
}
