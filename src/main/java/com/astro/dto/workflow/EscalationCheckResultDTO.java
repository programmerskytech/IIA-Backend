package com.astro.dto.workflow;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EscalationCheckResultDTO {

    private boolean escalationRequired;
    private String escalationReason;
    private String escalationRoleName;
    private Integer escalationRoleId;
    private BigDecimal currentAmount;
    private BigDecimal approverLimit;
    private String currentApproverRole;
    private boolean skipCurrentApprover;
    private String skipReason;

    // Static factory methods
    public static EscalationCheckResultDTO noEscalation() {
        EscalationCheckResultDTO result = new EscalationCheckResultDTO();
        result.setEscalationRequired(false);
        result.setSkipCurrentApprover(false);
        return result;
    }

    public static EscalationCheckResultDTO escalateTo(
            String escalationRoleName,
            String reason,
            BigDecimal amount,
            BigDecimal limit
    ) {
        EscalationCheckResultDTO result = new EscalationCheckResultDTO();
        result.setEscalationRequired(true);
        result.setEscalationRoleName(escalationRoleName);
        result.setEscalationReason(reason);
        result.setCurrentAmount(amount);
        result.setApproverLimit(limit);
        result.setSkipCurrentApprover(false);
        return result;
    }

    public static EscalationCheckResultDTO skipApprover(String reason) {
        EscalationCheckResultDTO result = new EscalationCheckResultDTO();
        result.setEscalationRequired(false);
        result.setSkipCurrentApprover(true);
        result.setSkipReason(reason);
        return result;
    }

    // Alias methods for compatibility
    public String getReason() {
        return escalationReason;
    }

    public String getEscalateTo() {
        return escalationRoleName;
    }

    public void setReason(String reason) {
        this.escalationReason = reason;
    }

    public void setEscalateTo(String roleName) {
        this.escalationRoleName = roleName;
    }
}
