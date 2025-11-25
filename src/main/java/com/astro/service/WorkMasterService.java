package com.astro.service;

import com.astro.dto.workflow.JobMasterRequestDto;
import com.astro.dto.workflow.JobMasterResponseDto;
import com.astro.dto.workflow.WorkMasterRequestDto;
import com.astro.dto.workflow.WorkMasterResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkMasterService {

    public WorkMasterResponseDto createWorkMaster(WorkMasterRequestDto workMasterRequestDto);
    public WorkMasterResponseDto  updateWorkMaster(String workCode,WorkMasterRequestDto workMasterRequestDto);
    public List<WorkMasterResponseDto> getAllWorkMasters();

    public WorkMasterResponseDto getWorkMasterById(String workCode);
    public void deleteWorkMaster(String workCode);
}
