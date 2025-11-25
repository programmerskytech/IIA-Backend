package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "Vendor_quotation_against_tender")
public class VendorQuotationAgainstTender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenderId;
    private String vendorId;
 //   private String vendorName;
    private String quotationFileName;
    private String priceBidFileName;
    private String fileType;

    @Column(name = "status")
    private String status;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "version")
    private Integer version;

    @Column(name = "is_latest")
    private Boolean isLatest;

    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "acceptance_status")
    private String acceptanceStatus;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();


    @Column(name = "indentor_status")
    private String indentorStatus; // values: "ACCEPTED", "REJECTED", "CHANGE_REQUESTED"

    @Column(name = "indentor_remarks")
    private String indentorRemarks;

    // SPO's decision / response
    @Column(name = "spo_status")
    private String spoStatus; // values: "ACCEPTED", "REJECTED", "CHANGE_REQUESTED_TO_INTENTOR"

    @Column(name = "spo_remarks")
    private String spoRemarks;

    @Column(name = "change_request_to_indentor")
    private Boolean changeRequestToIndentor = false;

   @Column(name = "modified_by")
   private Integer modifiedBy; // who performed this action/version

   @Enumerated(EnumType.STRING)
   @Column(name = "current_role")
   private WorkflowActorRole currentRole;

   @Enumerated(EnumType.STRING)
   @Column(name = "next_role")
   private WorkflowActorRole nextRole;

    @Column(name = "clarification_file_name")
    private String clarificationFileName;

    @Column(name = "vendor_response", columnDefinition = "TEXT")
    private String vendorResponse;

   public enum WorkflowActorRole {
      VENDOR,
      INDENTOR,
      STORE_PURCHASE_OFFICER
   }
}
