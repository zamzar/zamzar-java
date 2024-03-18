package com.zamzar.api;

import com.zamzar.api.core.ImportsApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Imports;
import com.zamzar.api.model.ModelImport;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Starts imports -- and retrieves information about existing imports -- on the Zamzar API servers.
 */
public class ImportsService implements Listable<ImportManager, Integer> {
    protected final ZamzarClient zamzar;
    protected final ImportsApi api;

    protected ImportsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new ImportsApi(zamzar.client);
    }

    /**
     * Retrieves an import request by its ID.
     */
    public ImportManager find(Integer id) throws ApiException {
        return toImport(api.getImportById(id));
    }

    /**
     * Retrieves a list of import requests.
     *
     * @param anchor indicates the position in the list from which to start retrieving import requests
     * @param limit  indicates the maximum number of import requests to retrieve
     */
    @Override
    public Paged<ImportManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Imports response = api.listImports(limit, after, before);
        return new Paged<>(this, toImports(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    /**
     * Starts an import request.
     */
    public ImportManager start(String url) throws ApiException {
        return new ImportManager(zamzar, api.startImport(url, null));
    }

    /**
     * Starts an import request, overriding the filename (rather than using the filename from the URL).
     */
    public ImportManager start(String url, String filename) throws ApiException {
        return new ImportManager(zamzar, api.startImport(url, filename));
    }

    protected List<ImportManager> toImports(List<ModelImport> models) {
        return models.stream().map(this::toImport).collect(Collectors.toList());
    }

    protected ImportManager toImport(ModelImport model) {
        return new ImportManager(zamzar, model);
    }
}
