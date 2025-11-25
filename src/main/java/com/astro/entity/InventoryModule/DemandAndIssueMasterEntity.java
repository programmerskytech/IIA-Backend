package com.astro.entity.InventoryModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demand_and_issue_master")
public class DemandAndIssueMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_location_id", nullable = false)
    private String senderLocationId;

    @Column(name="status")
    private String status;

    @Column(name = "sender_custodian_id", nullable = false, length = 64)
    private Integer senderCustodianId;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name="di_date", nullable = false)
    private LocalDate demandIssueDate;

    @Column(name = "created_by", nullable = false, length = 100)
    private Integer createdBy;

    private LocalDate issueDate;
    private Integer issuedBy;
}
