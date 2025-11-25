package com.astro.entity;

import com.astro.entity.ProcurementModule.MaterialDetails;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vendor_names_for_job_work_material")
public class VendorNamesForJobWorkMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long materialId;
    private String indentId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "job_code")
    private String jobCode;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "work_code")
    private String workCode;


}
