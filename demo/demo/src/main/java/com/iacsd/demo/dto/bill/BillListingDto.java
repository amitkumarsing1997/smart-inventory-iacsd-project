package com.iacsd.demo.dto.bill;

import com.iacsd.demo.model.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class BillListingDto {
    private String uid;
    private String customerName;
    private String mobileNumber;
    private Double totalAmt;
    private PaymentStatus paymentStatus;
    private Instant createdOn;

    public BillListingDto(String uid, String customerName, String mobileNumber, Double totalAmt,
                          PaymentStatus paymentStatus, Instant createdOn) {
        this.uid = uid;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.totalAmt = totalAmt;
        this.paymentStatus = paymentStatus;
        this.createdOn = createdOn;
    }
}
