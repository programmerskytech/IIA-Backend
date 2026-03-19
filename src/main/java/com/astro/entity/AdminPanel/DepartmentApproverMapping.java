package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "department_approver_mapping",
       uniqueConstraints = @UniqueConstraint(columnNames = {"department_name", "approver_type"}))
@Data
public class DepartmentApproverMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long mappingId;

    @Column(name = "department_name", nullable = false, length = 100)
    private String departmentName;

    @Column(name = "approver_type", nullable = false, length = 50)
    private String approverType; // DEAN, HEAD_SEG

    @Column(name = "approver_employee_id", length = 50)
    private String approverEmployeeId;

    @Column(name = "approver_role_id")
    private Integer approverRoleId;

    @Column(name = "approval_limit", precision = 15, scale = 2)
    private BigDecimal approvalLimit; // Dean: 1,50,000, Head SEG: 1,00,000

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    /**
     * Check if amount exceeds this approver's limit
     */
    public boolean exceedsLimit(BigDecimal amount) {
        if (amount == null || approvalLimit == null) return false;
        return amount.compareTo(approvalLimit) > 0;
    }

    /**
     * Check if this is a Dean approver type
     */
    public boolean isDean() {
        return "DEAN".equalsIgnoreCase(approverType);
    }

    /**
     * Check if this is a Head SEG approver type
     */
    public boolean isHeadSEG() {
        return "HEAD_SEG".equalsIgnoreCase(approverType);
    }
}
