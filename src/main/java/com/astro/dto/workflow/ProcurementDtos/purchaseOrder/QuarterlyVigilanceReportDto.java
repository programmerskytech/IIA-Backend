package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QuarterlyVigilanceReportDto {

    private String orderNo;
    private LocalDate orderDate;
    private Double value;
    private List<PoMaterialReport> descriptions;
    private String vendorName;
    private String location;
    private String deliveryDate;

}
