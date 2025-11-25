package com.astro.repository.InventoryModule.ogp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.OgpGtDtlEntity;

@Repository
public interface OgpGtDtlRepository extends JpaRepository<OgpGtDtlEntity,Long> {
    List<OgpGtDtlEntity> findByGtId(Long gtId);
}
