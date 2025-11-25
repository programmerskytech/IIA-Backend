package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.AssignEmployeeToIndentDto;
import com.astro.dto.workflow.ProcurementDtos.IndentDto.*;
import com.astro.dto.workflow.ProcurementDtos.IndentWorkflowStatusDto;
import com.astro.dto.workflow.ProcurementDtos.TechnoMomReportDTO;
import com.astro.entity.*;
import com.astro.entity.ProcurementModule.IndentCreation;
import com.astro.entity.ProcurementModule.MaterialDetails;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.*;
import com.astro.repository.ProcurementModule.IndentCreation.IndentCreationRepository;
import com.astro.repository.ProcurementModule.IndentCreation.IndentMaterialMappingRepository;
import com.astro.repository.ProcurementModule.IndentCreation.MaterialDetailsRepository;
import com.astro.service.IndentCreationService;
import com.astro.util.CommonUtils;
import com.astro.util.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.astro.util.CommonUtils.convertImageToBase64;


@Service
public class IndentCreationServiceImpl implements IndentCreationService {

    @Autowired
    private IndentCreationRepository indentCreationRepository;

    @Autowired
    private MaterialDetailsRepository materialDetailsRepository;
    @Autowired
    private IndentMaterialMappingRepository indentMaterialMappingRepository;
    @Autowired
    private ProjectMasterRepository projectMasterRepository;
    @Autowired
    private MaterialDetailsRepository materialRepo;
    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;

    @Autowired
    private VendorNamesForJobWorkMaterialRepository vendorNameRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeDepartmentMasterRepository employeeDepartmentMasterRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private VendorMasterRepository vendorMasterRepository;
    @Value("${filePath}")
    private String bp;
    private final String basePath;

    public IndentCreationServiceImpl(@Value("${filePath}") String bp) {
        this.basePath = bp + "/Indent";
    }

    @Transactional
    public IndentCreationResponseDTO createIndent(IndentCreationRequestDTO indentRequestDTO) {


        // Iterate over materialDetails and check if materialCode already exists
        String materialCategory = null;
        for (MaterialDetailsRequestDTO materialRequest : indentRequestDTO.getMaterialDetails()) {
          /* if (materialDetailsRepository.existsById(materialRequest.getMaterialCode())) {
                ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate Material Code",
                        "Material Code " + materialRequest.getMaterialCode() + " already exists.");
                throw new InvalidInputException(errorDetails);
            }

           */


            if (materialCategory == null) {
                materialCategory = materialRequest.getMaterialCategory();
            } else if (!materialCategory.equals(materialRequest.getMaterialCategory())) {
                throw new InvalidInputException(new ErrorDetails(400, 2, "Inconsistent Material Category",
                        "All materials must have the same material category."));
            }

        }
        Integer maxNumber = indentCreationRepository.findMaxIndentNumber();
        int nextNumber = (maxNumber == null) ? 1001 : maxNumber + 1;

        String indentId = "IND" + nextNumber;

        System.out.println("IndentId" + indentId);

        IndentCreation indentCreation = new IndentCreation();
        //   String indentId = "IND" + System.currentTimeMillis();
        indentCreation.setIndentorName(indentRequestDTO.getIndentorName());
        // indentCreation.setIndentId(indentRequestDTO.getIndentId());
        indentCreation.setIndentId(indentId);
        indentCreation.setIndentNumber(nextNumber);
        indentCreation.setIndentorMobileNo(indentRequestDTO.getIndentorMobileNo());
        indentCreation.setIndentorEmailAddress(indentRequestDTO.getIndentorEmailAddress());
        indentCreation.setConsignesLocation(indentRequestDTO.getConsignesLocation());
        // indentCreation.setUploadingPriorApprovalsFileName(indentRequestDTO.getUploadingPriorApprovalsFileName());
        //   String prior = saveBase64Files(indentRequestDTO.getUploadingPriorApprovalsFileName(), basePath);
        //   indentCreation.setUploadingPriorApprovalsFileName(prior);
        indentCreation.setProjectName(indentRequestDTO.getProjectName());
        indentCreation.setIsPreBitMeetingRequired(indentRequestDTO.getIsPreBidMeetingRequired());
        String Date = indentRequestDTO.getPreBidMeetingDate();
        if (Date != null) {
            indentCreation.setPreBidMeetingDate(CommonUtils.convertStringToDateObject(Date));

        } else {
            indentCreation.setPreBidMeetingDate(null);
        }
        // indentCreation.setPreBidMeetingDate(CommonUtils.convertStringToDateObject(Date));
        indentCreation.setPreBidMeetingVenue(indentRequestDTO.getPreBidMeetingVenue());
        indentCreation.setIsItARateContractIndent(indentRequestDTO.getIsItARateContractIndent());
        indentCreation.setEstimatedRate(indentRequestDTO.getEstimatedRate());
        indentCreation.setPeriodOfContract(indentRequestDTO.getPeriodOfContract());
        indentCreation.setSingleAndMultipleJob(indentRequestDTO.getSingleAndMultipleJob());
        indentCreation.setFileType(indentRequestDTO.getFileType());
        indentCreation.setEmployeeDepartment(indentRequestDTO.getEmployeeDepartment());
        indentCreation.setBuyBackAmount(indentRequestDTO.getBuyBackAmount());
        indentCreation.setProprietaryAndLimitedDeclaration(indentRequestDTO.getProprietaryAndLimitedDeclaration());

        // indentCreation.setTechnicalSpecificationsFileName(indentRequestDTO.getTechnicalSpecificationsFileName());
        //  indentCreation.setDraftEOIOrRFPFileName(indentRequestDTO.getDraftEOIOrRFPFileName());
        // indentCreation.setUploadPACOrBrandPACFileName(indentRequestDTO.getUploadPACOrBrandPACFileName());
        if (indentRequestDTO.getUploadingPriorApprovalsFileName() == null || indentRequestDTO.getUploadingPriorApprovalsFileName().isEmpty()) {
            indentCreation.setUploadingPriorApprovalsFileName(null);
        } else {
            String prior = saveBase64Files(indentRequestDTO.getUploadingPriorApprovalsFileName(), basePath);
            indentCreation.setUploadingPriorApprovalsFileName(prior);
        }

        if (indentRequestDTO.getTechnicalSpecificationsFileName() == null || indentRequestDTO.getTechnicalSpecificationsFileName().isEmpty()) {
            indentCreation.setTechnicalSpecificationsFileName(null);
        } else {
            String technical = saveBase64Files(indentRequestDTO.getTechnicalSpecificationsFileName(), basePath);
            indentCreation.setTechnicalSpecificationsFileName(technical);
        }

        if (indentRequestDTO.getDraftEOIOrRFPFileName() == null || indentRequestDTO.getDraftEOIOrRFPFileName().isEmpty()) {
            indentCreation.setDraftEOIOrRFPFileName(null);
        } else {
            String draft = saveBase64Files(indentRequestDTO.getDraftEOIOrRFPFileName(), basePath);
            indentCreation.setDraftEOIOrRFPFileName(draft);
        }

        if (indentRequestDTO.getUploadPACOrBrandPACFileName() == null || indentRequestDTO.getUploadPACOrBrandPACFileName().isEmpty()) {
            indentCreation.setUploadPACOrBrandPACFileName(null);
        } else {
            String pac = saveBase64Files(indentRequestDTO.getUploadPACOrBrandPACFileName(), basePath);
            indentCreation.setUploadPACOrBrandPACFileName(pac);
        }

        indentCreation.setBrandPac(indentRequestDTO.getBrandPac());
        indentCreation.setJustification(indentRequestDTO.getJustification());
        indentCreation.setBrandAndModel(indentRequestDTO.getBrandAndModel());
        indentCreation.setPurpose(indentRequestDTO.getPurpose());
        indentCreation.setQuarter(indentRequestDTO.getQuarter());
        indentCreation.setProprietaryJustification(indentRequestDTO.getProprietaryJustification());
        indentCreation.setBuyBack(indentRequestDTO.getBuyBack());
        //   indentCreation.setUploadBuyBackFileNames(indentRequestDTO.getUploadBuyBackFileNames());
        if (indentRequestDTO.getUploadBuyBackFileNames() == null || indentRequestDTO.getUploadBuyBackFileNames().isEmpty()) {
            indentCreation.setUploadBuyBackFileNames(null);
        } else {
            String buy = saveBase64Files(indentRequestDTO.getUploadBuyBackFileNames(), basePath);
            indentCreation.setUploadBuyBackFileNames(buy);
        }
        indentCreation.setSerialNumber(indentRequestDTO.getSerialNumber());
        indentCreation.setModelNumber(indentRequestDTO.getModelNumber());
        String dateOfPurchase = indentRequestDTO.getDateOfPurchase();
        if (dateOfPurchase != null) {
            indentCreation.setDateOfPurchase(CommonUtils.convertStringToDateObject(dateOfPurchase));

        } else {
            indentCreation.setDateOfPurchase(null);
        }
        indentCreation.setReason(indentRequestDTO.getReason());
        indentCreation.setCreatedBy(indentRequestDTO.getCreatedBy());
        indentCreation.setUpdatedBy(indentRequestDTO.getUpdatedBy());
        //   indentCreationRepository.save(indentCreation);

        // Save MaterialDetails entities and link them to the indentCreation
        List<MaterialDetails> materialDetailsList = indentRequestDTO.getMaterialDetails().stream().map(materialRequest -> {

            MaterialDetails material = new MaterialDetails();
            material.setMaterialCode(materialRequest.getMaterialCode());
            //  material.setIndentId(indentRequestDTO.getIndentId());
          //  material.setIndentId(indentId);
            material.setMaterialDescription(materialRequest.getMaterialDescription());
            material.setQuantity(materialRequest.getQuantity());
            material.setUnitPrice(materialRequest.getUnitPrice());
            material.setUom(materialRequest.getUom());
            material.setModeOfProcurement(materialRequest.getModeOfProcurement());
            material.setCurrency(materialRequest.getCurrency());
            // Calculate total price
            BigDecimal totalPrice = materialRequest.getQuantity().multiply(materialRequest.getUnitPrice());
            material.setTotalPrice(totalPrice);
            material.setBudgetCode(materialRequest.getBudgetCode());
            material.setMaterialCategory(materialRequest.getMaterialCategory());
            material.setMaterialSubCategory(materialRequest.getMaterialSubCategory());


            material.setIndentCreation(indentCreation);
            //  material.setIndentCreations(indentCreation);  // Associate with the current indentCreation
            return material;
        }).collect(Collectors.toList());

        indentCreation.setMaterialDetails(materialDetailsList);
        // Calculate sum of all material total prices
        BigDecimal totalIndentPrice = materialDetailsList.stream()
                .map(MaterialDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        indentCreation.setTotalIntentValue(totalIndentPrice);


        indentCreationRepository.save(indentCreation);
        //  List<MaterialDetails> savedMaterials = materialDetailsRepository.saveAll(materialDetailsList);
        List<MaterialDetails> savedMaterials = materialDetailsRepository.findByIndentCreation_IndentId(indentId);

        // Save VendorNames for each Material
        List<VendorNamesForJobWorkMaterial> vendorList = new ArrayList<>();
        for (int i = 0; i < materialDetailsList.size(); i++) {
            MaterialDetails savedMaterial = savedMaterials.get(i);
            MaterialDetailsRequestDTO materialRequest = indentRequestDTO.getMaterialDetails().get(i);
            System.out.println("Material ID: " + savedMaterial.getId() + ", Material Code: " + savedMaterial.getMaterialCode());

            if (materialRequest.getVendorNames() != null && !materialRequest.getVendorNames().isEmpty()) {
                for (String vendorName : materialRequest.getVendorNames()) {
                    VendorNamesForJobWorkMaterial vendor = new VendorNamesForJobWorkMaterial();
                    vendor.setVendorName(vendorName);
                    vendor.setMaterialId(savedMaterial.getId());
                    vendor.setIndentId(indentId);
                    vendor.setMaterialCode(savedMaterial.getMaterialCode());
                    vendorList.add(vendor);
                }
            }
        }

        // Save all vendor records
        if (!vendorList.isEmpty()) {
            vendorNameRepository.saveAll(vendorList);
        }
        IndentCreation indentCreations = indentCreationRepository.findByIndentId(indentId);
        return mapToResponseDTO(indentCreations);
    }

    public String saveBase64Files(List<String> base64Files, String basePath) {
        try {
            List<String> fileNames = new ArrayList<>();
            for (String base64File : base64Files) {
                String fileName = CommonUtils.saveBase64Image(base64File, basePath);
                fileNames.add(fileName);
            }
            return String.join(",", fileNames);
        } catch (Exception e) {
            throw new InvalidInputException(new ErrorDetails(
                    AppConstant.FILE_UPLOAD_ERROR,
                    AppConstant.USER_INVALID_INPUT,
                    AppConstant.ERROR_TYPE_CORRUPTED,
                    "Error while uploading files."));
        }
    }

    public IndentCreationResponseDTO updateIndent(String indentId, IndentCreationRequestDTO indentRequestDTO) {
        IndentCreation indentCreation = indentCreationRepository.findById(indentId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "indent not found for the provided indent ID.")
                ));

        // Update indent fields
        indentCreation.setIndentorName(indentRequestDTO.getIndentorName());
        indentCreation.setIndentorMobileNo(indentRequestDTO.getIndentorMobileNo());
        indentCreation.setIndentorEmailAddress(indentRequestDTO.getIndentorEmailAddress());
        indentCreation.setConsignesLocation(indentRequestDTO.getConsignesLocation());
        indentCreation.setProjectName(indentRequestDTO.getProjectName());
        indentCreation.setIsPreBitMeetingRequired(indentRequestDTO.getIsPreBidMeetingRequired());
        indentCreation.setProprietaryAndLimitedDeclaration(indentRequestDTO.getProprietaryAndLimitedDeclaration());

        String preBidDate = indentRequestDTO.getPreBidMeetingDate();
        indentCreation.setPreBidMeetingDate(preBidDate != null ? CommonUtils.convertStringToDateObject(preBidDate) : null);

        indentCreation.setPreBidMeetingVenue(indentRequestDTO.getPreBidMeetingVenue());
        indentCreation.setIsItARateContractIndent(indentRequestDTO.getIsItARateContractIndent());
        indentCreation.setEstimatedRate(indentRequestDTO.getEstimatedRate());
        indentCreation.setPeriodOfContract(indentRequestDTO.getPeriodOfContract());
        indentCreation.setSingleAndMultipleJob(indentRequestDTO.getSingleAndMultipleJob());

        if (indentRequestDTO.getUploadBuyBackFileNames() == null || indentRequestDTO.getUploadBuyBackFileNames().isEmpty()) {
            indentCreation.setUploadBuyBackFileNames(null);
        } else {
            String buy = saveBase64Files(indentRequestDTO.getUploadBuyBackFileNames(), basePath);
            indentCreation.setUploadBuyBackFileNames(buy);
        }
        if (indentRequestDTO.getUploadingPriorApprovalsFileName() == null || indentRequestDTO.getUploadingPriorApprovalsFileName().isEmpty()) {
            indentCreation.setUploadingPriorApprovalsFileName(null);
        } else {
            String prior = saveBase64Files(indentRequestDTO.getUploadingPriorApprovalsFileName(), basePath);
            indentCreation.setUploadingPriorApprovalsFileName(prior);
        }
        if (indentRequestDTO.getTechnicalSpecificationsFileName() == null || indentRequestDTO.getTechnicalSpecificationsFileName().isEmpty()) {
            indentCreation.setTechnicalSpecificationsFileName(null);
        } else {
            String technical = saveBase64Files(indentRequestDTO.getTechnicalSpecificationsFileName(), basePath);
            indentCreation.setTechnicalSpecificationsFileName(technical);
        }
        if (indentRequestDTO.getDraftEOIOrRFPFileName() == null || indentRequestDTO.getDraftEOIOrRFPFileName().isEmpty()) {
            indentCreation.setDraftEOIOrRFPFileName(null);
        } else {
            String draft = saveBase64Files(indentRequestDTO.getDraftEOIOrRFPFileName(), basePath);
            indentCreation.setDraftEOIOrRFPFileName(draft);
        }
        if (indentRequestDTO.getUploadPACOrBrandPACFileName() == null || indentRequestDTO.getUploadPACOrBrandPACFileName().isEmpty()) {
            indentCreation.setUploadPACOrBrandPACFileName(null);
        } else {

            String pac = saveBase64Files(indentRequestDTO.getUploadPACOrBrandPACFileName(), basePath);
            indentCreation.setUploadPACOrBrandPACFileName(pac);
        }


        indentCreation.setBrandPac(indentRequestDTO.getBrandPac());
        indentCreation.setJustification(indentRequestDTO.getJustification());
        indentCreation.setBrandAndModel(indentRequestDTO.getBrandAndModel());
        indentCreation.setQuarter(indentRequestDTO.getQuarter());
        indentCreation.setPurpose(indentRequestDTO.getPurpose());
        indentCreation.setProprietaryJustification(indentRequestDTO.getProprietaryJustification());
        indentCreation.setReason(indentRequestDTO.getReason());
        indentCreation.setBuyBack(indentRequestDTO.getBuyBack());
        indentCreation.setSerialNumber(indentRequestDTO.getSerialNumber());
        indentCreation.setModelNumber(indentRequestDTO.getModelNumber());

        String purchaseDate = indentRequestDTO.getDateOfPurchase();
        indentCreation.setDateOfPurchase(purchaseDate != null ? CommonUtils.convertStringToDateObject(purchaseDate) : null);

        indentCreation.setFileType(indentRequestDTO.getFileType());
        indentCreation.setUpdatedBy(indentRequestDTO.getUpdatedBy());
        indentCreation.setCreatedBy(indentRequestDTO.getCreatedBy());
        indentCreation.setEmployeeDepartment(indentRequestDTO.getEmployeeDepartment());


        List<MaterialDetails> existingMaterials = indentCreation.getMaterialDetails();
        Map<String, MaterialDetails> existingMap = existingMaterials.stream()
                .filter(m -> m.getMaterialCode() != null)
                .collect(Collectors.toMap(MaterialDetails::getMaterialCode, m -> m));

        for (MaterialDetailsRequestDTO materialRequest : indentRequestDTO.getMaterialDetails()) {
            MaterialDetails material = existingMap.getOrDefault(
                    materialRequest.getMaterialCode(), new MaterialDetails());

            material.setMaterialCode(materialRequest.getMaterialCode());
            material.setMaterialDescription(materialRequest.getMaterialDescription());
            material.setQuantity(materialRequest.getQuantity());
            material.setUnitPrice(materialRequest.getUnitPrice());
            material.setUom(materialRequest.getUom());
            material.setModeOfProcurement(materialRequest.getModeOfProcurement());
            material.setCurrency(materialRequest.getCurrency());
            material.setTotalPrice(materialRequest.getQuantity().multiply(materialRequest.getUnitPrice()));
            material.setBudgetCode(materialRequest.getBudgetCode());
            material.setMaterialCategory(materialRequest.getMaterialCategory());
            material.setMaterialSubCategory(materialRequest.getMaterialSubCategory());
            material.setIndentCreation(indentCreation);

            MaterialDetails savedMaterial = materialDetailsRepository.save(material);

            if (savedMaterial.getId() != null) {
                List<VendorNamesForJobWorkMaterial> existingVendors = vendorNameRepository
                        .findByIndentIdAndMaterialIdAndMaterialCode(indentCreation.getIndentId(), savedMaterial.getId(), savedMaterial.getMaterialCode());

                List<String> updatedVendorNames = materialRequest.getVendorNames() != null
                        ? materialRequest.getVendorNames()
                        : new ArrayList<>();

                for (VendorNamesForJobWorkMaterial existingVendor : existingVendors) {
                    if (!updatedVendorNames.contains(existingVendor.getVendorName())) {
                        vendorNameRepository.delete(existingVendor);
                    }
                }

                for (String newVendor : updatedVendorNames) {
                    boolean alreadyExists = existingVendors.stream()
                            .anyMatch(ev -> ev.getVendorName().equalsIgnoreCase(newVendor));

                    if (!alreadyExists) {
                        VendorNamesForJobWorkMaterial vendor = new VendorNamesForJobWorkMaterial();
                        vendor.setVendorName(newVendor);
                        vendor.setIndentId(indentCreation.getIndentId());
                        vendor.setMaterialId(savedMaterial.getId());
                        vendor.setMaterialCode(savedMaterial.getMaterialCode());
                        vendorNameRepository.save(vendor);
                    }
                }
                if (!existingMaterials.contains(savedMaterial)) {
                    existingMaterials.add(savedMaterial);
                }
            }
        }
        BigDecimal totalIndentPrice = indentCreation.getMaterialDetails().stream()
                .map(MaterialDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        indentCreation.setTotalIntentValue(totalIndentPrice);


        indentCreationRepository.save(indentCreation);
        return mapToResponseDTO(indentCreation);
    }


    public IndentCreationResponseDTO getIndentById(String indentId) {
        IndentCreation indentCreation = indentCreationRepository.findById(indentId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided Indent ID.")
                ));
        return mapToResponseDTO(indentCreation);
    }

    @Override
    public IndentDataResponseDto getIndentDataById(String indentId) throws IOException {
        IndentCreation indentCreation = indentCreationRepository.findById(indentId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided Indent ID.")
                ));

        IndentDataResponseDto response = new IndentDataResponseDto();
        response.setIndentorName(indentCreation.getIndentorName());
        response.setIndentId(indentCreation.getIndentId());
        response.setIndentorMobileNo(indentCreation.getIndentorMobileNo());
        response.setIndentorEmailAddress(indentCreation.getIndentorEmailAddress());
        //  response.setConsignesLocation(indentCreation.getConsignesLocation());
        response.setPriorApprovalsFileName(indentCreation.getUploadingPriorApprovalsFileName());
        response.setProjectName(indentCreation.getProjectName());
        response.setProprietaryAndLimitedDeclaration(indentCreation.getProprietaryAndLimitedDeclaration());
        response.setIsPreBidMeetingRequired(indentCreation.getIsPreBitMeetingRequired());
        LocalDate Date = indentCreation.getPreBidMeetingDate();
        if (Date != null) {
            response.setPreBidMeetingDate(CommonUtils.convertDateToString(Date));
        } else {
            indentCreation.setPreBidMeetingDate(null);
        }
        response.setPreBidMeetingVenue(indentCreation.getPreBidMeetingVenue());
        response.setIsItARateContractIndent(indentCreation.getIsItARateContractIndent());
        response.setEstimatedRate(indentCreation.getEstimatedRate());
        response.setPeriodOfContract(indentCreation.getPeriodOfContract());
        response.setSingleAndMultipleJob(indentCreation.getSingleAndMultipleJob());
        response.setTechnicalSpecificationsFile(indentCreation.getTechnicalSpecificationsFileName());
        response.setDraftFileName(indentCreation.getDraftEOIOrRFPFileName());
        response.setPacAndBrandFileName(indentCreation.getUploadPACOrBrandPACFileName());
        if (indentCreation.getUploadingPriorApprovalsFileName() == null || indentCreation.getUploadingPriorApprovalsFileName().isEmpty()) {
            response.setUploadingPriorApprovalsFileName(null);
        } else {
            response.setUploadingPriorApprovalsFileName(
                    convertFilesToBase64(indentCreation.getUploadingPriorApprovalsFileName(), basePath));
        }
        if (indentCreation.getTechnicalSpecificationsFileName() == null || indentCreation.getTechnicalSpecificationsFileName().isEmpty()) {
            response.setTechnicalSpecificationsFileName(null);
        } else {
            response.setTechnicalSpecificationsFileName(
                    convertFilesToBase64(indentCreation.getTechnicalSpecificationsFileName(), basePath));
        }
        if (indentCreation.getDraftEOIOrRFPFileName() == null || indentCreation.getDraftEOIOrRFPFileName().isEmpty()) {
            response.setDraftEOIOrRFPFileName(null);
        } else {
            response.setDraftEOIOrRFPFileName(
                    convertFilesToBase64(indentCreation.getDraftEOIOrRFPFileName(), basePath));
        }

        if (indentCreation.getUploadPACOrBrandPACFileName() == null || indentCreation.getUploadPACOrBrandPACFileName().isEmpty()) {
            response.setUploadPACOrBrandPACFileName(null);
        } else {
            response.setUploadPACOrBrandPACFileName(
                    convertFilesToBase64(indentCreation.getUploadPACOrBrandPACFileName(), basePath));
        }
        response.setBrandPac(indentCreation.getBrandPac());
        response.setJustification(indentCreation.getJustification());
        response.setBrandAndModel(indentCreation.getBrandAndModel());
        response.setPurpose(indentCreation.getPurpose());
        response.setQuarter(indentCreation.getQuarter());
        response.setProprietaryJustification(indentCreation.getProprietaryJustification());
        response.setReason(indentCreation.getReason());
        response.setFileType(indentCreation.getFileType());
        response.setBuyBack(indentCreation.getBuyBack());
        if (indentCreation.getUploadBuyBackFileNames() == null || indentCreation.getUploadBuyBackFileNames().isEmpty()) {
            response.setUploadBuyBackFileNames(null);
        } else {
            response.setUploadBuyBackFileNames(convertFilesToBase64(indentCreation.getUploadBuyBackFileNames(), basePath));
        }
        response.setBuyBackFileName(indentCreation.getUploadBuyBackFileNames());
        response.setSerialNumber(indentCreation.getSerialNumber());
        response.setModelNumber(indentCreation.getModelNumber());
        LocalDate dateOfPurchase = indentCreation.getDateOfPurchase();
        if (dateOfPurchase != null) {
            response.setDateOfPurchase(CommonUtils.convertDateToString(dateOfPurchase));
        } else {
            indentCreation.setDateOfPurchase(null);
        }
        response.setCreatedBy(indentCreation.getCreatedBy());
        response.setUpdatedBy(indentCreation.getUpdatedBy());
       // Optional<WorkflowTransition> lastRecord = workflowTransitionRepository.findTopByRequestIdOrderByCreatedDateDesc(indentId);
        Optional<WorkflowTransition> lastRecord = workflowTransitionRepository.findTopByRequestIdOrderByWorkflowTransitionIdDesc(indentId);

        if (lastRecord.isPresent()) {
            WorkflowTransition transition = lastRecord.get();

            response.setApprovedBy(transition.getCurrentRole());
            String d = CommonUtils.convertDateTooString(transition.getCreatedDate());
            response.setDate(d);
            response.setRemarks(transition.getRemarks());
        }


        String materialSubCategory = indentCreation.getMaterialDetails().stream()
                .map(MaterialDetails::getMaterialSubCategory)
                .findFirst()
                .orElse(null);
        // Converting "Capital" or "Consumable" to "Normal"
      /*  if ("Capital".equalsIgnoreCase(materialSubCategory) || "Consumable".equalsIgnoreCase(materialSubCategory)) {
            materialSubCategory = "Normal";
        }*/
        if ("Computer & Peripherals".equalsIgnoreCase(materialSubCategory)) {
            materialSubCategory = "Computer";
        } else {
            materialSubCategory = "Normal";
        }


        response.setMaterialCategory(materialSubCategory);  //set material category to indent response
        response.setConsignesLocation(indentCreation.getConsignesLocation());
        // Map material details
        List<MaterialDetailsResponseDTO> materialDetailsResponse = indentCreation.getMaterialDetails().stream().map(material -> {
            MaterialDetailsResponseDTO materialResponse = new MaterialDetailsResponseDTO();
            materialResponse.setMaterialCode(material.getMaterialCode());
            materialResponse.setMaterialDescription(material.getMaterialDescription());
            materialResponse.setQuantity(material.getQuantity());
            materialResponse.setUnitPrice(material.getUnitPrice());
            materialResponse.setUom(material.getUom());
            materialResponse.setTotalPrice(material.getTotalPrice());
            materialResponse.setBudgetCode(material.getBudgetCode());
            materialResponse.setModeOfProcurement(material.getModeOfProcurement());
            materialResponse.setMaterialCategory(material.getMaterialCategory());
            materialResponse.setMaterialSubCategory(material.getMaterialSubCategory());
            materialResponse.setCurrency(material.getCurrency());

            List<String> vendorNames = vendorNameRepository.findByMaterialId(material.getId())
                    .stream()
                    .map(VendorNamesForJobWorkMaterial::getVendorName)
                    .collect(Collectors.toList());
            System.out.println("material_id" + material.getId());
           materialResponse.setVendorNames(vendorNames);

            return materialResponse;
        }).collect(Collectors.toList());

        // Calculate total price of all materials
        BigDecimal totalPriceOfAllMaterials = materialDetailsResponse.stream()
                .map(MaterialDetailsResponseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String projectName = indentCreation.getProjectName();// project name is project code
      /*  BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectNameDescription(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);*/
        // String projectCode = indentCreation.getProjectCode();
        BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectCode(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);

        System.out.println("allocatedAmount: " + allocatedAmount);
        response.setTotalPriceOfAllMaterials(totalPriceOfAllMaterials);

        response.setMaterialDetails(materialDetailsResponse);
       WorkflowTransition wt = workflowTransitionRepository.findTopByRequestIdOrderByWorkflowSequenceDesc(indentId);
        response.setStatus(wt.getStatus());
        response.setProcessStage(wt.getNextRole());

        return response;

    }

    public static List<String> convertFilesToBase64(String fileNames, String basePath) throws IOException {
        List<String> base64List = new ArrayList<>();

        if (fileNames != null && !fileNames.isEmpty()) {
            String[] fileNameArray = fileNames.split(",");

            for (String fileName : fileNameArray) {
                String trimmedFileName = fileName.trim();
                if (!trimmedFileName.isEmpty()) {
                    String base64 = CommonUtils.convertImageToBase64(trimmedFileName, basePath);
                    base64List.add(base64);
                }
            }
        }

        return base64List;
    }


    // Get All Indents
    public List<IndentCreationResponseDTO> getAllIndents() {
        List<IndentCreation> indentList = indentCreationRepository.findAll();
        return indentList.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }
    @Transactional
    public IndentCreationResponseDTO getIndentDataForTenderById(String indentId) throws IOException {
        IndentCreation indentCreation = indentCreationRepository.findById(indentId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided Indent ID.")
                ));
        //  return mapToResponseDTO(indentCreation);
        IndentCreationResponseDTO response = new IndentCreationResponseDTO();
        response.setIndentorName(indentCreation.getIndentorName());
        response.setIndentId(indentCreation.getIndentId());
        response.setIndentorMobileNo(indentCreation.getIndentorMobileNo());
        response.setIndentorEmailAddress(indentCreation.getIndentorEmailAddress());
        // response.setConsignesLocation(indentCreation.getConsignesLocation());
        response.setUploadingPriorApprovalsFileName(indentCreation.getUploadingPriorApprovalsFileName());
        //  response.setProjectName(indentCreation.getProjectName());
        response.setProjectName(
                (indentCreation.getProjectName() != null && !indentCreation.getProjectName().isBlank())
                        ? indentCreation.getProjectName()
                        : null
        );
        response.setProprietaryAndLimitedDeclaration(indentCreation.getProprietaryAndLimitedDeclaration());
        response.setIsPreBidMeetingRequired(indentCreation.getIsPreBitMeetingRequired());
        LocalDate Date = indentCreation.getPreBidMeetingDate();
        if (Date != null) {
            response.setPreBidMeetingDate(CommonUtils.convertDateToString(Date));
        } else {
            indentCreation.setPreBidMeetingDate(null);
        }
        response.setPreBidMeetingVenue(indentCreation.getPreBidMeetingVenue());
        response.setIsItARateContractIndent(indentCreation.getIsItARateContractIndent());
        response.setEstimatedRate(indentCreation.getEstimatedRate());
        response.setPeriodOfContract(indentCreation.getPeriodOfContract());
        response.setSingleAndMultipleJob(indentCreation.getSingleAndMultipleJob());
        response.setTechnicalSpecificationsFileName(indentCreation.getTechnicalSpecificationsFileName());
        response.setDraftEOIOrRFPFileName(indentCreation.getDraftEOIOrRFPFileName());
        response.setUploadPACOrBrandPACFileName(indentCreation.getUploadPACOrBrandPACFileName());
        response.setBrandPac(indentCreation.getBrandPac());
        response.setJustification(indentCreation.getJustification());
        response.setBrandAndModel(indentCreation.getBrandAndModel());
        response.setPurpose(indentCreation.getPurpose());
        response.setQuarter(indentCreation.getQuarter());
        response.setProprietaryJustification(indentCreation.getProprietaryJustification());
        response.setReason(indentCreation.getReason());
        response.setFileType(indentCreation.getFileType());
        response.setBuyBack(indentCreation.getBuyBack());
        if (indentCreation.getUploadBuyBackFileNames() == null || indentCreation.getUploadBuyBackFileNames().isEmpty()) {
            response.setUploadBuyBackFile(null);
        } else {
            response.setUploadBuyBackFile(convertFilesToBase64(indentCreation.getUploadBuyBackFileNames(), basePath));
        }

        response.setUploadBuyBackFileNames(indentCreation.getUploadBuyBackFileNames());
        response.setSerialNumber(indentCreation.getSerialNumber());
        response.setModelNumber(indentCreation.getModelNumber());
        response.setBuyBackAmount(indentCreation.getBuyBackAmount());
        response.setCancelStatus(indentCreation.getCancelStatus());
        response.setCancelRemarks(indentCreation.getCancelRemarks());
        LocalDate dateOfPurchase = indentCreation.getDateOfPurchase();
        if (dateOfPurchase != null) {
            response.setDateOfPurchase(CommonUtils.convertDateToString(dateOfPurchase));
        } else {
            indentCreation.setDateOfPurchase(null);
        }
        response.setCreatedBy(indentCreation.getCreatedBy());
        response.setUpdatedBy(indentCreation.getUpdatedBy());


        String materialSubCategory = indentCreation.getMaterialDetails().stream()
                .map(MaterialDetails::getMaterialSubCategory)
                .findFirst()
                .orElse(null);
        // Converting "Capital" or "Consumable" to "Normal"
       /* if ("Capital".equalsIgnoreCase(materialCategory) || "Consumable".equalsIgnoreCase(materialCategory)) {
            materialCategory = "Normal";
        } else if ("Computer".equalsIgnoreCase(materialCategory)) {
            materialCategory = "Computer";
        }
        response.setMaterialCategory(materialCategory); */
        //set material category to indent response
        if ("Computer & Peripherals".equalsIgnoreCase(materialSubCategory)) {
            materialSubCategory = "Computer";
        } else {
            materialSubCategory = "Normal";
        }
        String consignesLocation;
        if ("BNG".equalsIgnoreCase(indentCreation.getConsignesLocation())) {
            consignesLocation = "Normal";
        } else {
            consignesLocation = "Computer";
        }

        response.setMaterialCategory(materialSubCategory);  //set material category to indent response
        response.setConsignesLocation(consignesLocation);

        // Map material details
        List<MaterialDetailsResponseDTO> materialDetailsResponse = indentCreation.getMaterialDetails().stream().map(material -> {
            MaterialDetailsResponseDTO materialResponse = new MaterialDetailsResponseDTO();
            materialResponse.setMaterialCode(material.getMaterialCode());
            materialResponse.setMaterialDescription(material.getMaterialDescription());
            materialResponse.setQuantity(material.getQuantity());
            materialResponse.setUnitPrice(material.getUnitPrice());
            materialResponse.setUom(material.getUom());
            materialResponse.setTotalPrice(material.getTotalPrice());
            materialResponse.setBudgetCode(material.getBudgetCode());
            materialResponse.setModeOfProcurement(material.getModeOfProcurement());
            materialResponse.setMaterialCategory(material.getMaterialCategory());
            materialResponse.setMaterialSubCategory(material.getMaterialSubCategory());
            materialResponse.setCurrency(material.getCurrency());

            List<String> vendorNames = vendorNameRepository.findByMaterialId(material.getId())
                    .stream()
                    .map(VendorNamesForJobWorkMaterial::getVendorName)
                    .collect(Collectors.toList());
            System.out.println("material_id" + material.getId());

            System.out.println("VendorNames:" + vendorNames);
            // materialResponse.setVendorNames(vendorNames);
            List<String> vendorIds = vendorNames; // your existing list of vendorIds

            List<String> vendorIdNameList = vendorIds.stream()
                    .map(vendorId -> {
                        // Fetch only vendorName using vendorId
                        String name = vendorMasterRepository.findVendorNameByVendorId(vendorId);
                        return vendorId + "-" + name;
                    })
                    .collect(Collectors.toList());

            materialResponse.setVendorNames(vendorIdNameList);


            return materialResponse;
        }).collect(Collectors.toList());

        // Calculate total price of all materials
        BigDecimal totalPriceOfAllMaterials = materialDetailsResponse.stream()
                .map(MaterialDetailsResponseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String projectName = indentCreation.getProjectName();// project name is project code
      /*  BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectNameDescription(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);*/
        // String projectCode = indentCreation.getProjectCode();
        BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectCode(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);
        if ("Engineering".equals(indentCreation.getEmployeeDepartment())) {
            response.setEmployeeDepartment(indentCreation.getEmployeeDepartment());
        } else {
            response.setEmployeeDepartment("OtherDept");
        }


        System.out.println("allocatedAmount: " + allocatedAmount);
        response.setTotalPriceOfAllMaterials(totalPriceOfAllMaterials);

        response.setMaterialDetails(materialDetailsResponse);

        return response;
    }

    @Transactional
    private IndentCreationResponseDTO mapToResponseDTO(IndentCreation indentCreation) {
        IndentCreationResponseDTO response = new IndentCreationResponseDTO();
        response.setIndentorName(indentCreation.getIndentorName());
        response.setIndentId(indentCreation.getIndentId());
        response.setIndentorMobileNo(indentCreation.getIndentorMobileNo());
        response.setIndentorEmailAddress(indentCreation.getIndentorEmailAddress());
        // response.setConsignesLocation(indentCreation.getConsignesLocation());
        response.setUploadingPriorApprovalsFileName(indentCreation.getUploadingPriorApprovalsFileName());
        //  response.setProjectName(indentCreation.getProjectName());
        response.setProjectName(
                (indentCreation.getProjectName() != null && !indentCreation.getProjectName().isBlank())
                        ? indentCreation.getProjectName()
                        : null
        );
        response.setProprietaryAndLimitedDeclaration(indentCreation.getProprietaryAndLimitedDeclaration());
        response.setIsPreBidMeetingRequired(indentCreation.getIsPreBitMeetingRequired());
        LocalDate Date = indentCreation.getPreBidMeetingDate();
        if (Date != null) {
            response.setPreBidMeetingDate(CommonUtils.convertDateToString(Date));
        } else {
            indentCreation.setPreBidMeetingDate(null);
        }
        response.setPreBidMeetingVenue(indentCreation.getPreBidMeetingVenue());
        response.setIsItARateContractIndent(indentCreation.getIsItARateContractIndent());
        response.setEstimatedRate(indentCreation.getEstimatedRate());
        response.setPeriodOfContract(indentCreation.getPeriodOfContract());
        response.setSingleAndMultipleJob(indentCreation.getSingleAndMultipleJob());
        response.setTechnicalSpecificationsFileName(indentCreation.getTechnicalSpecificationsFileName());
        response.setDraftEOIOrRFPFileName(indentCreation.getDraftEOIOrRFPFileName());
        response.setUploadPACOrBrandPACFileName(indentCreation.getUploadPACOrBrandPACFileName());
        response.setBrandPac(indentCreation.getBrandPac());
        response.setJustification(indentCreation.getJustification());
        response.setBrandAndModel(indentCreation.getBrandAndModel());
        response.setPurpose(indentCreation.getPurpose());
        response.setQuarter(indentCreation.getQuarter());
        response.setProprietaryJustification(indentCreation.getProprietaryJustification());
        response.setReason(indentCreation.getReason());
        response.setFileType(indentCreation.getFileType());
        response.setBuyBack(indentCreation.getBuyBack());
        response.setUploadBuyBackFileNames(indentCreation.getUploadBuyBackFileNames());
        response.setSerialNumber(indentCreation.getSerialNumber());
        response.setModelNumber(indentCreation.getModelNumber());
        response.setBuyBackAmount(indentCreation.getBuyBackAmount());
        response.setCancelStatus(indentCreation.getCancelStatus());
        response.setCancelRemarks(indentCreation.getCancelRemarks());
        response.setEmployeeId(indentCreation.getEmployeeId());
        response.setEmployeeName(indentCreation.getEmployeeName());
        response.setEmployeeDept(indentCreation.getEmployeeDepartment());
        LocalDate dateOfPurchase = indentCreation.getDateOfPurchase();
        if (dateOfPurchase != null) {
            response.setDateOfPurchase(CommonUtils.convertDateToString(dateOfPurchase));
        } else {
            indentCreation.setDateOfPurchase(null);
        }
        response.setCreatedBy(indentCreation.getCreatedBy());
        response.setUpdatedBy(indentCreation.getUpdatedBy());


        String materialSubCategory = indentCreation.getMaterialDetails().stream()
                .map(MaterialDetails::getMaterialSubCategory)
                .findFirst()
                .orElse(null);
        // Converting "Capital" or "Consumable" to "Normal"
       /* if ("Capital".equalsIgnoreCase(materialCategory) || "Consumable".equalsIgnoreCase(materialCategory)) {
            materialCategory = "Normal";
        } else if ("Computer".equalsIgnoreCase(materialCategory)) {
            materialCategory = "Computer";
        }
        response.setMaterialCategory(materialCategory); */
        //set material category to indent response
        if ("Computer & Peripherals".equalsIgnoreCase(materialSubCategory)) {
            materialSubCategory = "Computer";
        } else {
            materialSubCategory = "Normal";
        }
        String consignesLocation;
        if ("BNG".equalsIgnoreCase(indentCreation.getConsignesLocation())) {
            consignesLocation = "Normal";
        } else {
            consignesLocation = "Computer";
        }

        response.setMaterialCategory(materialSubCategory);  //set material category to indent response
        response.setConsignesLocation(consignesLocation);

        // Map material details
        List<MaterialDetailsResponseDTO> materialDetailsResponse = indentCreation.getMaterialDetails().stream().map(material -> {
            MaterialDetailsResponseDTO materialResponse = new MaterialDetailsResponseDTO();
            materialResponse.setMaterialCode(material.getMaterialCode());
            materialResponse.setMaterialDescription(material.getMaterialDescription());
            materialResponse.setQuantity(material.getQuantity());
            materialResponse.setUnitPrice(material.getUnitPrice());
            materialResponse.setUom(material.getUom());
            materialResponse.setTotalPrice(material.getTotalPrice());
            materialResponse.setBudgetCode(material.getBudgetCode());
            materialResponse.setModeOfProcurement(material.getModeOfProcurement());
            materialResponse.setMaterialCategory(material.getMaterialCategory());
            materialResponse.setMaterialSubCategory(material.getMaterialSubCategory());
            materialResponse.setCurrency(material.getCurrency());

            List<String> vendorNames = vendorNameRepository.findByMaterialId(material.getId())
                    .stream()
                    .map(VendorNamesForJobWorkMaterial::getVendorName)
                    .collect(Collectors.toList());
            System.out.println("material_id" + material.getId());

            System.out.println("VendorNames:" + vendorNames);
           // materialResponse.setVendorNames(vendorNames);
            List<String> vendorIds = vendorNames; // your existing list of vendorIds

            List<String> vendorIdNameList = vendorIds.stream()
                    .map(vendorId -> {
                        // Fetch only vendorName using vendorId
                        String name = vendorMasterRepository.findVendorNameByVendorId(vendorId);
                        return vendorId + "-" + name;
                    })
                    .collect(Collectors.toList());

            materialResponse.setVendorNames(vendorIdNameList);


            return materialResponse;
        }).collect(Collectors.toList());

        // Calculate total price of all materials
        BigDecimal totalPriceOfAllMaterials = materialDetailsResponse.stream()
                .map(MaterialDetailsResponseDTO::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String projectName = indentCreation.getProjectName();// project name is project code
      /*  BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectNameDescription(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);*/
        // String projectCode = indentCreation.getProjectCode();
        BigDecimal allocatedAmount = projectMasterRepository
                .findByProjectCode(projectName)
                .map(ProjectMaster::getAllocatedAmount)
                .orElse(BigDecimal.ZERO);
        response.setProjectLimit(allocatedAmount);
        if ("Engineering".equals(indentCreation.getEmployeeDepartment())) {
            response.setEmployeeDepartment(indentCreation.getEmployeeDepartment());
        } else {
            response.setEmployeeDepartment("OtherDept");
        }


        System.out.println("allocatedAmount: " + allocatedAmount);
        response.setTotalPriceOfAllMaterials(totalPriceOfAllMaterials);

        response.setMaterialDetails(materialDetailsResponse);

        return response;
    }

    @Override
    public void deleteIndent(String indentId) {

        IndentCreation indent = indentCreationRepository.findById(indentId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided ID."
                        )
                ));
        try {
            indentCreationRepository.delete(indent);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_ERROR,
                            "An error occurred while deleting the  Indent."
                    ),
                    ex
            );
        }
    }

    /*
        @Override
        public List<IndentReportDetailsDTO> getIndentReport(String startDate, String endDate) {

            List<Object[]> results = indentCreationRepository.fetchIndentReportDetails(
                    CommonUtils.convertStringToDateObject(startDate),
                    CommonUtils.convertStringToDateObject(endDate)
            );

            return results.stream().map(result -> {
                IndentReportDetailsDTO dto = new IndentReportDetailsDTO();
                dto.setIndentId((String) result[0]);
                dto.setApprovedDate((Date) result[1]);
                dto.setAssignedTo((String) result[2]);
                dto.setTenderRequest((String) result[3]);
                dto.setModeOfTendering((String) result[4]);
                dto.setCorrespondingPoSo((String) result[5]);
                dto.setStatusOfPoSo((String) result[6]);
                dto.setSubmittedDate((Date) result[7]);
                dto.setPendingApprovalWith((String) result[8]);
                dto.setPoSoApprovedDate((Date) result[9] );
                dto.setMaterial((String) result[10]);
                dto.setMaterialCategory((String) result[11]);
                dto.setMaterialSubCategory((String) result[12]);
                dto.setVendorName((String) result[13]);
                dto.setIndentorName((String) result[14]);
                dto.setValueOfIndent(result[15] != null ? ((BigDecimal) result[15]).doubleValue() : null);
                dto.setValueOfPo(result[16] != null ? ((BigDecimal) result[16]).doubleValue() : null);
                dto.setProject((String) result[17]);
                dto.setGrinNo((String) result[18]);
                dto.setInvoiceNo((String) result[19]);
                dto.setGissNo((String) result[20]);
                dto.setValuePendingToBePaid(result[21] != null ? ((BigDecimal) result[21]).doubleValue() : null);
                dto.setCurrentStageOfIndent((String) result[22]);
                dto.setShortClosedAndCancelled((String) result[23]);
                dto.setReasonForShortClosure((String) result[24]);

                return dto;
            }).collect(Collectors.toList());
        }


     */
    @Override
    public List<IndentReportDetailsDTO> getIndentReport(String startDate, String endDate) {
        // Convert String dates to LocalDate
        LocalDate startLocalDate = CommonUtils.convertStringToDateObject(startDate);
        LocalDate endLocalDate = CommonUtils.convertStringToDateObject(endDate);

        // Fetch results from the repository
        List<Object[]> results = indentCreationRepository.fetchIndentReportDetails(startLocalDate, endLocalDate);

        System.out.println(results);
        // Map results to DTO
        return results.stream().map(result -> {

            // Map each column to the DTO fields
            return new IndentReportDetailsDTO(
                    (String) result[0],                          // indentId
                    result[1] != null ? (Date) result[1] : null, // approvedDate
                    (String) result[2],                          // assignedTo
                    (String) result[3],                          // tenderRequest
                    (String) result[4],                          // modeOfTendering
                    (String) result[5],                          // correspondingPoSo
                    (String) result[6],                          // statusOfPoSo
                    result[7] != null ? (Date) result[7] : null, // submittedDate
                    (String) result[8],                          // pendingApprovalWith
                    result[9] != null ? (Date) result[9] : null, // poSoApprovedDate
                    (String) result[10],                         // material
                    (String) result[11],                         // materialCategory
                    (String) result[12],                         // materialSubCategory
                    (String) result[13],                         // vendorName
                    (String) result[14],                         // indentorName
                    result[15] != null ? ((BigDecimal) result[15]).doubleValue() : null, // valueOfIndent
                    result[16] != null ? ((BigDecimal) result[16]).doubleValue() : null, // valueOfPo
                    (String) result[17],
                    (String) result[18],                        // project
                    //  (String) result[18],                        // grinNo
                    (String) result[19],                         // invoiceNo
                    (String) result[20],                        // gissNo
                    result[21] != null ? ((BigDecimal) result[21]).doubleValue() : null, // valuePendingToBePaid
                    (String) result[22],                        // currentStageOfIndent
                    (String) result[23],                        // shortClosedAndCancelled
                    (String) result[24]                     // reasonForShortClosure


            );
        }).collect(Collectors.toList());
    }

    public List<TechnoMomReportDTO> getTechnoMomReport(String startDate, String endDate) {
        List<Object[]> results = indentCreationRepository.getTechnoMomReport(CommonUtils.convertStringToDateObject(startDate), CommonUtils.convertStringToDateObject(endDate));

        return results.stream().map(obj -> new TechnoMomReportDTO(
                (Date) obj[0],
                (String) obj[1],
                (String) obj[2],
                obj[3] != null ? new BigDecimal(obj[3].toString()) : BigDecimal.ZERO, // value
                (String) obj[4]
        )).collect(Collectors.toList());

    }

    @Override
    public List<materialHistoryDto> getIndentIdAndUserId(String materialCode) {
        List<String> indentIds = materialDetailsRepository.findIndentIdsByMaterialCode(materialCode);


        return indentIds.stream()
                .map(id -> {
                    materialHistoryDto dto = new materialHistoryDto();
                    dto.setIndentId(id);
                    IndentCreation indent = indentCreationRepository.findById(id).orElse(null);
                    if (indent != null) {
                        dto.setUserId(indent.getCreatedBy());
                    } else {
                        dto.setUserId(null);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }




    public void handleFileUpload(IndentCreation indentCreation, MultipartFile file, Consumer<byte[]> fileSetter) {
        if (file != null) {
            try (InputStream inputStream = file.getInputStream()) {
                byte[] fileBytes = inputStream.readAllBytes();
                fileSetter.accept(fileBytes); // Set file content (byte[])

            } catch (IOException e) {
                throw new InvalidInputException(new ErrorDetails(500, 3, "File Processing Error",
                        "Error while processing the uploaded file. Please try again."));
            }
        } else {
            fileSetter.accept(null);  // Handle gracefully if no file is uploaded
        }
    }

    @Override
    public List<IndentListReportDto> getAllIndentsReport(String startDate, String endDate, Integer userId, String roleName) {

        List<LocalDateTime> range = CommonUtils.getDateRenge(startDate, endDate);
        LocalDateTime from = range.get(0);
        LocalDateTime to = range.get(1);

      //  List<Object[]> rows = indentCreationRepository.getAllIndentListReport(from, to);
        List<Object[]> rows;
        if ("Indent Creator".equalsIgnoreCase(roleName)) {
            rows = indentCreationRepository.getAllIndentListUserIdsReport(from, to, userId);
            System.out.println(roleName);
        } else {
            rows =indentCreationRepository.getAllIndentListReport(from, to);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return rows.stream().map(row -> {
            IndentListReportDto dto = new IndentListReportDto();
            dto.setIndentId((String) row[0]);
            dto.setIndentorName((String) row[1]);
            dto.setIndentorMobileNo((String) row[2]);
            dto.setIndentorEmailAddress((String) row[3]);
            dto.setConsignesLocation((String) row[4]);
            dto.setProjectName((String) row[5]);

            if (row[6] != null) {
                LocalDate submitted = ((Timestamp) row[6]).toLocalDateTime().toLocalDate();
                dto.setSubmittedDate(CommonUtils.convertDateToString(submitted));
            }
            dto.setPendingWith((String) row[7]);
            if (row[8] != null) {
                LocalDate pending = ((Timestamp) row[8]).toLocalDateTime().toLocalDate();
                dto.setPendingFrom(CommonUtils.convertDateToString(pending));
            }
            dto.setStatus((String) row[9]);
            dto.setAsOnDate(LocalDate.now());
            dto.setCreatedBy((Integer) row[10]);
            dto.setIndentValue((BigDecimal) row[11]);


            String json = (String) row[12];
            try {
                List<IndentMaterialListReportDto> materials = mapper.readValue(
                        json,
                        mapper.getTypeFactory().constructCollectionType(
                                List.class,
                                IndentMaterialListReportDto.class
                        )
                );
                dto.setMaterialDetails(materials);
            } catch (Exception ex) {
                dto.setMaterialDetails(new ArrayList<>());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<IndentWorkflowStatusDto> getIndentWorkflowStatus(String indentId, Integer userId, String roleName) {

        List<WorkflowTransition> wts = workflowTransitionRepository.findByRequestId(indentId);

        // If roleName = Indent Creator, filter by createdBy = userId
        if ("Indent Creator".equalsIgnoreCase(roleName)) {
            wts = wts.stream()
                    .filter(wt -> wt.getCreatedBy() != null && wt.getCreatedBy().equals(userId))
                    .collect(Collectors.toList());
        }

        return wts.stream().map(wt -> {

            IndentWorkflowStatusDto indent = new IndentWorkflowStatusDto();
            indent.setRequestId(wt.getRequestId());
            indent.setCreatedBy(wt.getCreatedBy());
            indent.setModifiedBy(wt.getModifiedBy());
            indent.setStatus(wt.getStatus());
            indent.setNextAction(wt.getNextAction());
            indent.setAction(wt.getAction());
            indent.setCurrentRole(wt.getCurrentRole());
            indent.setNextRole(wt.getNextRole());
            indent.setRemarks(wt.getRemarks());
            indent.setCreatedDate(wt.getCreatedDate());
            indent.setModificationDate(wt.getModificationDate());
            return indent;
        }).collect(Collectors.toList());

    }
    @Override
    public List<SearchIndentIdDto> searchIndentIds(String type, String value) {
        List<SearchIndentIdDto> result;

        switch (type.toLowerCase()) {
            case "processid":
                result = indentCreationRepository.findByIndentIdContainingIgnoreCase(value);
                break;
            case "submitteddate":
                try {
                    LocalDate date = LocalDate.parse(value);
                    LocalDateTime startOfDay = date.atStartOfDay();
                    LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

                    result = indentCreationRepository.findByCreatedDateBetween(startOfDay, endOfDay);
                } catch (Exception e) {
                    throw new BusinessException(
                            new ErrorDetails(
                                    AppConstant.ERROR_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_RESOURCE,
                                    "Invalid submitted date format. Expected yyyy-MM-dd"
                            )
                    );
                }
                break;

            case "indentorname":
                result = indentCreationRepository.findByIndentorName(value);
                break;

            case "materialdescription":
                result = indentCreationRepository.findByMaterialDescription(value);
                break;

            case "vendorname":
                result = vendorNameRepository.findIndentIdsByVendorName(value);
                break;

            default:
                throw new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Invalid search type: " + type
                        )
                );
        }

        if (result == null || result.isEmpty()) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_RESOURCE,
                            "No matching indents found for the given search criteria."
                    )
            );
        }

        return result;
    }
    @Override
    public String assignEmployeeToIndent(AssignEmployeeToIndentDto dto) {
        IndentCreation indent = indentCreationRepository.findById(dto.getIndentId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided ID."
                        )
                ));
        indent.setEmployeeId(dto.getEmployeeId());
        indent.setEmployeeName(dto.getEmployeeName());

        indentCreationRepository.save(indent); // updates existing record

        Optional<EmployeeDepartmentMaster> em = employeeDepartmentMasterRepository.findByEmployeeId(dto.getEmployeeId());
        UserMaster um = userMasterRepository.findByUserId(indent.getCreatedBy());
        if(em.isPresent()){
            EmployeeDepartmentMaster employee = em.get();
            try {
                emailService.notifyEmployeeAssigned(indent,employee.getEmailAddress(), um.getEmail());
            } catch (MessagingException e) {

            }
        }


        return "Employee " + dto.getEmployeeName() + " assigned to indent " + dto.getIndentId() + " successfully";
    }
    @Override
    public String cancelIndent(CancelIndentRequestDto request)  {
        IndentCreation indent = indentCreationRepository.findById(request.getIndentId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Indent not found for the provided ID."
                        )
                ));

        indent.setCancelStatus(request.getCancelStatus());
        indent.setCancelRemarks(request.getCancelRemarks());

        indentCreationRepository.save(indent);
        return "indent saved";
    }


}
