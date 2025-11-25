package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ogp_detail")
@Data
public class OgpDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "ogp_process_id")
    private String ogpProcessId;

    @Column(name = "ogp_sub_process_id")
    private Integer ogpSubProcessId;

    @Column(name = "issue_note_id")
    private Integer issueNoteId;

    @Column(name = "asset_id")
    private Integer assetId;

    @Column(name = "locator_id")
    private Integer locatorId;

    @Column(name = "quantity")
    private BigDecimal quantity;
}