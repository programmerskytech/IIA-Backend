package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class poMaterialHistoryDto {
  //  private String userName;
    private String poId;
    private LocalDateTime createdDate;
    private String vendorName;
    private BigDecimal poValue;
}
