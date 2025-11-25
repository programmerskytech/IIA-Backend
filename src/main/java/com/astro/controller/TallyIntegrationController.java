package com.astro.controller;

import com.astro.constant.AppConstant;
import com.astro.dto.workflow.PaymentVoucherReportResponse;
import com.astro.exception.ErrorDetails;
import com.astro.service.TallyIntegrationService;
import com.astro.util.APIResponse;
import com.astro.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/tally-integration")
@CrossOrigin(origins = "*")
public class TallyIntegrationController {

    private static final Logger logger = LoggerFactory.getLogger(TallyIntegrationController.class);

    @Autowired
    private TallyIntegrationService tallyIntegrationService;

    /**
     * Fetch payment voucher data from report API
     */
    @GetMapping("/payment-vouchers")
    public ResponseEntity<?> getPaymentVoucherData(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {

        try {
            logger.info("Fetching payment voucher data from {} to {}", startDate, endDate);

            PaymentVoucherReportResponse data = tallyIntegrationService.fetchPaymentVoucherData(startDate, endDate);

            if (data != null) {
                APIResponse response = ResponseBuilder.getSuccessResponse(data);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Failed to fetch payment voucher data"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error fetching payment voucher data: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error fetching payment voucher data: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Generate Tally XML for payment vouchers
     */
    @PostMapping("/generate-xml")
    public ResponseEntity<?> generateTallyXML(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {

        try {
            logger.info("Generating Tally XML for payment vouchers from {} to {}", startDate, endDate);

            PaymentVoucherReportResponse data = tallyIntegrationService.fetchPaymentVoucherData(startDate, endDate);

            if (data != null) {
                String xml = tallyIntegrationService.generateTallyXML(data);
                if (xml != null) {
                    APIResponse response = ResponseBuilder.getSuccessResponse(xml);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Failed to generate Tally XML"));
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Failed to fetch payment voucher data"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error generating Tally XML: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error generating Tally XML: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Export payment vouchers to XML file
     */
    @PostMapping("/export-xml")
    public ResponseEntity<?> exportToXMLFile(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(required = false) String filePath) {

        try {
            logger.info("Exporting payment vouchers to XML file from {} to {}", startDate, endDate);

            if (filePath == null || filePath.trim().isEmpty()) {
                filePath = "payment_vouchers_" + startDate + "_to_" + endDate + ".xml";
            }

            String result = tallyIntegrationService.exportToXMLFile(startDate, endDate, filePath);

            if (result.startsWith("Successfully")) {
                APIResponse response = ResponseBuilder.getSuccessResponse(result);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails(result));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error exporting XML file: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error exporting XML file: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Sync payment vouchers with Tally
     */
    @PostMapping("/sync")
    public ResponseEntity<?> syncWithTally(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {

        try {
            logger.info("Syncing payment vouchers with Tally from {} to {}", startDate, endDate);

            boolean success = tallyIntegrationService.syncWithTally(startDate, endDate);

            if (success) {
                APIResponse response = ResponseBuilder.getSuccessResponse("Sync completed successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Failed to sync with Tally"));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error syncing with Tally: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error syncing with Tally: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Tally integration status
     */
    @GetMapping("/status")
    public ResponseEntity<?> getTallyIntegrationStatus() {
        try {
            String status = tallyIntegrationService.getTallyIntegrationStatus();
            APIResponse response = ResponseBuilder.getSuccessResponse(status);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error getting Tally integration status: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error getting status: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Validate Tally connection
     */
    @GetMapping("/validate-connection")
    public ResponseEntity<?> validateTallyConnection() {
        try {
            boolean isValid = tallyIntegrationService.validateTallyConnection();

            if (isValid) {
                APIResponse response = ResponseBuilder.getSuccessResponse("Connection valid");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                APIResponse response = ResponseBuilder.getErrorResponse(createValidationErrorDetails("Tally connection validation failed"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error validating Tally connection: {}", e.getMessage(), e);
            APIResponse response = ResponseBuilder.getErrorResponse(createErrorDetails("Error validating connection: " + e.getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ErrorDetails createErrorDetails(String message) {
        return new ErrorDetails(AppConstant.INTER_SERVER_ERROR,
                AppConstant.ERROR_TYPE_CODE_INTERNAL, AppConstant.ERROR_TYPE_ERROR, message);
    }

    private ErrorDetails createValidationErrorDetails(String message) {
        return new ErrorDetails(AppConstant.USER_INVALID_INPUT,
                AppConstant.ERROR_TYPE_CODE_VALIDATION, AppConstant.ERROR_TYPE_VALIDATION, message);
    }

}
