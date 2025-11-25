package com.astro.repository;

import com.astro.entity.ActionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionMasterRepository extends JpaRepository<ActionMaster, Integer> {
}
