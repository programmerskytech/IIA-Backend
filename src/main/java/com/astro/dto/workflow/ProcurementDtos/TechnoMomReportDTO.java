package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.math.BigDecimal;

import java.util.Date;

@Data
public class TechnoMomReportDTO {

    private Date date;
    private String uploadedTechnoCommercialMoMReports;
    private String poWoNumber;
    private BigDecimal value;
    private String correspondingIndentNumber;


    public TechnoMomReportDTO(Date date, String uploadedTechnoCommercialMoMReports,
                              String poWoNumber, BigDecimal value, String correspondingIndentNumber) {
        this.date = date;
        this.uploadedTechnoCommercialMoMReports = uploadedTechnoCommercialMoMReports;
        this.poWoNumber = poWoNumber;
        this.value = value;
        this.correspondingIndentNumber = correspondingIndentNumber;
    }


}
