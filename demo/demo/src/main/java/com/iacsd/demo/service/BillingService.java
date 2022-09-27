package com.iacsd.demo.service;

import com.iacsd.demo.dto.bill.BillDTO;
import com.iacsd.demo.dto.bill.BillListingDto;
import com.iacsd.demo.dto.bill.ViewBillDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BillingService {
	String getBill(BillDTO request);

    List<BillListingDto> getBills(String mobileNumber);

    ViewBillDto getBillDtls(String uid);

    ResponseEntity<Resource> downloadBill(String uid);


}
