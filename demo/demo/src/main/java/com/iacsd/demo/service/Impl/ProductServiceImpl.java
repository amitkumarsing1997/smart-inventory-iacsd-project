package com.iacsd.demo.service.Impl;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.product.ProductCriteriaDto;
import com.iacsd.demo.dto.product.ProductDto;
import com.iacsd.demo.dto.product.ProductListingDto;
import com.iacsd.demo.exception.ExceptionPool;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.repository.ProductCateogryRepository;
import com.iacsd.demo.repository.ProductRepository;
import com.iacsd.demo.service.AccountService;
import com.iacsd.demo.service.ProductService;
import com.iacsd.demo.specs.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCateogryRepository productCateogryRepository;

	@Autowired private AccountService accountService;

	@Override
	public Boolean saveOrUpdate(ProductDto productDto) {

		Product product = null;

		if (Optional.ofNullable(productDto.getUid()).isPresent()) {
			product = productRepository.findByUid(productDto.getUid());
		} else {
			product = new Product();
			product.setCreatedOn(Instant.now());
			product.setAccount(accountService.getLoggedInUserAccount());
			product.setAvailableQuantity(0L);

		}
		product.setCode(productDto.getCode());
		product.setName(productDto.getName());
		product.setProductCategory(productCateogryRepository.findByUid(productDto.getProductCatUid()));
		product.setMinimumQuantity(productDto.getMinimumQuantity());
		product.setUnit(productDto.getUnit());
		productRepository.save(product);
		return true;
	}

	@Override
	public ProductDto getProductDto(String productCode) {
		Product product = productRepository.findByCodeAndAccountId(productCode, accountService.getLoggedInUserAccount().getId());
		if (product == null)
			throw ExceptionPool.REQ_INVALID.get();
		ProductDto dto = ProductDto.from(product);
		dto.setProductCatUid(product.getProductCategory().getUid());
		dto.setProductCatName(product.getProductCategory().getName());
		return dto;
	}

	@Override
	public List<ProductDto> getAllProductsByProductCategory(String productCategoryUid) {
//		List<Product> productList = productRepository.findAllByProductCategoryUid(productCategoryUid);
//		List<ProductDto> productDtoList = productList.stream().map(product -> ProductDto.of(new ProductDto(), product))
//				.collect(Collectors.toList());
//		return productDtoList;
		return null;
	}

	@Override
	public List<ProductDto> searchProductsByName(String productName) throws RuntimeException{
		List<Product> productList = productRepository.findByNameLikeAndAccountId("%"+productName+"%", this.accountService.getLoggedInUserAccountId());
		return productList.stream().map(product -> ProductDto.of(new ProductDto(), product))
				.toList();
	}
	
	@Override
	public List<ProductDto> getLowStockProducts() {

		Account account = accountService.getLoggedInUserAccount();   // by amit
		List<Product> productList = productRepository.findLowStockProducts(account.getId());
		return productList.stream().map(ProductDto::from).toList();
	}

	@Override
	public List<ProductDto> getProductReport(String productCategoryUid) {
		List<Product> productList = null;
		if(productCategoryUid!=null) {
//			productList = productRepository.findAllByProductCategoryUid(productCategoryUid);
		}else {
			productList = productRepository.findAll();
		}
		List<ProductDto> productDtoList = productList.stream().map(product -> ProductDto.forReport(new ProductDto(), product))
				.collect(Collectors.toList());
		return productDtoList;
	}

	@Override
	public PaginationResponse<ProductListingDto> getProducts(ProductCriteriaDto dto, Pageable pageable) {
		dto = dto == null ? new ProductCriteriaDto() : dto;
		dto.setAccountId(accountService.getLoggedInUserAccountId());
		Page<ProductListingDto> page = productRepository.getProducts(ProductSpecs.getProductsByCriteria(dto), pageable);
		return PaginationResponse.<ProductListingDto>builder().body(page.getContent())
				.pageNumber(page.getNumber()).pageSize(page.getSize()).totalPages(page.getTotalPages())
				.numberOfElements(page.getNumberOfElements()).build();
	}

	@Override
	public ProductDto getProductDtls(String productUid) {
		Product product = this.productRepository.findByUid(productUid);
		if (product == null) {
			throw ExceptionPool.REQ_INVALID.get();
		}
		ProductDto dto = ProductDto.from(product);
		dto.setProductCatUid(product.getProductCategory().getUid());
		dto.setProductCatName(product.getProductCategory().getName());
		return dto;
	}
}
