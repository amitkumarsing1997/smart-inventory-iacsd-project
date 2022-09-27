package com.iacsd.demo.service.Impl;
import com.iacsd.demo.dto.CheckOutDto;
import com.iacsd.demo.dto.bill.BillDTO;
import com.iacsd.demo.dto.bill.BillListingDto;
import com.iacsd.demo.dto.bill.ProductInfoDTO;
import com.iacsd.demo.dto.bill.ViewBillDto;
import com.iacsd.demo.exception.ExceptionPool;
import com.iacsd.demo.exception.GenericException;
import com.iacsd.demo.model.*;
import com.iacsd.demo.repository.*;
import com.iacsd.demo.service.AccountService;
import com.iacsd.demo.service.AppUserService;
import com.iacsd.demo.service.BillingService;
import com.iacsd.demo.util.PDFUtil;
import com.iacsd.demo.util.TemplateEngineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillingServiceImpl implements BillingService {

	@Autowired
	private CheckinRepo checkinRepo;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private CheckOutRepo checkOutRepo;
	@Autowired
	private BillRepo billRepo;
	@Autowired
	private ProductRepository productRepository;

	@Autowired private AppUserService appUserService;


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String getBill(BillDTO dto) {
		Account acc = this.accountService.getLoggedInUserAccount();
		Bill bill = new Bill();
		BillDTO.fillEntity(dto, bill);
		bill.setAccount(acc);
		if (!CollectionUtils.isEmpty(dto.getProducts())) {
			Map<String, CheckOutDto> checkInUidOutMap = dto.getProducts().stream().collect(Collectors.toMap(CheckOutDto::getCheckInUid, checkOut -> checkOut));
			List<CheckIn> checkIns = this.checkinRepo.findByUidIn(checkInUidOutMap.keySet());
			List<CheckOut> checkOuts = new ArrayList<>();
			for (CheckIn checkIn: checkIns) {
				Product product = checkIn.getProduct();
				CheckOutDto checkOutDto = checkInUidOutMap.get(checkIn.getUid());
				if (checkOutDto.getQuantity() <= checkIn.getRemainingQuantity()) {
					checkIn.setRemainingQuantity(checkIn.getRemainingQuantity() - checkOutDto.getQuantity());
					product.setAvailableQuantity(product.getAvailableQuantity() - checkOutDto.getQuantity());
					CheckOut checkOut = new CheckOut();
					CheckOutDto.fillEntity(checkOutDto, checkOut);
					checkOut.setCheckIn(checkIn);
					checkOut.setUnit(checkIn.getUnit());
					checkOut.setRateUnit(checkIn.getRateUnit());
					checkOut.setCheckedOutOn(Instant.now());
					checkOut.setBill(bill);
					checkOut.setAccount(acc);
					checkOuts.add(checkOut);
					this.productRepository.save(product);
				} else {
					throw new GenericException("Not enough quantity");
				}
			}
			this.checkinRepo.saveAll(checkIns);
			bill.setCheckOuts(checkOuts);
		} else {
			throw ExceptionPool.REQ_INVALID.get();
		}
		bill = this.billRepo.save(bill);
		return bill.getUid();
	}

	@Override
	public List<BillListingDto> getBills(String mobileNumber) {
		AppUser appUser = this.appUserService.getLoggedInUser();
		return this.billRepo.getBillsByMobileNum(mobileNumber, appUser.getAccount().getId());
	}

	@Override
	public ViewBillDto getBillDtls(String uid) {
		Bill bill = this.billRepo.findByUid(uid).orElseThrow(ExceptionPool.REQ_INVALID);
		ViewBillDto dto = ViewBillDto.from(bill);
		dto.setProducts(this.checkOutRepo.getCheckoutProducts(bill.getId()));
		return dto;
	}


    @Override
	public ResponseEntity<Resource> downloadBill(String uid) {
		Bill bill = this.billRepo.findByUid(uid).orElseThrow(ExceptionPool.REQ_INVALID);
		List<ProductInfoDTO> myList = checkOutRepo.getCheckoutProducts(bill.getId());
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("shopName", bill.getAccount().getAccName());
		data.put("shopAddress", bill.getAccount().getAddress());
		data.put("customerName", bill.getCustName());
		data.put("customerMobileNo", bill.getMobileNo());
		data.put("products",myList);

//				List.of(
//				Map.of("name", "Red Bull", "rate", 110, "rateUnit", "Count", "qty", 2, "unit", "Count", "total", 220),
//				Map.of("name", "Aloo Bhujia", "rate", 20, "rateUnit", "Count", "qty", 2, "unit", "Count","total", 40),
//				Map.of("name", "Sugar", "rate", .10, "rateUnit", "Gram", "qty", 500, "unit", "Gram","total", 50)
//		));
		double totalSum = myList.stream().mapToDouble(f -> f.getTotal()).sum();
		data.put("total", totalSum);
		Resource resource =  new ByteArrayResource(PDFUtil.htmlToPdf(TemplateEngineUtil.buildTemplate("bill.html", data)));
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+"customer_name"+".pdf");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		try {
			return ResponseEntity.ok().headers(header).contentLength(resource.contentLength())
					.contentType(MediaType.APPLICATION_PDF).body(resource);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new GenericException("Error Occurred");
		}
	}

}
