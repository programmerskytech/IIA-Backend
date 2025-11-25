package com.astro.repository;

import com.astro.entity.DepartmentMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMasterRepository extends JpaRepository<DepartmentMaster, Long> {
    
    List<DepartmentMaster> findByIsActiveTrue();
    
    boolean existsByDepartmentName(String departmentName);
}