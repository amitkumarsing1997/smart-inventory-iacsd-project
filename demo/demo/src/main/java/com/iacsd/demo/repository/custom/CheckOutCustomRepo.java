package com.iacsd.demo.repository.custom;

import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.model.CheckOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CheckOutCustomRepo {
    Page<CheckInOutListingDto> getAllCheckOuts(Specification<CheckOut> specs, Pageable pageable);
}
