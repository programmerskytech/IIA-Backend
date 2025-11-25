package com.astro.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "location_master")
@Data
public class LocationMaster {

    @Id
    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "address")
    private String address;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();

}
