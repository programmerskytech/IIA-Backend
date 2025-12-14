package com.astro.entity.ProcurementModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "indent_cancellation_request")
@Data
public class IndentCancellationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indent_id", nullable = false)
    private String indentId;

    @Column(name = "requested_by", nullable = false)
    private Integer requestedBy;

    @Column(name = "requested_by_name")
    private String requestedByName;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "request_status")
    private String requestStatus; // PENDING, APPROVED, REJECTED

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approved_by_name")
    private String approvedByName;

    @Column(name = "approval_remarks", columnDefinition = "TEXT")
    private String approvalRemarks;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    private LocalDateTime updatedDate = LocalDateTime.now();
}
