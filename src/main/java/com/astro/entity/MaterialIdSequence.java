package com.astro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="material_id_sequence")
public class MaterialIdSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_id")
    private Integer materialId;

}
