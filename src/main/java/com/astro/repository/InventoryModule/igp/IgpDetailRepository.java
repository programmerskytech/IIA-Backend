package com.astro.repository.InventoryModule.igp;

import com.astro.dto.workflow.InventoryModule.igp.IgpDetailReportDto;
import com.astro.entity.InventoryModule.IgpDetailEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IgpDetailRepository extends JpaRepository<IgpDetailEntity, Integer> {
    List<IgpDetailEntity> findByIgpSubProcessId(Integer IgpSubProcessId);
    // @Query(value = """
    //     SELECT 
    //         m.igp_process_id,
    //         m.igp_sub_process_id,
    //         m.igp_date,
    //         m.location_id,
    //         m.created_by,
    //         m.create_date,
    //         JSON_ARRAYAGG(
    //             JSON_OBJECT(
    //                 'detailId', COALESCE(d.detail_id, pd.detail_id),
    //                 'igpSubProcessId', COALESCE(d.igp_sub_process_id, pd.igp_sub_process_id),
    //                 'materialCode', pd.material_code,
    //                 'materialDesc', pd.material_desc,
    //                 'assetId', d.asset_id,
    //                 'locatorId', d.locator_id,
    //                 'uomId', COALESCE(am.uom_id, pd.uom_id),
    //                 'quantity', COALESCE(d.quantity, pd.quantity),
    //                 'type', CASE 
    //                     WHEN d.detail_id IS NOT NULL THEN 'ASSET'
    //                     ELSE 'MATERIAL'
    //                 END
    //             )
    //         ) as details
    //     FROM igp_master m
    //     LEFT JOIN igp_detail d ON m.igp_sub_process_id = d.igp_sub_process_id
    //     LEFT JOIN igp_po_detail pd ON m.igp_sub_process_id = pd.igp_sub_process_id
    //     LEFT JOIN asset_master am ON d.asset_id = am.asset_id
    //     GROUP BY m.igp_sub_process_id
    //     """, 
    //     nativeQuery = true)
    @Query(value = """
        SELECT 
            om.issue_note_id,
            om.ogp_sub_process_id,
            im.igp_sub_process_id,
            NULL as po_id,
            JSON_ARRAYAGG(
                JSON_OBJECT(
                    'detailId', COALESCE(id.detail_id, od.detail_id),
                    'materialCode', NULL,
                    'materialDesc', NULL,
                    'assetId', CASE 
                        WHEN id.asset_id IS NOT NULL THEN id.asset_id
                        WHEN od.asset_id IS NOT NULL THEN od.asset_id
                        ELSE ind.asset_id
                    END,
                    'assetDesc', am.asset_desc,
                    'locatorId', CASE 
                        WHEN id.locator_id IS NOT NULL THEN id.locator_id
                        WHEN od.locator_id IS NOT NULL THEN od.locator_id
                        ELSE ind.locator_id
                    END,
                    'uomId', am.uom_id,
                    'quantity', CASE 
                        WHEN id.quantity IS NOT NULL THEN id.quantity
                        WHEN od.quantity IS NOT NULL THEN od.quantity
                        ELSE ind.quantity
                    END,
                    'type', 'ASSET'
                )
            ) as details,
            om.status,
            om.sender_name,      
            om.receiver_name,   
            om.ogp_type
        FROM ogp_master om
        JOIN issue_note_master inm ON om.issue_note_id = inm.issue_note_id
        JOIN issue_note_detail ind ON inm.issue_note_id = ind.issue_note_id
        LEFT JOIN igp_master im ON om.ogp_sub_process_id = im.ogp_sub_process_id
        LEFT JOIN igp_detail id ON im.igp_sub_process_id = id.igp_sub_process_id
        LEFT JOIN ogp_detail od ON om.ogp_sub_process_id = od.ogp_sub_process_id
        LEFT JOIN asset_master am ON CASE 
            WHEN id.asset_id IS NOT NULL THEN id.asset_id
            WHEN od.asset_id IS NOT NULL THEN od.asset_id
            ELSE ind.asset_id
        END = am.asset_id
        WHERE im.igp_sub_process_id IS NULL
        GROUP BY om.issue_note_id, om.ogp_sub_process_id, im.igp_sub_process_id, om.status
        
        UNION ALL
        
        SELECT 
            NULL as issue_note_id,
            omp.ogp_sub_process_id,
            im.igp_sub_process_id,
            omp.po_id,
            JSON_ARRAYAGG(
                JSON_OBJECT(
                    'detailId', COALESCE(ipd.detail_id, opd.detail_id),
                    'materialCode', COALESCE(ipd.material_code, opd.material_code),
                    'materialDesc', COALESCE(ipd.material_desc, opd.material_desc),
                    'assetId', NULL,
                    'assetDesc', NULL,
                    'locatorId', NULL,
                    'uomId', COALESCE(ipd.uom_id, opd.uom_id),
                    'quantity', COALESCE(ipd.quantity, opd.quantity),
                    'type', 'MATERIAL'
                )
            ) as details,
            omp.status,
             omp.sender_name,    
             omp.receiver_name,  
             omp.ogp_type
        FROM ogp_master_po omp
        LEFT JOIN igp_master im ON omp.ogp_sub_process_id = im.ogp_sub_process_id
        LEFT JOIN igp_po_detail ipd ON im.igp_sub_process_id = ipd.igp_sub_process_id
        LEFT JOIN ogp_po_detail opd ON omp.ogp_sub_process_id = opd.ogp_sub_process_id
        WHERE im.igp_sub_process_id IS NULL
        GROUP BY omp.po_id, omp.ogp_sub_process_id, im.igp_sub_process_id, omp.status
        """, 
        nativeQuery = true)
    List<Object[]> findAllIgpDetails();


    

    
    @Query(value = """
        SELECT 
            detail_id as detailId,
            igp_sub_process_id as igpSubProcessId,
            material_code as materialCode,
            material_desc as materialDesc,
            NULL as assetId,
            NULL as locatorId,
            uom_id as uomId,
            quantity,
            'MATERIAL' as type
        FROM igp_po_detail 
        WHERE igp_sub_process_id = :igpSubProcessId
        UNION ALL
        SELECT 
            detail_id,
            igp_sub_process_id,
            NULL as materialCode,
            NULL as materialDesc,
            asset_id,
            locator_id,
            (SELECT uom_id FROM asset_master WHERE asset_id = id.asset_id) as uomId,
            quantity,
            'ASSET' as type
        FROM igp_detail id
        WHERE igp_sub_process_id = :igpSubProcessId
        """, nativeQuery = true)
    List<IgpDetailReportDto> findAllDetailsByIgpSubProcessId(@Param("igpSubProcessId") Integer igpSubProcessId);
}