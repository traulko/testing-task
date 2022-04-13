package com.traulka.app.util;

import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@UtilityClass
public class SpecificationUtils {
    public static Predicate fieldLike(CriteriaBuilder cb, Expression<String> x, String filter) {
        return cb.like(cb.lower(x), getSearchString(filter));
    }

    public static String getSearchString(String str) {
        return "%" + str.toLowerCase().trim() + "%";
    }
}
