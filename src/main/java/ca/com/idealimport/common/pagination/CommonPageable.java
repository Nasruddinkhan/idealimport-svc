package ca.com.idealimport.common.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CommonPageable implements Pageable {
    private int pageNumber;
    private int pageSize;
    private Sort sort;

    public CommonPageable(int pageNumber, int pageSize, Sort sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }
    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return (long) pageNumber * (long) pageSize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new CommonPageable(getPageNumber() + 1, getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new CommonPageable(getPageNumber() - 1, getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable first() {
        return new CommonPageable(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return  new CommonPageable(pageNumber, pageSize, sort);
    }

    @Override
    public boolean hasPrevious() {
        return  getPageNumber() > 0;
    }

}
