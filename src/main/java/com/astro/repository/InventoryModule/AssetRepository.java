package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {

    Asset findByAssetCode(String assetCode);
    Asset findByMaterialCode(String materialCode);
}
