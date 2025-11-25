package com.astro.repository.InventoryModule.igp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astro.entity.InventoryModule.IgpMaterialMasterEntity;

public interface IgpMaterialMasterRepository extends JpaRepository<IgpMaterialMasterEntity, Long> {
    List<IgpMaterialMasterEntity> findByStatus(String status);
}
