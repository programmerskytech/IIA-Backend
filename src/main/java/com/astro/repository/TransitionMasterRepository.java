package com.astro.repository;

import com.astro.entity.TransitionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitionMasterRepository extends JpaRepository<TransitionMaster, Integer> {
    TransitionMaster findByWorkflowIdAndTransitionOrder(Integer workflowId, Integer order);
    List<TransitionMaster> findByWorkflowId(Integer workflowId);
    TransitionMaster findByWorkflowIdAndNextRoleId(Integer workflowId, Integer roleId);
    TransitionMaster findByWorkflowIdAndTransitionOrderAndTransitionSubOrder(Integer workflowId, Integer order, Integer subOrder);
}
