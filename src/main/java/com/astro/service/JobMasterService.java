package com.astro.service;

import com.astro.dto.workflow.JobMasterRequestDto;
import com.astro.dto.workflow.JobMasterResponseDto;


import java.util.List;

public interface JobMasterService {

    public JobMasterResponseDto createJobMaster(JobMasterRequestDto jobMasterRequestDto);
    public JobMasterResponseDto  updateJobMaster(String jobCode,JobMasterRequestDto jobMasterRequestDto);
    public List<JobMasterResponseDto > getAllJobMasters();

    public JobMasterResponseDto  getJobMasterById(String jobCode);
    public void deleteJobMaster(String jobCode);


}
