package com.astro.repository.ProcurementModule.WorkOrder;

import com.astro.entity.ProcurementModule.PurchaseOrderAttributes;
import com.astro.entity.ProcurementModule.WorkOrderMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkOrderMaterialRepository extends JpaRepository<WorkOrderMaterial,Long> {

}
