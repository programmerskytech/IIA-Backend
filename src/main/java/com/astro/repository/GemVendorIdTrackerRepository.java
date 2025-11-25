package com.astro.repository;

import com.astro.entity.GemVendorIdTracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GemVendorIdTrackerRepository extends JpaRepository<GemVendorIdTracker, Long> {
    Optional<GemVendorIdTracker> findTopByOrderByVendorIdDesc();

    Optional<GemVendorIdTracker> findByGemVendorId(String gemVendorId);
}
