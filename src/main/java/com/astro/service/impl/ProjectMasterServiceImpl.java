package com.astro.service.impl;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterRequestDTO;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterResponseDto;

import com.astro.entity.AdminPanel.BudgetMaster;
import com.astro.entity.ProjectMaster;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.exception.InvalidInputException;
import com.astro.repository.AdminPanel.BudgetMasterRepository;
import com.astro.repository.ProjectMasterRepository;
import com.astro.service.ProjectMasterService;
import com.astro.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService {
    @Autowired
    private ProjectMasterRepository projectMasterRepository;

    @Autowired
    private BudgetMasterRepository budgetMasterRepository;

    @Autowired
    private com.astro.repository.ProcurementModule.PurchaseOrder.PurchaseOrderRepository purchaseOrderRepository;
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

        // Handle Invalid Date strings from frontend
        String financialYear = projectMasterRequestDTO.getFinancialYear();
        if (financialYear != null && !financialYear.equalsIgnoreCase("Invalid Date")) {
            projectMaster.setFinancialYear(financialYear);
        }

        projectMaster.setAllocatedAmount(projectMasterRequestDTO.getAllocatedAmount());
        projectMaster.setAvailableProjectLimit(projectMasterRequestDTO.getAvailableProjectLimit());
        projectMaster.setDepartmentDivision(projectMasterRequestDTO.getDepartmentDivision());
        projectMaster.setBudgetType(projectMasterRequestDTO.getBudgetType());
        String Date = projectMasterRequestDTO.getStartDate();
        projectMaster.setStartDate(CommonUtils.convertIsoDateStringToDateObject(Date));
        String endDate = projectMasterRequestDTO.getEndDate();
        projectMaster.setEndDate(CommonUtils.convertIsoDateStringToDateObject(endDate));
        projectMaster.setRemarksNotes(projectMasterRequestDTO.getRemarksNotes());
        projectMaster.setProjectHead(projectMasterRequestDTO.getProjectHead());
        projectMaster.setProjectHeadName(projectMasterRequestDTO.getProjectHeadName());

        // Set new Admin Panel fields
        projectMaster.setStatus(projectMasterRequestDTO.getStatus() != null ? projectMasterRequestDTO.getStatus() : "Active");
        projectMaster.setCategory(projectMasterRequestDTO.getCategory());
        projectMaster.setBudgetCode(projectMasterRequestDTO.getBudgetCode());

        projectMaster.setCreatedBy(projectMasterRequestDTO.getCreatedBy());
        projectMaster.setUpdatedBy(projectMasterRequestDTO.getUpdatedBy());

        projectMasterRepository.save(projectMaster);
        linkBudgetToProject(projectMaster.getBudgetCode(), projectMaster.getProjectCode());

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

        // Handle Invalid Date strings from frontend
        String financialYear = projectMasterRequestDTO.getFinancialYear();
        if (financialYear != null && !financialYear.equalsIgnoreCase("Invalid Date")) {
            projectMaster.setFinancialYear(financialYear);
        }

        projectMaster.setAllocatedAmount(projectMasterRequestDTO.getAllocatedAmount());
        if (projectMasterRequestDTO.getAvailableProjectLimit() != null) {
            projectMaster.setAvailableProjectLimit(projectMasterRequestDTO.getAvailableProjectLimit());
        }
        projectMaster.setDepartmentDivision(projectMasterRequestDTO.getDepartmentDivision());
        projectMaster.setBudgetType(projectMasterRequestDTO.getBudgetType());
        String Date = projectMasterRequestDTO.getStartDate();
        projectMaster.setStartDate(CommonUtils.convertIsoDateStringToDateObject(Date));
        String endDate = projectMasterRequestDTO.getEndDate();
        projectMaster.setEndDate(CommonUtils.convertIsoDateStringToDateObject(endDate));
        projectMaster.setRemarksNotes(projectMasterRequestDTO.getRemarksNotes());
        projectMaster.setProjectHead(projectMasterRequestDTO.getProjectHead());
        projectMaster.setProjectHeadName(projectMasterRequestDTO.getProjectHeadName());

        // Update Admin Panel fields
        if (projectMasterRequestDTO.getStatus() != null) {
            projectMaster.setStatus(projectMasterRequestDTO.getStatus());
        }
        if (projectMasterRequestDTO.getCategory() != null) {
            projectMaster.setCategory(projectMasterRequestDTO.getCategory());
        }
        if (projectMasterRequestDTO.getBudgetCode() != null) {
            projectMaster.setBudgetCode(projectMasterRequestDTO.getBudgetCode());
        }

        projectMaster.setCreatedBy(projectMasterRequestDTO.getCreatedBy());
        projectMaster.setUpdatedBy(projectMasterRequestDTO.getUpdatedBy());

        projectMasterRepository.save(projectMaster);
        linkBudgetToProject(projectMaster.getBudgetCode(), projectMaster.getProjectCode());

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


    @Override
    public ProjectMasterResponseDto getProjectAvailableBudget(String projectCode) {
        ProjectMaster projectMaster = projectMasterRepository.findById(projectCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Project master not found for the provided project code."
                        )
                ));

        // Calculate total PO value for this project
        BigDecimal totalPoValue = purchaseOrderRepository.getTotalPoValueByProjectName(projectMaster.getProjectNameDescription());

        if (totalPoValue == null) {
            totalPoValue = BigDecimal.ZERO;
        }

        // Calculate available budget
        BigDecimal availableBudget = projectMaster.getAllocatedAmount().subtract(totalPoValue);
        projectMaster.setAvailableProjectLimit(availableBudget);

        ProjectMasterResponseDto responseDTO = mapToResponseDTO(projectMaster);
        responseDTO.setAvailableProjectLimit(availableBudget);

        return responseDTO;
    }

    @Override
    @Transactional
    public void updateProjectAvailableBudget(String projectCode) {
        ProjectMaster projectMaster = projectMasterRepository.findById(projectCode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Project master not found for the provided project code."
                        )
                ));

        // Calculate total PO value for this project
        BigDecimal totalPoValue = purchaseOrderRepository.getTotalPoValueByProjectName(projectMaster.getProjectNameDescription());

        if (totalPoValue == null) {
            totalPoValue = BigDecimal.ZERO;
        }

        // Update available budget
        BigDecimal availableBudget = projectMaster.getAllocatedAmount().subtract(totalPoValue);
        projectMaster.setAvailableProjectLimit(availableBudget);

        projectMasterRepository.save(projectMaster);
    }

    private ProjectMasterResponseDto mapToResponseDTO(ProjectMaster projectMaster) {

        ProjectMasterResponseDto responseDTO = new ProjectMasterResponseDto();
        responseDTO.setProjectCode(projectMaster.getProjectCode());
        responseDTO.setProjectNameDescription(projectMaster.getProjectNameDescription());
        responseDTO.setFinancialYear(projectMaster.getFinancialYear());
        responseDTO.setAllocatedAmount(projectMaster.getAllocatedAmount());
        responseDTO.setAvailableProjectLimit(projectMaster.getAvailableProjectLimit());
        responseDTO.setDepartmentDivision(projectMaster.getDepartmentDivision());
        responseDTO.setBudgetType(projectMaster.getBudgetType());
        LocalDate startDate = projectMaster.getStartDate();
        responseDTO.setStartDate(startDate != null ? startDate.toString() : null);  // ISO yyyy-MM-dd
        LocalDate endDate = projectMaster.getEndDate();
        responseDTO.setEndDate(endDate != null ? endDate.toString() : null);  // ISO yyyy-MM-dd
        responseDTO.setRemarksNotes(projectMaster.getRemarksNotes());
        responseDTO.setProjectHead(projectMaster.getProjectHead());
        responseDTO.setProjectHeadName(projectMaster.getProjectHeadName());

        // Map new Admin Panel fields
        responseDTO.setStatus(projectMaster.getStatus());
        responseDTO.setCategory(projectMaster.getCategory());
        responseDTO.setBudgetCode(projectMaster.getBudgetCode());

        responseDTO.setCreatedBy(projectMaster.getCreatedBy());
        responseDTO.setUpdatedBy(projectMaster.getUpdatedBy());
        responseDTO.setCreatedDate(projectMaster.getCreatedDate());
        responseDTO.setUpdatedDate(projectMaster.getUpdatedDate());

        return responseDTO;
    }

    /**
     * When a project is linked to a budget, update BudgetMaster.projectCode so both
     * directions of the relationship are stored (bidirectional link).
     */
    private void linkBudgetToProject(String budgetCode, String projectCode) {
        if (budgetCode == null || budgetCode.trim().isEmpty()) return;
        Optional<BudgetMaster> budgetOpt = budgetMasterRepository.findByBudgetCode(budgetCode);
        budgetOpt.ifPresent(budget -> {
            budget.setProjectCode(projectCode);
            budgetMasterRepository.save(budget);
        });
    }
}
