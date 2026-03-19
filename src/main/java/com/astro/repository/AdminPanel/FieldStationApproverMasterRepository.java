package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.FieldStationApproverMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldStationApproverMasterRepository extends JpaRepository<FieldStationApproverMaster, Long> {

    /**
     * Find field station in-charge by station name
     */
    Optional<FieldStationApproverMaster> findByFieldStationNameAndIsActiveTrue(String fieldStationName);

    /**
     * Find all active field station approvers
     */
    List<FieldStationApproverMaster> findByIsActiveTrue();

    /**
     * Find by in-charge employee ID
     */
    List<FieldStationApproverMaster> findByInchargeEmployeeIdAndIsActiveTrue(String inchargeEmployeeId);

    /**
     * Check if field station exists
     */
    boolean existsByFieldStationNameAndIsActiveTrue(String fieldStationName);

    /**
     * Find field station by name (case-insensitive)
     */
    @Query("SELECT f FROM FieldStationApproverMaster f WHERE " +
           "LOWER(f.fieldStationName) = LOWER(:stationName) AND f.isActive = true")
    Optional<FieldStationApproverMaster> findByFieldStationNameIgnoreCase(@Param("stationName") String fieldStationName);

    /**
     * Get all field station names
     */
    @Query("SELECT f.fieldStationName FROM FieldStationApproverMaster f WHERE f.isActive = true ORDER BY f.fieldStationName")
    List<String> findAllFieldStationNames();

    /**
     * Find by field station name and in-charge type
     */
    @Query("SELECT f FROM FieldStationApproverMaster f WHERE " +
           "LOWER(f.fieldStationName) = LOWER(:stationName) AND " +
           "LOWER(f.inchargeType) = LOWER(:inchargeType) AND f.isActive = true")
    Optional<FieldStationApproverMaster> findByFieldStationNameAndInchargeType(
            @Param("stationName") String fieldStationName,
            @Param("inchargeType") String inchargeType);

    /**
     * Find all Engineer In-Charges for a field station
     */
    @Query("SELECT f FROM FieldStationApproverMaster f WHERE " +
           "LOWER(f.fieldStationName) = LOWER(:stationName) AND " +
           "f.inchargeType = 'ENGINEER_INCHARGE' AND f.isActive = true")
    List<FieldStationApproverMaster> findEngineerInChargesByStation(@Param("stationName") String fieldStationName);

    /**
     * Find all Professor In-Charges for a field station
     */
    @Query("SELECT f FROM FieldStationApproverMaster f WHERE " +
           "LOWER(f.fieldStationName) = LOWER(:stationName) AND " +
           "f.inchargeType = 'PROFESSOR_INCHARGE' AND f.isActive = true")
    List<FieldStationApproverMaster> findProfessorInChargesByStation(@Param("stationName") String fieldStationName);

    /**
     * Find all in-charges by type
     */
    List<FieldStationApproverMaster> findByInchargeTypeAndIsActiveTrue(String inchargeType);

    /**
     * Find all distinct field station names with their in-charge types
     */
    @Query("SELECT DISTINCT f.fieldStationName FROM FieldStationApproverMaster f WHERE f.isActive = true ORDER BY f.fieldStationName")
    List<String> findDistinctFieldStationNames();
}
