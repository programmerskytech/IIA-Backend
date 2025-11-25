package com.astro.entity.InventoryModule;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "igp_material_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgpMaterialDtlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="igp_id")
    private Long igpId;

    @Column(name="asset_id")
    private Integer assetId;

    private String materialCode;

    private String category;

    private String subCategory;

    @Column(name="material_description")
    private String description;

    private String uom;

    private BigDecimal quantity;

    private Double estimatedPriceWithCcy;

    private Boolean indigenousOrImported;
}
