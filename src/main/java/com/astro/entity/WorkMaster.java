package com.astro.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "work_master")
@Data
public class WorkMaster {

    @Id
    @Column(name = "work_code")
    private String workCode;
    @Column(name="work_sub_category")
    private String workSubCategory;
  //  @Column(name = "mode_of_procurement")
  //  private String modeOfProcurement;

   // @OneToMany(mappedBy = "jobCode", cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<VendorNamesForJobWorkMaterial> vendorNames;
    @Column(name = "work_description")
    private String workDescription;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate = LocalDateTime.now();
}
