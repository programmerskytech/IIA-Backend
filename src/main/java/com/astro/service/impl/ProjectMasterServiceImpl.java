package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterRequestDTO;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterResponseDto;

import com.astro.entity.ProjectMaster;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.ProjectMasterRepository;
import com.astro.service.ProjectMasterService;
import com.astro.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService {
    @Autowired
    private ProjectMasterRepository projectMasterRepository;
    @Override
    public ProjectMasterResponseDto createProjectMaster(ProjectMasterRequestDTO projectMasterRequestDTO) {

        // Check if the indentorId already exists
        if (projectMasterRepository.existsById(projectMasterRequestDTO.getProjectCode())) {
            ErrorDetails errorDetails = new ErrorDetails(400, 1, "Duplicate project code", "project  code " + projectMasterRequestDTO.getProjectCode() + " already exists.");
            throw new InvalidInputException(errorDetails);
        }
        ProjectMaster projectMaster = new ProjectMaster();
        projectMaster.setProjectCode(projectMasterRequestDTO.getProjectCode());
        projectMaster.setProjectNameDescription(projectMasterRequestDTO.getProjectNameDescription());
        projectMaster.setFinancialYear(projectMasterRequestDTO.getFinancialYear());
        projectMaster.setAllocatedAmount(projectMasterRequestDTO.getAllocatedAmount());
        projectMaster.setDepartmentDivision(projectMasterRequestDTO.getDepartmentDivision());
        projectMaster.setBudgetType(projectMasterRequestDTO.getBudgetType());
        String Date = projectMasterRequestDTO.getStartDate();
        projectMaster.setStartDate(CommonUtils.convertStringToDateObject(Date));
        String endDate = projectMasterRequestDTO.getEndDate();
        projectMaster.setEndDate(CommonUtils.convertStringToDateObject(endDate));
        projectMaster.setRemarksNotes(projectMasterRequestDTO.getRemarksNotes());
        projectMaster.setProjectHead(projectMasterRequestDTO.getProjectHead());
        projectMaster.setCreatedBy(projectMasterRequestDTO.getCreatedBy());
        projectMaster.setUpdatedBy(projectMasterRequestDTO.getUpdatedBy());

        projectMasterRepository.save(projectMaster);

        return mapToResponseDTO(projectMaster);
    }



    @Override
    public ProjectMasterResponseDto updateProjectMaster(String projectCode, ProjectMasterRequestDTO projectMasterRequestDTO) {

        ProjectMaster projectMaster = projectMasterRepository.findById(projectCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Project master not found for the provided project code.")
                ));


      //  projectMaster.setProjectCode(projectMasterRequestDTO.getProjectCode());
        projectMaster.setProjectNameDescription(projectMasterRequestDTO.getProjectNameDescription());
        projectMaster.setFinancialYear(projectMasterRequestDTO.getFinancialYear());
        projectMaster.setAllocatedAmount(projectMasterRequestDTO.getAllocatedAmount());
        projectMaster.setDepartmentDivision(projectMasterRequestDTO.getDepartmentDivision());
        projectMaster.setBudgetType(projectMasterRequestDTO.getBudgetType());
        String Date = projectMasterRequestDTO.getStartDate();
        projectMaster.setStartDate(CommonUtils.convertStringToDateObject(Date));
        String endDate = projectMasterRequestDTO.getEndDate();
        projectMaster.setEndDate(CommonUtils.convertStringToDateObject(endDate));
        projectMaster.setRemarksNotes(projectMasterRequestDTO.getRemarksNotes());
        projectMaster.setProjectHead(projectMasterRequestDTO.getProjectHead());
        projectMaster.setCreatedBy(projectMasterRequestDTO.getCreatedBy());
        projectMaster.setUpdatedBy(projectMasterRequestDTO.getUpdatedBy());

        return mapToResponseDTO(projectMaster);
    }

    @Override
    public List<ProjectMasterResponseDto> getAllProjectMasters() {

        List<ProjectMaster> projectMasters= projectMasterRepository.findAll();
        return projectMasters.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectMasterResponseDto getProjectMasterById(String projectCode) {
        ProjectMaster projectMaster= projectMasterRepository.findById(projectCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Project master not found for the provided project code.")
                ));
        return mapToResponseDTO(projectMaster);
    }

    @Override
    public void deleteMaterialMaster(String projectCode) {

      ProjectMaster projectMaster=projectMasterRepository.findById(projectCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Project master not found for the provided Project code."
                        )
                ));
        try {
            projectMasterRepository.delete(projectMaster);
        } catch (Exception ex) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.INTER_SERVER_ERROR,
                            AppConstant.ERROR_TYPE_CODE_INTERNAL,
                            AppConstant.ERROR_TYPE_ERROR,
                            "An error occurred while deleting the Project master."
                    ),
                    ex
            );
        }

    }


    private ProjectMasterResponseDto mapToResponseDTO(ProjectMaster projectMaster) {

        ProjectMasterResponseDto responseDTO = new ProjectMasterResponseDto();
        responseDTO.setProjectCode(projectMaster.getProjectCode());
        responseDTO.setProjectNameDescription(projectMaster.getProjectNameDescription());
        responseDTO.setFinancialYear(projectMaster.getFinancialYear());
        responseDTO.setAllocatedAmount(projectMaster.getAllocatedAmount());
        responseDTO.setDepartmentDivision(projectMaster.getDepartmentDivision());
        responseDTO.setBudgetType(projectMaster.getBudgetType());
        LocalDate startDate =projectMaster.getStartDate();
        responseDTO.setStartDate(CommonUtils.convertDateToString(startDate));
        LocalDate endDate =projectMaster.getEndDate();
        responseDTO.setEndDate(CommonUtils.convertDateToString(endDate));
        responseDTO.setRemarksNotes(projectMaster.getRemarksNotes());
        responseDTO.setProjectHead(projectMaster.getProjectHead());
        responseDTO.setCreatedBy(projectMaster.getCreatedBy());
        responseDTO.setUpdatedBy(projectMaster.getUpdatedBy());
        responseDTO.setCreatedDate(projectMaster.getCreatedDate());
        responseDTO.setUpdatedDate(projectMaster.getUpdatedDate());

        return responseDTO;
    }
}
