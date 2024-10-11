package com.example.payments.model;

import com.example.payments.util.NumberToWordsConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
@Builder
public class Payment {

    @Id
    private String id;

    // header
    private String invoiceNumber;
    private String paymentdate;

    // company details
    private String companyDetails;
    // client details
    private String clientDetails;
    // purchase number
    private String ponumber;

    // bank details
    private String targetBankAccount;// company
    private String sourceBankAccount;// client

    private String itemName;
    private int noOfDays;
    private Double pricePerDay;

    private int tds;
    // signature

    // private Double amount;
    // private String username;
    // private String status;
    // private String currency;

    public double getTotalPrice() {
        return pricePerDay * noOfDays;
    }

    public double getTotalAmountAfterTDS() {
        double totalPrice = getTotalPrice();
        return totalPrice - (totalPrice * tds / 100);
    }

    public String getTotalAmountAfterTDSInWords() {
        double totalPrice = getTotalPrice();
        totalPrice = totalPrice - (totalPrice * tds / 100);
        return NumberToWordsConverter.convert(totalPrice);
    }
    
}
