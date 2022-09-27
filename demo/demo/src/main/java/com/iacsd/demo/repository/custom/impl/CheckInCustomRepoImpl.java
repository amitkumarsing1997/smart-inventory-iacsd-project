package com.iacsd.demo.repository.custom.impl;

import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.repository.custom.CheckInCustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class CheckInCustomRepoImpl implements CheckInCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CheckInOutListingDto> getAllCheckIns(Specification<CheckIn> specs, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CheckInOutListingDto> query = cb.createQuery(CheckInOutListingDto.class);
        Root<CheckIn> root = query.from(CheckIn.class);
        Join<CheckIn, Product> checkInProductJoin = root.join("product", JoinType.INNER);
        query.select(cb.construct(
           CheckInOutListingDto.class,
           checkInProductJoin.get("name"),
           checkInProductJoin.get("code"),
           root.get("quantity"),
           root.get("unit"),
           root.get("rate"),
           root.get("rateUnit"),
           root.get("checkedInOn")
        ));

        query.where(specs.toPredicate(root, query, cb)).orderBy(cb.desc(root.get("checkedInOn")));

        TypedQuery<CheckInOutListingDto> typedQuery = entityManager.createQuery(query).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageSize() * pageable.getPageNumber());

        return new PageImpl<>(typedQuery.getResultList(), pageable, getTotalCheckIn(specs));
    }

    private Long getTotalCheckIn(Specification<CheckIn> specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<CheckIn> root = query.from(CheckIn.class);
        query.select(cb.count(root.get("id")));
        query.where(specs.toPredicate(root, query, cb));
        return entityManager.createQuery(query).getSingleResult();
    }
}
