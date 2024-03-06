package com.zamzar.api.pagination;

import com.zamzar.api.model.PagingNumeric;
import com.zamzar.api.model.PagingString;

public class Paging<ID> {

    protected final ID first;
    protected final ID last;
    protected final Integer limit;

    public static Paging<Integer> fromNumeric(PagingNumeric paging) {
        return new Paging<>(paging.getFirst(), paging.getLast(), paging.getLimit());
    }

    public static Paging<String> fromString(PagingString paging) {
        return new Paging<>(paging.getFirst(), paging.getLast(), paging.getLimit());
    }

    protected Paging(ID first, ID last, Integer limit) {
        this.first = first;
        this.last = last;
        this.limit = limit;
    }

    public ID getFirst() {
        return first;
    }

    public ID getLast() {
        return last;
    }

    public Integer getLimit() {
        return limit;
    }
}
