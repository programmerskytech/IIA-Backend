package com.astro.repository;

import com.astro.entity.DepartmentComputerPriceLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentComputerPriceLimitRepository extends JpaRepository<DepartmentComputerPriceLimit, Long> {

    Optional<DepartmentComputerPriceLimit> findByDepartmentNameAndIsActiveTrue(String departmentName);

    List<DepartmentComputerPriceLimit> findByIsActiveTrue();

    List<DepartmentComputerPriceLimit> findAllByOrderByDepartmentNameAsc();

    @Query("SELECT d FROM DepartmentComputerPriceLimit d WHERE LOWER(d.departmentName) = LOWER(:departmentName) AND d.isActive = true")
    Optional<DepartmentComputerPriceLimit> findByDepartmentNameIgnoreCaseAndIsActiveTrue(@Param("departmentName") String departmentName);

    boolean existsByDepartmentNameIgnoreCaseAndIsActiveTrue(String departmentName);
}
