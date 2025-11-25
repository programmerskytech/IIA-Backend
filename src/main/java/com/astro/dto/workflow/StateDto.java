package com.astro.dto.workflow;

import lombok.Data;
import java.util.Date;

@Data
public class StateDto {

    private Integer stateId;
    private String stateName;
    private String createdBy;
    private Date createdDate;
}
