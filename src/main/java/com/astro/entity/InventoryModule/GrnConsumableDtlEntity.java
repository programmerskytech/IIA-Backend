package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "grn_consumable_detail")
@Data
public class GrnConsumableDtlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Integer detailId;

    @Column(name = "quantity")
    private BigDecimal quantity;
    
    @Column(name = "grn_process_id", nullable = false)
    private String grnProcessId;
    
    @Column(name = "igp_sub_process_id")
    private Integer igpSubProcessId;

    @Column(name = "grn_sub_process_id", nullable = false)
    private Integer grnSubProcessId;
    
    @Column(name = "gi_sub_process_id")
    private Integer giSubProcessId;
    
    @Column(name = "material_code", nullable = false)
    private String materialCode;
    
    @Column(name = "locator_id", nullable = false)
    private Integer locatorId;
    
    @Column(name = "book_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal bookValue;
    
    @Column(name = "depriciation_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal depriciationRate;
}