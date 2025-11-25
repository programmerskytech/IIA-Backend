package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANSITION_CONDITION_MASTER")
@Data
public class TransitionConditionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONDITIONID")
    private Integer conditionId;

    @Column(name = "WORKFLOWID")
    private Integer workflowId;

    @Column(name = "CONDITIONKEY")
    private String conditionKey;

    @Column(name = "CONDITIONVALUE")
    private String conditionValue;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;
}
