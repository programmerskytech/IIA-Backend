package com.astro.service.impl;

import com.astro.dto.workflow.ProcurementDtos.IndentDto.IndentCreationResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.TenderWithIndentResponseDTO;
import com.astro.entity.AdminPanel.ApproverMaster;
import com.astro.entity.AdminPanel.WorkflowBranchMaster;
import com.astro.repository.AdminPanel.ApproverMasterRepository;
import com.astro.repository.AdminPanel.WorkflowBranchMasterRepository;
import com.astro.service.BranchWorkflowService;
import com.astro.service.IndentCreationService;
import com.astro.service.TenderRequestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BranchWorkflowServiceImpl implements BranchWorkflowService {

    @Autowired
    private WorkflowBranchMasterRepository branchRepository;

    @Autowired
    private ApproverMasterRepository approverRepository;

    @Autowired
    private IndentCreationService indentService;

    @Autowired
    private TenderRequestService tenderService;

    @Autowired
    private com.astro.repository.ProcurementModule.IndentCreation.IndentCreationRepository indentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WorkflowBranchMaster findMatchingBranch(Integer workflowId, Map<String, Object> conditions) {
        // Get all active branches for this workflow, ordered by display_order
        List<WorkflowBranchMaster> branches = branchRepository
                .findByWorkflowIdAndIsActiveTrue(workflowId)
                .stream()
                .sorted(Comparator.comparing(WorkflowBranchMaster::getDisplayOrder))
                .collect(Collectors.toList());

        // Find first matching branch
        for (WorkflowBranchMaster branch : branches) {
            if (matchesBranchCondition(branch, conditions)) {
                System.out.println("✅ Matched Branch: " + branch.getBranchCode() + " - " + branch.getBranchName());
                return branch;
            }
        }

        System.out.println("⚠️ No matching branch found for workflow " + workflowId);
        return null;
    }

    @Override
    public List<ApproverMaster> getApproversForBranch(Long branchId) {
        // Get all approvers for this branch by branchId only
        List<ApproverMaster> allApprovers = approverRepository.findAll();
        return allApprovers.stream()
                .filter(a -> a.getBranchId().equals(branchId) && "Active".equals(a.getStatus()))
                .sorted(Comparator
                        .comparing(ApproverMaster::getApprovalLevel)
                        .thenComparing(ApproverMaster::getApprovalSequence))
                .collect(Collectors.toList());
    }

    @Override
    public ApproverMaster getFirstApprover(Long branchId) {
        List<ApproverMaster> allApprovers = approverRepository.findAll();
        List<ApproverMaster> approvers = allApprovers.stream()
                .filter(a -> a.getBranchId().equals(branchId) && "Active".equals(a.getStatus()))
                .sorted(Comparator
                        .comparing(ApproverMaster::getApprovalLevel)
                        .thenComparing(ApproverMaster::getApprovalSequence))
                .collect(Collectors.toList());

        if (approvers.isEmpty()) {
            System.out.println("⚠️ No active approvers found for branch " + branchId);
            return null;
        }

        ApproverMaster firstApprover = approvers.get(0);
        System.out.println("✅ First Approver: " + firstApprover.getRoleName() +
                         " (Level: " + firstApprover.getApprovalLevel() +
                         ", Seq: " + firstApprover.getApprovalSequence() + ")");
        return firstApprover;
    }

    @Override
    public ApproverMaster getNextApprover(Long branchId, Integer currentApprovalLevel, Integer currentApprovalSequence) {
        List<ApproverMaster> allApprovers = approverRepository.findAll();
        List<ApproverMaster> branchApprovers = allApprovers.stream()
                .filter(a -> a.getBranchId().equals(branchId) && "Active".equals(a.getStatus()))
                .sorted(Comparator
                        .comparing(ApproverMaster::getApprovalLevel)
                        .thenComparing(ApproverMaster::getApprovalSequence))
                .collect(Collectors.toList());

        System.out.println("🔍 Finding next approver for branch " + branchId);
        System.out.println("   Current Level: " + currentApprovalLevel + ", Current Seq: " + currentApprovalSequence);
        System.out.println("   Total approvers in branch: " + branchApprovers.size());

        if (branchApprovers.isEmpty()) {
            System.out.println("⚠️ No approvers found for branch " + branchId);
            return null;
        }

        // Find next approver after current level and sequence
        for (ApproverMaster approver : branchApprovers) {
            System.out.println("   Checking approver: " + approver.getRoleName() +
                             " (Level: " + approver.getApprovalLevel() +
                             ", Seq: " + approver.getApprovalSequence() + ")");

            boolean isNextLevel = approver.getApprovalLevel() > currentApprovalLevel;
            boolean isSameLevelNextSequence =
                approver.getApprovalLevel().equals(currentApprovalLevel) &&
                approver.getApprovalSequence() > currentApprovalSequence;

            if (isNextLevel || isSameLevelNextSequence) {
                System.out.println("✅ Next Approver: " + approver.getRoleName() +
                                 " (Level: " + approver.getApprovalLevel() +
                                 ", Seq: " + approver.getApprovalSequence() + ")");
                return approver;
            }
        }

        System.out.println("✅ No more approvers - workflow complete for branch " + branchId);
        return null; // No more approvers - workflow complete
    }

    @Override
    public boolean matchesBranchCondition(WorkflowBranchMaster branch, Map<String, Object> conditions) {
        if (branch.getConditionConfig() == null || branch.getConditionConfig().trim().isEmpty()) {
            // No conditions means always match (default branch)
            return true;
        }

        try {
            // Parse JSON condition configuration
            Map<String, Object> branchConditions = objectMapper.readValue(
                    branch.getConditionConfig(),
                    new TypeReference<Map<String, Object>>() {}
            );

            System.out.println("🔍 Matching Branch: " + branch.getBranchCode());
            System.out.println("   Branch Conditions: " + branchConditions);
            System.out.println("   Actual Values: " + conditions);

            // Check amount range if specified
            if (branchConditions.containsKey("minAmount") || branchConditions.containsKey("maxAmount")) {
                if (!matchesAmountRange(branchConditions, conditions)) {
                    return false;
                }
            }

            // Check category if specified
            if (branchConditions.containsKey("category")) {
                if (!matchesCategory(branchConditions, conditions)) {
                    return false;
                }
            }

            // Check location if specified
            if (branchConditions.containsKey("location")) {
                if (!matchesLocation(branchConditions, conditions)) {
                    return false;
                }
            }

            // Check project if specified
            if (branchConditions.containsKey("projectName")) {
                if (!matchesProject(branchConditions, conditions)) {
                    return false;
                }
            }

            // All conditions matched
            System.out.println("✅ ALL CONDITIONS MATCHED for branch: " + branch.getBranchCode());
            return true;

        } catch (Exception e) {
            System.err.println("❌ Error parsing branch condition for " + branch.getBranchCode() + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean matchesAmountRange(Map<String, Object> branchConditions, Map<String, Object> actualConditions) {
        Object totalAmountObj = actualConditions.get("totalAmount");
        if (totalAmountObj == null) {
            return false;
        }

        BigDecimal totalAmount = convertToBigDecimal(totalAmountObj);
        if (totalAmount == null) {
            return false;
        }

        // Check min amount
        if (branchConditions.containsKey("minAmount")) {
            BigDecimal minAmount = convertToBigDecimal(branchConditions.get("minAmount"));
            if (minAmount != null && totalAmount.compareTo(minAmount) < 0) {
                System.out.println("   ❌ Amount " + totalAmount + " is less than min " + minAmount);
                return false;
            }
        }

        // Check max amount
        if (branchConditions.containsKey("maxAmount")) {
            BigDecimal maxAmount = convertToBigDecimal(branchConditions.get("maxAmount"));
            if (maxAmount != null && totalAmount.compareTo(maxAmount) > 0) {
                System.out.println("   ❌ Amount " + totalAmount + " exceeds max " + maxAmount);
                return false;
            }
        }

        System.out.println("   ✅ Amount " + totalAmount + " matches range");
        return true;
    }

    private boolean matchesCategory(Map<String, Object> branchConditions, Map<String, Object> actualConditions) {
        String branchCategory = String.valueOf(branchConditions.get("category"));
        String actualCategory = String.valueOf(actualConditions.get("category"));

        if (actualCategory == null || "null".equals(actualCategory)) {
            return false;
        }

        boolean matches = branchCategory.equalsIgnoreCase(actualCategory);
        System.out.println("   " + (matches ? "✅" : "❌") + " Category: " + actualCategory +
                         " vs " + branchCategory);
        return matches;
    }

    private boolean matchesLocation(Map<String, Object> branchConditions, Map<String, Object> actualConditions) {
        String branchLocation = String.valueOf(branchConditions.get("location"));
        String actualLocation = String.valueOf(actualConditions.get("location"));

        if (actualLocation == null || "null".equals(actualLocation) || actualLocation.trim().isEmpty()) {
            System.out.println("   ❌ Location: actualLocation is null or empty");
            return false;
        }

        // Trim and compare
        boolean matches = branchLocation.trim().equalsIgnoreCase(actualLocation.trim());
        System.out.println("   " + (matches ? "✅" : "❌") + " Location: '" + actualLocation.trim() +
                         "' vs '" + branchLocation.trim() + "'");
        return matches;
    }

    private boolean matchesProject(Map<String, Object> branchConditions, Map<String, Object> actualConditions) {
        String branchProject = String.valueOf(branchConditions.get("projectName"));
        String actualProject = String.valueOf(actualConditions.get("projectName"));

        if (actualProject == null || "null".equals(actualProject)) {
            return false;
        }

        boolean matches = branchProject.equalsIgnoreCase(actualProject);
        System.out.println("   " + (matches ? "✅" : "❌") + " Project: " + actualProject +
                         " vs " + branchProject);
        return matches;
    }

    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof Number) {
                return BigDecimal.valueOf(((Number) value).doubleValue());
            } else {
                return new BigDecimal(value.toString());
            }
        } catch (Exception e) {
            System.err.println("❌ Cannot convert to BigDecimal: " + value);
            return null;
        }
    }

    @Override
    public Map<String, Object> buildIndentConditions(String requestId) {
        Map<String, Object> conditions = new HashMap<>();

        if (requestId == null || requestId.trim().isEmpty()) {
            System.err.println("❌ RequestId is null or empty!");
            return conditions;
        }

        try {
            // Use repository directly to avoid transaction issues
            com.astro.entity.ProcurementModule.IndentCreation indent = indentRepository.findById(requestId).orElse(null);

            if (indent == null) {
                System.err.println("❌ Indent not found: " + requestId);
                return conditions;
            }

            conditions.put("totalAmount", indent.getTotalIntentValue());
            conditions.put("category", indent.getMaterialCategoryType());
            conditions.put("location", indent.getConsignesLocation());
            conditions.put("projectName", indent.getProjectName());

            System.out.println("📋 Indent Conditions Built for " + requestId + ": " + conditions);
            System.out.println("   Total Amount: " + indent.getTotalIntentValue());
            System.out.println("   Category: " + indent.getMaterialCategoryType());
            System.out.println("   Location: " + indent.getConsignesLocation());
            System.out.println("   Project: " + indent.getProjectName());

        } catch (Exception e) {
            System.err.println("❌ Error building indent conditions for " + requestId + ": " + e.getMessage());
            e.printStackTrace();
        }

        return conditions;
    }

    @Override
    public Map<String, Object> buildTenderConditions(String requestId) {
        Map<String, Object> conditions = new HashMap<>();

        try {
            TenderWithIndentResponseDTO tender = tenderService.getTenderRequestById(requestId);

            conditions.put("totalAmount", tender.getTotalTenderValue());

            // Extract project name and location from first indent if available
            if (tender.getIndentResponseDTO() != null && !tender.getIndentResponseDTO().isEmpty()) {
                String projectName = tender.getIndentResponseDTO().get(0).getProjectName();
                conditions.put("projectName", projectName);

                String location = tender.getIndentResponseDTO().get(0).getConsignesLocation();
                conditions.put("location", location);
            }

            System.out.println("📋 Tender Conditions Built: " + conditions);

        } catch (Exception e) {
            System.err.println("❌ Error building tender conditions: " + e.getMessage());
        }

        return conditions;
    }

    @Override
    public Map<String, Object> buildPOConditions(String requestId) {
        Map<String, Object> conditions = new HashMap<>();

        // PO conditions will be based on tender/indent data
        // This can be implemented when PO workflow uses branches
        System.out.println("📋 PO Conditions Built: " + conditions);

        return conditions;
    }
}
