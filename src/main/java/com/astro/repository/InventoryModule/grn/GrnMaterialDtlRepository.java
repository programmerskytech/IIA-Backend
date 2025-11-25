package com.astro.repository.InventoryModule.grn;

import com.astro.entity.InventoryModule.GrnMaterialDtlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrnMaterialDtlRepository extends JpaRepository<GrnMaterialDtlEntity, Integer> {
    List<GrnMaterialDtlEntity> findByGrnSubProcessId(Integer grnSubProcessId);
    List<GrnMaterialDtlEntity> findByAssetId(Integer assetId);

    List<GrnMaterialDtlEntity> findByGiSubProcessIdAndAssetId(Integer giSubProcessId, Integer assetId);

    List<GrnMaterialDtlEntity> findByGrnProcessId(String grnProcessId);
}