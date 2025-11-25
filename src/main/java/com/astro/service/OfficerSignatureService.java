package com.astro.service;

import com.astro.entity.OfficerSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OfficerSignatureService {

    public OfficerSignature saveSignature(String officerName, MultipartFile file);


}
