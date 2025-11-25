package com.astro.dto.workflow.ProcurementDtos.IndentDto;

import lombok.Data;

@Data
public class SearchIndentIdDto {

    private String indentId;
    public SearchIndentIdDto(String indentId) {
        this.indentId = indentId;
    }
}
