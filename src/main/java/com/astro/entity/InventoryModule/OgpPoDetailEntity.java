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
@Table(name="ogp_po_detail")
@Entity
public class OgpPoDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "ogp_sub_process_id", nullable = false)
    private Integer ogpSubProcessId;

    @Column(name = "material_code", nullable = false)
    private String materialCode;

    @Column(name = "material_desc", nullable = false)
    private String materialDescription;

    @Column(name = "uom_id", nullable = false)
    private String uomId;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
}
