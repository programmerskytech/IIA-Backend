package com.astro.repository.InventoryModule.ogp;

import com.astro.entity.InventoryModule.OgpDetailEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OgpDetailRepository extends JpaRepository<OgpDetailEntity, Integer> {
    boolean existsByIssueNoteIdAndAssetIdAndLocatorId(Integer issueNoteId, Integer assetId, Integer locatorId);
    List<OgpDetailEntity> findByOgpSubProcessId(Integer ogpSubProcessId);
    @Query("SELECT o.quantity FROM OgpDetailEntity o WHERE o.ogpSubProcessId = :ogpSubProcessId AND o.assetId = :assetId AND o.locatorId = :locatorId")
    Optional<BigDecimal> findQuantityByOgpSubProcessIdAndAssetIdAndLocatorId(
        @Param("ogpSubProcessId") Integer ogpSubProcessId,
        @Param("assetId") Integer assetId,
        @Param("locatorId") Integer locatorId);
}