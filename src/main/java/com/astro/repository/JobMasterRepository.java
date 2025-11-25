package com.astro.repository;

import com.astro.entity.JobMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMasterRepository extends JpaRepository<JobMaster, String> {




}
