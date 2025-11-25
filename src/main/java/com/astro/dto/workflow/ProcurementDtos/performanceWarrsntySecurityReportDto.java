package com.astro.dto.workflow.ProcurementDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class performanceWarrsntySecurityReportDto {
    private String poId;
    private LocalDateTime createdDate;
    private String modeOfProcurement;
    private String vendorName;
    private String titleOfTender;
    private BigDecimal totalValueOfPo;

    private String typeOfSecurity;
    private String securityNumber;
    private LocalDate securityDate;
    private LocalDate expiryDate;
  //  private BigDecimal securityAmount;
    private BigDecimal securityAmount; // calculated
    private String applicablePbgToBeSubmitted;
    public performanceWarrsntySecurityReportDto(String poId, LocalDateTime createdDate, String modeOfProcurement,
                                                String vendorName, String titleOfTender, BigDecimal totalValueOfPo,
                                                String typeOfSecurity, String securityNumber, LocalDate securityDate,
                                                LocalDate expiryDate, String applicablePbgToBeSubmitted) {
        this.poId = poId;
        this.createdDate = createdDate;
        this.modeOfProcurement = modeOfProcurement;
        this.vendorName = vendorName;
        this.titleOfTender = titleOfTender;
        this.totalValueOfPo = totalValueOfPo;
        this.typeOfSecurity = typeOfSecurity;
        this.securityNumber = securityNumber;
        this.securityDate = securityDate;
        this.expiryDate = expiryDate;
        this.applicablePbgToBeSubmitted = applicablePbgToBeSubmitted;
    }


}
