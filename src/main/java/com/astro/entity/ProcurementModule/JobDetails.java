package com.astro.entity.ProcurementModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "job_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_code")
    private String jobCode;

    @Column(name = "job_description", length = 500)
    private String jobDescription;

    @Column(name = "category")
    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "uom")
    private String uom;

    @Column(name = "brief_description", length = 1000)
    private String briefDescription;

    @Column(name = "estimated_price", precision = 19, scale = 2)
    private BigDecimal estimatedPrice;

    @Column(name = "total_price", precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "currency")
    private String currency;

    @Column(name = "origin")
    private String origin;  // Indigenous or Imported

    @Column(name = "quantity", precision = 19, scale = 2)
    private BigDecimal quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indent_id", referencedColumnName = "indent_id")
    private IndentCreation indentCreation;
}