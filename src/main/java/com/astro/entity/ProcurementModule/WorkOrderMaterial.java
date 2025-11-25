package com.astro.entity.ProcurementModule;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class WorkOrderMaterial {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_code")
    private String workCode;
    @Column(name = "work_description")
    private String workDescription;
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
    @Column(name = "budget_code ")
    private String budgetCode;

    @ManyToOne
    @JoinColumn(name = "wo_id", nullable = false)
    private WorkOrder workOrder;

}
