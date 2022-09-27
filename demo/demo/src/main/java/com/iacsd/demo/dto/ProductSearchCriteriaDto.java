package com.iacsd.demo.dto;

public class ProductSearchCriteriaDto {

	private String productName;
	private String productCategoryUid;
	
	public String getProductCategoryUid() {
		return productCategoryUid;
	}
	public void setProductCategoryUid(String productCategoryUid) {
		this.productCategoryUid = productCategoryUid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
