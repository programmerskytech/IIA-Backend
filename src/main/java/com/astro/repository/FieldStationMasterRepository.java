package com.astro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astro.entity.FieldStationMasterEntity;

@Repository
public interface FieldStationMasterRepository extends JpaRepository<FieldStationMasterEntity, Integer> {
    
}
