package com.astro.controller;


import com.astro.dto.workflow.ProjectMaster.ProjectMasterRequestDTO;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterResponseDto;
import com.astro.service.ProjectMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-master")
public class ProjectMasterController {

    @Autowired
    private ProjectMasterService projectMasterService;

    @PostMapping
    public ResponseEntity<Object> createProjectMaster(@RequestBody ProjectMasterRequestDTO requestDTO) {
        ProjectMasterResponseDto material = projectMasterService.createProjectMaster(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(material), HttpStatus.OK);
    }

    @PutMapping("/{projectCode}")
    public ResponseEntity<Object> updateProjectMaster(@PathVariable String projectCode,
                                                       @RequestBody ProjectMasterRequestDTO requestDTO) {
        ProjectMasterResponseDto response =projectMasterService.updateProjectMaster(projectCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllProjectMaster() {
        List<ProjectMasterResponseDto> response = projectMasterService.getAllProjectMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<Object> getProjectMasterById(@PathVariable String projectCode) {
        ProjectMasterResponseDto responseDTO = projectMasterService.getProjectMasterById(projectCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<String> deleteProjectMaster(@PathVariable String projectCode) {
        projectMasterService.deleteMaterialMaster(projectCode);
        return ResponseEntity.ok("project master deleted successfully. projectCode:"+" " +projectCode);
    }


}
