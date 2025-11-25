package com.astro.repository.ProcurementModule.WorkOrder;

import com.astro.entity.ProcurementModule.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {


}
