package com.astro.repository.InventoryModule.ogp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.IgpPoDtlEntity;

@Repository
public interface IgpPoDtlRepository extends JpaRepository<IgpPoDtlEntity, Integer> {
    List<IgpPoDtlEntity> findByIgpSubProcessId(Integer igpSubProcessId);
}
