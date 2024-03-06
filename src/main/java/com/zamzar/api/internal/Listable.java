package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

public interface Listable<ITEM, ID> {
    default Paged<ITEM, ID> list() throws ApiException {
        return list(null, null);
    }

    default Paged<ITEM, ID> list(Anchor<ID> anchor) throws ApiException {
        return list(anchor, null);
    }
    
    default Paged<ITEM, ID> list(Integer limit) throws ApiException {
        return list(null, limit);
    }

    Paged<ITEM, ID> list(Anchor<ID> anchor, Integer limit) throws ApiException;
}
