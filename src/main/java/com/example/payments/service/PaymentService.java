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
        Payment p=Payment.builder()

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
//                .status(payment.getStatus())
//                .amount(payment.getAmount())
//                .currency(payment.getCurrency())
//                .username(payment.getUsername())

                .build();
        return paymentRepository.save(p);
    }
    // Method to initiate a list of payments
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
//    // 1. Find pending payments
//    public List<Payment> findPendingPayments() {
//        return paymentRepository.findByStatus("PENDING");
//    }
//
//    // 2. Find total amount
//    public Double getTotalAmount() {
//        return paymentRepository.sumAllAmounts();
//    }
//
//    // 3. Find amount by invoice number
//    public Payment getAmountByInvoiceNumber(String invoiceNumber) {
//        Payment payment = paymentRepository.findByInvoiceNumber(invoiceNumber);
//        return payment;
//    }
    // Generate PDF using the fetched payment
    public Payment generateInvoicePdf(Payment payment) {
        try {
            return invoicePdfGenerator.generateInvoicePdf(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//
//    // 4. Find complete and pending payments by payment date
//    public Map<String, List<Payment>> getPaymentsByStatusAndDate(String paymentDate) {
//        Map<String, List<Payment>> paymentsByStatus = new HashMap<>();
//        paymentsByStatus.put("completed", paymentRepository.findByPaymentdateAndStatus(paymentDate, "PAID"));
//        paymentsByStatus.put("pending", paymentRepository.findByPaymentdateAndStatus(paymentDate, "PENDING"));
//        return paymentsByStatus;
//    }

    // 5. Edit payment
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

//            payment.setAmount(paymentdto.getAmount());
//            payment.setCurrency(paymentdto.getCurrency());
//            payment.setUsername(paymentdto.getUsername());
//            payment.setStatus(paymentdto.getStatus());

            return paymentRepository.save(payment);
        }
        throw new RuntimeException("Payment not found");
    }

    // 6. Delete payment
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }



    // Utility method to convert amount to words
    private String convertAmountToWords(double amount) {
        return NumberToWordsConverter.convert((long) amount); // Implement this method or use a library
    }

    public Payment getPaymentByInvoiceNumber(String invoiceNumber) {
        // Your logic to retrieve payment by invoice number from the database
        //paymentRepository.findByInvoiceNumber(invoiceNumber);
        return generateInvoicePdf(paymentRepository.findByInvoiceNumber(invoiceNumber));
    }

}
