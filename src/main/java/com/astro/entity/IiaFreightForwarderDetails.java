package com.astro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "iia_freight_forwarder_details")
@Data
public class IiaFreightForwarderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name", length = 255, nullable = false)
    private String countryName;

    @Column(name = "freight_forwarder_details", columnDefinition = "TEXT")
    private String freightForwarderDetails;
}
