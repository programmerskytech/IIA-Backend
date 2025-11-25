package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ohq_master_consumable")
@Data
public class OhqMasterConsumableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ohq_id")
    private Integer ohqId;

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
}