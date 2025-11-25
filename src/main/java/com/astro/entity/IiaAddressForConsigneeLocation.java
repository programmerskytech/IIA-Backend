package com.astro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "iia_address_for_consignee_location")
@Data
public class IiaAddressForConsigneeLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consignee", length = 255)
    private String consignee;

    @Column(name = "iia_address", columnDefinition = "TEXT")
    private String iiaAddress;


}
