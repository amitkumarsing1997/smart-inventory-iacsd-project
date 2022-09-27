package com.iacsd.demo.repository.custom;

import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.model.CheckIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CheckInCustomRepo {
    Page<CheckInOutListingDto> getAllCheckIns(Specification<CheckIn> specs, Pageable pageable);
}
