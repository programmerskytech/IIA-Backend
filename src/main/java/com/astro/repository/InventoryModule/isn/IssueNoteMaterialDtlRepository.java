package com.astro.repository.InventoryModule.isn;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.InventoryModule.IsnMaterialDtlEntity;

@Repository
public interface IssueNoteMaterialDtlRepository extends JpaRepository<IsnMaterialDtlEntity, Integer> {
    List<IsnMaterialDtlEntity> findByIssueNoteId(Integer issueNoteId);
}