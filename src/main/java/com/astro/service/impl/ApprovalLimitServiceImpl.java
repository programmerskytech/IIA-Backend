package com.astro.service.impl;

import com.astro.dto.AdminPanel.ApprovalLimitDTO;
import com.astro.dto.workflow.EscalationCheckResultDTO;
import com.astro.entity.AdminPanel.ApprovalLimitMaster;
import com.astro.repository.AdminPanel.ApprovalLimitMasterRepository;
import com.astro.service.AdminPanel.ApprovalLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalLimitServiceImpl implements ApprovalLimitService {

    @Autowired
    private ApprovalLimitMasterRepository repository;

    @Override
    public ApprovalLimitDTO create(ApprovalLimitDTO dto) {
        ApprovalLimitMaster entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public ApprovalLimitDTO update(Long limitId, ApprovalLimitDTO dto) {
        ApprovalLimitMaster entity = repository.findById(limitId)
                .orElseThrow(() -> new RuntimeException("Approval limit not found: " + limitId));

        entity.setRoleId(dto.getRoleId());
        entity.setRoleName(dto.getRoleName());
        entity.setCategory(dto.getCategory());
        entity.setDepartmentName(dto.getDepartmentName());
        entity.setLocation(dto.getLocation());
        entity.setMinAmount(dto.getMinAmount());
        entity.setMaxAmount(dto.getMaxAmount());
        entity.setEscalationRoleId(dto.getEscalationRoleId());
        entity.setEscalationRoleName(dto.getEscalationRoleName());
        entity.setIsActive(dto.getIsActive());
        entity.setPriority(dto.getPriority());
        entity.setUpdatedBy(dto.getUpdatedBy());

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long limitId) {
        ApprovalLimitMaster entity = repository.findById(limitId)
                .orElseThrow(() -> new RuntimeException("Approval limit not found: " + limitId));
        entity.setIsActive(false);
        repository.save(entity);
    }

    @Override
    public ApprovalLimitDTO getById(Long limitId) {
        return repository.findById(limitId)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Approval limit not found: " + limitId));
    }

    @Override
    public List<ApprovalLimitDTO> getAll() {
        return repository.findAllActiveOrderByRoleAndPriority()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovalLimitDTO> getByRole(String roleName) {
        return repository.findByRoleNameAndIsActiveTrue(roleName)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovalLimitDTO> getByRoleId(Integer roleId) {
        return repository.findByRoleIdAndIsActiveTrue(roleId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovalLimitDTO> getByCategory(String category) {
        return repository.findByCategoryAndIsActiveTrue(category)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApprovalLimitMaster findApplicableLimit(String roleName, String category, String departmentName, String location) {
        List<ApprovalLimitMaster> limits = repository.findApplicableLimits(
                null, // roleId - we'll match by name
                roleName,
                category,
                departmentName,
                location
        );

        // Return first matching limit (ordered by priority)
        return limits.isEmpty() ? null : limits.get(0);
    }

    @Override
    public boolean exceedsLimit(String roleName, String category, String departmentName, String location, BigDecimal amount) {
        ApprovalLimitMaster limit = findApplicableLimit(roleName, category, departmentName, location);
        if (limit == null) {
            return false; // No limit configured means no restriction
        }
        return limit.exceedsLimit(amount);
    }

    @Override
    public EscalationCheckResultDTO checkEscalation(String roleName, String category, String departmentName, String location, BigDecimal amount) {
        ApprovalLimitMaster limit = findApplicableLimit(roleName, category, departmentName, location);

        if (limit == null) {
            // No limit configured - no escalation needed
            return EscalationCheckResultDTO.noEscalation();
        }

        if (limit.exceedsLimit(amount)) {
            // Amount exceeds limit - escalation required
            String reason = String.format(
                    "Amount ₹%s exceeds %s's approval limit of ₹%s",
                    amount.toString(),
                    roleName,
                    limit.getMaxAmount() != null ? limit.getMaxAmount().toString() : "unlimited"
            );

            EscalationCheckResultDTO result = EscalationCheckResultDTO.escalateTo(
                    limit.getEscalationRoleName(),
                    reason,
                    amount,
                    limit.getMaxAmount()
            );
            result.setEscalationRoleId(limit.getEscalationRoleId());
            result.setCurrentApproverRole(roleName);
            return result;
        }

        // Amount within limit - no escalation needed
        EscalationCheckResultDTO result = EscalationCheckResultDTO.noEscalation();
        result.setCurrentApproverRole(roleName);
        result.setApproverLimit(limit.getMaxAmount());
        result.setCurrentAmount(amount);
        return result;
    }

    @Override
    public BigDecimal getMaxApprovalAmount(String roleName, String category, String departmentName, String location) {
        ApprovalLimitMaster limit = findApplicableLimit(roleName, category, departmentName, location);
        return limit != null ? limit.getMaxAmount() : null;
    }

    // Conversion methods
    private ApprovalLimitDTO toDTO(ApprovalLimitMaster entity) {
        ApprovalLimitDTO dto = new ApprovalLimitDTO();
        dto.setLimitId(entity.getLimitId());
        dto.setRoleId(entity.getRoleId());
        dto.setRoleName(entity.getRoleName());
        dto.setCategory(entity.getCategory());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setLocation(entity.getLocation());
        dto.setMinAmount(entity.getMinAmount());
        dto.setMaxAmount(entity.getMaxAmount());
        dto.setEscalationRoleId(entity.getEscalationRoleId());
        dto.setEscalationRoleName(entity.getEscalationRoleName());
        dto.setIsActive(entity.getIsActive());
        dto.setPriority(entity.getPriority());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    private ApprovalLimitMaster toEntity(ApprovalLimitDTO dto) {
        ApprovalLimitMaster entity = new ApprovalLimitMaster();
        entity.setRoleId(dto.getRoleId());
        entity.setRoleName(dto.getRoleName());
        entity.setCategory(dto.getCategory());
        entity.setDepartmentName(dto.getDepartmentName());
        entity.setLocation(dto.getLocation());
        entity.setMinAmount(dto.getMinAmount() != null ? dto.getMinAmount() : BigDecimal.ZERO);
        entity.setMaxAmount(dto.getMaxAmount());
        entity.setEscalationRoleId(dto.getEscalationRoleId());
        entity.setEscalationRoleName(dto.getEscalationRoleName());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setPriority(dto.getPriority() != null ? dto.getPriority() : 0);
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    @Override
    public ApprovalLimitDTO updateStatus(Long limitId, Boolean isActive, String updatedBy) {
        ApprovalLimitMaster entity = repository.findById(limitId)
                .orElseThrow(() -> new RuntimeException("Approval limit not found: " + limitId));
        entity.setIsActive(isActive);
        entity.setUpdatedBy(updatedBy);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public List<String> getDistinctRoles() {
        return repository.findDistinctRoleNames();
    }

    @Override
    public List<String> getDistinctCategories() {
        return repository.findDistinctCategories();
    }
}
