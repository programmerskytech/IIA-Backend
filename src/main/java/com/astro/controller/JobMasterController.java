package com.astro.controller;

import com.astro.dto.workflow.JobMasterRequestDto;
import com.astro.dto.workflow.JobMasterResponseDto;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterRequestDTO;
import com.astro.dto.workflow.ProjectMaster.ProjectMasterResponseDto;
import com.astro.service.JobMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-master")
public class JobMasterController {

    @Autowired
    private JobMasterService jobMasterService;


    @PostMapping
    public ResponseEntity<Object> createJobMaster(@RequestBody JobMasterRequestDto requestDTO) {
        JobMasterResponseDto material = jobMasterService.createJobMaster(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(material), HttpStatus.OK);
    }

    @PutMapping("/{jobCode}")
    public ResponseEntity<Object> updateJobMaster(@PathVariable String jobCode,
                                                      @RequestBody JobMasterRequestDto requestDTO) {
        JobMasterResponseDto response =jobMasterService.updateJobMaster(jobCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Object> getAllJobMaster() {
        List<JobMasterResponseDto> response = jobMasterService.getAllJobMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{jobCode}")
    public ResponseEntity<Object> getJobMasterById(@PathVariable String jobCode) {
        JobMasterResponseDto responseDTO = jobMasterService.getJobMasterById(jobCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{jobCode}")
    public ResponseEntity<String> deleteJobMaster(@PathVariable String jobCode) {
       jobMasterService.deleteJobMaster(jobCode);
        return ResponseEntity.ok("job master deleted successfully. jobCode:"+" " +jobCode);
    }




}
