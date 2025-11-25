package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "gatepass_out_in")
public class GatepassOutAndIn {
    @Id
    private String gatePassId;
    private String gatePassType;
    private String materialDetails;
    private LocalDate expectedDateOfReturn;
   // private BigDecimal editQuantity;
    @Column(name = "extendEDR")
    private BigDecimal extendEDR;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();
}
