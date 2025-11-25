package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class GrnWorkflowStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "sub_process_id")
    private Integer subProcessId;

    @Column(name = "action")
    private String action; // APPROVE / REJECT / CHANGE REQUEST

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

}
