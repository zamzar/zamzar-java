package com.zamzar.api.pagination;

import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;

import java.util.Collections;
import java.util.List;

/**
 * Represents a page of items retrieved from the Zamzar API servers.
 *
 * @param <ITEM> the type of item being listed
 * @param <ID>   the type of the item's ID (e.g., Integer or String)
 */
public class Paged<ITEM, ID> {
    protected final Listable<ITEM, ID> lister;
    protected final List<ITEM> items;

    protected final Paging<ID> paging;

    public Paged(Listable<ITEM, ID> lister, List<ITEM> items, Paging<ID> paging) {
        this.lister = lister;
        this.items = Collections.unmodifiableList(items);
        this.paging = paging;
    }

    /**
     * Returns all the items contained in the current page.
     */
    public List<ITEM> getItems() {
        return items;
    }

    /**
     * Makes an API request to retrieve the next page of items.
     */
    public Paged<ITEM, ID> nextPage() throws ApiException {
        return lister.list(Anchor.after(paging.getLast()), paging.getLimit());
    }

    /**
     * Makes an API request to retrieve the previous page of items.
     */
    public Paged<ITEM, ID> previousPage() throws ApiException {
        return lister.list(Anchor.before(paging.getFirst()), paging.getLimit());
    }
}
