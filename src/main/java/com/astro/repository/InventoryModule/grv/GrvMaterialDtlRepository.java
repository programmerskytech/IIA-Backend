package com.astro.repository.InventoryModule.grv;

import com.astro.entity.InventoryModule.GrvMaterialDtlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrvMaterialDtlRepository extends JpaRepository<GrvMaterialDtlEntity, Integer> {
    
    List<GrvMaterialDtlEntity> findByGrvSubProcessId(Integer grvSubProcessId);
    List<GrvMaterialDtlEntity> findByMaterialCode(String materialCode);
    List<GrvMaterialDtlEntity> findByReturnType(String returnType);
    List<GrvMaterialDtlEntity> findByGrvProcessIdAndGrvSubProcessId(String grvProcessId, Integer grvSubProcessId);
    Optional<GrvMaterialDtlEntity> findByGrvSubProcessIdAndMaterialCode(Integer grvSubProcessId, String materialCode);

    Optional<GrvMaterialDtlEntity> findByGiSubProcessIdAndMaterialCode(Integer giSubProcessId, String materialCode);
    
}