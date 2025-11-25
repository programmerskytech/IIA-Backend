package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class GoodsTransfer {

    @Id
    @Column(name = "goods_transfer_id")
    private String goodsTransferID;
    private String consignorDetails;
    private String consigneeDetails;
    private String fieldStationName;
    private String materialCode;
    private String uom;
    private Integer quantity;
    private String locator;
    private String note;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();
}
