package com.iacsd.demo.repository.custom;

import com.iacsd.demo.dto.product.ProductListingDto;
import com.iacsd.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductCustomRepo {
    Page<ProductListingDto> getProducts(Specification<Product> spec, Pageable pageable);
}
