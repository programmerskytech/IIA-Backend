package com.astro.repository;

import com.astro.entity.MaterialIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialIdSequenceRepository extends JpaRepository<MaterialIdSequence,Long> {

    @Query("SELECT MAX(i.materialId) FROM MaterialIdSequence i")
    Integer findMaxMaterialId();
}
