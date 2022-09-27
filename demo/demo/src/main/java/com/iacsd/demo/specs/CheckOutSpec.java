package com.iacsd.demo.specs;

import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.CheckOut;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.util.CriteriaUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class CheckOutSpec {
    public static Specification<CheckOut> getCheckOutByAccount(Long accountId) {
        return (root, query, criteriaBuilder) -> {
            Join<CheckOut, Account> checkInAccountJoin = CriteriaUtils.getJoinFromRoot(root,"account", JoinType.INNER);
            return criteriaBuilder.equal(checkInAccountJoin.get("id"), accountId);
        };
    }

    public static Specification<CheckOut> getByProductCode(String code) {
        return (root, query, criteriaBuilder) -> {
            Join<CheckOut, CheckIn> checkOutCheckInJoin = CriteriaUtils.getJoinFromRoot(root,"checkIn", JoinType.INNER);
            Join<CheckIn, Product> checkInProductJoin = CriteriaUtils.getJoinFromParentJoin(checkOutCheckInJoin,"product", JoinType.INNER);
            return criteriaBuilder.like(checkInProductJoin.get("code"), "%"+code+"%");
        };
    }

    public static Specification<CheckOut> getCheckOutByCriteria(CheckInOutCriteriaDto criteria) {
        Specification<CheckOut> specs = Specification.where(getCheckOutByAccount(criteria.getAccountId()));

        if (StringUtils.hasText(criteria.getProductCode())) {
            specs = specs.and(getByProductCode(criteria.getProductCode()));
        }
        return specs;
    }
}
