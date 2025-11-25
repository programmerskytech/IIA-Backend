package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WORKFLOW_MASTER")
@Data
public class WorkflowMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKFLOWID")
    private Integer workflowId;

    @Column(name = "WORKFLOWNAME")
    private String workflowName;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;
}
