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

public class ImportsService implements Listable<ImportManager, Integer> {
    protected final ZamzarClient zamzar;
    protected final ImportsApi api;

    public ImportsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new ImportsApi(zamzar.client);
    }

    public ImportManager find(Integer id) throws ApiException {
        return toImport(api.getImportById(id));
    }

    @Override
    public Paged<ImportManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Imports response = api.listImports(limit, after, before);
        return new Paged<>(this, toImports(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    public ImportManager start(String url) throws ApiException {
        return new ImportManager(zamzar, api.startImport(url, null)); // FIXME - add ability to set the name
    }

    protected List<ImportManager> toImports(List<ModelImport> models) {
        return models.stream().map(this::toImport).collect(Collectors.toList());
    }

    protected ImportManager toImport(ModelImport model) {
        return new ImportManager(zamzar, model);
    }
}
