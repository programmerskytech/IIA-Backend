package com.astro.repository.InventoryModule.GoodsTransfer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.GtDtlEntity;

@Repository
public interface GtDtlRepository extends JpaRepository<GtDtlEntity,Long> {
    List<GtDtlEntity> findByGtId(Long gtId);
}
