package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProcurementDtos.IndentDto.IndentCreationResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.SreviceOrderDto.*;

import com.astro.dto.workflow.ProcurementDtos.TenderWithIndentResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.purchaseOrder.ApprovedPoListReportDto;
import com.astro.dto.workflow.ProcurementDtos.purchaseOrder.PurchaseOrderAttributesResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.purchaseOrder.pendingPoReportDto;
import com.astro.entity.ProcurementModule.*;
import com.astro.entity.ProjectMaster;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.repository.ProcurementModule.IndentIdRepository;
import com.astro.repository.ProcurementModule.ServiceOrderRepository.ServiceOrderMaterialRepository;
import com.astro.repository.ProcurementModule.ServiceOrderRepository.ServiceOrderRepository;
import com.astro.repository.ProcurementModule.TenderRequestRepository;
import com.astro.repository.ProjectMasterRepository;
import com.astro.service.IndentCreationService;
import com.astro.service.ServiceOrderService;
import com.astro.service.TenderRequestService;
import com.astro.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy; // added by abhinav

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;
    @Autowired
    private ServiceOrderMaterialRepository serviceOrderMaterialRepository;
    @Autowired
    private IndentCreationService indentCreationService;
    @Autowired
    @Lazy // added by abhinav
    private TenderRequestService tenderRequestService;
    @Autowired
    private IndentIdRepository indentIdRepository;
    @Autowired
    private TenderRequestRepository tenderRequestRepository;
    @Autowired
    private ProjectMasterRepository projectMasterRepository;

    public ServiceOrderResponseDTO createServiceOrder(ServiceOrderRequestDTO serviceOrderRequestDTO) {
        // Check if the indentorId already exists
      /*  if (serviceOrderRepository.existsById(serviceOrderRequestDTO.getSoId())) {
            ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate Service Order ID", "SO ID " + serviceOrderRequestDTO.getSoId() + " already exists.");
            throw new InvalidInputException(errorDetails);
        }

       */
/*
        // Iterate over materialDetails and check if materialCode already exists
        for (ServiceOrderMaterialRequestDTO materialRequest : serviceOrderRequestDTO.getMaterials()) {
            if (serviceOrderMaterialRepository.existsById(materialRequest.getMaterialCode())) {
                ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate Material Code",
                        "Material Code " + materialRequest.getMaterialCode() + " already exists.");
                throw new InvalidInputException(errorDetails);
            }
        }

 */
        String tenderId = serviceOrderRequestDTO.getTenderId();
        String soId = generatePoId(tenderId);

        //  String soId = "SO" + System.currentTimeMillis();
        ServiceOrder serviceOrder = new ServiceOrder();
        // serviceOrder.setSoId(serviceOrderRequestDTO.getSoId());
        serviceOrder.setSoId(soId);
        serviceOrder.setTenderId(serviceOrderRequestDTO.getTenderId());
        serviceOrder.setConsignesAddress(serviceOrderRequestDTO.getConsignesAddress());
        serviceOrder.setBillingAddress(serviceOrderRequestDTO.getBillingAddress());
        serviceOrder.setJobCompletionPeriod(serviceOrderRequestDTO.getJobCompletionPeriod());
        serviceOrder.setIfLdClauseApplicable(serviceOrderRequestDTO.getIfLdClauseApplicable());
        serviceOrder.setIncoTerms(serviceOrderRequestDTO.getIncoTerms());
        serviceOrder.setPaymentTerms(serviceOrderRequestDTO.getPaymentTerms());
        serviceOrder.setVendorName(serviceOrderRequestDTO.getVendorName());
        serviceOrder.setVendorAddress(serviceOrderRequestDTO.getVendorAddress());
        serviceOrder.setApplicablePBGToBeSubmitted(serviceOrderRequestDTO.getApplicablePBGToBeSubmitted());
        serviceOrder.setVendorsAccountNo(serviceOrderRequestDTO.getVendorsAccountNo());
        serviceOrder.setVendorsZRSCCode(serviceOrderRequestDTO.getVendorsZRSCCode());
        serviceOrder.setVendorsAccountName(serviceOrderRequestDTO.getVendorsAccountName());
        serviceOrder.setVendorId(serviceOrderRequestDTO.getVendorId());
        String Date = serviceOrderRequestDTO.getStartDateAmc();
        if (Date != null) {
            serviceOrder.setStartDateAmc(CommonUtils.convertStringToDateObject(Date));
        } else {
            serviceOrder.setStartDateAmc(null);
        }
        String endDate = serviceOrderRequestDTO.getEndDateAmc();
        if (Date != null) {
            serviceOrder.setEndDateAmc(CommonUtils.convertStringToDateObject(endDate));
        } else {
            serviceOrder.setEndDateAmc(null);
        }

        //  serviceOrder.setTotalValueOfSo(serviceOrderRequestDTO.getTotalValueOfSo());
        serviceOrder.setProjectName(serviceOrderRequestDTO.getProjectName());
        serviceOrder.setCreatedBy(serviceOrderRequestDTO.getCreatedBy());
        serviceOrder.setUpdatedBy(serviceOrderRequestDTO.getUpdatedBy());
        List<ServiceOrderMaterial> serviceOrderMaterials = serviceOrderRequestDTO.getMaterials().stream()
                .map(dto -> {
                    ServiceOrderMaterial material = new ServiceOrderMaterial();
                    material.setMaterialCode(dto.getMaterialCode());
                    // material.setSoId(serviceOrderRequestDTO.getSoId());
                    material.setSoId(soId);
                    material.setMaterialDescription(dto.getMaterialDescription());
                    material.setQuantity(dto.getQuantity());
                    material.setRate(dto.getRate());
                    material.setExchangeRate(dto.getExchangeRate());
                    material.setCurrency(dto.getCurrency());
                    material.setGst(dto.getGst());
                    material.setDuties(dto.getDuties());
                    material.setBudgetCode(dto.getBudgetCode());
                    material.setServiceOrder(serviceOrder);
                    return material;
                })
                .collect(Collectors.toList());


        serviceOrder.setMaterials(serviceOrderMaterials);
        serviceOrderRepository.save(serviceOrder);

        return mapToResponseDTO(serviceOrder);
    }

    public String generatePoId(String tenderId) {
        String numericPart = tenderId.replaceAll("\\D+", "");
        return "SO" + numericPart;
    }

    public ServiceOrderResponseDTO updateServiceOrder(String soId, ServiceOrderRequestDTO serviceOrderRequestDTO) {
        ServiceOrder existingServiceOrder = serviceOrderRepository.findById(soId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Service order not found for the provided SO ID.")
                ));
        existingServiceOrder.setTenderId(serviceOrderRequestDTO.getTenderId());
        existingServiceOrder.setConsignesAddress(serviceOrderRequestDTO.getConsignesAddress());
        existingServiceOrder.setBillingAddress(serviceOrderRequestDTO.getBillingAddress());
        existingServiceOrder.setJobCompletionPeriod(serviceOrderRequestDTO.getJobCompletionPeriod());
        existingServiceOrder.setIfLdClauseApplicable(serviceOrderRequestDTO.getIfLdClauseApplicable());
        existingServiceOrder.setIncoTerms(serviceOrderRequestDTO.getIncoTerms());
        existingServiceOrder.setPaymentTerms(serviceOrderRequestDTO.getPaymentTerms());
        existingServiceOrder.setVendorName(serviceOrderRequestDTO.getVendorName());
        existingServiceOrder.setVendorAddress(serviceOrderRequestDTO.getVendorAddress());
        existingServiceOrder.setApplicablePBGToBeSubmitted(serviceOrderRequestDTO.getApplicablePBGToBeSubmitted());
        existingServiceOrder.setVendorsAccountNo(serviceOrderRequestDTO.getVendorsAccountNo());
        existingServiceOrder.setVendorsZRSCCode(serviceOrderRequestDTO.getVendorsZRSCCode());
        existingServiceOrder.setVendorsAccountName(serviceOrderRequestDTO.getVendorsAccountName());
        existingServiceOrder.setProjectName(serviceOrderRequestDTO.getProjectName());
        existingServiceOrder.setVendorId(serviceOrderRequestDTO.getVendorId());
        //  existingServiceOrder.setTotalValueOfSo(serviceOrderRequestDTO.getTotalValueOfSo());

        existingServiceOrder.setUpdatedBy(serviceOrderRequestDTO.getUpdatedBy());
        existingServiceOrder.setCreatedBy(serviceOrderRequestDTO.getCreatedBy());
        // Update attributes
        List<ServiceOrderMaterial> existingAttributes = existingServiceOrder.getMaterials();

        // Remove orphaned attributes manually
        existingAttributes.clear();
        List<ServiceOrderMaterial> updatedMaterials = serviceOrderRequestDTO.getMaterials().stream()
                .map(dto -> {
                    ServiceOrderMaterial material = new ServiceOrderMaterial();
                    material.setMaterialCode(dto.getMaterialCode());
                    material.setSoId(existingServiceOrder.getSoId());
                    material.setMaterialDescription(dto.getMaterialDescription());
                    material.setQuantity(dto.getQuantity());
                    material.setRate(dto.getRate());
                    material.setExchangeRate(dto.getExchangeRate());
                    material.setCurrency(dto.getCurrency());
                    material.setGst(dto.getGst());
                    material.setDuties(dto.getDuties());
                    material.setBudgetCode(dto.getBudgetCode());
                    material.setServiceOrder(existingServiceOrder);
                    return material;
                })
                .collect(Collectors.toList());


        //  existingServiceOrder.setMaterials(updatedMaterials);
        //  serviceOrderRepository.save(existingServiceOrder);
        existingAttributes.addAll(updatedMaterials);
        serviceOrderRepository.save(existingServiceOrder);

        return mapToResponseDTO(existingServiceOrder);
    }

    public List<ServiceOrderResponseDTO> getAllServiceOrders() {
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
        return serviceOrders.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public soWithTenderAndIndentResponseDTO getServiceOrderById(String soId) {
        ServiceOrder serviceOrder = serviceOrderRepository.findById(soId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "service order not found for the provided soId.")
                ));
        // Fetch related Tender & Indent
        TenderWithIndentResponseDTO tenderWithIndent = tenderRequestService.getTenderRequestById(serviceOrder.getTenderId());
        soWithTenderAndIndentResponseDTO response = new soWithTenderAndIndentResponseDTO();

        response.setSoId(serviceOrder.getSoId());
        response.setTenderId(serviceOrder.getTenderId());
        response.setConsignesAddress(serviceOrder.getConsignesAddress());
        response.setBillingAddress(serviceOrder.getBillingAddress());
        response.setJobCompletionPeriod(serviceOrder.getJobCompletionPeriod());
        response.setIfLdClauseApplicable(serviceOrder.getIfLdClauseApplicable());
        response.setIncoTerms(serviceOrder.getIncoTerms());
        response.setPaymentTerms(serviceOrder.getPaymentTerms());
        response.setVendorName(serviceOrder.getVendorName());
        response.setVendorAddress(serviceOrder.getVendorAddress());
        response.setApplicablePBGToBeSubmitted(serviceOrder.getApplicablePBGToBeSubmitted());
        response.setVendorsAccountNo(serviceOrder.getVendorsAccountNo());
        response.setVendorsZRSCCode(serviceOrder.getVendorsZRSCCode());
        response.setVendorsAccountName(serviceOrder.getVendorsAccountName());
        // response.setTotalValueOfSo(ServiceOrder.getTotalValueOfSo());
        //response.setProjectName(serviceOrder.getProjectName());
        response.setCreatedBy(serviceOrder.getCreatedBy());
        response.setUpdatedBy(serviceOrder.getUpdatedBy());
        response.setCreatedDate(serviceOrder.getCreatedDate());
        response.setUpdatedDate(serviceOrder.getUpdatedDate());
        response.setTotalValueOfSo(tenderWithIndent.getTotalTenderValue());
        response.setVendorId(serviceOrder.getVendorId());
        response.setMaterials(serviceOrder.getMaterials().stream()
                .map(dto -> {
                    ServiceOrderMaterialResponseDTO material = new ServiceOrderMaterialResponseDTO();
                    material.setMaterialCode(dto.getMaterialCode());
                    material.setMaterialDescription(dto.getMaterialDescription());
                    material.setQuantity(dto.getQuantity());
                    material.setRate(dto.getRate());
                    material.setExchangeRate(dto.getExchangeRate());
                    material.setCurrency(dto.getCurrency());
                    material.setGst(dto.getGst());
                    material.setDuties(dto.getDuties());
                    material.setBudgetCode(dto.getBudgetCode()); // Associate with PurchaseOrder
                    return material;
                })
                .collect(Collectors.toList()));
        response.setTenderDetails(tenderWithIndent);
        String projectName = tenderWithIndent.getIndentResponseDTO()
                .stream()
                .findFirst()
                .map(IndentCreationResponseDTO::getProjectName)
                .orElse(null);
        BigDecimal projectLimit = tenderWithIndent.getIndentResponseDTO()
                .stream()
                .findFirst()
                .map(IndentCreationResponseDTO::getProjectLimit)
                .orElse(null);
        response.setProjectName(projectName);
        response.setProjectLimit(projectLimit);
        return response;
    }

    public void deleteServiceOrder(String soId) {

        ServiceOrder serviceOrder = serviceOrderRepository.findById(soId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "service order not found for the provided ID."
                        )
                ));
        try {
            serviceOrderRepository.delete(serviceOrder);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_ERROR,
                            "An error occurred while deleting the  so."
                    ),
                    ex
            );
        }

    }


    private ServiceOrderResponseDTO mapToResponseDTO(ServiceOrder ServiceOrder) {
        ServiceOrderResponseDTO response = new ServiceOrderResponseDTO();
        response.setSoId(ServiceOrder.getSoId());
        response.setTenderId(ServiceOrder.getTenderId());
        response.setConsignesAddress(ServiceOrder.getConsignesAddress());
        response.setBillingAddress(ServiceOrder.getBillingAddress());
        response.setJobCompletionPeriod(ServiceOrder.getJobCompletionPeriod());
        response.setIfLdClauseApplicable(ServiceOrder.getIfLdClauseApplicable());
        response.setIncoTerms(ServiceOrder.getIncoTerms());
        response.setPaymentTerms(ServiceOrder.getPaymentTerms());
        response.setVendorName(ServiceOrder.getVendorName());
        response.setVendorAddress(ServiceOrder.getVendorAddress());
        response.setApplicablePBGToBeSubmitted(ServiceOrder.getApplicablePBGToBeSubmitted());
        response.setVendorsAccountNo(ServiceOrder.getVendorsAccountNo());
        response.setVendorsZRSCCode(ServiceOrder.getVendorsZRSCCode());
        response.setVendorsAccountName(ServiceOrder.getVendorsAccountName());
        response.setVendorId(ServiceOrder.getVendorId());
        // response.setTotalValueOfSo(ServiceOrder.getTotalValueOfSo());
        response.setProjectName(ServiceOrder.getProjectName());
        response.setCreatedBy(ServiceOrder.getCreatedBy());
        response.setUpdatedBy(ServiceOrder.getUpdatedBy());
        response.setCreatedDate(ServiceOrder.getCreatedDate());
        response.setUpdatedDate(ServiceOrder.getUpdatedDate());
        response.setMaterials(ServiceOrder.getMaterials().stream()
                .map(dto -> {
                    ServiceOrderMaterialResponseDTO material = new ServiceOrderMaterialResponseDTO();
                    material.setMaterialCode(dto.getMaterialCode());
                    material.setMaterialDescription(dto.getMaterialDescription());
                    material.setQuantity(dto.getQuantity());
                    material.setRate(dto.getRate());
                    material.setExchangeRate(dto.getExchangeRate());
                    material.setCurrency(dto.getCurrency());
                    material.setGst(dto.getGst());
                    material.setDuties(dto.getDuties());
                    material.setBudgetCode(dto.getBudgetCode()); // Associate with PurchaseOrder
                    return material;
                })
                .collect(Collectors.toList()));

        List<String> indentIds = indentIdRepository.findTenderWithIndent(ServiceOrder.getTenderId());

        // Calculate total tender value by summing totalPriceOfAllMaterials of all indents
        BigDecimal totalTenderValue = indentIds.stream()
                .map(indentCreationService::getIndentById) // Fetch Indent data
                .map(IndentCreationResponseDTO::getTotalPriceOfAllMaterials) // Extract total price
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up values
        response.setTotalValue(totalTenderValue);
        System.out.println("tottalTenderValue" + totalTenderValue);
        Optional<TenderRequest> tenderRequest = tenderRequestRepository.findByTenderId(ServiceOrder.getTenderId());

        String projectName = tenderRequest.map(TenderRequest::getProjectName).orElse(null);
        response.setProjectName(projectName);
        System.out.println("projectName:" + projectName);
        BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectNameDescription(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);
        System.out.println("allocatedAmount: " + allocatedAmount);
        return response;
    }


    @Override
    public List<ApprovedSoListReportDto> getApprovedSoListReport(String startDate, String endDate, Integer userId, String roleName) {
        LocalDate from = CommonUtils.convertStringToDateObject(startDate);
        LocalDate to = CommonUtils.convertStringToDateObject(endDate);

      //  List<Object[]> rows = serviceOrderRepository.getApprovedSoReport(from, to);
        List<Object[]> rows;
        if ("Indent Creator".equalsIgnoreCase(roleName)) {
            rows = serviceOrderRepository.getApprovedUserIdsSoReport(from, to, userId);
            System.out.println(roleName);
        } else {
            rows =serviceOrderRepository.getApprovedSoReport(from, to);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return rows.stream().map(row -> {
            ApprovedSoListReportDto dto = new ApprovedSoListReportDto();
            dto.setApprovedDate(CommonUtils.convertDateToString(row[0] != null
                    ? ((Timestamp) row[0]).toLocalDateTime().toLocalDate()
                    : null));
            dto.setSoId((String) row[1]);
            dto.setVendorName((String) row[2]);
            dto.setValue(row[3] != null
                    ? ((BigDecimal) row[3]).doubleValue()
                    : 0.0
            );
            dto.setTenderId((String) row[4]);
            dto.setProject((String) row[5]);
            dto.setVendorId((String) row[6]);
            dto.setIndentIds((String) row[7]);
            dto.setModeOfProcurement((String) row[8]);

            String json = (String) row[9];
            try {
                List<ServiceOrderMaterialResponseDTO> materials = mapper.readValue(
                        json,
                        mapper.getTypeFactory().constructCollectionType(
                                List.class,
                                ServiceOrderMaterialResponseDTO.class
                        )
                );
                dto.setMaterials(materials);
            } catch (Exception e) {
                dto.setMaterials(new ArrayList<>());
            }

            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<PendingSoReportDto> getPendingSoReport(String startDate, String endDate , Integer userId, String roleName) {

        LocalDate from = CommonUtils.convertStringToDateObject(startDate);
        LocalDate to = CommonUtils.convertStringToDateObject(endDate);

       // List<Object[]> rows = serviceOrderRepository.getPendingSoReport(from, to);
        List<Object[]> rows;
        if ("Indent Creator".equalsIgnoreCase(roleName)) {
            rows = serviceOrderRepository.getPendingSoUserIdReport(from, to, userId);
            System.out.println(roleName);
        } else {
            rows =serviceOrderRepository.getPendingSoReport(from, to);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        List<PendingSoReportDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            PendingSoReportDto dto = new PendingSoReportDto();

            String soId = (String) row[0];
            dto.setSoId(soId);
            dto.setTenderId((String) row[1]);
            dto.setIndentIds((String) row[2]);

            if (row[3] != null) {
                dto.setValue(((BigDecimal) row[3]));
            }
            dto.setVendorName((String) row[4]);

            if (row[5] != null) {
                LocalDate submitted = ((Timestamp) row[5]).toLocalDateTime().toLocalDate();
                dto.setSubmittedDate(CommonUtils.convertDateToString(submitted));
            }

            dto.setPendingWith((String) row[6]);

            if (row[7] != null) {
                LocalDate pending = ((Timestamp) row[7]).toLocalDateTime().toLocalDate();
                dto.setPendingFrom(CommonUtils.convertDateToString(pending));
            }

            dto.setStatus((String) row[8]);
            dto.setAsOnDate(LocalDate.now());

            String materialsJson = (String) row[9];
            try {
                List<ServiceOrderMaterialResponseDTO> materials = mapper.readValue(
                        materialsJson,
                        mapper.getTypeFactory().constructCollectionType(
                                List.class,
                                ServiceOrderMaterialResponseDTO.class
                        )
                );
                dto.setMaterials(materials);
            } catch (Exception e) {
                dto.setMaterials(new ArrayList<>());
            }

            result.add(dto);
        }

        return result;
    }


}
