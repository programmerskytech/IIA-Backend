package com.astro.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSITION_MASTER")
@Data
public class TransitionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSITIONID")
    private Integer transitionId;

    @Column(name = "TRANSITIONNAME")
    private String transitionName;

    @Column(name = "WORKFLOWID")
    private Integer workflowId;

    @Column(name = "CURRENTROLEID")
    private Integer currentRoleId;

    @Column(name = "NEXTROLEID")
    private Integer nextRoleId;

    @Column(name = "PREVIOUSROLEID")
    private Integer previousRoleId;

    @Column(name = "CONDITIONID")
    private Integer conditionId;

    @Column(name = "TRANSITIONORDER")
    private Integer transitionOrder;

    @Column(name = "TRANSITIONSUBORDER")
    private Integer transitionSubOrder;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;
}
