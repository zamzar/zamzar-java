package com.zamzar.api.pagination;

import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;

import java.util.Collections;
import java.util.List;

public class Paged<ITEM, ID> {
    protected final Listable<ITEM, ID> lister;
    protected final List<ITEM> items;

    protected final Paging<ID> paging;

    public Paged(Listable<ITEM, ID> lister, List<ITEM> items, Paging<ID> paging) {
        this.lister = lister;
        this.items = Collections.unmodifiableList(items);
        this.paging = paging;
    }

    public List<ITEM> getItems() {
        return items;
    }

    public Paged<ITEM, ID> nextPage() throws ApiException {
        return lister.list(Anchor.after(paging.getLast()), paging.getLimit());
    }

    public Paged<ITEM, ID> previousPage() throws ApiException {
        return lister.list(Anchor.before(paging.getFirst()), paging.getLimit());
    }
}
