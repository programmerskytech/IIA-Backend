package com.astro.repository.InventoryModule.igp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astro.entity.InventoryModule.IgpMaterialDtlEntity;

public interface IgpMaterialDtlRepository extends JpaRepository<IgpMaterialDtlEntity, Long> {
    List<IgpMaterialDtlEntity> findByIgpId(Long igpId);
}
