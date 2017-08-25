/* 
 * Copyright © 2017 www.lvmama.com
 */

/*
 * 修订记录:
 * @author 钟勋（zhongxun@lvmama.com） 2017-08-24 21:51 创建
 */
package org.antframework.common.util.jpa;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SpecificationUtil {
    public static final char SEPARATOR = '_';

    public static <T> Specification<T> parse(Map<String, Object> criteriaMap) {
        final List<Criteria> criterias = Criteria.parse(criteriaMap);

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                for (Criteria criteria : criterias) {
                    predicates.add(buildPredicate(root, cb, criteria));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    private static Path<String> getPath(Root root, String attributeName) {
        Path<String> path = root;
        for (String name : StringUtils.split(attributeName, '.')) {
            path = path.get(name);
        }
        return path;
    }

    private static Predicate buildPredicate(Root root, CriteriaBuilder cb, Criteria criteria) {
        Path<String> path = getPath(root, criteria.attributeName);

        Predicate predicate;
        switch (criteria.operator) {
            case EQ:
                predicate = cb.equal(path, criteria.value);
                break;
            case GT:
                predicate = cb.greaterThan(path, (Comparable) criteria.value);
                break;
            case GTE:
                predicate = cb.greaterThanOrEqualTo(path, (Comparable) criteria.value);
                break;
            case LT:
                predicate = cb.lessThan(path, (Comparable) criteria.value);
                break;
            case LTE:
                predicate = cb.lessThanOrEqualTo(path, (Comparable) criteria.value);
                break;
            case LIKE:
                predicate = cb.like(path, "%" + criteria.value + "%");
                break;
            case LLIKE:
                predicate = cb.like(path, "%" + criteria.value);
                break;
            case RLIKE:
                predicate = cb.like(path, criteria.value + "%");
                break;
            case NULL:
                predicate = cb.isNull(path);
                break;
            case NOTNULL:
                predicate = cb.isNotNull(path);
                break;
            case IN:
                if (criteria.value instanceof Object[]) {
                    predicate = path.in((Object[]) criteria.value);
                } else if (criteria.value instanceof Collection) {
                    predicate = path.in((Collection<?>) criteria.value);
                } else {
                    throw new IllegalArgumentException("非法的IN操作");
                }
            default:
                throw new IllegalArgumentException("非法的操作符");
        }

        return predicate;
    }

    public enum Operator {
        EQ,
        GT,
        GTE,
        LT,
        LTE,
        LIKE,
        LLIKE,
        RLIKE,
        NULL,
        NOTNULL,
        IN
    }

    private static class Criteria {

        private String attributeName;
        private Operator operator;
        private Object value;

        public Criteria(String attributeName, Operator operator, Object value) {
            this.attributeName = attributeName;
            this.operator = operator;
            this.value = value;
        }

        public static List<Criteria> parse(Map<String, Object> criteriaMap) {
            List<Criteria> criterias = new ArrayList<>();
            for (String key : criteriaMap.keySet()) {
                String[] names = StringUtils.split(key, SEPARATOR);
                if (names.length != 2) {
                    throw new IllegalArgumentException("非法查询参数" + key);
                }
                criterias.add(new Criteria(names[1], Operator.valueOf(names[0]), criteriaMap.get(key)));
            }

            return criterias;
        }
    }
}
