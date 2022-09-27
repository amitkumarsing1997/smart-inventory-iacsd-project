package com.iacsd.demo.repository;

import com.iacsd.demo.dto.bill.BillListingDto;
import com.iacsd.demo.model.Bill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepo extends CrudRepository<Bill, Long> {
    @Query("SELECT new com.iacsd.demo.dto.bill.BillListingDto(b.uid, b.custName, b.mobileNo, b.totalAmt, b.paymentStatus, b.createdOn)  " +
            "FROM Bill b INNER JOIN b.account acc WHERE b.mobileNo = :mobileNumber AND acc.id = :accountId ORDER BY b.createdOn")
    List<BillListingDto> getBillsByMobileNum(String mobileNumber, Long accountId);

    Optional<Bill> findByUid(String uid);
}
