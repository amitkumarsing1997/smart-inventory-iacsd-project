package com.iacsd.demo.rest;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("checkOut")
public class CheckOutApi {

    @Autowired private CheckOutService checkOutService;

    @PostMapping("get/all/by/page")
    public PaginationResponse<CheckInOutListingDto> getAllCheckOut(@RequestBody(required = false) CheckInOutCriteriaDto criteria, Pageable pageable) {
        return this.checkOutService.getAllCheckOut(criteria, pageable);
    }
}
