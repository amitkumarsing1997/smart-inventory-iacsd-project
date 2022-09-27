package com.iacsd.demo.dto;

import com.iacsd.demo.model.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCategoryDto {

	private String uid;
	private String name;
	private Boolean isDeleted;

	public static ProductCategoryDto from(ProductCategory productCategory) {
		ProductCategoryDto dto = new ProductCategoryDto();
		dto.uid = productCategory.getUid();
		dto.name = productCategory.getName();
		dto.isDeleted = productCategory.getIsDeleted();
		return dto;
	}

}
