package com.astro.repository;

import com.astro.entity.VendorIdTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VendorIdTrackerRepository extends JpaRepository<VendorIdTracker, Long> {
    Optional<VendorIdTracker> findByPrimaryBusiness(String primaryBusiness);
}