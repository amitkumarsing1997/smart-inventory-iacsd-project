package com.iacsd.demo.service;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.product.ProductCriteriaDto;
import com.iacsd.demo.dto.product.ProductDto;
import com.iacsd.demo.dto.product.ProductListingDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	Boolean saveOrUpdate(ProductDto productDto);
	
	ProductDto getProductDto(String productCode);
		
	List<ProductDto> getAllProductsByProductCategory(String productCategoryUid);

	List<ProductDto> searchProductsByName(String productName);
	
	List<ProductDto> getLowStockProducts();
	
	List<ProductDto> getProductReport(String productCategoryUid);

	PaginationResponse<ProductListingDto> getProducts(ProductCriteriaDto dto, Pageable pageable);

	ProductDto getProductDtls(String productUid);
}
