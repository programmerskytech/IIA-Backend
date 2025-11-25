package com.astro.dto.workflow;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class RegisteredVendorsDataDto {

    private String tenderNumber;
    private String purchaseOrder;
    private String deliveryAndAcceptanceStatus;
    private String paymentStatus;
    private String paymentUTRNumber;
    private LocalDate date;
    private String tenderRequestCopy;
    private String poCopy;


 //   private String uploadTenderDocumentsFileName;
   // private String uploadGeneralTermsAndConditionsFileName;
   // private String uploadSpecificTermsAndConditionsFileName;

}
