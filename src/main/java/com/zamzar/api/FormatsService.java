package com.zamzar.api;

import com.zamzar.api.core.FormatsApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Format;
import com.zamzar.api.model.Formats;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

public class FormatsService implements Listable<Format, String> {
    protected final ZamzarClient zamzar;
    protected final FormatsApi api;

    public FormatsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new FormatsApi(zamzar.client);
    }

    public Format find(String name) throws ApiException {
        return api.getFormatById(name);
    }

    @Override
    public Paged<Format, String> list(Anchor<String> anchor, Integer limit) throws ApiException {
        final String after = anchor == null ? null : anchor.getAfterParameterValue();
        final String before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Formats response = api.listFormats(limit, after, before);
        return new Paged<>(this, response.getData(), Paging.fromString(response.getPaging()));
    }
}
