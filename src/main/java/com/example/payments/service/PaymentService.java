package com.example.payments.service;

import com.example.payments.dto.Paymentdto;
import com.example.payments.model.Payment;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.util.InvoicePdfGenerator;
import com.example.payments.util.NumberToWordsConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoicePdfGenerator invoicePdfGenerator;

    public Payment initiatePayment(Paymentdto payment) {
        Payment p = Payment.builder()
                .invoiceNumber(payment.getInvoiceNumber())
                .paymentdate(payment.getPaymentdate())
                .companyDetails(payment.getCompanyDetails())
                .clientDetails(payment.getClientDetails())
                .ponumber(payment.getPonumber())
                .targetBankAccount(payment.getTargetBankAccount())
                .sourceBankAccount(payment.getSourceBankAccount())
                .itemName(payment.getItemName())
                .noOfDays(payment.getNoOfDays())
                .pricePerDay(payment.getPricePerDay())
                .tds(payment.getTds())
                .build();
        return paymentRepository.save(p);
    }

    public List<Payment> initiatePayments(List<Paymentdto> payments) {
        List<Payment> paymentList = payments.stream().map(payment -> Payment.builder()
                .invoiceNumber(payment.getInvoiceNumber())
                .paymentdate(payment.getPaymentdate())
                .companyDetails(payment.getCompanyDetails())
                .clientDetails(payment.getClientDetails())
                .ponumber(payment.getPonumber())
                .targetBankAccount(payment.getTargetBankAccount())
                .sourceBankAccount(payment.getSourceBankAccount())
                .itemName(payment.getItemName())
                .noOfDays(payment.getNoOfDays())
                .pricePerDay(payment.getPricePerDay())
                .tds(payment.getTds())
                .build()).collect(Collectors.toList());

        return paymentRepository.saveAll(paymentList);
    }

    public Payment editPayment(String id, Paymentdto paymentdto) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setInvoiceNumber(paymentdto.getInvoiceNumber());
            payment.setPaymentdate(paymentdto.getPaymentdate());
            payment.setCompanyDetails(paymentdto.getCompanyDetails());
            payment.setClientDetails(paymentdto.getClientDetails());
            payment.setPonumber(paymentdto.getPonumber());
            payment.setTargetBankAccount(paymentdto.getTargetBankAccount());
            payment.setSourceBankAccount(paymentdto.getSourceBankAccount());
            payment.setItemName(paymentdto.getItemName());
            payment.setNoOfDays(paymentdto.getNoOfDays());
            payment.setPricePerDay(paymentdto.getPricePerDay());
            payment.setTds(paymentdto.getTds());
            return paymentRepository.save(payment);
        }
        throw new RuntimeException("Payment not found");
    }

    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }

    // Generate PDF using the fetched payment
    public Payment generateInvoicePdf(String invoiceNumber) {
        try {
            return invoicePdfGenerator.getInvoicePDF(invoiceNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
