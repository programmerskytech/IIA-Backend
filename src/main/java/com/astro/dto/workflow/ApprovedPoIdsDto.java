package com.astro.dto.workflow;

import lombok.Data;

@Data
public class ApprovedPoIdsDto {

    private String poId;
    private String indentorName;
    private String vendorName;


    public ApprovedPoIdsDto(String poId, String indentorName, String vendorName) {

        this.poId=poId;
        this.indentorName=indentorName;
        this.vendorName=vendorName;
    }
}
