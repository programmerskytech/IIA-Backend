package com.astro.entity.InventoryModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GprnMaterialsId implements Serializable {
    private String gprnNo;
    private String materialCode;

}
