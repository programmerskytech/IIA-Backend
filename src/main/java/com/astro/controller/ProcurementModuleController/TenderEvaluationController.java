package com.astro.controller.ProcurementModuleController;

import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationRequestDto;
import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationResponseDto;
import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationResponseWithBitTypeAndValueDto;
import com.astro.dto.workflow.WorkflowTransitionDto;
import com.astro.service.TenderEvaluationService;
import com.astro.service.WorkflowService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tender-evaluation")
public class TenderEvaluationController {

    @Autowired
    private TenderEvaluationService tenderEvaluationService;

    @Autowired
    private WorkflowService workflowService;
    @PostMapping
    public ResponseEntity<Object> createTenderEvaluation(
            @RequestBody TenderEvaluationRequestDto tenderEvaluationRequestDto){

        TenderEvaluationResponseDto created = tenderEvaluationService.createTenderEvaluation(tenderEvaluationRequestDto);

       // String requestId = created.getTenderId(); // Useing the indent ID as the request ID
     //   String workflowName = "Tender Evaluator Workflow";
     //   Integer userId = created.getCreatedBy();
        //initiateing Workflow API
      //  WorkflowTransitionDto workflowTransitionDto = workflowService.initiateWorkflow(requestId, workflowName, userId);

        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(created), HttpStatus.OK);

    }

    @PutMapping(value = "/{tenderId}")
    public ResponseEntity<Object> updateTenderEvaluation(
            @PathVariable String tenderId,@RequestBody TenderEvaluationRequestDto tenderEvaluationRequestDto){
        TenderEvaluationResponseDto response =tenderEvaluationService.updateTenderEvaluation(tenderId, tenderEvaluationRequestDto);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllTenderEvaluation() {
        List<TenderEvaluationResponseDto> response = tenderEvaluationService.getAllTenderEvaluations();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{tenderId}")
    public ResponseEntity<Object> getTenderEvaluationById(@PathVariable String tenderId) {
        TenderEvaluationResponseWithBitTypeAndValueDto response = tenderEvaluationService.getTenderEvaluationById(tenderId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{tenderId}")
    public ResponseEntity<String> deleteProjectMaster(@PathVariable String tenderId) {
        tenderEvaluationService.deleteTenderEvaluation(tenderId);
        return ResponseEntity.ok("Tender Evalulation deleted successfully. projectCode:"+" " + tenderId);
    }



}
