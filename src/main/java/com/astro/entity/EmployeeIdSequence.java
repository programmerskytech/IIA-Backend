package com.astro.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "employee_id_sequence")
public class EmployeeIdSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;
}
