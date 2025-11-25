package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.OhqMasterConsumableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OhqMasterConsumableRepository extends JpaRepository<OhqMasterConsumableEntity, Integer> {
    
    List<OhqMasterConsumableEntity> findByMaterialCode(String materialCode);
    
    List<OhqMasterConsumableEntity> findByLocatorId(Integer locatorId);
    
    Optional<OhqMasterConsumableEntity> findByMaterialCodeAndLocatorId(String materialCode, Integer locatorId);
    Optional<OhqMasterConsumableEntity> findByMaterialCodeAndLocatorIdAndCustodianId(String materialCode, Integer locatorId, String custodianId);

    @Query(value = """
        SELECT 
            ohq.material_code,
            am.description,
            am.uom,
            SUM(ohq.quantity) AS total_quantity,
            ohq.book_value,
            ohq.depriciation_rate,
            ohq.unit_price,
            COALESCE(JSON_ARRAYAGG(
                JSON_OBJECT(
                    'locatorId', ohq.locator_id,
                    'locatorDesc', lm.locator_desc,
                    'quantity', ohq.quantity
                )
            ), '[]') AS locator_details,
            ohq.custodian_id
        FROM ohq_master_consumable ohq
        JOIN material_master am ON ohq.material_code = am.material_code
        JOIN locator_master lm ON ohq.locator_id = lm.locator_id
        WHERE ohq.quantity > 0
        GROUP BY ohq.material_code, am.description, am.uom, 
                 ohq.book_value, ohq.depriciation_rate, ohq.unit_price, ohq.custodian_id
    """, nativeQuery = true)
List<Object[]> getOhqConsumableReport();

    Optional<OhqMasterConsumableEntity> findByMaterialCodeAndCustodianId(String materialCode, String custodianId);
}