package com.iacsd.demo.service;

import com.iacsd.demo.dto.CheckInDTO;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCheckInDto;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import org.springframework.data.domain.Pageable;


public interface CheckinService {
	String checkinProductService(CheckInDTO request);
	ProductCheckInDto getAllCheckedInProductService(String productId);

    PaginationResponse<CheckInOutListingDto> getAllCheckInByPage(CheckInOutCriteriaDto criteria, Pageable pageable);
}
