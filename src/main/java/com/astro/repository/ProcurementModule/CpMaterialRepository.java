package com.astro.repository.ProcurementModule;

import com.astro.entity.ProcurementModule.CpMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpMaterialRepository extends JpaRepository<CpMaterials, Long> {
}
