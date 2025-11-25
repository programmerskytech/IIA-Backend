package com.astro.repository;

import com.astro.entity.MaterialCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialCreationRepository extends JpaRepository<MaterialCreation, String> {



}
