package com.astro.controller.ProcurementModuleController;


import com.astro.dto.workflow.AssignEmployeeToIndentDto;
import com.astro.dto.workflow.ProcurementDtos.IndentDto.*;

import com.astro.dto.workflow.ProcurementDtos.IndentWorkflowStatusDto;
import com.astro.dto.workflow.WorkflowTransitionDto;
import com.astro.entity.ProcurementModule.IndentCreation;
import com.astro.entity.UserMaster;
import com.astro.repository.ProcurementModule.IndentCreation.IndentCreationRepository;
import com.astro.repository.UserMasterRepository;
import com.astro.service.IndentCreationService;

import com.astro.service.UserService;
import com.astro.service.WorkflowService;
import com.astro.util.ResponseBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/indents")
public class IndentCreationController {

    @Autowired
    private IndentCreationService indentCreationService;

    @Autowired
    private IndentCreationRepository indentCreationRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private UserService userService;
    private static final Logger log = LoggerFactory.getLogger(IndentCreationController.class);
//(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

 @PostMapping
 public ResponseEntity<Object> createIndent(
         @RequestBody IndentCreationRequestDTO indentRequestDTO
) throws JsonProcessingException {

     IndentCreationResponseDTO responseDTO = indentCreationService.createIndent(indentRequestDTO);

     String requestId = responseDTO.getIndentId(); // Useing the indent ID as the request ID
     String workflowName = "Indent Workflow";
   //String createdBy = indentRequestDTO.getCreatedBy();
   //Optional<UserMaster> userMaster = userService.getUserMasterByCreatedBy(createdBy);
    //Integer userId = userMaster.get().getUserId();
     Integer userId = indentRequestDTO.getCreatedBy();

     //initiateing Workflow API
    WorkflowTransitionDto workflowTransitionDto = workflowService.initiateWorkflow(requestId, workflowName, userId);

     return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
}


    @PutMapping(value = "/{indentId}")
    public ResponseEntity<Object> updateIndent(
            @PathVariable String indentId, @RequestBody IndentCreationRequestDTO indentRequestDTO

    ) throws JsonProcessingException {

        IndentCreationResponseDTO responseDTO = indentCreationService.updateIndent(indentId, indentRequestDTO);


        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }


    // Get Indent by ID
    @GetMapping("/{indentId}")
    public ResponseEntity<Object> getIndentById(@PathVariable String indentId) {
        IndentCreationResponseDTO responseDTO = indentCreationService.getIndentById(indentId);
        // Set filenames for the uploaded files in the response DTO
       // responseDTO.setUploadingPriorApprovalsFileName(responseDTO.getUploadingPriorApprovalsFileName());
       // responseDTO.setUploadTenderDocumentsFileName(responseDTO.getUploadTenderDocumentsFileName());
     //   responseDTO.setUploadGOIOrRFPFileName(responseDTO.getUploadGOIOrRFPFileName());
      //  responseDTO.setUploadPACOrBrandPACFileName(responseDTO.getUploadPACOrBrandPACFileName());
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    } @GetMapping("/IndentDataForTender/{indentId}")
    public ResponseEntity<Object> getIndentDataForTenderById(@PathVariable String indentId) throws IOException {
        IndentCreationResponseDTO responseDTO = indentCreationService.getIndentDataForTenderById(indentId);
        // Set filenames for the uploaded files in the response DTO
        // responseDTO.setUploadingPriorApprovalsFileName(responseDTO.getUploadingPriorApprovalsFileName());
        // responseDTO.setUploadTenderDocumentsFileName(responseDTO.getUploadTenderDocumentsFileName());
        //   responseDTO.setUploadGOIOrRFPFileName(responseDTO.getUploadGOIOrRFPFileName());
        //  responseDTO.setUploadPACOrBrandPACFileName(responseDTO.getUploadPACOrBrandPACFileName());
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("/indentData/{indentId}")
    public ResponseEntity<Object> getIndentDataById(@PathVariable String indentId) throws IOException {
        IndentDataResponseDto responseDTO = indentCreationService.getIndentDataById(indentId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping("/indentStatus/{indentId}")
    public ResponseEntity<Object> getIndentStauts(@PathVariable String indentId,
    @RequestParam Integer userId , @RequestParam String roleName) throws IOException {
        List<IndentWorkflowStatusDto> responseDTO = indentCreationService.getIndentWorkflowStatus(indentId, userId, roleName);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @GetMapping("/materialHistory/{materialCode}")
    public ResponseEntity<Object> getMaterialHistory(@PathVariable String materialCode) {
        List<materialHistoryDto> responseDTO = indentCreationService.getIndentIdAndUserId(materialCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    // Get All Indents
    @GetMapping
    public ResponseEntity<Object> getAllIndents() {
        List<IndentCreationResponseDTO> responseDTOs = indentCreationService.getAllIndents();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTOs), HttpStatus.OK);
    }

    @DeleteMapping("/{indentId}")
    public ResponseEntity<String> deleteIndent(@PathVariable String indentId) {
        indentCreationService.deleteIndent(indentId);
        return ResponseEntity.ok("indent deleted successfully. Id:"+" " +indentId);
    }


    @GetMapping("/search")
    public ResponseEntity<Object> searchIndents(
            @RequestParam String type,
            @RequestParam String value
    ) {
        List<SearchIndentIdDto> result = indentCreationService.searchIndentIds(type, value);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(result), HttpStatus.OK);

    }

    @PostMapping("/assign-employee")
    public ResponseEntity<Object> assignEmployee(@RequestBody AssignEmployeeToIndentDto dto) {
        String response = indentCreationService.assignEmployeeToIndent(dto);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @PutMapping("indent/cancel")
    public ResponseEntity<?> cancelIndent(@RequestBody CancelIndentRequestDto request) {

          String  response = indentCreationService.cancelIndent(request);
        return new ResponseEntity<>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);

    }





}
