package com.astro.repository;

import com.astro.entity.InventoryModule.DemandAndIssueDtlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DemandAndIssueDtlEntityRepository extends JpaRepository<DemandAndIssueDtlEntity, Long> {

    List<DemandAndIssueDtlEntity> findByDiId(Long diId);

    @Query("SELECT COALESCE(SUM(d.quantity), 0) FROM DemandAndIssueDtlEntity d " +
            "JOIN DemandAndIssueMasterEntity m ON d.diId = m.id " +
            "WHERE d.materialCode = :materialCode AND m.status IN :statuses")
    BigDecimal getInProgressQty(@Param("materialCode") String materialCode, @Param("statuses") List<String> statuses);

}
