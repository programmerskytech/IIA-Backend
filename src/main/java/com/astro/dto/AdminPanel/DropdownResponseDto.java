package com.astro.dto.AdminPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for returning all dropdowns for a specific form
 * Used when frontend needs multiple dropdown values in a single API call
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DropdownResponseDto {

    private String formName;
    private String formDisplayName;
    private List<FieldDropdownDto> dropdowns;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldDropdownDto {
        private String fieldName;           // designatorName
        private String fieldDisplayName;    // designatorDisplayName
        private String dataType;            // STRING, NUMBER, DATE, BOOLEAN
        private List<LOVResponseDto> values;
    }
}
