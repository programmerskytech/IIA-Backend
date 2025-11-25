package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data

public class Asset {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
   // private Long id;
    @Id
    private String assetCode;


    private String materialCode;


    private String description;


    private String uom;

    private String makeNo;
    private String modelNo;
    private String serialNo;
    private String componentName;
    private String componentCode;
    private int quantity;


    private String locator;

    private String transactionHistory;


    private String currentCondition;

    private String updatedBy;
    private String createdBy;


    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate = LocalDateTime.now();


}
