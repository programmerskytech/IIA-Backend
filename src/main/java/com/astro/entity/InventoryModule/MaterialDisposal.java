package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "material_disposal")
public class MaterialDisposal {

    @Id
    private String materialDisposalCode;
    private String disposalCategory;
    private String disposalMode;
    private String vendorDetails;
    private LocalDate disposalDate;
    private BigDecimal currentBookValue;
    private BigDecimal editReserveValue;
    private BigDecimal finalBidValue;
    @Lob
    private byte[] saleNote;
    private String saleNoteFileName;
    private BigDecimal editQuantity;
    private BigDecimal editValueMaterials;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate = LocalDateTime.now();

}
