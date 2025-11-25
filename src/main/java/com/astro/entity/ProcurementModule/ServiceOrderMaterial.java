package com.astro.entity.ProcurementModule;

import com.astro.entity.ProcurementModule.ServiceOrder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_order_material")
@Data
public class ServiceOrderMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "material_code")
    private String materialCode;
    @Column(name = "so_id")
    private String soId;
    @Column(name = "material_description")
    private String materialDescription;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;
    @Column(name = "currency")
    private String currency;
    @Column(name = "gst")
    private BigDecimal gst;
    @Column(name = "duties")
    private BigDecimal duties;
    @Column(name = "budget_code")
    private String budgetCode;

   // @ManyToOne
    //@JoinColumn(name = "service_order_id")
    //private ServiceOrder serviceOrder;
   @ManyToOne
   @JoinColumn(name = "so_id", nullable = false, referencedColumnName = "so_id", insertable = false, updatable = false)
   private ServiceOrder serviceOrder;





}
