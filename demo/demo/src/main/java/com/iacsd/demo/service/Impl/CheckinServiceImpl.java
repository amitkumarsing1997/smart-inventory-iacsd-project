package com.iacsd.demo.service.Impl;

import com.iacsd.demo.dto.CheckInDTO;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCheckInDto;
import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.repository.CheckinRepo;
import com.iacsd.demo.repository.ProductRepository;
import com.iacsd.demo.security.JwtUtil;
import com.iacsd.demo.service.AccountService;
import com.iacsd.demo.service.CheckinService;
import com.iacsd.demo.specs.CheckInSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinServiceImpl implements CheckinService {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CheckinRepo checkinRepo;

	@Autowired
	private AccountService accountService;

	@Override
	public String checkinProductService(CheckInDTO request) {
		CheckIn checkIn = new CheckIn();
		Account acc = this.accountService.getLoggedInUserAccount();
		Product product = productRepository.findByUid(request.getProductUid());
		long availableQuantity = product.getAvailableQuantity() != null ? product.getAvailableQuantity() : 0;
		product.setAvailableQuantity(availableQuantity + request.getQuantity());

		CheckInDTO.fillEntity(request, checkIn);
		checkIn.setRemainingQuantity(request.getQuantity());
		checkIn.setAccount(acc);
		checkIn.setProduct(product);
		checkIn.setSentCount(0);

		CheckIn ch = checkinRepo.save(checkIn);
		return ch.getUid();

	}

	public ProductCheckInDto getAllCheckedInProductService(String productCode) {
		Account account = this.accountService.getLoggedInUserAccount();
		Product product = this.productRepository.findByCodeAndAccountId(productCode, account.getId());
		List<CheckIn> checkInEntries = checkinRepo.findByProductIdAndRemainingQuantityGreaterThanOrderByExpireOn(product.getId(), 0L);
		return ProductCheckInDto.from(product, checkInEntries);
	}

	@Override
	public PaginationResponse<CheckInOutListingDto> getAllCheckInByPage(CheckInOutCriteriaDto criteria, Pageable pageable) {
		criteria = criteria == null ? new CheckInOutCriteriaDto() : criteria;
		criteria.setAccountId(this.accountService.getLoggedInUserAccount().getId());
		Page<CheckInOutListingDto> page = this.checkinRepo.getAllCheckIns(CheckInSpecs.getCheckInByCriteria(criteria), pageable);
		return PaginationResponse.<CheckInOutListingDto>builder().body(page.getContent()).pageSize(page.getSize())
				.totalPages(page.getTotalPages()).totalElements(page.getTotalElements())
				.numberOfElements(page.getNumberOfElements()).pageNumber(page.getNumber()).build();

	}
}
