package com.iacsd.demo.rest;


import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.product.ProductCriteriaDto;
import com.iacsd.demo.dto.product.ProductDto;
import com.iacsd.demo.dto.product.ProductListingDto;
import com.iacsd.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("product")
public class ProductApi {

	@Autowired
	private ProductService productService;

	@PostMapping("/saveOrUpdate")
	public ApiResponse<Boolean> saveOrUpdateProduct(@RequestBody ProductDto productDto) {
		return new ApiResponse<>(productService.saveOrUpdate(productDto));
	}

	@GetMapping("get/by/code/{code}")
	public ApiResponse<ProductDto> getByCode(@PathVariable String code, @AuthenticationPrincipal String user) {
		log.info("USER :: {}", user);
		return ApiResponse.<ProductDto>builder().body(this.productService.getProductDto(code)).build();
	}
	
	@GetMapping("/search/{productName}")
	public ApiResponse<List<ProductDto>> searchProductByName(@PathVariable String productName){
		List<ProductDto> productDtos = null;
		try {
			productDtos = productService.searchProductsByName(productName);
			return new ApiResponse<>(productDtos);
		} catch (RuntimeException ex) {
			return new ApiResponse<>(ex.getMessage(), false);
		}
	}
	
	@GetMapping("/getAll/{productCategoryUid}")
	public ApiResponse<List<ProductDto>> getAllByProductCategory(@PathVariable String productCategoryUid){
		return new ApiResponse<>(productService.getAllProductsByProductCategory(productCategoryUid));
	}

	//not checked by amit
	@PostMapping("/report/getAll")
	public ApiResponse<List<ProductDto>> getReportData( @RequestBody(required=false) String productCategoryUid){
		return new ApiResponse<>(productService.getProductReport(productCategoryUid));
	}

	@GetMapping("/lowstock")
	public ApiResponse<List<ProductDto>> getLowStockProducts(){
		return new ApiResponse<>(productService.getLowStockProducts());
	}


	//not checked by amit
	@PostMapping("get/all/by/criteria")
	public PaginationResponse<ProductListingDto> getProducts(@RequestBody ProductCriteriaDto dto, @PageableDefault Pageable pageable) {
		return productService.getProducts(dto, pageable);
	}

	@GetMapping("get/a/{productUid}")
	public ApiResponse<ProductDto> getProductDtls(@PathVariable String productUid) {
		return ApiResponse.<ProductDto>builder().body(productService.getProductDtls(productUid)).build();
	}

}
