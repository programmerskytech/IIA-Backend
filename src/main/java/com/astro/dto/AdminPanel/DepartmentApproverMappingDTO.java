package com.astro.dto.AdminPanel;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepartmentApproverMappingDTO {

    private Long mappingId;
    private String departmentName;
    private String approverType; // DEAN, HEAD_SEG
    private String approverEmployeeId;
    private String approverName; // Resolved from employee
    private Integer approverRoleId;
    private BigDecimal approvalLimit;
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

    // Helper methods
    public boolean isDean() {
        return "DEAN".equalsIgnoreCase(approverType);
    }

    public boolean isHeadSEG() {
        return "HEAD_SEG".equalsIgnoreCase(approverType);
    }

    public static DepartmentApproverMappingDTO createRequest(
            String departmentName,
            String approverType,
            String approverEmployeeId,
            Integer approverRoleId,
            BigDecimal approvalLimit
    ) {
        DepartmentApproverMappingDTO dto = new DepartmentApproverMappingDTO();
        dto.setDepartmentName(departmentName);
        dto.setApproverType(approverType);
        dto.setApproverEmployeeId(approverEmployeeId);
        dto.setApproverRoleId(approverRoleId);
        dto.setApprovalLimit(approvalLimit);
        dto.setIsActive(true);
        return dto;
    }
}
