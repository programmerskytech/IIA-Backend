package com.astro.repository;

import com.astro.entity.ProcurementModule.PurchaseOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderHistoryRepository extends JpaRepository<PurchaseOrderHistory, Integer> {

}
