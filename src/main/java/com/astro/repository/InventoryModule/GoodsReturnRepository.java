package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.GoodsReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsReturnRepository extends JpaRepository<GoodsReturn, String> {
}
