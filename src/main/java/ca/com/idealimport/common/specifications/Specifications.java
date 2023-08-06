package ca.com.idealimport.common.specifications;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

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

    public static <T> Specification<T> fieldProperty(Long fieldValue, String... propertyNames) {
        return (root, query, criteriaBuilder) -> {
            Path<T> path = root;
            path = Arrays.stream(propertyNames)
                    .reduce(path,
                            Path::get,
                            (rootPath, finalPath) -> finalPath);
            return criteriaBuilder.equal(path, fieldValue);
        };

    }

    public static <T> Specification<T> fieldProperty(String fieldName, Boolean fieldValue) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(fieldName), fieldValue);
    }
}
