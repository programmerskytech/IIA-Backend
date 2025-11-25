package com.astro.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "vendor_id_sequence")
public class VendorIdSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id")
    private String vendorId; // Changed from Integer to String
}