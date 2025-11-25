package com.astro.entity.ProcurementModule;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class IndentMaterialMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "indent_id", nullable = false)
    private IndentCreation indentCreation;

    @ManyToOne
    @JoinColumn(name = "material_code", nullable = false)
    private MaterialDetails materialDetails;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
