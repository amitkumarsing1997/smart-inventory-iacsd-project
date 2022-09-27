package com.iacsd.demo.dto.product;

import com.iacsd.demo.model.Product;
import com.iacsd.demo.model.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
	private String uid;
	private String name;
	private String code;
	private Long minimumQuantity;
	private Unit unit;
	private Long avlQty;
	private String productCatUid;
	private String productCatName;

	public static ProductDto of(ProductDto dto, Product product) {
		dto.uid = product.getUid();
		dto.name = product.getName();
		dto.code = product.getCode();
		dto.minimumQuantity = product.getMinimumQuantity();
		dto.unit = product.getUnit();
		return dto;
	}
	
	public static ProductDto forReport(ProductDto productDto, Product product) {
//		productDto.setAvailableQuantity(product.getAvailableQuantity());
		productDto.setCode(product.getCode());
		productDto.setMinimumQuantity(product.getMinimumQuantity());
		productDto.setName(product.getName());
//		if (product.getProductCategory() != null) {
//			productDto.setProductCategoryName(product.getProductCategory().getName());
//		}
		return productDto;
	}

	public static void fillEntity(ProductDto dto, Product entity) {
		entity.setCode(dto.code);
		entity.setName(dto.name);
		entity.setMinimumQuantity(dto.minimumQuantity);
		entity.setUnit(dto.unit);

	}

	public static ProductDto from(Product entity) {
		ProductDto dto = new ProductDto();
		dto.uid = entity.getUid();
		dto.name = entity.getName();
		dto.code = entity.getCode();
		dto.minimumQuantity = entity.getMinimumQuantity();
		dto.unit = entity.getUnit();
		dto.avlQty = entity.getAvailableQuantity();
		return dto;
	}
}
