package com.astro.service.AdminPanel;

import com.astro.entity.AdminPanel.DesignatorMaster;
import com.astro.entity.AdminPanel.FormMaster;
import com.astro.entity.AdminPanel.LOVMaster;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service interface for managing List of Values (LOV) across all forms.
 * Provides centralized dropdown management for the entire application.
 */
public interface LOVService {

    // ========== FORM MASTER OPERATIONS ==========

    /**
     * Get all forms
     * @return List of all forms
     */
    List<FormMaster> getAllForms();

    /**
     * Get all active forms
     * @return List of active forms only
     */
    List<FormMaster> getActiveForms();

    /**
     * Get form by ID
     * @param formId Form identifier
     * @return Optional containing the form if found
     */
    Optional<FormMaster> getFormById(Long formId);

    /**
     * Get form by name
     * @param formName Form name (e.g., "IndentCreation", "PurchaseOrder")
     * @return Optional containing the form if found
     */
    Optional<FormMaster> getFormByName(String formName);

    /**
     * Create new form
     * @param formMaster Form details
     * @return Created form
     */
    FormMaster createForm(FormMaster formMaster);

    /**
     * Update existing form
     * @param formId Form identifier
     * @param formMaster Updated form details
     * @return Updated form
     */
    FormMaster updateForm(Long formId, FormMaster formMaster);

    // ========== DESIGNATOR MASTER OPERATIONS ==========

    /**
     * Get all designators for a specific form
     * @param formId Form identifier
     * @return List of designators for the form
     */
    List<DesignatorMaster> getDesignatorsByFormId(Long formId);

    /**
     * Get all active designators for a specific form
     * @param formId Form identifier
     * @return List of active designators for the form
     */
    List<DesignatorMaster> getActiveDesignatorsByFormId(Long formId);

    /**
     * Get designator by form name and designator name
     * @param formName Form name
     * @param designatorName Designator name
     * @return Optional containing the designator if found
     */
    Optional<DesignatorMaster> getDesignatorByFormAndName(String formName, String designatorName);

    /**
     * Get designator by ID
     * @param designatorId Designator identifier
     * @return Optional containing the designator if found
     */
    Optional<DesignatorMaster> getDesignatorById(Long designatorId);

    /**
     * Create new designator
     * @param designatorMaster Designator details
     * @return Created designator
     */
    DesignatorMaster createDesignator(DesignatorMaster designatorMaster);

    /**
     * Update existing designator
     * @param designatorId Designator identifier
     * @param designatorMaster Updated designator details
     * @return Updated designator
     */
    DesignatorMaster updateDesignator(Long designatorId, DesignatorMaster designatorMaster);

    // ========== LOV MASTER OPERATIONS ==========

    /**
     * Get all LOV values for a specific designator
     * @param designatorId Designator identifier
     * @return List of LOV values
     */
    List<LOVMaster> getLOVsByDesignatorId(Long designatorId);

    /**
     * Get all active LOV values for a specific designator
     * @param designatorId Designator identifier
     * @return List of active LOV values ordered by displayOrder
     */
    List<LOVMaster> getActiveLOVsByDesignatorId(Long designatorId);

    /**
     * Get LOV values by form name and field name
     * @param formName Form name (e.g., "MaterialMaster")
     * @param fieldName Field/Designator name (e.g., "category")
     * @return List of active LOV values for the field
     */
    List<LOVMaster> getLOVsByFormAndField(String formName, String fieldName);

    /**
     * Get LOV by ID
     * @param lovId LOV identifier
     * @return Optional containing the LOV if found
     */
    Optional<LOVMaster> getLOVById(Long lovId);

    /**
     * Get dependent LOV values (for cascading dropdowns)
     * @param parentLovId Parent LOV identifier
     * @return List of child LOV values
     */
    List<LOVMaster> getDependentLOVs(Long parentLovId);

    /**
     * Create new LOV value
     * @param lovMaster LOV details
     * @return Created LOV
     */
    LOVMaster createLOV(LOVMaster lovMaster);

    /**
     * Update existing LOV value
     * @param lovId LOV identifier
     * @param lovMaster Updated LOV details
     * @return Updated LOV
     */
    LOVMaster updateLOV(Long lovId, LOVMaster lovMaster);

    /**
     * Delete LOV value (soft delete by setting isActive = false)
     * @param lovId LOV identifier
     */
    void deleteLOV(Long lovId);

    // ========== BULK OPERATIONS ==========

    /**
     * Get all dropdowns for a specific form
     * Returns a map where key is designator name and value is list of LOV values
     * @param formName Form name
     * @return Map of designator name to LOV values
     */
    Map<String, List<LOVMaster>> getAllDropdownsForForm(String formName);

    /**
     * Get multiple LOV lists in a single call
     * @param formFieldPairs List of form-field pairs (e.g., ["MaterialMaster.category", "JobMaster.uom"])
     * @return Map of form.field to LOV values
     */
    Map<String, List<LOVMaster>> getBulkLOVs(List<String> formFieldPairs);

    /**
     * Import LOV values in bulk
     * @param designatorId Designator identifier
     * @param lovValues List of LOV values to import
     * @return List of created LOVs
     */
    List<LOVMaster> bulkImportLOVs(Long designatorId, List<LOVMaster> lovValues);

    // ========== UTILITY OPERATIONS ==========

    /**
     * Check if a LOV value already exists for a designator
     * @param designatorId Designator identifier
     * @param lovValue LOV value to check
     * @return true if exists, false otherwise
     */
    boolean lovExists(Long designatorId, String lovValue);

    /**
     * Get default LOV value for a designator
     * @param designatorId Designator identifier
     * @return Optional containing the default LOV if found
     */
    Optional<LOVMaster> getDefaultLOV(Long designatorId);

    /**
     * Reorder LOV values for a designator
     * @param designatorId Designator identifier
     * @param lovIdsInOrder List of LOV IDs in desired display order
     */
    void reorderLOVs(Long designatorId, List<Long> lovIdsInOrder);

    /**
     * Get total count of all active LOV values across all forms and designators
     * @return Total count of active LOV entries
     */
    long getTotalActiveLOVCount();
}
