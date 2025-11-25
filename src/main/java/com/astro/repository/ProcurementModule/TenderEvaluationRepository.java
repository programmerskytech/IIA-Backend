package com.astro.repository.ProcurementModule;

import com.astro.entity.ProcurementModule.TenderEvaluation;
import com.astro.entity.ProcurementModule.TenderRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenderEvaluationRepository extends JpaRepository<TenderEvaluation,String> {


    TenderEvaluation findByTenderId(String tenderId);
}
