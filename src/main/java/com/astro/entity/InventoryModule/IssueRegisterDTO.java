package com.astro.entity.InventoryModule;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IssueRegisterDTO {

    private Integer issueID;
    private LocalDate dateOfIssue;
    private String itemDescription;
    private String category;
    private String subCategory;
    private BigDecimal quantityIssued;
    private String unitOfMeasure;
    private String location;
    private String issuedTo;
    private String issuedBy;
    private String purpose;

    // Constructor
    public IssueRegisterDTO(Integer issueID, LocalDate dateOfIssue, String itemDescription, String category,
                            String subCategory, BigDecimal quantityIssued, String unitOfMeasure, String location,
                            String issuedTo, String issuedBy, String purpose) {
        this.issueID = issueID;
        this.dateOfIssue = dateOfIssue;
        this.itemDescription = itemDescription;
        this.category = category;
        this.subCategory = subCategory;
        this.quantityIssued = quantityIssued;
        this.unitOfMeasure = unitOfMeasure;
        this.location = location;
        this.issuedTo = issuedTo;
        this.issuedBy = issuedBy;
        this.purpose = purpose;
    }
}
