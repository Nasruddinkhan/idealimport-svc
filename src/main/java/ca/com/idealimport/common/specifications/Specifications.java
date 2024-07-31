package ca.com.idealimport.common.specifications;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

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

    public static <T> Specification<T> fieldProperty(String fieldValue, String... propertyNames) {
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

    public static <T> Specification<T> fieldInClause(List<?> values, String... fieldNames) {
        return (root, query, criteriaBuilder) -> {
            Path<?> path = getPath(root, fieldNames);
            return path.in(values);
        };
    }

    private static Path<?> getPath(Path<?> root, String... fieldNames) {
        Path<?> path = root;
        for (String fieldName : fieldNames) {
            path = path.get(fieldName);
        }
        return path;
    }
}
