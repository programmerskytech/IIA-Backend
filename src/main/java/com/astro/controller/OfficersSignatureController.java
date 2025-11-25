package com.astro.controller;

import com.astro.entity.OfficerSignature;
import com.astro.service.OfficerSignatureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/Signature")
public class OfficersSignatureController {
    @Autowired
    private OfficerSignatureService officerSignatureService;
    @PostMapping("/upload")
    public ResponseEntity<OfficerSignature> uploadSignature(
            @RequestParam("officerName") String officerName,
            @RequestParam("signatureFile") MultipartFile signatureFile) {

        OfficerSignature saved = officerSignatureService.saveSignature(officerName, signatureFile);
        return ResponseEntity.ok(saved);
    }
}
