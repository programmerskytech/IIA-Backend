package com.astro.repository.InventoryModule.grn;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.GrnConsumableDtlEntity;

@Repository
public interface GrnConsumableDtlRepository extends JpaRepository<GrnConsumableDtlEntity, Integer> {
    List<GrnConsumableDtlEntity> findByGiSubProcessIdAndMaterialCode(Integer giSubProcessId, String materialCode);

    List<GrnConsumableDtlEntity> findByGrnSubProcessId(Integer grnSubProcessId);
}
