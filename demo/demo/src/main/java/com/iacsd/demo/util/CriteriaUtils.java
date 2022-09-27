package com.iacsd.demo.util;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public class CriteriaUtils {

    private CriteriaUtils() {}

    public static <X,Y> Join<X,Y> getJoinFromRoot(Root<X> root, String field, JoinType joinType) {
        return (Join<X, Y>) root.getJoins().stream().filter(j -> j.getAttribute().getName().equals(field))
                .findFirst().orElseGet(() -> root.join(field, joinType));
    }

    public static <X,Y,Z> Join<Y,Z> getJoinFromParentJoin(Join<X,Y> join, String field, JoinType joinType) {
        return (Join<Y, Z>) join.getJoins().stream().filter(j -> j.getAttribute().getName().equals(field))
                .findFirst().orElseGet(() -> join.join(field, joinType));
    }
}
