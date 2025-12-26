package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_master")
@Data
public class BudgetMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetId;

    @Column(name = "budget_code", nullable = false, unique = true, length = 50)
    private String budgetCode;

    @Column(name = "budget_name", nullable = false, length = 200)
    private String budgetName;

    @Column(name = "category", length = 100)
    private String category;

    // Financial fields
    @Column(name = "allocated_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal allocatedAmount = BigDecimal.ZERO;

    @Column(name = "on_hold_amount", precision = 15, scale = 2)
    private BigDecimal onHoldAmount = BigDecimal.ZERO;

    @Column(name = "spent_amount", precision = 15, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;

    // Remaining amount is calculated
    @Transient
    public BigDecimal getRemainingAmount() {
        return allocatedAmount
                .subtract(onHoldAmount != null ? onHoldAmount : BigDecimal.ZERO)
                .subtract(spentAmount != null ? spentAmount : BigDecimal.ZERO);
    }

    // Period
    @Column(name = "fiscal_year", nullable = false, length = 10)
    private String fiscalYear;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Status
    @Column(name = "status", length = 50)
    private String status = "Active"; // Active, Closed, Exhausted

    // Optional project link
    @Column(name = "project_code", length = 50)
    private String projectCode;

    @Column(name = "department_name", length = 100)
    private String departmentName;

    // Audit fields
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
