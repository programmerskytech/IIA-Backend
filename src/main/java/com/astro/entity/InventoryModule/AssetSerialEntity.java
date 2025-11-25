package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset_serial")
@Data
public class AssetSerialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer assetId;
    private String assetCode;
    private String serialNo;
    private String custodianId;
    private Integer locatorId;
    private String poId;
    @Column(name = "status")
    private String status; // values: "Active", "Disposed"

    private LocalDateTime createdDate;
}
