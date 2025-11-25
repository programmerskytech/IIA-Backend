package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

@Data
public class TenderEvaluationRequestDto {

    private String tenderId;
    private String uploadQualifiedVendorsFileName;
    private String uploadTechnicallyQualifiedVendorsFileName;
    private String uploadCommeriallyQualifiedVendorsFileName;
    private String formationOfTechnoCommerialComitee;
    private String responseFileName;
    private String responseForTechnicallyQualifiedVendorsFileName;
    private String responseForCommeriallyQualifiedVendorsFileName;
    private String fileType;
    private String updatedBy;
    private Integer createdBy;
    private Integer uploadQualifiedVendorsFileNameCreatedBy;
    private Integer uploadTechnicallyQualifiedVendorsFileNameCreatedBy;
    private Integer uploadCommeriallyQualifiedVendorsFileNameCreatedBy;
    private Integer formationOfTechnoCommerialComiteeCreatedBy;
    private Integer responseFileNameCreatedBy;
    private Integer responseForTechnicallyQualifiedVendorsFileNameCreatedBy;
    private Integer responseForCommeriallyQualifiedVendorsFileNameCreatedBy;

}
