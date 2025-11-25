package com.astro.dto.workflow.ProcurementDtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class QuotationViewHistoryDto {
    private String status;
    private String remarks;
    private LocalDateTime date;
}
