package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.AssetSerialEntity;
import com.azure.core.http.HttpHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetSerialEntityRepository extends JpaRepository<AssetSerialEntity, Long> {
    @Query("""
        SELECT s.serialNo
        FROM AssetSerialEntity s
        WHERE s.assetId = :assetId
          AND s.assetCode = :assetCode
          AND s.locatorId = :locatorId
          AND s.custodianId = :custodianId
          AND (s.status IS NULL OR s.status <> 'Disposed')
    """)
    List<String> findSerialNumbers(Integer assetId, String assetCode, Integer locatorId, String custodianId);

    @Query("SELECT s FROM AssetSerialEntity s WHERE s.assetId = :assetId AND s.custodianId = :custodianId AND s.locatorId = :locatorId")
    List<AssetSerialEntity> findByAssetIdAndCustodianAndLocator(Integer assetId, String custodianId, Integer locatorId);

    AssetSerialEntity findByAssetIdAndSerialNoAndCustodianIdAndLocatorId(
            Integer assetId,
            String serialNo,
            String custodianId,
            Integer locatorId
    );

    @Query("SELECT s.serialNo FROM AssetSerialEntity s WHERE s.assetId = :assetId AND s.locatorId = :locatorId AND s.custodianId = :custodianId")
    List<String> findSerialNosByAssetIdAndLocatorIdAndCustodianId(
            Integer assetId, Integer locatorId, String custodianId);

    HttpHeaders findByAssetId(Integer assetId);

    Optional<AssetSerialEntity> findByAssetIdAndCustodianIdAndLocatorIdAndSerialNo(
            Integer assetId,
            String custodianId,
            Integer locatorId,
            String serialNo
    );

    List<AssetSerialEntity> findByAssetIdAndLocatorIdAndCustodianIdAndStatus(Integer assetId, Integer locatorId, String custodianId, String disposed);

    Optional<AssetSerialEntity> findByAssetIdAndLocatorIdAndCustodianIdAndSerialNoAndStatus(Integer assetId, Integer locatorId, String custodianId, String serialNo, String disposed);
}