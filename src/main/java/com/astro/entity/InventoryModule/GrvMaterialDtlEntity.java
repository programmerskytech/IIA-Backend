package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "grv_material_detail")
@Data
public class GrvMaterialDtlEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detailId;
    private Integer giSubProcessId;
    private String grvProcessId;
    private Integer grvSubProcessId;
    private String materialCode;
    private String materialDesc;
    private String uomId;
    private BigDecimal rejectedQuantity;
    private BigDecimal returnQuantity;
    private String returnType;
    private String rejectReason;
}