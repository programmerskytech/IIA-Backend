package com.astro.repository.InventoryModule.ogp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.OgpDetailRejectedGiEntity;

@Repository
public interface OgpDetailRejectedGiRepository extends JpaRepository<OgpDetailRejectedGiEntity, Integer> {
    List<OgpDetailRejectedGiEntity> findByOgpSubprocessId(Integer ogpSubprocessId);
}
