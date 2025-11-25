package com.astro.repository;

import com.astro.entity.OfficerSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerSignatureRepository extends JpaRepository<OfficerSignature, Long> {

    @Query("SELECT o.signaturePath FROM OfficerSignature o WHERE o.designation = :designation")
    String findSignaturePathByDesignation(@Param("designation") String designation);


}
