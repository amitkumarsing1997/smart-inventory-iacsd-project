package com.iacsd.demo.dto.bill;

import com.iacsd.demo.dto.CheckOutDto;
import com.iacsd.demo.model.Bill;
import com.iacsd.demo.model.PaymentMode;
import com.iacsd.demo.model.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BillDTO {
    private String customerName;
    private String mobileNumber;
    private Double totalAmt;
    private Double paidAmt;
    private PaymentMode paymentMode;
    private PaymentStatus paymentStatus;
    private List<CheckOutDto> products;

    public static void fillEntity(BillDTO dto, Bill entity) {
        entity.setCustName(dto.customerName);
        entity.setMobileNo(dto.mobileNumber);
        entity.setTotalAmt(dto.totalAmt);
        entity.setPaidAmt(dto.paidAmt);
        entity.setPaymentMode(dto.paymentMode);
        entity.setPaymentStatus(dto.paymentStatus);
    }

    public static BillDTO from(Bill entity) {
        BillDTO dto = new BillDTO();
        dto.customerName = entity.getCustName();
        dto.mobileNumber = entity.getMobileNo();
        dto.totalAmt = entity.getTotalAmt();
        dto.paidAmt = entity.getPaidAmt();
        dto.paymentMode = entity.getPaymentMode();
        dto.paymentStatus = entity.getPaymentStatus();
        return dto;
    }
}
