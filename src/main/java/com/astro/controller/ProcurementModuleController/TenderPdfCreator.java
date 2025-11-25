package com.astro.controller.ProcurementModuleController;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.ProcurementDtos.CancelTenderRequestDto;
import com.astro.dto.workflow.ProcurementDtos.IndentDto.IndentCreationResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.IndentDto.MaterialDetailsResponseDTO;
import com.astro.dto.workflow.ProcurementDtos.PoFormateDto;
import com.astro.dto.workflow.ProcurementDtos.TenderWithIndentResponseDTO;
import com.astro.dto.workflow.TransitionActionReqDto;
import com.astro.dto.workflow.VendorDto;
import com.astro.entity.ProcurementModule.TenderRequest;
import com.astro.entity.VendorMaster;
import com.astro.entity.WorkflowTransition;
import com.astro.exception.BusinessException;
import com.astro.exception.ErrorDetails;
import com.astro.repository.ProcurementModule.IndentCreation.IndentCreationRepository;
import com.astro.repository.ProcurementModule.IndentIdRepository;
import com.astro.repository.ProcurementModule.TenderRequestRepository;
import com.astro.repository.VendorMasterRepository;
import com.astro.repository.WorkflowTransitionRepository;
import com.astro.service.IndentCreationService;
import com.astro.service.PurchaseOrderService;
import com.astro.service.TenderRequestService;
import com.astro.service.WorkflowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TenderPdfCreator {
    @Autowired
    private TenderRequestService TRService;
    @Autowired
    private VendorMasterRepository vendorMasterRepository;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private ObjectMapper mapper;



    @GetMapping("/data/tender-format")
    public String getTenderFormatPage(
            @RequestParam("tenderId") String tenderId,
            @RequestParam("vendorId") String vendorId,
            ModelMap model) {

        TenderWithIndentResponseDTO tenderData = TRService.getTenderRequestById(tenderId);

        List<MaterialDetailsResponseDTO> allMaterials = new ArrayList<>();
        for (IndentCreationResponseDTO indent : tenderData.getIndentResponseDTO()) {
            if (indent.getMaterialDetails() != null) {
                allMaterials.addAll(indent.getMaterialDetails());
            }
        }

        // Calculate tender validity in days
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate opening = LocalDate.parse(tenderData.getOpeningDate(), formatter);
            LocalDate closing = LocalDate.parse(tenderData.getClosingDate(), formatter);
            long days = Duration.between(opening.atStartOfDay(), closing.atStartOfDay()).toDays();
            tenderData.setValidityPeriod(days + " Days");
        } catch (Exception e) {
            tenderData.setValidityPeriod("____ Days");
        }

        // Vendor info
        Optional<VendorMaster> vm = vendorMasterRepository.findByVendorId(tenderData.getVendorId());
        VendorDto vendor = new VendorDto();
        if (vm.isPresent()) {
            VendorMaster ve = vm.get();
            vendor.setVendorId(ve.getVendorId());
            vendor.setVendorName(ve.getVendorName());
            vendor.setEmailAddress(ve.getEmailAddress());
            vendor.setAddress(ve.getAddress());
        }

        model.addAttribute("tender", tenderData);
        model.addAttribute("vendor", vendor);
        model.addAttribute("materials", allMaterials);

        return "Tender-Format";
    }


    @GetMapping("/data/po-format")
    public String getPoFormatPage( @RequestParam("poId") String poId, ModelMap model) throws IOException {
       PoFormateDto poData = purchaseOrderService.getPoFormatDetails(poId);
       System.out.println(poData);
        model.addAttribute("po", poData);

        return "po-formate";
    }



}
