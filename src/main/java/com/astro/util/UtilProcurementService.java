package com.astro.util;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProcurementDtos.CancelTenderRequestDto;
import com.astro.dto.workflow.TransitionActionReqDto;
import com.astro.entity.ProcurementModule.TenderRequest;
import com.astro.entity.VendorQuotationAgainstTender;
import com.astro.entity.WorkflowTransition;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.repository.ProcurementModule.IndentCreation.IndentCreationRepository;
import com.astro.repository.ProcurementModule.IndentIdRepository;
import com.astro.repository.ProcurementModule.TenderRequestRepository;
import com.astro.repository.VendorQuotationAgainstTenderRepository;
import com.astro.repository.WorkflowTransitionRepository;
import com.astro.service.IndentCreationService;
import com.astro.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UtilProcurementService {
    @Autowired
    private TenderRequestRepository TRrepo;
    @Autowired
    private IndentCreationService indentCreationService;
    @Autowired
    private IndentIdRepository indentIdRepository;
    @Autowired
    private IndentCreationRepository indentCreationRepository;
    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private VendorQuotationAgainstTenderRepository vendorQuotationAgainstTenderRepository;

    public String cancelTender(CancelTenderRequestDto request) {
        TenderRequest tenderRequest = TRrepo.findById(request.getTenderId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_RESOURCE,
                                "Tender not found for the provided ID."
                        )
                ));

        //  Fetch all indent IDs related to this tender
        List<String> indentIds = indentIdRepository.findTenderWithIndent(tenderRequest.getTenderId());

        // Check if any indent is NOT cancelled in workflow
        boolean hasActiveIndents = indentIds.stream().anyMatch(indentId -> {
            WorkflowTransition lastTransition = workflowTransitionRepository
                    .findTopByRequestIdOrderByTransitionOrderDescWorkflowTransitionIdDesc(indentId);
            return lastTransition != null && !"Canceled".equalsIgnoreCase(lastTransition.getStatus());
        });


        //  All indents are cancelled → proceed to cancel tender
        tenderRequest.setCancelStatus(request.getCancelStatus());
        tenderRequest.setCancelRemarks(request.getCancelRemarks());
        TRrepo.save(tenderRequest);

        // latest workflow transition tender id
        WorkflowTransition latestTransition = workflowTransitionRepository
                .findTopByRequestIdOrderByTransitionOrderDescWorkflowTransitionIdDesc(request.getTenderId());

        TransitionActionReqDto transitionDto = new TransitionActionReqDto();
        transitionDto.setWorkflowTransitionId(latestTransition.getWorkflowTransitionId());
        transitionDto.setRequestId(latestTransition.getRequestId());
        transitionDto.setActionBy(request.getActionBy());
        transitionDto.setAction("REJECTED");
        transitionDto.setRemarks(request.getCancelRemarks());
        transitionDto.setAssignmentRole(latestTransition.getCurrentRole());

        // 7️⃣ Perform workflow action
        workflowService.performTransitionAction(transitionDto);

        // Fetch lastest existing vendor quotations for this tender
        // Fetch all latest vendor quotations for this tender
        List<VendorQuotationAgainstTender> latestQuotations =
                vendorQuotationAgainstTenderRepository.findAllLatestByTenderId(tenderRequest.getTenderId());

        for (VendorQuotationAgainstTender v : latestQuotations) {
            // Mark old as not latest
            v.setIsLatest(false);
            vendorQuotationAgainstTenderRepository.save(v);

            // Create a new CANCELLED quotation
            VendorQuotationAgainstTender cancelledQuotation = new VendorQuotationAgainstTender();
            cancelledQuotation.setTenderId(v.getTenderId());
            cancelledQuotation.setVendorId(v.getVendorId());
            cancelledQuotation.setQuotationFileName(v.getQuotationFileName());
            cancelledQuotation.setPriceBidFileName(v.getPriceBidFileName());
            cancelledQuotation.setFileType(v.getFileType());
            cancelledQuotation.setClarificationFileName(v.getClarificationFileName());
            cancelledQuotation.setVendorResponse(v.getVendorResponse());
            cancelledQuotation.setCreatedBy(v.getCreatedBy());
            cancelledQuotation.setVersion(v.getVersion() + 1);
            cancelledQuotation.setIsLatest(true);
            cancelledQuotation.setStatus("CANCELLED");
            cancelledQuotation.setModifiedBy(request.getActionBy());
            cancelledQuotation.setCurrentRole(v.getCurrentRole());
            cancelledQuotation.setNextRole(v.getNextRole());
            cancelledQuotation.setCreatedDate(LocalDateTime.now());
            cancelledQuotation.setUpdatedDate(LocalDateTime.now());

            vendorQuotationAgainstTenderRepository.save(cancelledQuotation);
        }
        return "Tender cancelled successfully.";
    }
}
