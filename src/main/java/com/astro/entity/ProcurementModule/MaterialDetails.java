package com.astro.entity.ProcurementModule;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
public class MaterialDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_code", nullable = false)
    private String materialCode;
    //@Column(name = "indent_id")
  //  @Column(name = "indent_id")
  //  private String indentId;

    @Column(name = "material_description")
    private String materialDescription;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "uom")
    private String uom;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "budget_code")
    private String budgetCode;

    @Column(name = "material_category")
    private String materialCategory;

    @Column(name = "material_sub_category")
    private String materialSubCategory;

    @Column(name = "mode_of_procurement")
    private String modeOfProcurement;
    @Column(name="currency")
    private String currency;

    @Column(name = "conversion_rate")
    private BigDecimal conversionRate;

    @ManyToOne
    @JoinColumn(name = "indent_id", referencedColumnName = "indent_id")
    private IndentCreation indentCreation;

}
