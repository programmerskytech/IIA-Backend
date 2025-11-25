package com.astro.repository;

import com.astro.entity.UomMaster;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UomMasterRepository extends JpaRepository<UomMaster, String> {




}
