package com.astro.repository.InventoryModule.ogp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.OgpPoDetailEntity;

@Repository
public interface OgpPoDetailRepository extends JpaRepository<OgpPoDetailEntity, Integer> {
    List<OgpPoDetailEntity> findByOgpSubProcessId(Integer ogpSubProcessId);
    List<OgpPoDetailEntity> findByOgpSubProcessIdAndMaterialCode(Integer ogpSubProcessId, String materialCode);
}
