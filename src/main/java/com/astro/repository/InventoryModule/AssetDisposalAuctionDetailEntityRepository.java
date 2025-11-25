package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.AssetDisposalAuctionDetailEntity;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDisposalAuctionDetailEntityRepository extends JpaRepository<AssetDisposalAuctionDetailEntity, Integer> {
}
