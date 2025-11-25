package com.astro.repository.InventoryModule;


import com.astro.entity.InventoryModule.GoodsTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsTransferRepository extends JpaRepository<GoodsTransfer, String> {


}
