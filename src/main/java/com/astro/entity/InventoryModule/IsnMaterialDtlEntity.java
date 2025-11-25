package com.astro.entity.InventoryModule;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "issue_note_detail")
public class IsnMaterialDtlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "issue_note_id")
    private Integer issueNoteId;

    @Column(name = "asset_id")
    private Integer assetId;

    @Column(name = "locator_id")
    private Integer locatorId;

    @Column(name = "quantity")
    private BigDecimal quantity;
}