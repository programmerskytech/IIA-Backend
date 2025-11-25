package com.astro.repository.InventoryModule.grv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.GrvMasterEntity;

@Repository
public interface GrvMasterRepository extends JpaRepository<GrvMasterEntity, Integer> {
}
