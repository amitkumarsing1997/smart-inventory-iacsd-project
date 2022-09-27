package com.iacsd.demo.specs;

import com.iacsd.demo.dto.product.ProductCriteriaDto;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.model.ProductCategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecs {

	public static Specification<Product> getProductByNameSpec(String productName) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.like(root.get("name"), "%"+productName+"%");
		};
	}

	public static Specification<Product> getProductByProductCategorySpec(String productCategoryUid) {
		return (root, query, criteriaBuilder) -> {
			Join<Product, ProductCategory> productCategoryJoin = root.join("productCategory");
			return criteriaBuilder.equal(productCategoryJoin.get("uid"), productCategoryUid);
		};
	}

	public static Specification<Product> getProductByAccount(Long accountId) {
		return (root, query, criteriaBuilder) -> {
			Join<Product, Account> productAccountJoin = root.join("account");
			return criteriaBuilder.equal(productAccountJoin.get("id"), accountId);
		};
	}

	public static Specification<Product> getProductsByCriteria(ProductCriteriaDto dto) {
		Specification<Product> productSpec = Specification.where(getProductByAccount(dto.getAccountId()));
		List<Specification<Product>> specs = new ArrayList<>();
		if (dto.getCategoryUid() != null) {
			specs.add(getProductByProductCategorySpec(dto.getCategoryUid()));
		}
		if (dto.getProductName() != null) {
			specs.add(getProductByNameSpec(dto.getProductName()));
		}

		for(Specification<Product> spec: specs) {
			productSpec = productSpec.and(spec);
		}
		return productSpec;
	}

}
