package com.astro.entity.InventoryModule;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "issue_note_master")
public class IsnMasterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_note_id")
    private Integer issueNoteId;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "consignee_detail")
    private String consigneeDetail;

    @Column(name = "indentor_name")
    private String indentorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_note_type")
    private IssueNoteType issueNoteType;

    @Column(name = "field_station")
    private String fieldStation;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name="location_id")
    private String locationId;
    
    public enum IssueNoteType {
        Returnable,
        NonReturnable
    }
}
