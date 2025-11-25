package com.astro.repository;

import com.astro.entity.ProjectMaster;
import io.netty.util.AsyncMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMaster, String> {

    Optional<ProjectMaster> findByProjectNameDescription(String projectNameDescription);

   Optional<ProjectMaster> findByProjectCode(String projectName);

    List<ProjectMaster> findByProjectCodeIn(Set<String> projectCodes);
}
