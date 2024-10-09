package com.example.payments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paymentdto {

    // invoicenumber
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "Invoice number must be alphanumeric and between 6 to 10 characters.")
    private String invoiceNumber;
    // paymentdate
    @NotBlank(message = "Payment date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Payment date should be in the format YYYY-MM-DD")
    private String paymentdate;

    // company details
    @NotBlank(message = "company details is required")
    private String companyDetails;
    // client details
    @NotBlank(message = "client details is required")
    private String clientDetails;
    // purchase number
    @Pattern(regexp = "^[A-Z0-9]+$", message = "PO number should contain only uppercase letters and digits")
    private String ponumber;

    // bank details of company and client
    // company bank account
    @NotBlank(message = "Target bank account is required")
    private String targetBankAccount;
    // client bank account
    @NotBlank(message = "Source bank account is required")
    private String sourceBankAccount;


    @NotBlank(message = "item details is required")
    private String itemName;
    @NotNull(message = "no of days is required")
    @Min(value = 1, message = "No of days should be greater than 0")
    private int noOfDays;
    @NotNull(message = "price per day is required")
    @Min(value = 1, message = "Price per day should be greater than 0")
    private Double pricePerDay;


    @Min(value = 5, message = "TDS should be greater than or equal to 5")
    private int tds;


//    @NotBlank(message = "Status is required")
//    @Pattern(regexp = "^(PAID|PENDING)$", message = "Status should be either 'PAID' or 'PENDING'")
//    private String status;

//    @NotBlank(message = "Username is required")
//    private String username;
//    @NotBlank(message = "Currency is required")
//    private String currency;
//    @Min(value = 1, message = "Amount should be greater than 0")
//    private Double amount;
}
