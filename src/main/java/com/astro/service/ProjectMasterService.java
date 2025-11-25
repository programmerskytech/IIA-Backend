package com.astro.service;

import com.astro.dto.workflow.ProjectMaster.ProjectMasterRequestDTO;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterResponseDto;

import java.util.List;

public interface ProjectMasterService {

    public ProjectMasterResponseDto createProjectMaster(ProjectMasterRequestDTO projectMasterRequestDTO);
    public ProjectMasterResponseDto updateProjectMaster(String projectCode, ProjectMasterRequestDTO projectMasterRequestDTO);
    public List<ProjectMasterResponseDto> getAllProjectMasters();

    public ProjectMasterResponseDto getProjectMasterById(String projectCode);
    public void deleteMaterialMaster(String projectCode);




}
