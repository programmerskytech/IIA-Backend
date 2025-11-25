package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.OgpAssetDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OgpAssetDisposalRepository extends JpaRepository<OgpAssetDisposal, Integer> {
    List<OgpAssetDisposal> findByStatus(String awaitingForApproval);
}
