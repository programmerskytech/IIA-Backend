package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "payment_voucher_materials")
@Data
public class PaymentVoucherMaterials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "material_description")
    private String materialDescription;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "gst")
    private BigDecimal gst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_voucher_id")
    private PaymentVoucher paymentVoucher;
}
