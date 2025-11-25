package com.astro.entity.InventoryModule;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "igp_po_detail")
@Data
public class IgpPoDtlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "igp_sub_process_id", nullable = false)
    private Integer igpSubProcessId;

    @Column(name = "material_code", nullable = false)
    private String materialCode;

    @Column(name = "material_desc", nullable = false)
    private String materialDescription;

    @Column(name = "uom_id", nullable = false)
    private String uomId;

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;
}
