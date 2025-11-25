package com.astro.repository.ProcurementModule.IndentCreation;

import com.astro.entity.ProcurementModule.IndentCreation;
import com.astro.entity.ProcurementModule.IndentMaterialMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndentMaterialMappingRepository extends JpaRepository<IndentMaterialMapping,Long> {


}
