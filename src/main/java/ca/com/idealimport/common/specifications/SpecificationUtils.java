package ca.com.idealimport.common.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> or(List<Specification<T>> specifications) {
        return (root, query, criteriaBuilder) ->
                specifications.stream()
                        .map(specification -> specification.toPredicate(root, query, criteriaBuilder))
                        .reduce(criteriaBuilder::or)
                        .orElse(criteriaBuilder.conjunction());
    }

    public static <T> Specification<T> and(List<Specification<T>> specifications) {
        return (root, query, criteriaBuilder) ->
                specifications.stream()
                        .map(specification -> specification.toPredicate(root, query, criteriaBuilder))
                        .reduce(criteriaBuilder::and)
                        .orElse(criteriaBuilder.conjunction());
    }
}
