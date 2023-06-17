package ca.com.idealimport.common.util;

import ca.com.idealimport.common.pagination.CommonPageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {

    public  CommonPageable getPageableOrder(int page, int size, String sortBy, String... sortField) {
        final var sort = Sort.by(sortBy.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        return new CommonPageable(page, size, sort);

    }


}
