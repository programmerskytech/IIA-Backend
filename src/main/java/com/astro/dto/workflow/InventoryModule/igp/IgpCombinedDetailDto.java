package com.astro.dto.workflow.InventoryModule.igp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IgpCombinedDetailDto {
    private Integer issueNoteId;
    private Integer ogpSubProcessId;
    private Integer igpSubProcessId;
    private String poId;
    private String status;
    private String senderName;
    private String receiverName;
    private String ogpType;
    private List<IgpItemDetailDto> details;
}