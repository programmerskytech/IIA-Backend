package com.astro.service;

import com.astro.dto.workflow.ProcurementDtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TenderEvaluationService {

    public TenderEvaluationResponseDto createTenderEvaluation(TenderEvaluationRequestDto tenderEvaluationRequestDto);
    public TenderEvaluationResponseDto updateTenderEvaluation(String tenderId, TenderEvaluationRequestDto tenderEvaluationRequestDto);
    public TenderEvaluationResponseWithBitTypeAndValueDto getTenderEvaluationById(String tenderId);
    public List<TenderEvaluationResponseDto> getAllTenderEvaluations();
    public void deleteTenderEvaluation(String tenderId);

}
