package com.astro.repository;

import com.astro.entity.DesignationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationMasterRepository extends JpaRepository<DesignationMaster, Long> {
    
    List<DesignationMaster> findByIsActiveTrue();
    
    boolean existsByDesignationName(String designationName);
}