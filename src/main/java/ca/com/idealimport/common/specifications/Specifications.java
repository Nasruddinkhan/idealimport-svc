package ca.com.idealimport.common.specifications;

import org.springframework.data.jpa.domain.Specification;

public class Specifications {
    private Specifications() {
    }

    public static <T> Specification<T> fieldContains(String fieldName, String fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(fieldName)),
                        "%" + fieldValue.toLowerCase() + "%");
    }

    public static <T> Specification<T> fieldProperty(String fieldName, String fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), fieldValue);
    }

    public static <T> Specification<T> fieldProperty(String fieldName, Boolean fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), fieldValue);
    }


}
