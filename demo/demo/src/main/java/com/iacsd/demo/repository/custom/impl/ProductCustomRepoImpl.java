package com.iacsd.demo.repository.custom.impl;

import com.iacsd.demo.dto.product.ProductListingDto;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.repository.custom.ProductCustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductCustomRepoImpl implements ProductCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductListingDto> getProducts(Specification<Product> spec, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductListingDto> query = cb.createQuery(ProductListingDto.class);
        Root<Product> root = query.from(Product.class);

        query.select(cb.construct(
                ProductListingDto.class,
                root.get("uid"),
                root.get("name"),
                root.get("code"),
                root.get("unit"),
                root.get("availableQuantity")
        )).orderBy(cb.asc(root.get("name")));

        if (spec != null) {
            query.where(spec.toPredicate(root, query, cb));
        }

        List<ProductListingDto> products = entityManager.createQuery(query).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();

        return new PageImpl<>(products, pageable, getTotalRecord(spec));
    }

    private Long getTotalRecord(Specification<Product> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Product> root = query.from(Product.class);

        query.select(cb.count(root.get("id")));

        if (spec != null) {
            query.where(spec.toPredicate(root, query, cb));
        }
        return entityManager.createQuery(query).getSingleResult();
    }

}
