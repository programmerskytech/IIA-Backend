package com.astro.dto.workflow.InventoryModule.GprnDto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;


@Data
public class GprnMaterialsResponseDto {

    private String materialCode;
    private String description;
    private String uom;
    private Integer orderedQuantity;
    private Integer quantityDelivered;
    private Integer receivedQuantity;
    private Double unitPrice;
    private BigDecimal netPrice;
    private String makeNo;
    private String modelNo;
    private String serialNo;
    private String warranty;
    private String note;
    private String photoFileName;




}
