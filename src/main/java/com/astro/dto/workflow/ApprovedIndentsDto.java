package com.astro.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedIndentsDto {

    private String indentId;
    private String projectName;
    private String indentorName;
    private String createdDate;
    private List<String> materialDes = new ArrayList<>();


    public ApprovedIndentsDto(String indentId, String projectName, String indentorName, LocalDateTime createdDate, String materialDes) {
        this.indentId = indentId;
        this.projectName = projectName;
        this.indentorName = indentorName;
        this.createdDate = createdDate != null ? createdDate.toString() : null;
        this.materialDes = new ArrayList<>();
        if (materialDes != null) {
            this.materialDes.add(materialDes);
        }
    }
}
