package com.astro.repository;


import com.astro.entity.VendorMasterUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorMasterUtilRepository extends JpaRepository<VendorMasterUtil, String> {

    List<VendorMasterUtil> findByApprovalStatus(VendorMasterUtil.ApprovalStatus approvalStatus);
    @Query("SELECT MAX(i.vendorNumber) FROM VendorMasterUtil i")
    Integer findMaxVendorNumber();

    Optional<VendorMasterUtil> findByVendorId(String vendorId);


}
