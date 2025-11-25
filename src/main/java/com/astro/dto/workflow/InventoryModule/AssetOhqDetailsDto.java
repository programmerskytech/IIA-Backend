package com.astro.dto.workflow.InventoryModule;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetOhqDetailsDto {


        private Integer ohqId;
        private Integer assetId;
        private String assetDesc;
        private String modelNo;
        private String serialNo;
        private String poId;
        private BigDecimal unitPrice;
        private BigDecimal quantity;
        private BigDecimal bookValue;
        private BigDecimal depriciationRate;
        private String custodianId;
        private Integer locatorId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        private LocalDateTime gprnDate;


}
