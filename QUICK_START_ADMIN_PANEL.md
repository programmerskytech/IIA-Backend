# 🚀 ADMIN PANEL - QUICK START GUIDE

## ⚡ FASTEST IMPLEMENTATION PATH

Follow these steps exactly in order. Total time: **~2 hours**

---

## STEP 1: Run Database Migration (5 minutes)

```bash
# Navigate to your project root
cd "e:\Work 2.0\IIA\Backend-prod"

# Run the SQL migration
mysql -u root -p iia_database < database-migrations/001_admin_panel_schema.sql
```

**✅ Verify:** Check that 8 new tables are created

---

## STEP 2: Add New Entity Classes (20 minutes)

Create folder: `src/main/java/com/astro/entity/AdminPanel/`

Copy these 8 files from `COMPLETE_CODE_PACKAGE.md`:
1. BudgetMaster.java
2. BudgetCategoryMaster.java
3. FormMaster.java
4. DesignatorMaster.java
5. LOVMaster.java
6. WorkflowBranchMaster.java
7. ApproverMaster.java
8. AdminAuditLog.java

---

## STEP 3: Update Existing Entities (10 minutes)

### Update `EmployeeDepartmentMaster.java`

**ADD these fields after line 21:**

```java
// Split name fields
@Column(name = "first_name", length = 100)
private String firstName;

@Column(name = "last_name", length = 100)
private String lastName;

// Split address fields
@Column(name = "street_address", length = 255)
private String streetAddress;

@Column(name = "city", length = 100)
private String city;

@Column(name = "state", length = 100)
private String state;

@Column(name = "zip_code", length = 20)
private String zipCode;

// Additional employment fields
@Column(name = "date_of_birth")
private LocalDate dateOfBirth;

@Column(name = "manager", length = 100)
private String manager;

@Column(name = "employment_type", length = 50)
private String employmentType; // Full-time, Part-time, Contract

@Column(name = "hire_date")
private LocalDate hireDate;

@Column(name = "end_date")
private LocalDate endDate; // Resignation/Termination date (optional)
```

### Update `ProjectMaster.java`

**ADD these fields after line 44:**

```java
@Column(name = "status", length = 50)
private String status = "Active"; // Active, Completed, Closed

@Column(name = "category", length = 100)
private String category;
```

---

## STEP 4: Update WorkflowName Enum (2 minutes)

**File:** `src/main/java/com/astro/constant/WorkflowName.java`

**ADD this line after line 11:**

```java
PAYMENT_VOUCHER("Payment Voucher Workflow", "PAYMENT VOUCHER WORKFLOW"),
```

---

## STEP 5: Test Compilation (2 minutes)

```bash
mvn clean compile
```

**If errors:** Check import statements, ensure all fields are properly typed

---

## STEP 6: Create Minimal Controllers (30 minutes)

I'll create simplified, working controllers that handle 80% of use cases.

### Create: `src/main/java/com/astro/controller/AdminPanel/BudgetController.java`

```java
package com.astro.controller.AdminPanel;

import com.astro.entity.AdminPanel.BudgetMaster;
import com.astro.repository.AdminPanel.BudgetMasterRepository;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/budget")
public class BudgetController {

    @Autowired
    private BudgetMasterRepository budgetRepository;

    @PostMapping
    public ResponseEntity<Object> createBudget(@RequestBody BudgetMaster budget) {
        BudgetMaster saved = budgetRepository.save(budget);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBudgets() {
        List<BudgetMaster> budgets = budgetRepository.findAll();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budgets), HttpStatus.OK);
    }

    @GetMapping("/{budgetCode}")
    public ResponseEntity<Object> getBudgetByCode(@PathVariable String budgetCode) {
        BudgetMaster budget = budgetRepository.findByBudgetCode(budgetCode)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budget), HttpStatus.OK);
    }

    @PutMapping("/{budgetCode}")
    public ResponseEntity<Object> updateBudget(@PathVariable String budgetCode, @RequestBody BudgetMaster budget) {
        BudgetMaster existing = budgetRepository.findByBudgetCode(budgetCode)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        existing.setBudgetName(budget.getBudgetName());
        existing.setCategory(budget.getCategory());
        existing.setAllocatedAmount(budget.getAllocatedAmount());
        existing.setStatus(budget.getStatus());
        existing.setUpdatedBy(budget.getUpdatedBy());

        BudgetMaster saved = budgetRepository.save(existing);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    @DeleteMapping("/{budgetCode}")
    public ResponseEntity<Object> deleteBudget(@PathVariable String budgetCode) {
        BudgetMaster budget = budgetRepository.findByBudgetCode(budgetCode)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Budget deleted"), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getBudgetSummary() {
        List<BudgetMaster> budgets = budgetRepository.findAll();

        BigDecimal totalAllocated = budgets.stream()
                .map(BudgetMaster::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSpent = budgets.stream()
                .map(BudgetMaster::getSpentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRemaining = budgets.stream()
                .map(BudgetMaster::getRemainingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalAllocated", totalAllocated);
        summary.put("totalSpent", totalSpent);
        summary.put("totalRemaining", totalRemaining);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(summary), HttpStatus.OK);
    }
}
```

### Create Repositories (5 minutes each)

Create folder: `src/main/java/com/astro/repository/AdminPanel/`

**File: Budget MasterRepository.java**

```java
package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.BudgetMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetMasterRepository extends JpaRepository<BudgetMaster, Long> {
    Optional<BudgetMaster> findByBudgetCode(String budgetCode);
    List<BudgetMaster> findByStatus(String status);
    List<BudgetMaster> findByFiscalYear(String fiscalYear);
    List<BudgetMaster> findByCategory(String category);
}
```

**File: FormMasterRepository.java**

```java
package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.FormMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormMasterRepository extends JpaRepository<FormMaster, Long> {
    Optional<FormMaster> findByFormName(String formName);
    List<FormMaster> findByIsActiveTrue();
    List<FormMaster> findByModuleName(String moduleName);
}
```

**File: DesignatorMasterRepository.java**

```java
package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.DesignatorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignatorMasterRepository extends JpaRepository<DesignatorMaster, Long> {
    List<DesignatorMaster> findByFormId(Long formId);
    List<DesignatorMaster> findByFormIdAndIsActiveTrue(Long formId);
}
```

**File: LOVMasterRepository.java**

```java
package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.LOVMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LOVMasterRepository extends JpaRepository<LOVMaster, Long> {
    List<LOVMaster> findByDesignatorId(Long designatorId);
    List<LOVMaster> findByDesignatorIdAndIsActiveTrue(Long designatorId);
    List<LOVMaster> findByDesignatorIdOrderByDisplayOrderAsc(Long designatorId);
}
```

**File: WorkflowBranchMasterRepository.java**

```java
package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.WorkflowBranchMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowBranchMasterRepository extends JpaRepository<WorkflowBranchMaster, Long> {
    List<WorkflowBranchMaster> findByWorkflowId(Integer workflowId);
    List<WorkflowBranchMaster> findByWorkflowIdAndIsActiveTrue(Integer workflowId);
    Optional<WorkflowBranchMaster> findByWorkflowIdAndBranchCode(Integer workflowId, String branchCode);
}
```

**File: ApproverMasterRepository.java**

```java
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
}
```

---

## STEP 7: Create Remaining Simple Controllers (15 minutes each)

### File: `LOVController.java`

```java
package com.astro.controller.AdminPanel;

import com.astro.entity.AdminPanel.*;
import com.astro.repository.AdminPanel.*;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/lov")
public class LOVController {

    @Autowired
    private FormMasterRepository formRepository;

    @Autowired
    private DesignatorMasterRepository designatorRepository;

    @Autowired
    private LOVMasterRepository lovRepository;

    // Get all forms
    @GetMapping("/forms")
    public ResponseEntity<Object> getAllForms() {
        List<FormMaster> forms = formRepository.findByIsActiveTrue();
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(forms), HttpStatus.OK);
    }

    // Get designators for a form
    @GetMapping("/forms/{formId}/designators")
    public ResponseEntity<Object> getDesignators(@PathVariable Long formId) {
        List<DesignatorMaster> designators = designatorRepository.findByFormIdAndIsActiveTrue(formId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(designators), HttpStatus.OK);
    }

    // Get LOV values for a designator
    @GetMapping("/designators/{designatorId}/values")
    public ResponseEntity<Object> getLOVValues(@PathVariable Long designatorId) {
        List<LOVMaster> values = lovRepository.findByDesignatorIdOrderByDisplayOrderAsc(designatorId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(values), HttpStatus.OK);
    }

    // Add new LOV value
    @PostMapping("/values")
    public ResponseEntity<Object> addLOVValue(@RequestBody LOVMaster lov) {
        LOVMaster saved = lovRepository.save(lov);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    // Update LOV value
    @PutMapping("/values/{lovId}")
    public ResponseEntity<Object> updateLOVValue(@PathVariable Long lovId, @RequestBody LOVMaster lov) {
        LOVMaster existing = lovRepository.findById(lovId)
                .orElseThrow(() -> new RuntimeException("LOV not found"));

        existing.setLovDisplayValue(lov.getLovDisplayValue());
        existing.setColorCode(lov.getColorCode());
        existing.setDisplayOrder(lov.getDisplayOrder());
        existing.setIsActive(lov.getIsActive());
        existing.setUpdatedBy(lov.getUpdatedBy());

        LOVMaster saved = lovRepository.save(existing);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    // Delete LOV value
    @DeleteMapping("/values/{lovId}")
    public ResponseEntity<Object> deleteLOVValue(@PathVariable Long lovId) {
        LOVMaster lov = lovRepository.findById(lovId)
                .orElseThrow(() -> new RuntimeException("LOV not found"));
        lovRepository.delete(lov);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("LOV deleted"), HttpStatus.OK);
    }

    // Get LOVs by form name and field name (convenience method)
    @GetMapping("/forms/{formName}/field/{fieldName}/values")
    public ResponseEntity<Object> getLOVsByFormAndField(@PathVariable String formName, @PathVariable String fieldName) {
        FormMaster form = formRepository.findByFormName(formName)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        List<DesignatorMaster> designators = designatorRepository.findByFormId(form.getFormId());
        Optional<DesignatorMaster> designator = designators.stream()
                .filter(d -> d.getDesignatorName().equalsIgnoreCase(fieldName))
                .findFirst();

        if (!designator.isPresent()) {
            return new ResponseEntity<>(ResponseBuilder.getErrorResponse("Designator not found"), HttpStatus.NOT_FOUND);
        }

        List<LOVMaster> values = lovRepository.findByDesignatorIdAndIsActiveTrue(designator.get().getDesignatorId());
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(values), HttpStatus.OK);
    }
}
```

### File: `ApproverController.java`

```java
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

    // Get approvers by workflow and branch
    @GetMapping("/{workflowId}/{branchId}")
    public ResponseEntity<Object> getApproversByWorkflowAndBranch(
            @PathVariable Integer workflowId,
            @PathVariable Long branchId) {
        List<ApproverMaster> approvers = approverRepository
                .findByWorkflowIdAndBranchIdOrderByApprovalLevelAscApprovalSequenceAsc(workflowId, branchId);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(approvers), HttpStatus.OK);
    }

    // Add approver
    @PostMapping
    public ResponseEntity<Object> addApprover(@RequestBody ApproverMaster approver) {
        // Auto-generate approver code: W{workflow_id}-B{branch_id}-{sequence}
        String code = String.format("W%d-B%d-%03d",
                approver.getWorkflowId(),
                approver.getBranchId(),
                approver.getApprovalSequence());
        approver.setApproverCode(code);

        ApproverMaster saved = approverRepository.save(approver);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
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
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Approver deleted"), HttpStatus.OK);
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
}
```

---

## STEP 8: Run and Test (10 minutes)

```bash
mvn spring-boot:run
```

**Test with Postman/Curl:**

```bash
# Test Budget API
curl -X GET http://localhost:8080/api/admin/budget

# Test LOV API
curl -X GET http://localhost:8080/api/admin/lov/forms

# Test Approvers API
curl -X GET http://localhost:8080/api/admin/approvers
```

---

## ✅ VERIFICATION CHECKLIST

- [ ] Database tables created
- [ ] Application starts without errors
- [ ] Budget API returns empty array
- [ ] LOV API returns default forms
- [ ] Can create a budget via POST
- [ ] Can retrieve budget via GET
- [ ] Can update budget via PUT
- [ ] Can delete budget via DELETE

---

## 🎯 NEXT STEP: FRONTEND INTEGRATION

Once backend is working, use the Frontend Prompt file I'll create next to share with your frontend developer.

---

**Total Implementation Time: ~2 hours**
**Lines of Code Added: ~1500**
**New Tables: 8**
**New APIs: 40+**

