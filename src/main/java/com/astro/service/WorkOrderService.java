package com.astro.service;


import com.astro.dto.workflow.ProcurementDtos.WorkOrderDto.WorkOrderRequestDTO;
import com.astro.dto.workflow.ProcurementDtos.WorkOrderDto.WorkOrderResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.WorkOrderDto.woWithTenderAndIndentResponseDTO;

import java.util.List;

public interface WorkOrderService {

    public WorkOrderResponseDTO createWorkOrder(WorkOrderRequestDTO workOrderRequestDTO);


    public WorkOrderResponseDTO updateWorkOrder(String woId, WorkOrderRequestDTO WorkOrderRequestDTO);
    public List<WorkOrderResponseDTO> getAllWorkOrders();
    public woWithTenderAndIndentResponseDTO getWorkOrderById(String woId);
    public void deleteWorkOrder(String woId);


}
