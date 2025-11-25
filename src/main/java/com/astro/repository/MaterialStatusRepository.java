package com.astro.repository;

import com.astro.entity.MaterialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialStatusRepository extends JpaRepository<MaterialStatus,Long> {

    List<MaterialStatus> findByMaterialCode(String materialCode);
}
