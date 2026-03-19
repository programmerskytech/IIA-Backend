package com.astro.dto.AdminPanel;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FieldStationApproverDTO {

    private Long id;
    private String fieldStationName;

    // Type: ENGINEER_INCHARGE or PROFESSOR_INCHARGE
    private String inchargeType;

    private String inchargeEmployeeId;
    private String inchargeEmployeeName;
    private String inchargeName; // Resolved from employee (alias for inchargeEmployeeName)
    private Integer inchargeRoleId;
    private String roleName; // Derived: "Engineer In-Charge" or "Professor In-Charge"
    private BigDecimal approvalLimit;
    private Boolean isActive;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

    /**
     * Create request for Engineer In-Charge
     */
    public static FieldStationApproverDTO createEngineerInCharge(
            String fieldStationName,
            String inchargeEmployeeId,
            String inchargeEmployeeName,
            Integer inchargeRoleId,
            BigDecimal approvalLimit
    ) {
        FieldStationApproverDTO dto = new FieldStationApproverDTO();
        dto.setFieldStationName(fieldStationName);
        dto.setInchargeType("ENGINEER_INCHARGE");
        dto.setInchargeEmployeeId(inchargeEmployeeId);
        dto.setInchargeEmployeeName(inchargeEmployeeName);
        dto.setInchargeRoleId(inchargeRoleId);
        dto.setApprovalLimit(approvalLimit);
        dto.setRoleName("Engineer In-Charge");
        dto.setIsActive(true);
        return dto;
    }

    /**
     * Create request for Professor In-Charge
     */
    public static FieldStationApproverDTO createProfessorInCharge(
            String fieldStationName,
            String inchargeEmployeeId,
            String inchargeEmployeeName,
            Integer inchargeRoleId,
            BigDecimal approvalLimit
    ) {
        FieldStationApproverDTO dto = new FieldStationApproverDTO();
        dto.setFieldStationName(fieldStationName);
        dto.setInchargeType("PROFESSOR_INCHARGE");
        dto.setInchargeEmployeeId(inchargeEmployeeId);
        dto.setInchargeEmployeeName(inchargeEmployeeName);
        dto.setInchargeRoleId(inchargeRoleId);
        dto.setApprovalLimit(approvalLimit);
        dto.setRoleName("Professor In-Charge");
        dto.setIsActive(true);
        return dto;
    }

    /**
     * Generic create request (backward compatible)
     */
    public static FieldStationApproverDTO createRequest(
            String fieldStationName,
            String inchargeEmployeeId,
            Integer inchargeRoleId,
            BigDecimal approvalLimit
    ) {
        return createEngineerInCharge(fieldStationName, inchargeEmployeeId, null, inchargeRoleId, approvalLimit);
    }
}
