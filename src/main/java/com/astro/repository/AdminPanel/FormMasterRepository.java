package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.FormMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormMasterRepository extends JpaRepository<FormMaster, Long> {
    Optional<FormMaster> findByFormName(String formName);
    List<FormMaster> findByIsActiveTrue();
    List<FormMaster> findByModuleName(String moduleName);
    List<FormMaster> findAllByOrderByDisplayOrderAsc();
}
