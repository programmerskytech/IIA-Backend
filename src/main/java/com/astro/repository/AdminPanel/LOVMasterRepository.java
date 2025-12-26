package com.astro.repository.AdminPanel;

import com.astro.entity.AdminPanel.LOVMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LOVMasterRepository extends JpaRepository<LOVMaster, Long> {
    List<LOVMaster> findByDesignatorId(Long designatorId);
    List<LOVMaster> findByDesignatorIdAndIsActiveTrue(Long designatorId);
    List<LOVMaster> findByDesignatorIdOrderByDisplayOrderAsc(Long designatorId);
    List<LOVMaster> findByDesignatorIdAndIsActiveTrueOrderByDisplayOrderAsc(Long designatorId);
    Optional<LOVMaster> findByDesignatorIdAndLovValue(Long designatorId, String lovValue);
    List<LOVMaster> findByParentLovId(Long parentLovId);
    long countByIsActiveTrue();
}
