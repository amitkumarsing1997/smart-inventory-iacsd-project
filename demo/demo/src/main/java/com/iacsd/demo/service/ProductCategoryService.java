package com.iacsd.demo.service;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryService {

	List<ProductCategoryDto> getAllProductCategories();
	
	PaginationResponse<ProductCategoryDto> getAllProductCategoriesByPage(int pageNo, int pageSize);
	
	Boolean saveOrUpdate(ProductCategoryDto productCategoryDto);
	
	Boolean isProductCategoryNameExist(String productCategoryName);
}
