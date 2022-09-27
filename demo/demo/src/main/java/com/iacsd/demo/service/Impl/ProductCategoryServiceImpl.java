package com.iacsd.demo.service.Impl;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCategoryDto;
import com.iacsd.demo.exception.GenericException;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.ProductCategory;
import com.iacsd.demo.repository.ProductCateogryRepository;
import com.iacsd.demo.service.AccountService;
import com.iacsd.demo.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCateogryRepository productCategoryRepository;

	@Autowired private AccountService accountService;


	@Override
	public List<ProductCategoryDto> getAllProductCategories() {


		Account account = accountService.getLoggedInUserAccount();  // by amit aditya
		List<ProductCategory> productCategoryList = productCategoryRepository.findByAccountId(account.getId());
		return productCategoryList.stream().map(ProductCategoryDto::from).toList();
	}

	@Override
	public PaginationResponse<ProductCategoryDto> getAllProductCategoriesByPage(int pageNo, int pageSize) {
		Account account = accountService.getLoggedInUserAccount(); //byAmit aditya
		Page<ProductCategory> page = productCategoryRepository.findByAccountId(account.getId(),PageRequest.of(pageNo, pageSize));  //by amit aditya
		List<ProductCategoryDto> productCategoryDtoList = page.getContent().stream().map(ProductCategoryDto::from).toList();
//		return new PaginationResponse<>(pageNo, pageSize, page.getTotalPages(), page.getNumberOfElements(),
//				page.getTotalElements(), productCategoryDtoList);
		return PaginationResponse.<ProductCategoryDto>builder().body(productCategoryDtoList)
                .pageNumber(page.getNumber()).pageSize(page.getSize()).totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements()).totalElements(page.getTotalElements()).build();
	}

	@Override
	public Boolean saveOrUpdate(ProductCategoryDto productCategoryDto) {
		Account account = accountService.getLoggedInUserAccount(); //by amit aditya
		ProductCategory productCategory;
		if(Optional.ofNullable(productCategoryDto.getUid()).isPresent()) {
			productCategory = productCategoryRepository.findByUid(productCategoryDto.getUid());
			//productCategory.setAccount(account);  //by amit aditya
		}else {
			productCategory = new ProductCategory();
			productCategory.setAccount(account);  //by amit aditya
		}
		if(isProductCategoryNameExist(productCategoryDto.getName()))
		{
			throw new GenericException("category already exists");
		}
		productCategory.setName(productCategoryDto.getName());
		productCategoryRepository.save(productCategory);
		return true;
	}

	@Override
	public Boolean isProductCategoryNameExist(String productCategoryName) {
		   Account account = accountService.getLoggedInUserAccount(); // By amit aditya
		log.info("Product nmae :: {}", productCategoryName.getClass().getSimpleName());
		Boolean isExists = productCategoryRepository.isProductCategoryNameExist(productCategoryName ,account.getId());//By amit adtiya);
		log.info("Exists :: {}", isExists);
		return isExists;
	}

}
