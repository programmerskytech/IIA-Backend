package com.astro.entity.InventoryModule;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "igp_material_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgpMaterialMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private String ogpId;

    private String igpDate;

    private String igpType;

    private Integer indentId;

    private Integer createdBy;

    private LocalDateTime createDate;

    private String locationId;
}
