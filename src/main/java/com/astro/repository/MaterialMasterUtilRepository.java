package com.astro.repository;

import com.astro.entity.MaterialMasterUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialMasterUtilRepository extends JpaRepository<MaterialMasterUtil, String> {


    List<MaterialMasterUtil> findByApprovalStatus(MaterialMasterUtil.ApprovalStatus approvalStatus);
    @Query("SELECT MAX(i.materialNumber) FROM MaterialMasterUtil i")
    Integer findMaxMaterialNumber();

    Optional<MaterialMasterUtil> findByMaterialCode(String materialCode);
}
