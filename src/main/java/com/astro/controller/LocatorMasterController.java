package com.astro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astro.dto.workflow.LocatorMasterResDto;
import com.astro.service.LocatorService;
import com.astro.util.ResponseBuilder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/locator-master")
public class LocatorMasterController {

    @Autowired
    private LocatorService ls;

    @GetMapping()
    public ResponseEntity<Object> getMethodName() {
        List<LocatorMasterResDto> res = ls.getLocatorMaster();
        return new ResponseEntity<Object>(ResponseBuilder.getSuccessResponse(res), HttpStatus.OK);
    }
}
