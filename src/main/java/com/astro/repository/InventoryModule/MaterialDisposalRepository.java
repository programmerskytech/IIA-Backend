package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.MaterialDisposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialDisposalRepository extends JpaRepository<MaterialDisposal,String> {
}
