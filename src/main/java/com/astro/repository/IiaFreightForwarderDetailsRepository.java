package com.astro.repository;

import com.astro.entity.IiaFreightForwarderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IiaFreightForwarderDetailsRepository extends JpaRepository<IiaFreightForwarderDetails,Long> {

    @Query("SELECT f.freightForwarderDetails FROM IiaFreightForwarderDetails f WHERE f.countryName = :countryName")
    String findFreightForwarderDetailsByCountryName(@Param("countryName") String countryName);
}
