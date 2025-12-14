package com.astro.dto.workflow;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class DepartmentComputerPriceLimitResponseDTO {

    private Long id;

    private String departmentName;

    private BigDecimal priceLimit;

    private Boolean isActive;

    private Integer createdBy;

    private Integer updatedBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String remarks;
}
