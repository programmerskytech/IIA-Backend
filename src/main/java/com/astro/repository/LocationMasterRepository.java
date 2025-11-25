package com.astro.repository;

import com.astro.entity.LocationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationMasterRepository extends JpaRepository<LocationMaster,String> {
}
