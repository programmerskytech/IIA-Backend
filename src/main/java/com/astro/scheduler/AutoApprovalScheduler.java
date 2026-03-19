package com.astro.scheduler;

import com.astro.dto.workflow.WorkflowTransitionDto;
import com.astro.entity.AdminPanel.ApproverMaster;
import com.astro.entity.WorkflowTransition;
import com.astro.repository.AdminPanel.ApproverMasterRepository;
import com.astro.repository.WorkflowTransitionRepository;
import com.astro.service.WorkflowService;
import com.astro.util.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class AutoApprovalScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AutoApprovalScheduler.class);

    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;

    @Autowired
    private ApproverMasterRepository approverMasterRepository;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 * * * ?")
    public void processAutoApprovals() {
        logger.info("Auto-approval scheduler started");

        List<WorkflowTransition> pendingTransitions = workflowTransitionRepository.findPendingBranchTransitions();

        if (pendingTransitions.isEmpty()) {
            logger.info("No pending branch transitions found");
            return;
        }

        int autoApprovedCount = 0;

        for (WorkflowTransition transition : pendingTransitions) {
            try {
                ApproverMaster approver = approverMasterRepository.findById(transition.getApproverId()).orElse(null);

                if (approver == null || approver.getAutoApproveHours() == null || approver.getAutoApproveHours() <= 0) {
                    continue;
                }

                Date transitionCreatedDate = transition.getModificationDate() != null
                        ? transition.getModificationDate()
                        : transition.getCreatedDate();

                if (transitionCreatedDate == null) {
                    continue;
                }

                Instant deadline = transitionCreatedDate.toInstant().plus(approver.getAutoApproveHours(), ChronoUnit.HOURS);

                if (Instant.now().isAfter(deadline)) {
                    logger.info("Auto-approving request {} - approver {} exceeded {} hours",
                            transition.getRequestId(), approver.getRoleName(), approver.getAutoApproveHours());

                    WorkflowTransitionDto result = workflowService.performAutoApproval(transition, approver.getAutoApproveHours());

                    if (result != null) {
                        autoApprovedCount++;

                        try {
                            emailService.sendAutoApprovalNotification(
                                    transition, approver.getRoleName(), approver.getAutoApproveHours());
                        } catch (Exception emailEx) {
                            logger.error("Failed to send auto-approval email for request {}: {}",
                                    transition.getRequestId(), emailEx.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error processing auto-approval for transition {}: {}",
                        transition.getWorkflowTransitionId(), e.getMessage(), e);
            }
        }

        logger.info("Auto-approval scheduler completed. Auto-approved: {}", autoApprovedCount);
    }
}
