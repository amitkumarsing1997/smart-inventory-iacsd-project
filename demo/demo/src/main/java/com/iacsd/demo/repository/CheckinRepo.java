package com.iacsd.demo.repository;

import com.iacsd.demo.model.CheckIn;
//import com.iacsd.demo.repository.custom.CheckInCustomRepo;
import com.iacsd.demo.repository.custom.CheckInCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface CheckinRepo extends JpaRepository<CheckIn, Integer> , CheckInCustomRepo {
	List<CheckIn> findByProductIdAndRemainingQuantityGreaterThanOrderByExpireOn(Long productId, Long remainingQty);
	CheckIn findByUid(String uid);
	List<CheckIn> findByUidIn(Collection<String> uids);
	
//	List<CheckIn> findByExpireOnGreaterThanEqualAndLessThanEqual(Instant startDate,Instant endDate);
	List<CheckIn> findByExpireOnAfter(Instant today);
}
