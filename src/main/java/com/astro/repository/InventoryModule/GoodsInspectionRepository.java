package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.GoodsInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsInspectionRepository extends JpaRepository<GoodsInspection,String> {
}
