package com.astro.entity.InventoryModule;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "grn_material_in_dtl")
public class GrnMaterialInDtlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long grnId;

    private Integer assetId;

    private String assetDesc;

    private String materialCode;

    private String materialDesc;

    private Integer quantity;

    private Double unitPrice;

    private Integer uomId;
}
