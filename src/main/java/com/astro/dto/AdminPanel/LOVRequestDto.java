package com.astro.dto.AdminPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for LOV creation/update requests from admin panel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LOVRequestDto {

    private Long designatorId;
    private String lovValue;
    private String lovDisplayValue;
    private String lovDescription;
    private Boolean isActive = true;
    private Boolean isDefault = false;
    private Integer displayOrder;
    private String colorCode;
    private String iconName;
    private Long parentLovId;
}
