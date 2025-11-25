package com.astro.dto.workflow;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MaterialTransitionHistory {

    private Long id;
    private String materialCode;
    private String action;
    private String status;
    private String comments;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;



}
