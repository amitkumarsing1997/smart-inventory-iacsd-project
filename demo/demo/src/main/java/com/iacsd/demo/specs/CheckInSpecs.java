package com.iacsd.demo.specs;

import com.iacsd.demo.dto.checkinout.CheckInOutCriteriaDto;
import com.iacsd.demo.model.Account;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.Product;
import com.iacsd.demo.util.CriteriaUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class CheckInSpecs {

    public static Specification<CheckIn> getCheckInByAccount(Long accountId) {
        return (root, query, criteriaBuilder) -> {
            Join<CheckIn, Account> checkInAccountJoin = CriteriaUtils.getJoinFromRoot(root,"account", JoinType.INNER);
            return criteriaBuilder.equal(checkInAccountJoin.get("id"), accountId);
        };
    }

    public static Specification<CheckIn> getByProductCode(String code) {
        return (root, query, criteriaBuilder) -> {
            Join<CheckIn, Product> checkInProductJoin = CriteriaUtils.getJoinFromRoot(root,"product", JoinType.INNER);
            return criteriaBuilder.like(checkInProductJoin.get("code"), "%"+code+"%");
        };
    }

    public static Specification<CheckIn> getCheckInByCriteria(CheckInOutCriteriaDto criteria) {
        Specification<CheckIn> specs = Specification.where(getCheckInByAccount(criteria.getAccountId()));

        if (StringUtils.hasText(criteria.getProductCode())) {
            specs = specs.and(getByProductCode(criteria.getProductCode()));
        }
        return specs;
    }
}
