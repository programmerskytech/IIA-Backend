package com.astro.dto.workflow.ProcurementDtos.purchaseOrder;

import lombok.Data;

import java.util.Date;

@Data
public class PoFormateApprovalHistory {

    private String status;
    private String nextAction;
    private String action;
    private String remarks;
    private Integer createdBy;
    private String createdRole;
    private Integer modifiedBy;
    private Date modificationDate;
    private Date createdDate;
}
