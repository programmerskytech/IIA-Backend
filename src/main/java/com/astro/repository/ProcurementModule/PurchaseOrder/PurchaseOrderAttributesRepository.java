package com.astro.repository.ProcurementModule.PurchaseOrder;

import com.astro.dto.workflow.ProcurementDtos.IndentDto.materialHistoryDto;
import com.astro.dto.workflow.ProcurementDtos.performanceWarrsntySecurityReportDto;
import com.astro.dto.workflow.poMaterialHistoryDto;
import com.astro.entity.ProcurementModule.PurchaseOrderAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderAttributesRepository extends JpaRepository<PurchaseOrderAttributes, Long> {

   // @Query("SELECT poa.quantity FROM PurchaseOrderAttributes poa WHERE poa.poId = :poId AND poa.materialCode = :materialCode")
   // Optional<BigDecimal> findQuantityByPoIdAndMaterialCode(@Param("poId") String poId, @Param("materialCode") String materialCode);
   @Query("SELECT poa.quantity FROM PurchaseOrderAttributes poa WHERE poa.purchaseOrder.poId = :poId AND poa.materialCode = :materialCode")
   Optional<BigDecimal> findQuantityByPoIdAndMaterialCode(@Param("poId") String poId, @Param("materialCode") String materialCode);


   // Optional<PurchaseOrderAttributes> findByPoIdAndMaterialCode(String poId, String materialCode);
   Optional<PurchaseOrderAttributes> findByPurchaseOrder_PoIdAndMaterialCode(String poId, String materialCode);

   @Query("""
        SELECT new com.astro.dto.workflow.poMaterialHistoryDto(
            po.poId,
            po.createdDate,
            po.vendorName,
            po.totalValueOfPo
        )
        FROM PurchaseOrderAttributes attr
        JOIN attr.purchaseOrder po
        WHERE attr.materialCode = :materialCode
          AND po.createdDate >= :fromDate
        ORDER BY po.createdDate DESC
        """)
   List<poMaterialHistoryDto> findLatestPOByMaterialCode(
           @Param("materialCode") String materialCode,
           @Param("fromDate") LocalDateTime fromDate
   );

   @Query("SELECT p.gst FROM PurchaseOrderAttributes p WHERE p.materialCode = :materialCode AND p.purchaseOrder.poId = :poId")
   BigDecimal findGstByMaterialCodeAndPoId(@Param("materialCode") String materialCode,
                                           @Param("poId") String poId);

   @Query("SELECT p.currency FROM PurchaseOrderAttributes p WHERE p.materialCode = :materialCode AND p.purchaseOrder.poId = :poId")
   String findCurrencyByMaterialCodeAndPoId(@Param("materialCode") String materialCode,
                                            @Param("poId") String poId);

   @Query("SELECT p.exchangeRate FROM PurchaseOrderAttributes p WHERE p.materialCode = :materialCode AND p.purchaseOrder.poId = :poId")
   BigDecimal findExchangeRateByMaterialCodeAndPoId(@Param("materialCode") String materialCode,
                                                    @Param("poId") String poId);


 /*  @Query("""
SELECT new com.astro.dto.workflow.ProcurementDtos.IndentDto.materialHistoryDto(
    po.poId,
    CAST(po.createdDate AS string),
    po.vendorName
)
FROM PurchaseOrderAttributes attr
JOIN attr.purchaseOrder po
WHERE attr.materialCode = :materialCode
ORDER BY po.createdDate DESC
""")
   List<materialHistoryDto> findLatestPOByMaterialCode(@Param("materialCode") String materialCode, Pageable pageable);
*/



}
