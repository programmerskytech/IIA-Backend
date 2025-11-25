package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.OhqConsumableStoreStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OhqConsumableStoreStockRepository extends JpaRepository<OhqConsumableStoreStockEntity,Long> {
    Optional<OhqConsumableStoreStockEntity> findByMaterialCodeAndLocatorIdAndCustodianId(String materialCode, String locationId, Integer custodianId);

    Optional<OhqConsumableStoreStockEntity> findByMaterialCode(String materialCode);
}
