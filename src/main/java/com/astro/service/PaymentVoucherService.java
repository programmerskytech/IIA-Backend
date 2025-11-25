package com.astro.service;

import com.astro.dto.workflow.PaymentVoucherReportDto;
import com.astro.dto.workflow.paymentVoucherRequestDto;
import com.astro.entity.PaymentVoucher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentVoucherService {

    public String createPaymentVoucher(paymentVoucherRequestDto dto);

    public paymentVoucherRequestDto getVoucherByProcessNo(String processNo);
    public List<PaymentVoucherReportDto> getPaymentVoucherReport(String startDate, String endDate);
}
