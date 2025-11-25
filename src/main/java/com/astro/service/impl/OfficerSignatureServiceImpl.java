package com.astro.service.impl;

import com.astro.entity.OfficerSignature;
import com.astro.repository.OfficerSignatureRepository;
import com.astro.service.FileProcessingService;
import com.astro.service.OfficerSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Service
public class OfficerSignatureServiceImpl implements OfficerSignatureService {
    @Autowired
    private FileProcessingService fileProcessingService;
    @Autowired
    private OfficerSignatureRepository officerSignatureRepository;
    @Override
    public OfficerSignature saveSignature(String officerName,  MultipartFile file) {
        String fileName = fileProcessingService.uploadFile("Tender", file);

       OfficerSignature os = new OfficerSignature();
       os.setSignaturePath(fileName);
       os.setDesignation("Store Purchase");
       os.setOfficerName(officerName);

        return officerSignatureRepository.save(os);
    }
}
