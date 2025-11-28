package com.astro.repository.ProcurementModule.IndentCreation;

import com.astro.entity.ProcurementModule.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDetailsRepository extends JpaRepository<JobDetails, Long> {

    List<JobDetails> findByIndentCreation_IndentId(String indentId);

    @Query("SELECT DISTINCT j.indentCreation.indentId FROM JobDetails j WHERE j.jobCode = :jobCode")
    List<String> findIndentIdsByJobCode(@Param("jobCode") String jobCode);
}