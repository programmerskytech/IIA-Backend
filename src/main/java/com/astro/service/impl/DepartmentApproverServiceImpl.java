package com.astro.service.impl;

import com.astro.dto.AdminPanel.DepartmentApproverMappingDTO;
import com.astro.entity.AdminPanel.DepartmentApproverMapping;
import com.astro.repository.AdminPanel.DepartmentApproverMappingRepository;
import com.astro.service.AdminPanel.DepartmentApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentApproverServiceImpl implements DepartmentApproverService {

    @Autowired
    private DepartmentApproverMappingRepository repository;

    @Override
    public DepartmentApproverMappingDTO create(DepartmentApproverMappingDTO dto) {
        DepartmentApproverMapping entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public DepartmentApproverMappingDTO update(Long mappingId, DepartmentApproverMappingDTO dto) {
        DepartmentApproverMapping entity = repository.findById(mappingId)
                .orElseThrow(() -> new RuntimeException("Department approver mapping not found: " + mappingId));

        entity.setDepartmentName(dto.getDepartmentName());
        entity.setApproverType(dto.getApproverType());
        entity.setApproverEmployeeId(dto.getApproverEmployeeId());
        entity.setApproverRoleId(dto.getApproverRoleId());
        entity.setApprovalLimit(dto.getApprovalLimit());
        entity.setIsActive(dto.getIsActive());
        entity.setUpdatedBy(dto.getUpdatedBy());

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long mappingId) {
        DepartmentApproverMapping entity = repository.findById(mappingId)
                .orElseThrow(() -> new RuntimeException("Department approver mapping not found: " + mappingId));
        entity.setIsActive(false);
        repository.save(entity);
    }

    @Override
    public DepartmentApproverMappingDTO getById(Long mappingId) {
        return repository.findById(mappingId)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Department approver mapping not found: " + mappingId));
    }

    @Override
    public List<DepartmentApproverMappingDTO> getAll() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentApproverMappingDTO> getByDepartment(String departmentName) {
        return repository.findByDepartmentNameAndIsActiveTrue(departmentName)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentApproverMappingDTO getDeanForDepartment(String departmentName) {
        return repository.findDeanByDepartment(departmentName)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public DepartmentApproverMappingDTO getHeadSEGForDepartment(String departmentName) {
        return repository.findHeadSEGByDepartment(departmentName)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public DepartmentApproverMappingDTO getApproverByTypeAndDepartment(String departmentName, String approverType) {
        return repository.findByDepartmentNameAndApproverTypeAndIsActiveTrue(departmentName, approverType)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<DepartmentApproverMappingDTO> getAllDeans() {
        return repository.findByApproverTypeAndIsActiveTrue("DEAN")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepartmentApproverMappingDTO> getAllHeadSEGs() {
        return repository.findByApproverTypeAndIsActiveTrue("HEAD_SEG")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentApproverMapping getApproverForDepartmentAndAmount(String departmentName, BigDecimal amount) {
        // First, check if the department has a configured approver
        List<DepartmentApproverMapping> approvers = repository.findByDepartmentNameAndIsActiveTrue(departmentName);

        if (approvers.isEmpty()) {
            System.out.println("⚠️ No approvers configured for department: " + departmentName);
            return null;
        }

        // Find Head SEG first (lower limit = ₹1,00,000)
        Optional<DepartmentApproverMapping> headSEG = approvers.stream()
                .filter(a -> "HEAD_SEG".equalsIgnoreCase(a.getApproverType()))
                .findFirst();

        // Find Dean (higher limit = ₹1,50,000)
        Optional<DepartmentApproverMapping> dean = approvers.stream()
                .filter(a -> "DEAN".equalsIgnoreCase(a.getApproverType()))
                .findFirst();

        // Decision logic based on amount
        if (headSEG.isPresent() && !headSEG.get().exceedsLimit(amount)) {
            // Amount within Head SEG's limit
            System.out.println("✅ Amount ₹" + amount + " within Head SEG's limit for " + departmentName);
            return headSEG.get();
        } else if (dean.isPresent()) {
            // Either Head SEG limit exceeded or not configured, route to Dean
            if (dean.get().exceedsLimit(amount)) {
                System.out.println("⚠️ Amount ₹" + amount + " exceeds Dean's limit for " + departmentName + " - will escalate to Director");
            } else {
                System.out.println("✅ Amount ₹" + amount + " within Dean's limit for " + departmentName);
            }
            return dean.get();
        } else if (headSEG.isPresent()) {
            // Only Head SEG configured
            System.out.println("⚠️ Only Head SEG configured for " + departmentName + ", amount may exceed limit");
            return headSEG.get();
        }

        return null;
    }

    @Override
    public boolean hasApprover(String departmentName, String approverType) {
        if (approverType == null) {
            // Check if department has any approver
            return !repository.findByDepartmentNameAndIsActiveTrue(departmentName).isEmpty();
        }
        return repository.findByDepartmentNameAndApproverTypeAndIsActiveTrue(departmentName, approverType).isPresent();
    }

    @Override
    public List<String> getDepartmentsWithApprovers() {
        return repository.findDistinctDepartmentNames();
    }

    @Override
    public List<DepartmentApproverMappingDTO> getByApproverType(String approverType) {
        return repository.findByApproverTypeAndIsActiveTrue(approverType)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentApproverMappingDTO findApproverForAmount(String departmentName, BigDecimal indentValue) {
        DepartmentApproverMapping approver = getApproverForDepartmentAndAmount(departmentName, indentValue);
        return approver != null ? toDTO(approver) : null;
    }

    // Conversion methods
    private DepartmentApproverMappingDTO toDTO(DepartmentApproverMapping entity) {
        DepartmentApproverMappingDTO dto = new DepartmentApproverMappingDTO();
        dto.setMappingId(entity.getMappingId());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setApproverType(entity.getApproverType());
        dto.setApproverEmployeeId(entity.getApproverEmployeeId());
        dto.setApproverRoleId(entity.getApproverRoleId());
        dto.setApprovalLimit(entity.getApprovalLimit());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    private DepartmentApproverMapping toEntity(DepartmentApproverMappingDTO dto) {
        DepartmentApproverMapping entity = new DepartmentApproverMapping();
        entity.setDepartmentName(dto.getDepartmentName());
        entity.setApproverType(dto.getApproverType());
        entity.setApproverEmployeeId(dto.getApproverEmployeeId());
        entity.setApproverRoleId(dto.getApproverRoleId());
        entity.setApprovalLimit(dto.getApprovalLimit());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }
}
