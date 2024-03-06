package com.zamzar.api;

import com.zamzar.api.internal.Awaitable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;
import com.zamzar.api.model.ModelImport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ImportManager extends Awaitable<ImportManager> {

    protected static List<ModelImport.StatusEnum> TERMINAL_STATUSES = Arrays.asList(
        ModelImport.StatusEnum.SUCCESSFUL,
        ModelImport.StatusEnum.FAILED
    );

    protected final ZamzarClient zamzar;
    protected final ModelImport model;

    public ImportManager(ZamzarClient zamzar, ModelImport model) {
        this.zamzar = zamzar;
        this.model = model;
    }

    public ModelImport getModel() {
        return model;
    }

    public Integer getId() {
        return getModel().getId();
    }

    public boolean hasCompleted() {
        return TERMINAL_STATUSES.contains(getModel().getStatus());
    }

    public boolean hasSucceeded() {
        return getModel().getStatus() == ModelImport.StatusEnum.SUCCESSFUL;
    }

    public Failure getFailure() {
        return getModel().getFailure();
    }

    public ImportManager refresh() throws ApiException {
        return zamzar.imports().find(getId());
    }

    public FileManager getImportedFile() {
        return new FileManager(zamzar, getModel().getFile());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportManager that = (ImportManager) o;
        return Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImportManager{");
        sb.append("model=").append(model);
        sb.append('}');
        return sb.toString();
    }
}
