# COMPLETE ADMIN PANEL CODE PACKAGE

This document contains ALL the code you need to implement. Copy each section to the appropriate file.

---

## 📦 PACKAGE 1: ENTITY CLASSES

### File: `src/main/java/com/astro/entity/AdminPanel/BudgetMaster.java`

```java
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
```

### File: `src/main/java/com/astro/entity/AdminPanel/BudgetCategoryMaster.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_category_master")
@Data
public class BudgetCategoryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
```

### File: `src/main/java/com/astro/entity/AdminPanel/FormMaster.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_master")
@Data
public class FormMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_id")
    private Long formId;

    @Column(name = "form_name", nullable = false, unique = true, length = 100)
    private String formName;

    @Column(name = "form_display_name", nullable = false, length = 200)
    private String formDisplayName;

    @Column(name = "form_description", columnDefinition = "TEXT")
    private String formDescription;

    @Column(name = "module_name", length = 100)
    private String moduleName;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
```

### File: `src/main/java/com/astro/entity/AdminPanel/DesignatorMaster.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "designator_master",
       uniqueConstraints = @UniqueConstraint(columnNames = {"form_id", "designator_name"}))
@Data
public class DesignatorMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designator_id")
    private Long designatorId;

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "designator_name", nullable = false, length = 100)
    private String designatorName;

    @Column(name = "designator_display_name", nullable = false, length = 200)
    private String designatorDisplayName;

    @Column(name = "designator_description", columnDefinition = "TEXT")
    private String designatorDescription;

    @Column(name = "data_type", length = 50)
    private String dataType = "STRING"; // STRING, NUMBER, DATE, BOOLEAN

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
```

### File: `src/main/java/com/astro/entity/AdminPanel/LOVMaster.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lov_master",
       uniqueConstraints = @UniqueConstraint(columnNames = {"designator_id", "lov_value"}))
@Data
public class LOVMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lov_id")
    private Long lovId;

    @Column(name = "designator_id", nullable = false)
    private Long designatorId;

    @Column(name = "lov_value", nullable = false, length = 200)
    private String lovValue;

    @Column(name = "lov_display_value", nullable = false, length = 200)
    private String lovDisplayValue;

    @Column(name = "lov_description", columnDefinition = "TEXT")
    private String lovDescription;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    // Optional attributes
    @Column(name = "color_code", length = 20)
    private String colorCode;

    @Column(name = "icon_name", length = 50)
    private String iconName;

    // Hierarchical support
    @Column(name = "parent_lov_id")
    private Long parentLovId;

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
```

### File: `src/main/java/com/astro/entity/AdminPanel/WorkflowBranchMaster.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_branch_master",
       uniqueConstraints = @UniqueConstraint(columnNames = {"workflow_id", "branch_code"}))
@Data
public class WorkflowBranchMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "workflow_id", nullable = false)
    private Integer workflowId;

    @Column(name = "branch_code", nullable = false, length = 50)
    private String branchCode;

    @Column(name = "branch_name", nullable = false, length = 200)
    private String branchName;

    @Column(name = "branch_description", columnDefinition = "TEXT")
    private String branchDescription;

    @Column(name = "condition_type", length = 50)
    private String conditionType; // CATEGORY, LOCATION, AMOUNT, CUSTOM

    @Column(name = "condition_config", columnDefinition = "JSON")
    private String conditionConfig; // JSON configuration

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
```

### File: `src/main/java/com/astro/entity/AdminPanel/ApproverMaster.java`

```java
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
```

### File: `src/main/java/com/astro/entity/AdminPanel/AdminAuditLog.java`

```java
package com.astro.entity.AdminPanel;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_audit_log")
@Data
public class AdminAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "entity_type", nullable = false, length = 100)
    private String entityType;

    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;

    @Column(name = "action", nullable = false, length = 50)
    private String action; // CREATE, UPDATE, DELETE, ACTIVATE, DEACTIVATE

    @Column(name = "old_value", columnDefinition = "JSON")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "JSON")
    private String newValue;

    @Column(name = "changed_by", nullable = false, length = 100)
    private String changedBy;

    @Column(name = "changed_date")
    private LocalDateTime changedDate = LocalDateTime.now();

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;
}
```

---

**Due to character limits, I'll continue with DTOs, Repositories, Services, and Controllers in the next message. The implementation guide above shows you the complete structure.**

**Would you like me to:**
1. ✅ Continue generating all remaining code files (DTOs, Repositories, Services, Controllers)
2. ✅ Create a simple ZIP/folder structure you can directly copy-paste
3. ✅ Generate the Frontend Integration Prompt with all API details

**I'll proceed with option 1 - generating all remaining code in organized chunks. Shall I continue?**
