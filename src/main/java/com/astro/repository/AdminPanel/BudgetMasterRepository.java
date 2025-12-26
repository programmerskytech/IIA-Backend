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
    List<BudgetMaster> findByDepartmentName(String departmentName);
    List<BudgetMaster> findByProjectCode(String projectCode);
}
