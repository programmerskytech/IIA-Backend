package com.astro.entity.InventoryModule;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ogp_master")
@Data
public class OgpMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ogp_sub_process_id")
    private Integer ogpSubProcessId;

    @Column(name = "ogp_process_id")
    private String ogpProcessId;

    @Column(name = "issue_note_id")
    private Integer issueNoteId;

    @Column(name = "ogp_date")
    private LocalDate ogpDate;

    @Column(name = "status")
    private String status;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "ogp_type")
    private String ogpType;
    @Column(name="sender_name")
    private String senderName;

    @Column(name="receiver_name")
    private String receiverName;

    @Column(name="receiver_location")
    private String receiverLocation;

    @Column(name="date_of_return")
    private LocalDate dateOfReturn;

}
