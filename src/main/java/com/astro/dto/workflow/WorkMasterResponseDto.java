package com.astro.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class WorkMasterResponseDto {

    private String workCode;
    private String workSubCategory;
   // private String modeOfProcurement;
    private String workDescription;
  //  private List<String> vendorNames;
    private String updatedBy;
    private Integer createdBy;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;


}
