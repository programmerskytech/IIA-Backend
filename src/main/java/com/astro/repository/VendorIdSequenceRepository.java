package com.astro.repository;

import com.astro.entity.VendorIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorIdSequenceRepository extends JpaRepository<VendorIdSequence, Long> {

    @Query("SELECT i.vendorId FROM VendorIdSequence i ORDER BY i.id DESC")
    String findLastVendorId();
}