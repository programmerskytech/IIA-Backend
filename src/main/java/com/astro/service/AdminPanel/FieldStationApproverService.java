package com.astro.service.AdminPanel;

import com.astro.dto.AdminPanel.FieldStationApproverDTO;
import com.astro.entity.AdminPanel.FieldStationApproverMaster;

import java.util.List;

public interface FieldStationApproverService {

    // CRUD operations
    FieldStationApproverDTO create(FieldStationApproverDTO dto);

    FieldStationApproverDTO update(Long id, FieldStationApproverDTO dto);

    void delete(Long id);

    FieldStationApproverDTO getById(Long id);

    List<FieldStationApproverDTO> getAll();

    // Query operations
    FieldStationApproverDTO getByFieldStationName(String fieldStationName);

    FieldStationApproverMaster getFieldStationInCharge(String location);

    /**
     * Check if a field station has an in-charge configured
     */
    boolean hasInCharge(String fieldStationName);

    /**
     * Get all field station names
     */
    List<String> getAllFieldStationNames();

    /**
     * Check if location is a non-Bangalore field station
     */
    boolean isFieldStation(String location);

    /**
     * Get all in-charges by type (ENGINEER_INCHARGE or PROFESSOR_INCHARGE)
     */
    List<FieldStationApproverDTO> getByInchargeType(String inchargeType);

    /**
     * Get all Engineer In-Charges
     */
    List<FieldStationApproverDTO> getEngineerInCharges();

    /**
     * Get all Professor In-Charges
     */
    List<FieldStationApproverDTO> getProfessorInCharges();

    /**
     * Get in-charge by field station name and type
     */
    FieldStationApproverDTO getByFieldStationAndType(String fieldStationName, String inchargeType);

    /**
     * Get distinct field station names
     */
    List<String> getDistinctFieldStations();
}
