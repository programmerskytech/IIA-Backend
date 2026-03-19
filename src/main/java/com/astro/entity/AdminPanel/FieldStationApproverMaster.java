package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Field Station Approver Master
 * Manages Engineer In-Charge and Professor In-Charge for non-Bangalore locations.
 *
 * Note: "Field Station In-Charge" role is renamed to "Engineer In-Charge".
 * "Professor In-Charge" is a new role with the same access and approval responsibilities.
 */
@Entity
@Table(name = "field_station_approver_master")
@Data
public class FieldStationApproverMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_station_name", nullable = false, length = 100)
    private String fieldStationName;

    // Type of in-charge: ENGINEER_INCHARGE or PROFESSOR_INCHARGE
    @Column(name = "incharge_type", length = 50)
    private String inchargeType = "ENGINEER_INCHARGE";

    @Column(name = "incharge_employee_id", length = 50)
    private String inchargeEmployeeId;

    @Column(name = "incharge_employee_name", length = 200)
    private String inchargeEmployeeName;

    @Column(name = "incharge_role_id")
    private Integer inchargeRoleId;

    @Column(name = "approval_limit", precision = 15, scale = 2)
    private BigDecimal approvalLimit;

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
     * Check if amount exceeds this in-charge's limit
     */
    public boolean exceedsLimit(BigDecimal amount) {
        if (amount == null || approvalLimit == null) return false;
        return amount.compareTo(approvalLimit) > 0;
    }

    /**
     * Check if this is an Engineer In-Charge
     */
    public boolean isEngineerInCharge() {
        return "ENGINEER_INCHARGE".equalsIgnoreCase(inchargeType);
    }

    /**
     * Check if this is a Professor In-Charge
     */
    public boolean isProfessorInCharge() {
        return "PROFESSOR_INCHARGE".equalsIgnoreCase(inchargeType);
    }

    /**
     * Get the role name for this in-charge type
     */
    public String getRoleName() {
        if (isProfessorInCharge()) {
            return "Professor In-Charge";
        }
        return "Engineer In-Charge";
    }
}
