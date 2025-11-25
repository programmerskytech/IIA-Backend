package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "gem_vendor_id_tracker")
@Data
public class GemVendorIdTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false, unique = true)
    private Long vendorId;

    private String gemVendorId;
    private String vendorName;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

}
