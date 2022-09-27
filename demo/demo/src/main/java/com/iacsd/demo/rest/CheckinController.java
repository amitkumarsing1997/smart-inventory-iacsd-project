package com.iacsd.demo.rest;

import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.dto.CheckInDTO;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCheckInDto;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/checkin")
public class CheckinController {
	
	@Autowired
	private CheckinService checkinService;
	
	@PostMapping()
	public ApiResponse<String> checkinProducts(@RequestBody CheckInDTO dto) {
		return new ApiResponse<>(checkinService.checkinProductService(dto));
	}
	
	@GetMapping("get/all/by/product/{productCode}")
	public ApiResponse<ProductCheckInDto> getAllCheckedInProduct(@PathVariable String productCode){
		return new ApiResponse<>(checkinService.getAllCheckedInProductService(productCode));
	}

	@PostMapping("get/all/by/page")
	public PaginationResponse<CheckInOutListingDto> getAllCheckIn(@RequestBody(required = false) CheckInOutCriteriaDto criteria, Pageable pageable) {
		return this.checkinService.getAllCheckInByPage(criteria, pageable);
	}
}
