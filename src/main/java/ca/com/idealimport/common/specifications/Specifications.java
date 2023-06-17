package ca.com.idealimport.common.specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class Specifications<T> {
    public static <T> Specification<T> fieldContains(String fieldName, String fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                        "%" + fieldValue.toLowerCase() + "%");
    }

    public static <T> Specification<T> andCondition(String fieldName, String fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), fieldValue);
    }

    public static <T> Specification<T> or(List<Specification<T>> specifications) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Specification<T> specification : specifications) {
                predicates.add(specification.toPredicate(root, query, criteriaBuilder));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
