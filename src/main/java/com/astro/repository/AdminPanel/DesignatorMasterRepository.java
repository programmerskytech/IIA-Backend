package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.DesignatorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignatorMasterRepository extends JpaRepository<DesignatorMaster, Long> {
    List<DesignatorMaster> findByFormId(Long formId);
    List<DesignatorMaster> findByFormIdAndIsActiveTrue(Long formId);
    Optional<DesignatorMaster> findByFormIdAndDesignatorName(Long formId, String designatorName);
    List<DesignatorMaster> findByFormIdOrderByDisplayOrderAsc(Long formId);
}
