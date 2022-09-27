package com.iacsd.demo.repository;

import com.iacsd.demo.dto.bill.ProductInfoDTO;
import com.iacsd.demo.model.CheckOut;
import com.iacsd.demo.repository.custom.CheckOutCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckOutRepo extends JpaRepository<CheckOut, Integer>, CheckOutCustomRepo {

    @Query("SELECT new com.iacsd.demo.dto.bill.ProductInfoDTO(p.name, p.code, co.quantity, co.unit, co.rate, co.rateUnit)" +
            "FROM CheckOut co INNER JOIN co.bill b INNER JOIN co.checkIn ci INNER JOIN ci.product p " +
            "WHERE b.id = :billId")
	List<ProductInfoDTO> getCheckoutProducts(Long billId);
}
