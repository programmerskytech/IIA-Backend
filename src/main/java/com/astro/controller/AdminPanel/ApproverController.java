package com.astro.controller.AdminPanel;

import com.astro.entity.AdminPanel.ApproverMaster;
import com.astro.entity.AdminPanel.WorkflowBranchMaster;
import com.astro.repository.AdminPanel.ApproverMasterRepository;
import com.astro.repository.AdminPanel.WorkflowBranchMasterRepository;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/approvers")
@CrossOrigin
public class ApproverController {

    @Autowired
    private ApproverMasterRepository approverRepository;

    @Autowired
    private WorkflowBranchMasterRepository branchRepository;

    // Get all approvers
    @GetMapping
    public ResponseEntity<Object> getAllApprovers() {
        List<ApproverMaster> approvers = approverRepository.findAll();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    // Get approver by ID
    @GetMapping("/{approverId}")
    public ResponseEntity<Object> getApproverById(@PathVariable Long approverId) {
        ApproverMaster approver = approverRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approver), HttpStatus.OK);
    }

    // Get approvers by workflow and branch
    @GetMapping("/workflow/{workflowId}/branch/{branchId}")
    public ResponseEntity<Object> getApproversByWorkflowAndBranch(
            @PathVariable Integer workflowId,
            @PathVariable Long branchId) {
        List<ApproverMaster> approvers = approverRepository
                .findByWorkflowIdAndBranchIdOrderByApprovalLevelAscApprovalSequenceAsc(workflowId, branchId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    // Get active approvers by workflow and branch
    @GetMapping("/workflow/{workflowId}/branch/{branchId}/active")
    public ResponseEntity<Object> getActiveApproversByWorkflowAndBranch(
            @PathVariable Integer workflowId,
            @PathVariable Long branchId) {
        List<ApproverMaster> approvers = approverRepository
                .findByWorkflowIdAndBranchIdAndStatus(workflowId, branchId, "Active");
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    // Add approver
    @PostMapping
    public ResponseEntity<Object> addApprover(@RequestBody ApproverMaster approver) {
        try {
            // Auto-generate approver code: W{workflow_id}-B{branch_id}-{sequence}
            String code = String.format("W%d-B%d-%03d",
                    approver.getWorkflowId(),
                    approver.getBranchId(),
                    approver.getApprovalSequence());
            approver.setApproverCode(code);

            // Check if approver code already exists
            List<ApproverMaster> existing = approverRepository
                    .findByWorkflowIdAndBranchIdOrderByApprovalLevelAscApprovalSequenceAsc(
                            approver.getWorkflowId(),
                            approver.getBranchId());

            boolean codeExists = existing.stream()
                    .anyMatch(a -> a.getApproverCode().equals(code));

            if (codeExists) {
                throw new RuntimeException("Approver with sequence " + approver.getApprovalSequence() +
                        " already exists for this workflow and branch. Please use a different sequence number.");
            }

            ApproverMaster saved = approverRepository.save(approver);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = e.getMessage();
            if (errorMsg != null && errorMsg.contains("Duplicate entry")) {
                errorMsg = "Approver with this sequence already exists. Please use a different sequence number.";
            }
            throw new RuntimeException(errorMsg != null ? errorMsg : "Failed to create approver");
        }
    }

    // Update approver
    @PutMapping("/{approverId}")
    public ResponseEntity<Object> updateApprover(@PathVariable Long approverId, @RequestBody ApproverMaster approver) {
        ApproverMaster existing = approverRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        existing.setRoleId(approver.getRoleId());
        existing.setRoleName(approver.getRoleName());
        existing.setApprovalLevel(approver.getApprovalLevel());
        existing.setApprovalSequence(approver.getApprovalSequence());
        existing.setIsParallelApproval(approver.getIsParallelApproval());
        existing.setIsMandatory(approver.getIsMandatory());
        existing.setStatus(approver.getStatus());
        existing.setUpdatedBy(approver.getUpdatedBy());

        // Re-generate code if level/sequence changed
        String code = String.format("W%d-B%d-%03d",
                existing.getWorkflowId(),
                existing.getBranchId(),
                existing.getApprovalSequence());
        existing.setApproverCode(code);

        ApproverMaster saved = approverRepository.save(existing);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    // Delete approver
    @DeleteMapping("/{approverId}")
    public ResponseEntity<Object> deleteApprover(@PathVariable Long approverId) {
        ApproverMaster approver = approverRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));
        approverRepository.delete(approver);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Approver deleted successfully"), HttpStatus.OK);
    }

    // Activate/Deactivate approver
    @PutMapping("/{approverId}/status")
    public ResponseEntity<Object> updateApproverStatus(
            @PathVariable Long approverId,
            @RequestParam String status,
            @RequestParam String updatedBy) {
        ApproverMaster approver = approverRepository.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        approver.setStatus(status);
        approver.setUpdatedBy(updatedBy);

        ApproverMaster saved = approverRepository.save(approver);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    // Get workflow branches
    @GetMapping("/workflows/{workflowId}/branches")
    public ResponseEntity<Object> getWorkflowBranches(@PathVariable Integer workflowId) {
        List<WorkflowBranchMaster> branches = branchRepository.findByWorkflowIdAndIsActiveTrue(workflowId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(branches), HttpStatus.OK);
    }

    // Get all branches for a workflow
    @GetMapping("/workflows/{workflowId}/branches/all")
    public ResponseEntity<Object> getAllWorkflowBranches(@PathVariable Integer workflowId) {
        List<WorkflowBranchMaster> branches = branchRepository.findByWorkflowId(workflowId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(branches), HttpStatus.OK);
    }

    // Create workflow branch
    @PostMapping("/workflows/{workflowId}/branches")
    public ResponseEntity<Object> createWorkflowBranch(@PathVariable Integer workflowId, @RequestBody WorkflowBranchMaster branch) {
        try {
            branch.setWorkflowId(workflowId);

            // Validate conditionConfig is valid JSON or null
            String conditionConfig = branch.getConditionConfig();
            if (conditionConfig != null && !conditionConfig.trim().isEmpty()) {
                // Check if it's valid JSON
                if (!isValidJSON(conditionConfig)) {
                    // If not valid JSON and it's DEFAULT type, set to null
                    if ("DEFAULT".equalsIgnoreCase(branch.getConditionType())) {
                        branch.setConditionConfig(null);
                    } else {
                        throw new RuntimeException("Condition Config must be valid JSON format. Example: {\"minAmount\": 50000}");
                    }
                }
            } else if ("DEFAULT".equalsIgnoreCase(branch.getConditionType())) {
                // DEFAULT branches should have null config
                branch.setConditionConfig(null);
            }

            WorkflowBranchMaster saved = branchRepository.save(branch);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create branch: " + e.getMessage());
        }
    }

    // Helper method to validate JSON
    private boolean isValidJSON(String json) {
        if (json == null || json.trim().isEmpty()) {
            return true;
        }
        json = json.trim();
        return (json.startsWith("{") && json.endsWith("}")) ||
               (json.startsWith("[") && json.endsWith("]"));
    }

    // Update workflow branch
    @PutMapping("/branches/{branchId}")
    public ResponseEntity<Object> updateWorkflowBranch(@PathVariable Long branchId, @RequestBody WorkflowBranchMaster branch) {
        WorkflowBranchMaster existing = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        existing.setBranchCode(branch.getBranchCode());
        existing.setBranchName(branch.getBranchName());
        existing.setBranchDescription(branch.getBranchDescription());
        existing.setConditionType(branch.getConditionType());
        existing.setConditionConfig(branch.getConditionConfig());
        existing.setDisplayOrder(branch.getDisplayOrder());
        existing.setIsActive(branch.getIsActive());

        WorkflowBranchMaster saved = branchRepository.save(existing);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    // Delete workflow branch
    @DeleteMapping("/branches/{branchId}")
    public ResponseEntity<Object> deleteWorkflowBranch(@PathVariable Long branchId) {
        WorkflowBranchMaster branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        branchRepository.delete(branch);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Branch deleted successfully"), HttpStatus.OK);
    }
}
