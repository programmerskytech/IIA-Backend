package com.astro.dto.workflow;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class IndentWorkflowStatusDTO {

    private String indentId;
    private Integer indentNumber;
    private BigDecimal totalIndentValue;
    private String category;
    private String location;
    private Boolean isUnderProject;
    private String projectCode;
    private String projectName;
    private String indentorDepartment;
    private String modeOfProcurement;

    // Current workflow state
    private WorkflowBranchInfo currentBranch;
    private CurrentApproverInfo currentApprover;
    private NextApproverInfo nextApprover;

    // Status info
    private String currentStatus;
    private String currentStage;
    private Integer approvalLevel;
    private Boolean escalatedToDirector;
    private String escalationReason;

    // History
    private List<ApprovalHistoryItem> approvalHistory;

    @Data
    public static class WorkflowBranchInfo {
        private Long branchId;
        private String branchCode;
        private String branchName;
        private String conditionType;
    }

    @Data
    public static class CurrentApproverInfo {
        private Long approverId;
        private String role;
        private String name;
        private Integer level;
        private Integer sequence;
        private BigDecimal approvalLimit;
        private Boolean canApprove;
        private String limitExceededMessage;
    }

    @Data
    public static class NextApproverInfo {
        private Long approverId;
        private String role;
        private String name;
        private Integer level;
        private Boolean willBeSkipped;
        private String skipReason;
    }

    @Data
    public static class ApprovalHistoryItem {
        private Integer level;
        private String role;
        private String approverName;
        private String action;
        private String remarks;
        private LocalDateTime timestamp;
    }
}
