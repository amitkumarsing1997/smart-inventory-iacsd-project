package com.iacsd.demo.service;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CheckOutService {
   PaginationResponse<CheckInOutListingDto> getAllCheckOut(@RequestBody(required = false)CheckInOutCriteriaDto dto, Pageable pageable);
}
