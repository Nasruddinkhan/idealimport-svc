package ca.com.idealimport.common.specifications;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

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


    public static <T> void addSpecificationIfNotBlank(String value, String fieldName, List<Specification<T>> specificationList) {
        Optional.ofNullable(value)
                .filter(StringUtils::isNotBlank)
                .ifPresent(v -> specificationList.add(Specifications.fieldProperty(fieldName, v)));
    }

    public static <T> Specification<T> withInClause(String fieldName, List<?> values) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (values == null || values.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get(fieldName).in(values);
        };
    }
}
