package com.iacsd.demo.repository.custom.impl;

import com.iacsd.demo.dto.checkinout.CheckInOutListingDto;
import com.iacsd.demo.model.CheckOut;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.repository.custom.CheckOutCustomRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class CheckOutCustomRepoImpl implements CheckOutCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CheckInOutListingDto> getAllCheckOuts(Specification<CheckOut> specs, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CheckInOutListingDto> query = cb.createQuery(CheckInOutListingDto.class);
        Root<CheckOut> root = query.from(CheckOut.class);
        Join<CheckOut, Product> checkOutCheckInJoin = root.join("checkIn", JoinType.INNER).join("product", JoinType.INNER);
        query.select(cb.construct(
                CheckInOutListingDto.class,
                checkOutCheckInJoin.get("name"),
                checkOutCheckInJoin.get("code"),
                root.get("quantity"),
                root.get("unit"),
                root.get("rate"),
                root.get("rateUnit"),
                root.get("checkedOutOn")
        ));

        query.where(specs.toPredicate(root, query, cb)).orderBy(cb.desc(root.get("checkedOutOn")));

        TypedQuery<CheckInOutListingDto> typedQuery = entityManager.createQuery(query).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageSize() * pageable.getPageNumber());

        return new PageImpl<>(typedQuery.getResultList(), pageable, getTotalCheckOut(specs));
    }

    private Long getTotalCheckOut(Specification<CheckOut> specs) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<CheckOut> root = query.from(CheckOut.class);
        query.select(cb.count(root.get("id")));
        query.where(specs.toPredicate(root, query, cb));
        return entityManager.createQuery(query).getSingleResult();
    }
}
