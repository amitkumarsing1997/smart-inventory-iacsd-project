package com.iacsd.demo.service.Impl;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.repository.CheckOutRepo;
import com.iacsd.demo.service.AccountService;
import com.iacsd.demo.service.CheckOutService;
import com.iacsd.demo.specs.CheckOutSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CheckOutServiceImpl implements CheckOutService {

    @Autowired private AccountService accountService;

    @Autowired private CheckOutRepo checkOutRepo;

    @Override
    public PaginationResponse<CheckInOutListingDto> getAllCheckOut(CheckInOutCriteriaDto criteria, Pageable pageable) {
        criteria = criteria == null ? new CheckInOutCriteriaDto() : criteria;
        criteria.setAccountId(this.accountService.getLoggedInUserAccount().getId());
        Page<CheckInOutListingDto> page = this.checkOutRepo.getAllCheckOuts(CheckOutSpec.getCheckOutByCriteria(criteria), pageable);
        return PaginationResponse.<CheckInOutListingDto>builder().body(page.getContent()).pageSize(page.getSize())
                .totalPages(page.getTotalPages()).totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements()).pageNumber(page.getNumber()).build();
    }
}
