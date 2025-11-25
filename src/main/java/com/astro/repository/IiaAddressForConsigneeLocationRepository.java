package com.astro.repository;

import com.astro.entity.IiaAddressForConsigneeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IiaAddressForConsigneeLocationRepository  extends JpaRepository<IiaAddressForConsigneeLocation, Long> {
    @Query("SELECT i.iiaAddress FROM IiaAddressForConsigneeLocation i WHERE i.consignee = :consignee")
    String findIiaAddressByConsignee(@Param("consignee") String consignee);
}
