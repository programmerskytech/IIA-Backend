package com.astro.entity.ProcurementModule;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class IndentCreation {


        @Id
        @Column(name = "indent_id", nullable = false, unique = true)
        private String indentId;

        @Column(name = "indent_number", unique = true)
        private Integer indentNumber;


        @Column(name = "indentor_name")
        private String indentorName;


        @Column(name = "indentor_mobile_no")
        private String indentorMobileNo;

        @Column(name = "indentor_email_address")
        private String indentorEmailAddress;

        @Column(name = "consignes_location")
        private String consignesLocation;
        @Lob
        @Column(name = "uploading_prior_approvals")
        private byte[] uploadingPriorApprovals;

        @Column(name = "project_name")
        private String projectName;

        @Lob
        @Column(name = "upload_tender_documents")
        private byte[] uploadTenderDocuments;

        @Column(name = "is_pre_bit_meeting_required")
        private Boolean isPreBitMeetingRequired;

        @Column(name = "pre_bid_meeting_date", nullable = true)
        private LocalDate preBidMeetingDate;

        @Column(name = "pre_bid_meeting_venue")
        private String preBidMeetingVenue;

        @Column(name = "is_it_a_rate_contract_indent")
        private Boolean isItARateContractIndent;

        @Column(name = "estimated_rate")
        private BigDecimal estimatedRate;

        @Column(name = "period_of_contract")
        private BigDecimal periodOfContract;

        @Column(name = "single_and_multiple_job")
        private String singleAndMultipleJob;

        @Lob
        @Column(name = "upload_goi_or_rfp")
        private byte[] uploadGOIOrRFP;

        @Lob
        @Column(name = "upload_pac_or_brand_pac")
        private byte[] uploadPACOrBrandPAC;

        @Column(name = "uploading_prior_approvals_file_name")
        private String uploadingPriorApprovalsFileName;
        @Column(name = "technical_specifications_file_name")
        private String technicalSpecificationsFileName;
        @Column(name = "draft_Eoi_Or_Rfp_file_name")
        private String draftEOIOrRFPFileName;
        @Column(name = "upload_pac_or_brand_pac_file_name")
        private String uploadPACOrBrandPACFileName;
        @Column(name="file_type")
        private String fileType;
        @Column(name = "total_indent_value")
        private BigDecimal totalIntentValue;

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
        private String serialNumber;
        private LocalDate dateOfPurchase;
        private String uploadBuyBackFileNames;
        private String employeeDepartment;

        @Column(name = "proprietary_and_limited_declaration")
        private Boolean proprietaryAndLimitedDeclaration;
      //  private String totalAmount;
      //  @OneToMany(mappedBy = "indentCreation", cascade = CascadeType.ALL, orphanRemoval = true)
      //@ManyToMany(cascade = CascadeType.PERSIST)
  /*    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
      @JoinTable(
              name = "indent_material_mapping",
              joinColumns = @JoinColumn(name = "indent_id"),
              inverseJoinColumns = @JoinColumn(name = "material_code")
      )

   */
      @OneToMany(mappedBy = "indentCreation", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<MaterialDetails> materialDetails = new ArrayList<>();

        @Column(name = "employee_id")
        private String employeeId;

        @Column(name = "employee_name")
        private String employeeName;

        @Column(name = "cancel_status")
        private Boolean cancelStatus = false;

        @Column(name = "cancel_remarks")
        private String cancelRemarks;


        @Column(name = "created_by")
        private Integer createdBy;

        @Column(name = "updated_by")
        private String updatedBy;

        @Column(name = "created_date")
        private LocalDateTime createdDate = LocalDateTime.now();

        @Column(name = "updated_date")
        private LocalDateTime updatedDate = LocalDateTime.now();
    }


