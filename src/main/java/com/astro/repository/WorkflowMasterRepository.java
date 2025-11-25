package com.astro.repository;

import com.astro.entity.WorkflowMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowMasterRepository extends JpaRepository<WorkflowMaster, Integer> {
    WorkflowMaster findByWorkflowName(String workflowName);
}
