package com.astro.entity.ProcurementModule;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "purchase_order_history")
public class PurchaseOrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(name = "po_id")
    private String poId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "snapshot_json", columnDefinition = "TEXT")
    private String snapshotJson;
}
