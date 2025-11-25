package com.astro.repository.InventoryModule.igp;

import com.astro.entity.InventoryModule.IgpMasterEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IgpMasterRepository extends JpaRepository<IgpMasterEntity, Integer> {
    List<IgpMasterEntity> findByOgpSubProcessId(Integer ogpSubProcessId);
    
    @Query(value = """
            SELECT 
                im.igp_process_id,
                im.igp_sub_process_id,
                im.ogp_sub_process_id,
                im.igp_date,
                im.location_id,
                im.created_by,
                im.create_date,
                JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'detailId', id.detail_id,
                        'assetId', id.asset_id,
                        'assetDesc', am.asset_desc,
                        'materialDesc', am.material_desc,
                        'locatorId', id.locator_id,
                        'locatorDesc', lm.locator_desc,
                        'quantity', id.quantity,
                        'uomId', am.uom_id
                    )
                ) as igp_details
            FROM igp_master im
            JOIN igp_detail id ON im.igp_sub_process_id = id.igp_sub_process_id
            JOIN asset_master am ON id.asset_id = am.asset_id
            JOIN locator_master lm ON id.locator_id = lm.locator_id
            WHERE im.igp_date BETWEEN :startDate AND :endDate
            GROUP BY im.igp_sub_process_id
            ORDER BY im.igp_date DESC
            """, nativeQuery = true)
        List<Object[]> getIgpReport(LocalDateTime startDate, LocalDateTime endDate);

  /*  @Query(value = """
    SELECT 
        im.id,
        im.status,
        im.ogp_id,
        im.igp_date,
        im.igp_type,
        im.indent_id,
        im.created_by,
        im.create_date,
        im.location_id,
        JSON_ARRAYAGG(
            JSON_OBJECT(
                'id', id.id,
                'assetId', id.asset_id,
                'materialCode', id.material_code,
                'category', id.category,
                'subCategory', id.sub_category,
                'description', id.material_description,
                'uom', id.uom,
                'quantity', id.quantity,
                'estimatedPriceWithCcy', id.estimated_price_with_ccy,
                'indigenousOrImported', id.indigenous_or_imported
            )
        ) as igp_details
    FROM igp_material_master im
    JOIN igp_material_detail id ON im.id = id.igp_id
    WHERE im.igp_date BETWEEN :startDate AND :endDate
    GROUP BY im.id
    ORDER BY im.igp_date DESC
""", nativeQuery = true)
    List<Object[]> getIgpMaterailInReport(LocalDateTime startDate, LocalDateTime endDate);
*/
  @Query(value = """
SELECT 
    im.id,
    im.status,
    im.ogp_id,
    im.igp_date,
    im.igp_type,
    im.indent_id,
    im.created_by,
    im.create_date,
    im.location_id,
    IFNULL(JSON_ARRAYAGG(
        JSON_OBJECT(
            'id', id.id,
            'assetId', id.asset_id,
            'materialCode', id.material_code,
            'category', id.category,
            'subCategory', id.sub_category,
            'description', id.material_description,
            'uom', id.uom,
            'quantity', id.quantity,
            'estimatedPriceWithCcy', id.estimated_price_with_ccy,
            'indigenousOrImported', id.indigenous_or_imported
        )
    ), JSON_ARRAY()) as igp_details
FROM igp_material_master im
LEFT JOIN igp_material_detail id ON im.id = id.igp_id
WHERE STR_TO_DATE(im.igp_date, '%d/%m/%Y') BETWEEN :startDate AND :endDate
GROUP BY im.id
ORDER BY STR_TO_DATE(im.igp_date, '%d/%m/%Y') DESC
""", nativeQuery = true)
  List<Object[]> getIgpMaterailInReport(LocalDate startDate, LocalDate endDate);



}