package com.astro.service;

import com.astro.entity.AdminPanel.ApproverMaster;
import com.astro.entity.AdminPanel.WorkflowBranchMaster;

import java.util.List;
import java.util.Map;

/**
 * Service for branch-based workflow routing
 * This service handles matching workflow branches based on conditions
 * and determining the next approver in the sequential approval chain
 */
public interface BranchWorkflowService {

    /**
     * Find matching branch for a workflow based on conditions
     * @param workflowId Workflow ID
     * @param conditions Map of condition values (e.g., "totalAmount", "category", "location")
     * @return Matching WorkflowBranchMaster or null if no match
     */
    WorkflowBranchMaster findMatchingBranch(Integer workflowId, Map<String, Object> conditions);

    /**
     * Get all approvers for a branch in sequential order
     * @param branchId Branch ID
     * @return List of ApproverMaster ordered by level and sequence
     */
    List<ApproverMaster> getApproversForBranch(Long branchId);

    /**
     * Get the first approver for a branch
     * @param branchId Branch ID
     * @return First ApproverMaster or null
     */
    ApproverMaster getFirstApprover(Long branchId);

    /**
     * Get the next approver after current one
     * @param branchId Branch ID
     * @param currentApprovalLevel Current approval level
     * @param currentApprovalSequence Current approval sequence
     * @return Next ApproverMaster or null if no more approvers
     */
    ApproverMaster getNextApprover(Long branchId, Integer currentApprovalLevel, Integer currentApprovalSequence);

    /**
     * Check if branch condition matches given values
     * @param branch WorkflowBranchMaster with condition configuration
     * @param conditions Map of actual values to match against
     * @return true if branch matches
     */
    boolean matchesBranchCondition(WorkflowBranchMaster branch, Map<String, Object> conditions);

    /**
     * Build conditions map for indent workflow
     * @param requestId Indent ID
     * @return Map of conditions extracted from indent
     */
    Map<String, Object> buildIndentConditions(String requestId);

    /**
     * Build conditions map for tender workflow
     * @param requestId Tender ID
     * @return Map of conditions extracted from tender
     */
    Map<String, Object> buildTenderConditions(String requestId);

    /**
     * Build conditions map for PO workflow
     * @param requestId PO ID
     * @return Map of conditions extracted from PO
     */
    Map<String, Object> buildPOConditions(String requestId);
}
