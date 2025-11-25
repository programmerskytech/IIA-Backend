package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SUB_WORKFLOW_TRANSITION")
@Data
public class SubWorkflowTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBWORKFLOWTRANSITIONID")
    private Integer subWorkflowTransitionId;

    @Column(name = "WORKFLOWID")
    private Integer workflowId;

    @Column(name = "WORKFLOWNAME")
    private String workflowName;

    @Column(name = "WORKFLOWTRANSITIONID")
    private Integer workflowTransitionId;

    @Column(name = "REQUESTID")
    private String requestId;

    @Column(name = "CREATEDBY")
    private Integer createdBy;

    @Column(name = "MODIFIEDBY")
    private Integer modifiedBy;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "TRANSITIONNAME")
    private String transitionName;

    @Column(name = "TRANSITIONTYPE")
    private String transitionType;

    @Column(name = "ACTIONON")
    private Integer actionOn;

    @Column(name = "WORKFLOWSEQUENCE")
    private Integer workflowSequence;

    @Column(name = "MODIFICATIONDATE")
    private Date modificationDate;

    @Column(name = "CREATEDDATE")
    private Date createdDate;
}
