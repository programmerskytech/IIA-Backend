package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TenderEvaluationResponseWithBitTypeAndValueDto {

    private String tenderId;
    private String uploadQualifiedVendorsFileName;
    private String uploadTechnicallyQualifiedVendorsFileName;
    private String uploadCommeriallyQualifiedVendorsFileName;
    private String formationOfTechnoCommerialComitee;
    private String responseFileName;
    private String responseForTechnicallyQualifiedVendorsFileName;
    private String responseForCommeriallyQualifiedVendorsFileName;
    private Integer uploadQualifiedVendorsFileNameCreatedBy;
    private Integer uploadTechnicallyQualifiedVendorsFileNameCreatedBy;
    private Integer uploadCommeriallyQualifiedVendorsFileNameCreatedBy;
    private Integer formationOfTechnoCommerialComiteeCreatedBy;
    private Integer responseFileNameCreatedBy;
    private Integer responseForTechnicallyQualifiedVendorsFileNameCreatedBy;
    private Integer responseForCommeriallyQualifiedVendorsFileNameCreatedBy;
    private String fileType;
    private String bidType;
    private BigDecimal totalValueOfTender;
    private String updatedBy;
    private Integer createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;



}
