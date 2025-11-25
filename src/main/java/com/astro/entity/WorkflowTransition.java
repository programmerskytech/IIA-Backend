package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WORKFLOW_TRANSITION")
@Data
public class WorkflowTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKFLOWTRANSITIONID")
  //  @Column(name = "workflowTransitionId")
    private Integer workflowTransitionId;

    @Column(name = "WORKFLOWID")
  //  @Column(name="workflowId")
    private Integer workflowId;

    @Column(name = "WORKFLOWNAME")
    //@Column(name = "workflowName")
    private String workflowName;

    @Column(name = "TRANSITIONID")
   // @Column(name = "transitionId")
    private Integer transitionId;

    @Column(name = "REQUESTID")
   // @Column(name = "requestId")
    private String requestId;

   @Column(name = "CREATEDBY")
   // @Column(name = "createdBy")
    private Integer createdBy;

     @Column(name = "MODIFIEDBY")
    //@Column(name = "modifiedBy")
    private Integer modifiedBy;

    @Column(name = "STATUS")
   // @Column(name = "status")
    private String status;

    @Column(name = "NEXTACTION")
    //@Column(name = "nextAction")
    private String nextAction;

    @Column(name = "ACTION")
  //  @Column(name = "action")
    private String action;

    @Column(name = "CURRENTROLE")
   // @Column(name = "currentRole")
    private String currentRole;

    @Column(name = "NEXTROLE")
   // @Column(name = "nextRole")
    private String nextRole;

    @Column(name = "REMARKS")
   // @Column(name = "remarks")
    private String remarks;

    @Column(name = "TRANSITIONORDER")
    //@Column(name = "transitionOrder")
    private Integer transitionOrder;

    @Column(name = "WORKFLOWSEQUENCE")
   // @Column(name = "workflowSequence")
    private Integer workflowSequence;

    @Column(name = "TRANSITIONSUBORDER")
   // @Column(name = "transitionSubOrder")
    private Integer transitionSubOrder;

   @Column(name = "MODIFICATIONDATE")
   // @Column(name = "modificationDate")
    private Date modificationDate;

   @Column(name = "CREATEDDATE")
   // @Column(name = "createdDate")
    private Date createdDate;
}
