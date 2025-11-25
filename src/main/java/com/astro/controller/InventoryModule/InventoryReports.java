package com.astro.controller.InventoryModule;

import com.astro.dto.workflow.ProcurementDtos.IndentDto.IndentReportDetailsDTO;
import com.astro.entity.InventoryModule.IssueRegisterDTO;
import com.astro.service.InventoryModule.GiService;
import com.astro.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Inventory/reports")
public class InventoryReports {


    @Autowired
    private GiService giService;


}
