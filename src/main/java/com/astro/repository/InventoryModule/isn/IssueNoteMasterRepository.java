package com.astro.repository.InventoryModule.isn;

import com.astro.entity.InventoryModule.IssueRegisterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.IsnMasterEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IssueNoteMasterRepository extends JpaRepository<IsnMasterEntity, Integer> {
    
    @Query(value = """
        SELECT 
            inm.issue_note_id,
            inm.issue_note_type,
            inm.issue_date,
            inm.consignee_detail,
            inm.indentor_name,
            inm.field_station,
            inm.created_by,
            inm.create_date,
            inm.location_id,
            ind.detail_id,
            ind.asset_id,
            ind.locator_id,
            ind.quantity,
            am.material_desc,
            am.asset_desc,
            am.uom_id
        FROM issue_note_master inm
        JOIN issue_note_detail ind ON inm.issue_note_id = ind.issue_note_id
        JOIN asset_master am ON ind.asset_id = am.asset_id
        WHERE inm.create_date BETWEEN :startDate AND :endDate
        ORDER BY inm.create_date DESC
    """, nativeQuery = true)
    List<Object[]> getIssueNoteDetails(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}