package com.astro.repository;

import com.astro.entity.WorkMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkMasterRepository extends JpaRepository<WorkMaster,String> {
}
