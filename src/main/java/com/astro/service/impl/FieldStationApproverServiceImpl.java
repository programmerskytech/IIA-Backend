package com.astro.service.impl;

import com.astro.dto.AdminPanel.FieldStationApproverDTO;
import com.astro.entity.AdminPanel.FieldStationApproverMaster;
import com.astro.repository.AdminPanel.FieldStationApproverMasterRepository;
import com.astro.service.AdminPanel.FieldStationApproverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldStationApproverServiceImpl implements FieldStationApproverService {

    @Autowired
    private FieldStationApproverMasterRepository repository;

    @Override
    public FieldStationApproverDTO create(FieldStationApproverDTO dto) {
        FieldStationApproverMaster entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public FieldStationApproverDTO update(Long id, FieldStationApproverDTO dto) {
        FieldStationApproverMaster entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field station approver not found: " + id));

        entity.setFieldStationName(dto.getFieldStationName());
        entity.setInchargeEmployeeId(dto.getInchargeEmployeeId());
        entity.setInchargeRoleId(dto.getInchargeRoleId());
        entity.setApprovalLimit(dto.getApprovalLimit());
        entity.setIsActive(dto.getIsActive());
        entity.setUpdatedBy(dto.getUpdatedBy());

        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        FieldStationApproverMaster entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Field station approver not found: " + id));
        entity.setIsActive(false);
        repository.save(entity);
    }

    @Override
    public FieldStationApproverDTO getById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Field station approver not found: " + id));
    }

    @Override
    public List<FieldStationApproverDTO> getAll() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FieldStationApproverDTO getByFieldStationName(String fieldStationName) {
        return repository.findByFieldStationNameAndIsActiveTrue(fieldStationName)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public FieldStationApproverMaster getFieldStationInCharge(String location) {
        if (location == null || location.trim().isEmpty()) {
            return null;
        }

        // Normalize location
        String normalizedLocation = location.trim().toUpperCase();

        // Check if it's Bangalore - no field station in-charge needed
        if ("BANGALORE".equals(normalizedLocation) || "BLR".equals(normalizedLocation)) {
            System.out.println("📍 Location is Bangalore - no field station in-charge needed");
            return null;
        }

        // Try to find by exact match first
        return repository.findByFieldStationNameIgnoreCase(location)
                .orElseGet(() -> {
                    System.out.println("⚠️ No field station in-charge found for location: " + location);
                    return null;
                });
    }

    @Override
    public boolean hasInCharge(String fieldStationName) {
        return repository.existsByFieldStationNameAndIsActiveTrue(fieldStationName);
    }

    @Override
    public List<String> getAllFieldStationNames() {
        return repository.findAllFieldStationNames();
    }

    @Override
    public boolean isFieldStation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }

        String normalizedLocation = location.trim().toUpperCase();

        // Bangalore is NOT a field station
        if ("BANGALORE".equals(normalizedLocation) || "BLR".equals(normalizedLocation)) {
            return false;
        }

        // Check if the location exists in field station master
        return repository.existsByFieldStationNameAndIsActiveTrue(location) ||
               repository.findByFieldStationNameIgnoreCase(location).isPresent();
    }

    @Override
    public List<FieldStationApproverDTO> getByInchargeType(String inchargeType) {
        return repository.findByInchargeTypeAndIsActiveTrue(inchargeType)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FieldStationApproverDTO> getEngineerInCharges() {
        return getByInchargeType("ENGINEER_INCHARGE");
    }

    @Override
    public List<FieldStationApproverDTO> getProfessorInCharges() {
        return getByInchargeType("PROFESSOR_INCHARGE");
    }

    @Override
    public FieldStationApproverDTO getByFieldStationAndType(String fieldStationName, String inchargeType) {
        return repository.findByFieldStationNameAndInchargeType(fieldStationName, inchargeType)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<String> getDistinctFieldStations() {
        return repository.findDistinctFieldStationNames();
    }

    // Conversion methods
    private FieldStationApproverDTO toDTO(FieldStationApproverMaster entity) {
        FieldStationApproverDTO dto = new FieldStationApproverDTO();
        dto.setId(entity.getId());
        dto.setFieldStationName(entity.getFieldStationName());
        dto.setInchargeType(entity.getInchargeType());
        dto.setInchargeEmployeeId(entity.getInchargeEmployeeId());
        dto.setInchargeEmployeeName(entity.getInchargeEmployeeName());
        dto.setInchargeName(entity.getInchargeEmployeeName());
        dto.setInchargeRoleId(entity.getInchargeRoleId());
        dto.setRoleName(entity.getRoleName());
        dto.setApprovalLimit(entity.getApprovalLimit());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }

    private FieldStationApproverMaster toEntity(FieldStationApproverDTO dto) {
        FieldStationApproverMaster entity = new FieldStationApproverMaster();
        entity.setFieldStationName(dto.getFieldStationName());
        entity.setInchargeType(dto.getInchargeType() != null ? dto.getInchargeType() : "ENGINEER_INCHARGE");
        entity.setInchargeEmployeeId(dto.getInchargeEmployeeId());
        entity.setInchargeEmployeeName(dto.getInchargeEmployeeName());
        entity.setInchargeRoleId(dto.getInchargeRoleId());
        entity.setApprovalLimit(dto.getApprovalLimit());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }
}
