package com.astro.repository;

import com.astro.entity.IndentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IndentAssignmentRepository extends JpaRepository<IndentAssignment, Long> {

    List<IndentAssignment> findByAssignedToEmployeeIdAndStatus(String employeeId, String status);

    Optional<IndentAssignment> findByIndentIdAndStatus(String indentId, String status);

    List<IndentAssignment> findByIndentId(String indentId);
}
