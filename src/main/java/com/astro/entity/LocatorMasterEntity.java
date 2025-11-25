package com.astro.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "locator_master")
public class LocatorMasterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locator_id")
    private Integer locatorId;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "locator_desc")
    private String locatorDesc;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}