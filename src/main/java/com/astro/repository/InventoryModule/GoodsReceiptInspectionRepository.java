package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.GoodsReceiptInspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsReceiptInspectionRepository extends JpaRepository<GoodsReceiptInspection, String> {
}
