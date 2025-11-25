package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ohq_consumable_store_stock_entity")
@Data
public class OhqConsumableStoreStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ohq_id")
    private Long ohqId;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "locator_id")
    private Integer locatorId;

    @Column(name = "book_value")
    private BigDecimal bookValue;

    @Column(name = "depriciation_rate")
    private BigDecimal depriciationRate;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name="custodian_id")
    private String custodianId;

    @Column(name = "quantity")
    private BigDecimal quantity;

    private String uom;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;
}
