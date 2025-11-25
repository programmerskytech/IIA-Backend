package com.astro.entity.InventoryModule;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "grn_material_master")
public class GrnMaterialMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grnId;

    private Long igpId;

    private LocalDate igpDate;

    private Integer createdBy;

    private LocalDateTime createDate;

    private Integer indentorId;

}
