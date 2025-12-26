package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.ApproverMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApproverMasterRepository extends JpaRepository<ApproverMaster, Long> {
    List<ApproverMaster> findByWorkflowIdAndBranchId(Integer workflowId, Long branchId);
    List<ApproverMaster> findByWorkflowIdAndBranchIdAndStatus(Integer workflowId, Long branchId, String status);
    Optional<ApproverMaster> findByApproverCode(String approverCode);
    List<ApproverMaster> findByWorkflowIdAndBranchIdOrderByApprovalLevelAscApprovalSequenceAsc(Integer workflowId, Long branchId);
    List<ApproverMaster> findByWorkflowId(Integer workflowId);
    List<ApproverMaster> findByRoleId(Integer roleId);
}
