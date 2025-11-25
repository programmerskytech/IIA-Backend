package com.astro.dto.workflow.InventoryModule.GprnDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;


@Data
public class MaterialDtlDto {
   private String materialCode;
   private String materialDesc;
   private String uomId;
   private String category;
   private BigDecimal receivedQuantity;
   private BigDecimal orderedQuantity;
   private BigDecimal quantityDelivered;
   private BigDecimal unitPrice;
   private String makeNo;
   private String modelNo;
   private String serialNo;
   private String warrantyTerms;
   private String note;
   private List<String> imageBase64;
}



