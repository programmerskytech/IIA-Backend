package com.astro.dto.AdminPanel;

import com.astro.entity.AdminPanel.ApproverMaster;
import com.astro.entity.AdminPanel.WorkflowBranchMaster;
import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class WorkflowFullConfigDTO {

    private Integer workflowId;
    private String workflowName;
    private List<BranchConfigDTO> branches = new ArrayList<>();
    private Map<Long, List<ApproverMaster>> branchApproversMap = new HashMap<>();
    private List<ApprovalLimitDTO> approvalLimits;
    private List<DepartmentApproverMappingDTO> departmentApproverMappings;
    private List<FieldStationApproverDTO> fieldStationApprovers;

    /**
     * Set branches from WorkflowBranchMaster list
     */
    public void setBranches(List<WorkflowBranchMaster> branchMasters) {
        this.branches = branchMasters.stream()
                .map(branch -> BranchConfigDTO.fromEntity(branch, null))
                .toList();
    }

    /**
     * Add approvers for a specific branch
     */
    public void addBranchApprovers(Long branchId, List<ApproverMaster> approvers) {
        this.branchApproversMap.put(branchId, approvers);

        // Also update the branch's approvers list
        for (BranchConfigDTO branch : this.branches) {
            if (branch.getBranchId().equals(branchId)) {
                branch.setApprovers(approvers.stream()
                        .map(ApproverConfigDTO::fromEntity)
                        .toList());
                break;
            }
        }
    }

    @Data
    public static class BranchConfigDTO {
        private Long branchId;
        private String branchCode;
        private String branchName;
        private String branchDescription;
        private String conditionType;
        private String conditionConfig;
        private Map<String, Object> conditionConfigParsed;
        private String conditionLogic;
        private Boolean requiresBudgetCheck;
        private String budgetCheckConfig;
        private Integer displayOrder;
        private Boolean isActive;
        private List<ApproverConfigDTO> approvers;

        public static BranchConfigDTO fromEntity(WorkflowBranchMaster branch, List<ApproverMaster> approvers) {
            BranchConfigDTO dto = new BranchConfigDTO();
            dto.setBranchId(branch.getBranchId());
            dto.setBranchCode(branch.getBranchCode());
            dto.setBranchName(branch.getBranchName());
            dto.setBranchDescription(branch.getBranchDescription());
            dto.setConditionType(branch.getConditionType());
            dto.setConditionConfig(branch.getConditionConfig());
            dto.setConditionLogic(branch.getConditionLogic());
            dto.setRequiresBudgetCheck(branch.getRequiresBudgetCheck());
            dto.setBudgetCheckConfig(branch.getBudgetCheckConfig());
            dto.setDisplayOrder(branch.getDisplayOrder());
            dto.setIsActive(branch.getIsActive());

            if (approvers != null) {
                dto.setApprovers(approvers.stream()
                        .map(ApproverConfigDTO::fromEntity)
                        .toList());
            }
            return dto;
        }
    }

    @Data
    public static class ApproverConfigDTO {
        private Long approverId;
        private String approverCode;
        private Integer roleId;
        private String roleName;
        private Integer approvalLevel;
        private Integer approvalSequence;
        private Boolean isParallelApproval;
        private Boolean isMandatory;
        private String conditionCheckType;
        private String limitCheckConfig;
        private String skipIfCondition;
        private String escalateIfCondition;
        private Long escalationApproverId;
        private String status;

        public static ApproverConfigDTO fromEntity(ApproverMaster approver) {
            ApproverConfigDTO dto = new ApproverConfigDTO();
            dto.setApproverId(approver.getApproverId());
            dto.setApproverCode(approver.getApproverCode());
            dto.setRoleId(approver.getRoleId());
            dto.setRoleName(approver.getRoleName());
            dto.setApprovalLevel(approver.getApprovalLevel());
            dto.setApprovalSequence(approver.getApprovalSequence());
            dto.setIsParallelApproval(approver.getIsParallelApproval());
            dto.setIsMandatory(approver.getIsMandatory());
            dto.setConditionCheckType(approver.getConditionCheckType());
            dto.setLimitCheckConfig(approver.getLimitCheckConfig());
            dto.setSkipIfCondition(approver.getSkipIfCondition());
            dto.setEscalateIfCondition(approver.getEscalateIfCondition());
            dto.setEscalationApproverId(approver.getEscalationApproverId());
            dto.setStatus(approver.getStatus());
            return dto;
        }
    }
}
