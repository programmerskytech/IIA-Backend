package com.astro.repository;

import com.astro.entity.EmployeeIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeIdSequenceRepository extends JpaRepository<EmployeeIdSequence, Long> {

    @Query("SELECT MAX(i.employeeId) FROM EmployeeIdSequence i")
    Integer findMaxEmployeeId();
}
