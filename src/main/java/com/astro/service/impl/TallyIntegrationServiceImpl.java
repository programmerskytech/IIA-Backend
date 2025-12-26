package com.astro.service.impl;

import com.astro.dto.workflow.PaymentVoucherReportDto;
import com.astro.dto.workflow.PaymentVoucherReportResponse;
import com.astro.service.TallyIntegrationService;
import com.astro.util.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TallyIntegrationServiceImpl implements TallyIntegrationService {

    private static final Logger logger = LoggerFactory.getLogger(TallyIntegrationServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Value("${server.port:8081}")
    private String serverPort;

    @Value("${server.servlet.context-path:/astro-service}")
    private String contextPath;

    private final RestTemplate restTemplate;

    public TallyIntegrationServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public PaymentVoucherReportResponse fetchPaymentVoucherData(LocalDate startDate, LocalDate endDate) {
        try {
            String url = String.format("http://localhost:%s%s/api/reports/PaymentVoucherReport?startDate=%s&endDate=%s",
                    serverPort, contextPath,
                    startDate.format(DATE_FORMATTER),
                    endDate.format(DATE_FORMATTER));

            logger.info("Fetching payment voucher data from: {}", url);

            // Set headers to request JSON content
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Use APIResponse to handle the ResponseBuilder wrapped response
            ResponseEntity<APIResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, APIResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                APIResponse apiResponse = response.getBody();

                if (apiResponse == null) {
                    logger.error("API Response body is null");
                    return null;
                }

                // Convert APIResponse to PaymentVoucherReportResponse
                PaymentVoucherReportResponse result = new PaymentVoucherReportResponse();

                // Map response status
                PaymentVoucherReportResponse.ResponseStatus responseStatus = new PaymentVoucherReportResponse.ResponseStatus();
                if (apiResponse.getResponseStatus() != null) {
                    responseStatus.setStatusCode(apiResponse.getResponseStatus().getStatusCode());
                    responseStatus.setMessage(apiResponse.getResponseStatus().getMessage());
                    responseStatus.setErrorCode(apiResponse.getResponseStatus().getErrorCode() != null ?
                            String.valueOf(apiResponse.getResponseStatus().getErrorCode()) : null);
                    responseStatus.setErrorType(apiResponse.getResponseStatus().getErrorType());
                }
                result.setResponseStatus(responseStatus);

                // Convert responseData (List<PaymentVoucherReportDto>) to List<PaymentVoucherData>
                if (apiResponse.getResponseData() != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    List<PaymentVoucherReportDto> dtoList = mapper.convertValue(
                            apiResponse.getResponseData(),
                            mapper.getTypeFactory().constructCollectionType(List.class, PaymentVoucherReportDto.class)
                    );

                    List<PaymentVoucherReportResponse.PaymentVoucherData> dataList = new ArrayList<>();
                    for (PaymentVoucherReportDto dto : dtoList) {
                        PaymentVoucherReportResponse.PaymentVoucherData data = new PaymentVoucherReportResponse.PaymentVoucherData();
                        data.setPaymentVoucherNumber(dto.getPaymentVoucherNumber());
                        data.setPaymentVoucherDate(dto.getPaymentVoucherDate());
                        data.setPaymentVoucherIsFor(dto.getPaymentVoucherIsFor());
                        data.setPurchaseOrderId(dto.getPurchaseOrderId());
                        data.setGrnNumber(dto.getGrnNumber());
                        data.setPaymentVoucherType(dto.getPaymentVoucherType());
                        data.setVendorName(dto.getVendorName());
                        data.setVendorInvoiceNumber(dto.getVendorInvoiceNumber());
                        data.setVendorInvoiceDate(dto.getVendorInvoiceDate());
                        data.setCurrency(dto.getCurrency());
                        data.setExchangeRate(dto.getExchangeRate());
                        data.setRemarks(dto.getRemarks());
                        data.setTotalAmount(dto.getTotalAmount() != null ? dto.getTotalAmount().doubleValue() : null);
                        data.setPartialAmount(dto.getPartialAmount() != null ? dto.getPartialAmount().doubleValue() : null);
                        data.setAdvanceAmount(dto.getAdvanceAmount() != null ? dto.getAdvanceAmount().doubleValue() : null);
                        data.setPaidAmount(dto.getPaidAmount() != null ? dto.getPaidAmount().doubleValue() : null);
                        data.setSoId(dto.getSoId());
                        data.setCreatedBy(dto.getCreatedBy());
                        data.setCreatedDate(dto.getCreatedDate() != null ? dto.getCreatedDate().toString() : null);
                        // Note: materials mapping would need PaymentVoucherMaterialDto to be checked
                        data.setMaterials(null); // Set to null for now as material DTO structure may differ
                        dataList.add(data);
                    }
                    result.setResponseData(dataList);
                }

                logger.info("Successfully fetched {} payment vouchers",
                        result.getResponseData() != null ? result.getResponseData().size() : 0);
                return result;
            } else {
                logger.error("Failed to fetch payment voucher data. Status: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error fetching payment voucher data: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String generateTallyXML(PaymentVoucherReportResponse paymentVoucherData) {
        if (paymentVoucherData == null || paymentVoucherData.getResponseData() == null) {
            return null;
        }

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<ENVELOPE>\n");
        xml.append("  <HEADER>\n");
        xml.append("    <TALLYREQUEST>Import Data</TALLYREQUEST>\n");
        xml.append("  </HEADER>\n");
        xml.append("  <BODY>\n");
        xml.append("    <IMPORTDATA>\n");
        xml.append("      <REQUESTDESC>\n");
        xml.append("        <REPORTNAME>Vouchers</REPORTNAME>\n");
        xml.append("      </REQUESTDESC>\n");
        xml.append("      <REQUESTDATA>\n");

        for (PaymentVoucherReportResponse.PaymentVoucherData voucher : paymentVoucherData.getResponseData()) {
            xml.append(generateVoucherXML(voucher));
        }

        xml.append("      </REQUESTDATA>\n");
        xml.append("    </IMPORTDATA>\n");
        xml.append("  </BODY>\n");
        xml.append("</ENVELOPE>");

        return xml.toString();
    }

    private String generateVoucherXML(PaymentVoucherReportResponse.PaymentVoucherData voucher) {
        StringBuilder xml = new StringBuilder();

        xml.append("        <TALLYMESSAGE xmlns:UDF=\"TallyUDF\">\n");
        xml.append("          <VOUCHER VCHTYPE=\"Payment\" ACTION=\"Create\">\n");
        xml.append("            <VOUCHERTYPENAME>Payment</VOUCHERTYPENAME>\n");
        xml.append("            <VOUCHERNUMBER>").append(voucher.getPaymentVoucherNumber()).append("</VOUCHERNUMBER>\n");
        xml.append("            <DATE>").append(convertDateToTallyFormat(voucher.getPaymentVoucherDate())).append("</DATE>\n");
        xml.append("            <NARRATION>").append(generateNarration(voucher)).append("</NARRATION>\n");

        // Add ledger entries
        xml.append(generateLedgerEntries(voucher));

        xml.append("          </VOUCHER>\n");
        xml.append("        </TALLYMESSAGE>\n");

        return xml.toString();
    }

    private String generateLedgerEntries(PaymentVoucherReportResponse.PaymentVoucherData voucher) {
        StringBuilder xml = new StringBuilder();

        // Credit entry for vendor
        xml.append("            <ALLLEDGERENTRIES.LIST>\n");
        xml.append("              <LEDGERNAME>").append(voucher.getVendorName()).append("</LEDGERNAME>\n");
        xml.append("              <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>\n");
        xml.append("              <AMOUNT>-").append(getPaymentAmount(voucher)).append("</AMOUNT>\n");
        xml.append("            </ALLLEDGERENTRIES.LIST>\n");

        // Debit entry for bank/cash
        xml.append("            <ALLLEDGERENTRIES.LIST>\n");
        xml.append("              <LEDGERNAME>Bank Account</LEDGERNAME>\n");
        xml.append("              <ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>\n");
        xml.append("              <AMOUNT>").append(getPaymentAmount(voucher)).append("</AMOUNT>\n");
        xml.append("            </ALLLEDGERENTRIES.LIST>\n");

        return xml.toString();
    }

    private Double getPaymentAmount(PaymentVoucherReportResponse.PaymentVoucherData voucher) {
        if (voucher.getPaidAmount() != null && voucher.getPaidAmount() > 0) {
            return voucher.getPaidAmount();
        } else if (voucher.getPartialAmount() != null && voucher.getPartialAmount() > 0) {
            return voucher.getPartialAmount();
        } else if (voucher.getAdvanceAmount() != null && voucher.getAdvanceAmount() > 0) {
            return voucher.getAdvanceAmount();
        } else {
            return voucher.getTotalAmount();
        }
    }

    private String generateNarration(PaymentVoucherReportResponse.PaymentVoucherData voucher) {
        StringBuilder narration = new StringBuilder();
        narration.append("Payment for ");
        narration.append(voucher.getPaymentVoucherIsFor());
        if (voucher.getPurchaseOrderId() != null) {
            narration.append(" - PO: ").append(voucher.getPurchaseOrderId());
        }
        if (voucher.getSoId() != null) {
            narration.append(" - SO: ").append(voucher.getSoId());
        }
        if (voucher.getVendorInvoiceNumber() != null) {
            narration.append(" - Invoice: ").append(voucher.getVendorInvoiceNumber());
        }
        return narration.toString();
    }

    private String convertDateToTallyFormat(String dateStr) {
        if (dateStr == null) return "";
        try {
            // Convert from DD/MM/YYYY to YYYYMMDD format for Tally
            String[] parts = dateStr.split("/");
            if (parts.length == 3) {
                return parts[2] + parts[1] + parts[0];
            }
        } catch (Exception e) {
            logger.warn("Error converting date format: {}", dateStr);
        }
        return dateStr;
    }

    @Override
    public String exportToXMLFile(LocalDate startDate, LocalDate endDate, String filePath) {
        try {
            PaymentVoucherReportResponse data = fetchPaymentVoucherData(startDate, endDate);
            if (data == null) {
                return "Failed to fetch payment voucher data";
            }

            String xml = generateTallyXML(data);
            if (xml == null) {
                return "Failed to generate XML";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(xml);
                logger.info("Successfully exported payment voucher data to: {}", filePath);
                return "Successfully exported to " + filePath;
            }
        } catch (IOException e) {
            logger.error("Error writing XML file: {}", e.getMessage(), e);
            return "Error writing file: " + e.getMessage();
        }
    }

    @Override
    public boolean syncWithTally(LocalDate startDate, LocalDate endDate) {
        // This would implement direct Tally integration if available
        // For now, we'll export to XML file
        String fileName = "payment_vouchers_" +
                startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" +
                endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xml";
        String result = exportToXMLFile(startDate, endDate, fileName);

        return result.startsWith("Successfully");
    }

    @Override
    public String getTallyIntegrationStatus() {
        return "Tally Integration Service is running. XML export functionality available.";
    }

    @Override
    public boolean validateTallyConnection() {
        // For XML-based integration, we just check if we can fetch data
        try {
            PaymentVoucherReportResponse data = fetchPaymentVoucherData(
                    LocalDate.now().minusDays(1), LocalDate.now());
            return data != null && data.getResponseStatus() != null &&
                    data.getResponseStatus().getStatusCode() == 0;
        } catch (Exception e) {
            logger.error("Tally connection validation failed: {}", e.getMessage());
            return false;
        }
    }
}
