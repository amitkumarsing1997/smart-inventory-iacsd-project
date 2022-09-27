package com.iacsd.demo.rest;

import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.ProductCategoryDto;
import com.iacsd.demo.service.Impl.AppUserServiceImpl;
import com.iacsd.demo.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("productcategory")
@Slf4j
public class ProductCategoryApi {
	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("/getAll")
	public ApiResponse<List<ProductCategoryDto>> getAllProductCategories() {
		return new ApiResponse<>(productCategoryService.getAllProductCategories());
	}

	@GetMapping("/getAll/{pageNo}/{pageSize}")
	public PaginationResponse<ProductCategoryDto> getAllProductCategoriesByPage(@PathVariable int pageNo,
			@PathVariable int pageSize) {
		return productCategoryService.getAllProductCategoriesByPage(pageNo, pageSize);
	}
	
	@PostMapping("/saveOrUpdate")
	public ApiResponse<Boolean> saveOrUpdate(@RequestBody ProductCategoryDto productCategoryDto){
		return new ApiResponse<>(productCategoryService.saveOrUpdate(productCategoryDto));
	}
	
	@PostMapping("/isProductNameExist")
	public ApiResponse<Boolean>isProductCategoryNameExists(@RequestBody ProductCategoryDto productCategoryDto){
        //String productCategoryName
		return new ApiResponse<Boolean>(productCategoryService.isProductCategoryNameExist(productCategoryDto.getName()));
	}
	@GetMapping("/isProductNameExist/{productCategoryName}")
	public ApiResponse<Boolean>isProductCategoryNameExists(@PathVariable String productCategoryName){
		//String productCategoryName
		return new ApiResponse<Boolean>(productCategoryService.isProductCategoryNameExist(productCategoryName));
	}
}
