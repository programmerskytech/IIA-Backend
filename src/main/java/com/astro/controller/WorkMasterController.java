package com.astro.controller;

import com.astro.dto.workflow.WorkMasterRequestDto;
import com.astro.dto.workflow.WorkMasterResponseDto;
import com.astro.service.WorkMasterService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-master")
public class WorkMasterController {

    @Autowired
    private WorkMasterService workMasterService;

    @PostMapping
    public ResponseEntity<Object> createWorkMaster(@RequestBody WorkMasterRequestDto requestDTO) {
        WorkMasterResponseDto work = workMasterService.createWorkMaster(requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(work), HttpStatus.OK);
    }

    @PutMapping("/{workCode}")
    public ResponseEntity<Object> updateWorkMaster(@PathVariable String workCode,
                                                  @RequestBody WorkMasterRequestDto requestDTO) {
        WorkMasterResponseDto response =workMasterService.updateWorkMaster(workCode, requestDTO);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllWorkMaster() {
        List<WorkMasterResponseDto> response = workMasterService.getAllWorkMasters();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(response), HttpStatus.OK);
    }

    @GetMapping("/{workCode}")
    public ResponseEntity<Object> getWorkMasterById(@PathVariable String workCode) {
        WorkMasterResponseDto responseDTO = workMasterService.getWorkMasterById(workCode);
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(responseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{workCode}")
    public ResponseEntity<String> deleteWorkMaster(@PathVariable String workCode) {
        workMasterService.deleteWorkMaster(workCode);
        return ResponseEntity.ok("job master deleted successfully. jobCode:"+" " +workCode);
    }

}
