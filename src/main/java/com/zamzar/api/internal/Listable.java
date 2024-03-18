package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

public interface Listable<ITEM, ID> {
    /**
     * Retrieves a list of items starting from the beginning. Returns no more than 50 items (per page).
     */
    default Paged<ITEM, ID> list() throws ApiException {
        return list(null, null);
    }

    /**
     * Retrieves a list of items starting from the specified anchor. Returns no more than 50 items (per page).
     */
    default Paged<ITEM, ID> list(Anchor<ID> anchor) throws ApiException {
        return list(anchor, null);
    }

    /**
     * Retrieves a list of items starting from the beginning. Returns no more than the specified number of items (per page).
     */
    default Paged<ITEM, ID> list(Integer limit) throws ApiException {
        return list(null, limit);
    }

    Paged<ITEM, ID> list(Anchor<ID> anchor, Integer limit) throws ApiException;
}
