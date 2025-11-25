package com.astro.controller.ProcurementModuleController;


import com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto.ServiceOrderRequestDTO;
import com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto.ServiceOrderResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto.soWithTenderAndIndentResponseDTO;
import com.astro.dto.workflow.WorkflowTransitionDto;
import com.astro.entity.UserMaster;
import com.astro.service.ServiceOrderService;
import com.astro.service.UserService;
import com.astro.service.WorkflowService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {


    @Autowired
    private ServiceOrderService serviceOrder;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<Object> createServiceOrder(@RequestBody ServiceOrderRequestDTO requestDTO) {
        ServiceOrderResponseDTO responseDTO = serviceOrder.createServiceOrder(requestDTO);
        // Initiateing the workflow after saving the indent
        String requestId = responseDTO.getSoId();
        String workflowName = "SO Workflow";
     //   String createdBy = responseDTO.getCreatedBy();
      //  Optional<UserMaster> userMaster = userService.getUserMasterByCreatedBy(createdBy);
       // Integer userId = userMaster.get().getUserId();

        Integer userId = requestDTO.getCreatedBy();

        // Call initiateWorkflow API
     //   WorkflowTransitionDto workflowTransitionDto = workflowService.initiateWorkflow(requestId, workflowName, userId);
        // Call initiateWorkflow API
        WorkflowTransitionDto workflowTransitionDto = workflowService.initiateWorkflow(requestId, workflowName, userId);

        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @PutMapping("/{soId}")
    public ResponseEntity<Object> updateServiceOrder(@PathVariable String soId,
                                                                  @RequestBody ServiceOrderRequestDTO requestDTO) {
        ServiceOrderResponseDTO responseDTO = serviceOrder.updateServiceOrder(soId, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllServiceOrders() {
        List<ServiceOrderResponseDTO> responseDTOList = serviceOrder.getAllServiceOrders();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTOList), HttpStatus.OK);
    }

    @GetMapping("/{soId}")
    public ResponseEntity<Object> getServiceOrderById(@PathVariable String soId) {
        soWithTenderAndIndentResponseDTO responseDTO = serviceOrder.getServiceOrderById(soId);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{soId}")
    public ResponseEntity<String> deleteServiceOrder(@PathVariable String soId) {
        serviceOrder.deleteServiceOrder(soId);
        return ResponseEntity.ok("Service Order deleted successfully. Id:"+" " +soId);
    }


}
