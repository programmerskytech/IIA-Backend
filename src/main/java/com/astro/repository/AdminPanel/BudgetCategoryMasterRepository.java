package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.BudgetCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetCategoryMasterRepository extends JpaRepository<BudgetCategoryMaster, Long> {
    Optional<BudgetCategoryMaster> findByCategoryName(String categoryName);
    List<BudgetCategoryMaster> findByIsActiveTrue();
    List<BudgetCategoryMaster> findAllByOrderByDisplayOrderAsc();
}
