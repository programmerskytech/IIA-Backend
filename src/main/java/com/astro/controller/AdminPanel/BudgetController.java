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
@CrossOrigin
public class BudgetController {

    @Autowired
    private BudgetMasterRepository budgetRepository;

    @PostMapping
    public ResponseEntity<Object> createBudget(@RequestBody BudgetMaster budget) {
        try {
            // Check for duplicate budget code
            if (budget.getBudgetCode() != null &&
                budgetRepository.findByBudgetCode(budget.getBudgetCode()).isPresent()) {
                throw new RuntimeException("Budget code already exists: " + budget.getBudgetCode());
            }

            // Set default values if not provided
            if (budget.getOnHoldAmount() == null) {
                budget.setOnHoldAmount(BigDecimal.ZERO);
            }
            if (budget.getSpentAmount() == null) {
                budget.setSpentAmount(BigDecimal.ZERO);
            }

            BudgetMaster saved = budgetRepository.save(budget);
            return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create budget: " + e.getMessage());
        }
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
        existing.setOnHoldAmount(budget.getOnHoldAmount());
        existing.setSpentAmount(budget.getSpentAmount());
        existing.setFiscalYear(budget.getFiscalYear());
        existing.setStartDate(budget.getStartDate());
        existing.setEndDate(budget.getEndDate());
        existing.setStatus(budget.getStatus());
        existing.setProjectCode(budget.getProjectCode());
        existing.setDepartmentName(budget.getDepartmentName());
        existing.setUpdatedBy(budget.getUpdatedBy());

        BudgetMaster saved = budgetRepository.save(existing);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(saved), HttpStatus.OK);
    }

    @DeleteMapping("/{budgetCode}")
    public ResponseEntity<Object> deleteBudget(@PathVariable String budgetCode) {
        BudgetMaster budget = budgetRepository.findByBudgetCode(budgetCode)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse("Budget deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<Object> getBudgetSummary() {
        List<BudgetMaster> budgets = budgetRepository.findAll();

        BigDecimal totalAllocated = budgets.stream()
                .map(BudgetMaster::getAllocatedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOnHold = budgets.stream()
                .map(b -> b.getOnHoldAmount() != null ? b.getOnHoldAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSpent = budgets.stream()
                .map(b -> b.getSpentAmount() != null ? b.getSpentAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRemaining = budgets.stream()
                .map(BudgetMaster::getRemainingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalAllocated", totalAllocated);
        summary.put("totalOnHold", totalOnHold);
        summary.put("totalSpent", totalSpent);
        summary.put("totalRemaining", totalRemaining);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(summary), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getBudgetsByStatus(@PathVariable String status) {
        List<BudgetMaster> budgets = budgetRepository.findByStatus(status);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budgets), HttpStatus.OK);
    }

    @GetMapping("/fiscal-year/{fiscalYear}")
    public ResponseEntity<Object> getBudgetsByFiscalYear(@PathVariable String fiscalYear) {
        List<BudgetMaster> budgets = budgetRepository.findByFiscalYear(fiscalYear);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budgets), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Object> getBudgetsByCategory(@PathVariable String category) {
        List<BudgetMaster> budgets = budgetRepository.findByCategory(category);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budgets), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentName}")
    public ResponseEntity<Object> getBudgetsByDepartment(@PathVariable String departmentName) {
        List<BudgetMaster> budgets = budgetRepository.findByDepartmentName(departmentName);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(budgets), HttpStatus.OK);
    }
}
