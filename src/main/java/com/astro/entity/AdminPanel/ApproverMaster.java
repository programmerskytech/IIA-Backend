package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "approver_master")
@Data
public class ApproverMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "approver_id")
    private Long approverId;

    @Column(name = "approver_code", nullable = false, unique = true, length = 50)
    private String approverCode; // Auto-generated: W{workflow_id}-B{branch_id}-{sequence}

    @Column(name = "workflow_id", nullable = false)
    private Integer workflowId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    // Role-based approver
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    // Approval hierarchy
    @Column(name = "approval_level", nullable = false)
    private Integer approvalLevel = 1;

    @Column(name = "approval_sequence", nullable = false)
    private Integer approvalSequence = 1;

    // Approval logic
    @Column(name = "is_parallel_approval")
    private Boolean isParallelApproval = false; // OR logic if true

    @Column(name = "is_mandatory")
    private Boolean isMandatory = true;

    // Status
    @Column(name = "status", length = 50)
    private String status = "Active"; // Active, Inactive

    // Audit
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
