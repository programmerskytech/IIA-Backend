package com.astro.repository;

import com.astro.dto.workflow.VendorIdNameDTO;
import com.astro.entity.VendorMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorMasterRepository extends JpaRepository<VendorMaster, String> {


    Optional<VendorMaster> findByVendorId(String vendorId);

    boolean existsByPanNoIgnoreCase(String panNo);
    boolean existsByEmailAddressIgnoreCaseAndVendorType(String email, String vendorType);

    @Query("SELECT v.vendorName FROM VendorMaster v WHERE v.vendorId = :vendorId")
    String findVendorNameByVendorId(@Param("vendorId") String vendorId);

    @Query("SELECT v.vendorId, v.vendorName FROM VendorMaster v WHERE v.vendorId IN :vendorIds")
    List<Object[]> findVendorIdAndNameByIds(@Param("vendorIds") List<String> vendorIds);

    @Query("SELECT new com.astro.dto.workflow.VendorIdNameDTO(v.vendorId, v.vendorName, v.primaryBusiness, v.purchaseHistory, v.statusOfVendorActiveOrDebar) FROM VendorMaster v")
    List<VendorIdNameDTO> findAllVendorIdAndName();

    @Query("SELECT v FROM VendorMaster v WHERE v.statusOfVendorActiveOrDebar IS NULL OR LOWER(v.statusOfVendorActiveOrDebar) <> 'debar'")
    List<VendorMaster> findAllActiveVendors();

    // TC_45 & TC_51: Find vendors who have submitted quotations for a tender
    @Query("SELECT DISTINCT v FROM VendorMaster v JOIN VendorQuotationAgainstTender vq ON v.vendorId = vq.vendorId WHERE vq.tenderId = :tenderId")
    List<VendorMaster> findVendorsByTenderId(@Param("tenderId") String tenderId);

}
