package com.zamzar.api;

import com.zamzar.api.core.FormatsApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Format;
import com.zamzar.api.model.Formats;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

/**
 * Retrieves information about the file formats supported by the Zamzar API.
 */
public class FormatsService implements Listable<Format, String> {
    protected final ZamzarClient zamzar;
    protected final FormatsApi api;

    protected FormatsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new FormatsApi(zamzar.client);
    }

    /**
     * Retrieves a file format by its name.
     */
    public Format find(String name) throws ApiException {
        return api.getFormatById(name);
    }

    /**
     * Retrieves a list of file formats.
     *
     * @param anchor indicates the position in the list from which to start retrieving file formats
     * @param limit  indicates the maximum number of file formats to retrieve
     */
    @Override
    public Paged<Format, String> list(Anchor<String> anchor, Integer limit) throws ApiException {
        final String after = anchor == null ? null : anchor.getAfterParameterValue();
        final String before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Formats response = api.listFormats(limit, after, before);
        return new Paged<>(this, response.getData(), Paging.fromString(response.getPaging()));
    }
}
