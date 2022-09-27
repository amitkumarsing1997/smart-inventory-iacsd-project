package com.iacsd.demo.dto.bill;

import com.iacsd.demo.model.Bill;
import com.iacsd.demo.model.PaymentMode;
import com.iacsd.demo.model.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class ViewBillDto {
    private String uid;
    private String customerName;
    private String mobileNumber;
    private Double totalAmt;
    private Double paidAmt;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private Instant createdOn;
    private List<ProductInfoDTO> products;

    public static ViewBillDto from(Bill entity) {
        ViewBillDto dto = new ViewBillDto();
        dto.uid = entity.getUid();
        dto.customerName = entity.getCustName();
        dto.mobileNumber = entity.getMobileNo();
        dto.totalAmt = entity.getTotalAmt();
        dto.paidAmt = entity.getPaidAmt();
        dto.paymentMode = entity.getPaymentMode();
        dto.paymentStatus = entity.getPaymentStatus();
        dto.createdOn = entity.getCreatedOn();
        return dto;
    }

}

