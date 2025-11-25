package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.AssetDisposalAuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssetDisposalAuctionEntityRepository extends JpaRepository<AssetDisposalAuctionEntity, Integer> {
    @Query("""
        SELECT a, d, m, md
        FROM AssetDisposalAuctionEntity a
        JOIN a.auctionDetails d
        JOIN AssetDisposalMasterEntity m ON d.disposalId = m.disposalId
        JOIN AssetDisposalDetailEntity md ON m.disposalId = md.disposalId
        WHERE a.auctionId = :auctionId
    """)
    List<Object[]> findAuctionWithFullDetails(Integer auctionId);

    List<AssetDisposalAuctionEntity> findByAuctionDateBetween(LocalDate from, LocalDate to);

    List<AssetDisposalAuctionEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}
