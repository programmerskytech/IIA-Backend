package com.astro.entity.InventoryModule;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ogp_master_po")
@Data
public class OgpMasterPoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ogp_sub_process_id")
    private Integer ogpSubProcessId;

    @Column(name = "po_id", nullable = false)
    private String poId;

    @Column(name = "ogp_date", nullable = false)
    private LocalDate ogpDate;

    @Column(name = "location_id", nullable = false)
    private String locationId;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "ogp_type")
    private String ogpType;
    
    @Column(name = "status")
    private String status;
    @Column(name="sender_name")
    private String senderName;

    @Column(name="receiver_name")
    private String receiverName;

    @Column(name="receiver_location")
    private String receiverLocation;

    @Column(name="date_of_return")
    private LocalDate dateOfReturn;

}
