package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "igp_master")
@Data
public class IgpMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "igp_sub_process_id")
    private Integer igpSubProcessId;

    @Column(name = "igp_process_id")
    private String igpProcessId;

    @Column(name="ogp_sub_process_id")
    private Integer ogpSubProcessId;

    @Column(name = "igp_date")
    private LocalDate igpDate;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}