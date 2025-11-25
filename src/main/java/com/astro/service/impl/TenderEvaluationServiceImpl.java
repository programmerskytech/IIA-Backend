package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationRequestDto;
import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationResponseDto;
import com.astro.dto.workflow.ProcurementDtos.TenderEvaluationResponseWithBitTypeAndValueDto;
import com.astro.entity.ProcurementModule.TenderEvaluation;
import com.astro.entity.ProcurementModule.TenderRequest;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.ProcurementModule.TenderEvaluationRepository;
import com.astro.repository.ProcurementModule.TenderRequestRepository;
import com.astro.service.TenderEvaluationService;
import com.astro.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TenderEvaluationServiceImpl implements TenderEvaluationService {

    @Autowired
    private TenderEvaluationRepository tenderEvaluationRepository;
    @Autowired
    private TenderRequestRepository tenderRequestRepository;
    @Value("${filePath}")
    private String bp;
    private final String basePath;

    public TenderEvaluationServiceImpl(@Value("${filePath}") String bp) {
        this.basePath = bp + "/Tender";
    }


    @Override
    public TenderEvaluationResponseDto createTenderEvaluation(TenderEvaluationRequestDto tenderEvaluationRequestDto) {

        if (tenderEvaluationRepository.existsById(tenderEvaluationRequestDto.getTenderId())) {
            ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate Tender Evaluation ID", "Tender ID " + tenderEvaluationRequestDto.getTenderId() + " already exists.");
            throw new InvalidInputException(errorDetails);
        }
        TenderEvaluation tenderEvaluation = new TenderEvaluation();
        tenderEvaluation.setTenderId(tenderEvaluationRequestDto.getTenderId());
        if (tenderEvaluationRequestDto.getUploadQualifiedVendorsFileName() == null || tenderEvaluationRequestDto.getUploadQualifiedVendorsFileName().isEmpty()) {
            tenderEvaluation.setUploadQualifiedVendorsFileName(null);

        } else {
            String UploadQualifiedVendorsFileName = saveBase64Files(tenderEvaluationRequestDto.getUploadQualifiedVendorsFileName(), basePath);
            tenderEvaluation.setUploadQualifiedVendorsFileName(UploadQualifiedVendorsFileName);

        }
     //   tenderEvaluation.setUploadQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadQualifiedVendorsFileName());
        tenderEvaluation.setUploadTechnicallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadTechnicallyQualifiedVendorsFileName());
        tenderEvaluation.setUploadCommeriallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadCommeriallyQualifiedVendorsFileName());
        tenderEvaluation.setFormationOfTechnoCommerialComitee(tenderEvaluationRequestDto.getFormationOfTechnoCommerialComitee());
        tenderEvaluation.setResponseFileName(tenderEvaluationRequestDto.getResponseFileName());
        tenderEvaluation.setResponseForTechnicallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getResponseForTechnicallyQualifiedVendorsFileName());
        tenderEvaluation.setResponseForCommeriallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getResponseForCommeriallyQualifiedVendorsFileName());

        tenderEvaluation.setUploadQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setUploadTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadTechnicallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setUploadCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadCommeriallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setFormationOfTechnoCommerialComiteeCreatedBy(tenderEvaluationRequestDto.getFormationOfTechnoCommerialComiteeCreatedBy());
        tenderEvaluation.setResponseFileNameCreatedBy(tenderEvaluationRequestDto.getResponseFileNameCreatedBy());
        tenderEvaluation.setResponseForTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getResponseForTechnicallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setResponseForCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getResponseForCommeriallyQualifiedVendorsFileNameCreatedBy());

        tenderEvaluation.setFileType(tenderEvaluationRequestDto.getFileType());
        tenderEvaluation.setCreatedBy(tenderEvaluationRequestDto.getCreatedBy());
        tenderEvaluation.setUpdatedBy(tenderEvaluationRequestDto.getUpdatedBy());

        tenderEvaluationRepository.save(tenderEvaluation);

        return mapToResponseDTO(tenderEvaluation);
    }

    public String saveBase64Files(String base64File, String basePath) {
        try {
            List<String> fileNames = new ArrayList<>();

                String fileName = CommonUtils.saveBase64Image(base64File, basePath);
                fileNames.add(fileName);

            return fileName;
        } catch (Exception e) {
            throw new InvalidInputException(new ErrorDetails(
                    AppConstant.FILE_UPLOAD_ERROR,
                    AppConstant.USER_INVALID_INPUT,
                    AppConstant.ERROR_TYPE_CORRUPTED,
                    "Error while uploading files."));
        }
    }

    private TenderEvaluationResponseDto mapToResponseDTO(TenderEvaluation tenderEvaluation) {

        TenderEvaluationResponseDto responseDto = new TenderEvaluationResponseDto();
        responseDto.setTenderId(tenderEvaluation.getTenderId());
        responseDto.setUploadQualifiedVendorsFileName(tenderEvaluation.getUploadQualifiedVendorsFileName());
        responseDto.setUploadTechnicallyQualifiedVendorsFileName(tenderEvaluation.getUploadTechnicallyQualifiedVendorsFileName());
        responseDto.setUploadCommeriallyQualifiedVendorsFileName(tenderEvaluation.getUploadCommeriallyQualifiedVendorsFileName());
        responseDto.setFormationOfTechnoCommerialComitee(tenderEvaluation.getFormationOfTechnoCommerialComitee());
        responseDto.setResponseFileName(tenderEvaluation.getResponseFileName());
        responseDto.setResponseForTechnicallyQualifiedVendorsFileName(tenderEvaluation.getResponseForTechnicallyQualifiedVendorsFileName());
        responseDto.setResponseForCommeriallyQualifiedVendorsFileName(tenderEvaluation.getResponseForCommeriallyQualifiedVendorsFileName());

        responseDto.setUploadQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadQualifiedVendorsFileNameCreatedBy());
        responseDto.setUploadTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadTechnicallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setUploadCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadCommeriallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setFormationOfTechnoCommerialComiteeCreatedBy(tenderEvaluation.getFormationOfTechnoCommerialComiteeCreatedBy());
        responseDto.setResponseFileNameCreatedBy(tenderEvaluation.getResponseFileNameCreatedBy());
        responseDto.setResponseForTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getResponseForTechnicallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setResponseForCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getResponseForCommeriallyQualifiedVendorsFileNameCreatedBy());

        responseDto.setFileType(tenderEvaluation.getFileType());
        responseDto.setCreatedBy(tenderEvaluation.getCreatedBy());
        responseDto.setUpdatedBy(tenderEvaluation.getUpdatedBy());
        responseDto.setCreatedDate(tenderEvaluation.getCreatedDate());
        responseDto.setUpdatedDate(tenderEvaluation.getUpdatedDate());

        return responseDto;


    }

    @Override
    public TenderEvaluationResponseDto updateTenderEvaluation(String tenderId, TenderEvaluationRequestDto tenderEvaluationRequestDto) {
       TenderEvaluation tenderEvaluation = tenderEvaluationRepository.findById(tenderId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Tender eavaluation request not found for the provided tender ID.")
                ));

     //   tenderEvaluation.setTenderId(tenderEvaluationRequestDto.getTenderId());
        tenderEvaluation.setUploadQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadQualifiedVendorsFileName());
        tenderEvaluation.setUploadTechnicallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadTechnicallyQualifiedVendorsFileName());
        tenderEvaluation.setUploadCommeriallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getUploadCommeriallyQualifiedVendorsFileName());
        tenderEvaluation.setFormationOfTechnoCommerialComitee(tenderEvaluationRequestDto.getFormationOfTechnoCommerialComitee());
        tenderEvaluation.setResponseFileName(tenderEvaluationRequestDto.getResponseFileName());
        tenderEvaluation.setResponseForTechnicallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getResponseForTechnicallyQualifiedVendorsFileName());
        tenderEvaluation.setResponseForCommeriallyQualifiedVendorsFileName(tenderEvaluationRequestDto.getResponseForCommeriallyQualifiedVendorsFileName());
        tenderEvaluation.setFileType(tenderEvaluationRequestDto.getFileType());
        tenderEvaluation.setCreatedBy(tenderEvaluationRequestDto.getCreatedBy());
        tenderEvaluation.setUpdatedBy(tenderEvaluationRequestDto.getUpdatedBy());


        tenderEvaluation.setUploadQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setUploadTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadTechnicallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setUploadCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getUploadCommeriallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setFormationOfTechnoCommerialComiteeCreatedBy(tenderEvaluationRequestDto.getFormationOfTechnoCommerialComiteeCreatedBy());
        tenderEvaluation.setResponseFileNameCreatedBy(tenderEvaluationRequestDto.getResponseFileNameCreatedBy());
        tenderEvaluation.setResponseForTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getResponseForTechnicallyQualifiedVendorsFileNameCreatedBy());
        tenderEvaluation.setResponseForCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluationRequestDto.getResponseForCommeriallyQualifiedVendorsFileNameCreatedBy());


        tenderEvaluationRepository.save(tenderEvaluation);

        return mapToResponseDTO(tenderEvaluation);

    }

    @Override
    public TenderEvaluationResponseWithBitTypeAndValueDto getTenderEvaluationById(String tenderId) {
        TenderEvaluation tenderEvaluation =tenderEvaluationRepository.findById(tenderId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Tender Evaluation not found for the provided tendeer ID.")
                ));
        TenderEvaluationResponseWithBitTypeAndValueDto responseDto = new TenderEvaluationResponseWithBitTypeAndValueDto();

        responseDto.setTenderId(tenderEvaluation.getTenderId());
        responseDto.setUploadQualifiedVendorsFileName(tenderEvaluation.getUploadQualifiedVendorsFileName());
        responseDto.setUploadTechnicallyQualifiedVendorsFileName(tenderEvaluation.getUploadTechnicallyQualifiedVendorsFileName());
        responseDto.setUploadCommeriallyQualifiedVendorsFileName(tenderEvaluation.getUploadCommeriallyQualifiedVendorsFileName());
        responseDto.setFormationOfTechnoCommerialComitee(tenderEvaluation.getFormationOfTechnoCommerialComitee());
        responseDto.setResponseFileName(tenderEvaluation.getResponseFileName());
        responseDto.setResponseForTechnicallyQualifiedVendorsFileName(tenderEvaluation.getResponseForTechnicallyQualifiedVendorsFileName());
        responseDto.setResponseForCommeriallyQualifiedVendorsFileName(tenderEvaluation.getResponseForCommeriallyQualifiedVendorsFileName());

        responseDto.setUploadQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadQualifiedVendorsFileNameCreatedBy());
        responseDto.setUploadTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadTechnicallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setUploadCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getUploadCommeriallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setFormationOfTechnoCommerialComiteeCreatedBy(tenderEvaluation.getFormationOfTechnoCommerialComiteeCreatedBy());
        responseDto.setResponseFileNameCreatedBy(tenderEvaluation.getResponseFileNameCreatedBy());
        responseDto.setResponseForTechnicallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getResponseForTechnicallyQualifiedVendorsFileNameCreatedBy());
        responseDto.setResponseForCommeriallyQualifiedVendorsFileNameCreatedBy(tenderEvaluation.getResponseForCommeriallyQualifiedVendorsFileNameCreatedBy());


        responseDto.setFileType(tenderEvaluation.getFileType());
        responseDto.setCreatedBy(tenderEvaluation.getCreatedBy());
        responseDto.setUpdatedBy(tenderEvaluation.getUpdatedBy());
        responseDto.setCreatedDate(tenderEvaluation.getCreatedDate());
        responseDto.setUpdatedDate(tenderEvaluation.getUpdatedDate());
        Optional<TenderRequest> optionalTenderRequest = tenderRequestRepository.findByTenderId(tenderId);
        TenderRequest tenderRequest = optionalTenderRequest.get();

        responseDto.setBidType(tenderRequest.getBidType());
        responseDto.setTotalValueOfTender(tenderRequest.getTotalTenderValue());

      return responseDto;
    }

    @Override
    public List<TenderEvaluationResponseDto> getAllTenderEvaluations() {
        List<TenderEvaluation> tenderEvaluations= tenderEvaluationRepository.findAll();
        return tenderEvaluations.stream().map(this::mapToResponseDTO).collect(Collectors.toList());

    }

    @Override
    public void deleteTenderEvaluation(String tenderId) {

        TenderEvaluation tenderEvaluation=tenderEvaluationRepository.findById(tenderId)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Tender evalualation not found for the provided tender id."
                        )
                ));
        try {
            tenderEvaluationRepository.delete(tenderEvaluation);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_ERROR,
                            "An error occurred while deleting the Tender Evalulation."
                    ),
                    ex
            );
        }

    }
}
