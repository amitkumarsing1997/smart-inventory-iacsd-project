package com.iacsd.demo.rest;

import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.dto.bill.BillDTO;
import com.iacsd.demo.dto.bill.BillListingDto;
import com.iacsd.demo.dto.bill.ViewBillDto;
import com.iacsd.demo.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillingController {
	@Autowired
	private BillingService billingService;

	@PostMapping("generate")
	public ApiResponse<String> generateBill(@RequestBody BillDTO dto) {
		return new ApiResponse<>(billingService.getBill(dto));
	}

	@GetMapping("get/bills/{mobileNum}")
	public ApiResponse<List<BillListingDto>> getBills(@PathVariable String mobileNum) {
		return ApiResponse.<List<BillListingDto>>builder().body(this.billingService.getBills(mobileNum)).build();
	}
//	@GetMapping("gets/bills/{uid}")
//	public String getBill(@PathVariable String uid) {
//		return billingService.getDownloadBills(uid);
//	}

	@GetMapping("get/bill/dtls/{uid}")
	public ApiResponse<ViewBillDto> getBillDtls(@PathVariable String uid) {
		return ApiResponse.<ViewBillDto>builder().body(this.billingService.getBillDtls(uid)).build();
	}

		@GetMapping("download/{uid}")
		public ResponseEntity<Resource> downloadBill(@PathVariable String uid) {
			return this.billingService.downloadBill(uid);
		}
}

