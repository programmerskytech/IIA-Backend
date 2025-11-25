package com.astro.service;

import com.astro.dto.workflow.PaymentVoucherReportResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface TallyIntegrationService {

    /**
     * Fetch payment voucher data from the report API
     */
    PaymentVoucherReportResponse fetchPaymentVoucherData(LocalDate startDate, LocalDate endDate);

    /**
     * Transform payment voucher data to Tally XML format
     */
    String generateTallyXML(PaymentVoucherReportResponse paymentVoucherData);

    /**
     * Export payment voucher data to XML file for Tally import
     */
    String exportToXMLFile(LocalDate startDate, LocalDate endDate, String filePath);

    /**
     * Sync payment voucher data with Tally (if direct integration is available)
     */
    boolean syncWithTally(LocalDate startDate, LocalDate endDate);

    /**
     * Get Tally integration status
     */
    String getTallyIntegrationStatus();

    /**
     * Validate Tally connection
     */
    boolean validateTallyConnection();
}
