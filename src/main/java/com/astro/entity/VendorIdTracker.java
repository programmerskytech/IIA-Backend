package com.astro.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "vendor_id_tracker")
public class VendorIdTracker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "primary_business", unique = true)
    private String primaryBusiness;
    
    @Column(name = "prefix", unique = true, length = 10)
    private String prefix;
    
    @Column(name = "last_sequence")
    private Integer lastSequence = 0;
}