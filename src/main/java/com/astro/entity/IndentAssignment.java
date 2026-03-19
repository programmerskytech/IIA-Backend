package com.astro.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "indent_assignment")
public class IndentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indent_id", nullable = false)
    private String indentId;

    @Column(name = "assigned_to_employee_id", nullable = false)
    private String assignedToEmployeeId;

    @Column(name = "assigned_by_employee_id", nullable = false)
    private String assignedByEmployeeId;

    @Column(name = "assigned_date", nullable = false)
    private LocalDateTime assignedDate;

    @Column(name = "status")
    private String status = "ACTIVE";

    // ================= GETTERS & SETTERS =================
    public Long getId() {
        return id;
    }

    public String getIndentId() {
        return indentId;
    }

    public void setIndentId(String indentId) {
        this.indentId = indentId;
    }

    public String getAssignedToEmployeeId() {
        return assignedToEmployeeId;
    }

    public void setAssignedToEmployeeId(String assignedToEmployeeId) {
        this.assignedToEmployeeId = assignedToEmployeeId;
    }

    public String getAssignedByEmployeeId() {
        return assignedByEmployeeId;
    }

    public void setAssignedByEmployeeId(String assignedByEmployeeId) {
        this.assignedByEmployeeId = assignedByEmployeeId;
    }

    public LocalDateTime getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDateTime assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
