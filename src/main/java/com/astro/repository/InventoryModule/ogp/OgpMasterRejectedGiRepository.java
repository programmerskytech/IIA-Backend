package com.astro.repository.InventoryModule.ogp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.OgpMasterRejectedGiEntity;
import java.util.List;


@Repository
public interface OgpMasterRejectedGiRepository extends JpaRepository<OgpMasterRejectedGiEntity, Integer> {
    List<OgpMasterRejectedGiEntity> findByStatus(String status);

    boolean existsByGiId(String giId);
}
