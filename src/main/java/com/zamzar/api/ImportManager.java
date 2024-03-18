package com.zamzar.api;

import com.zamzar.api.internal.Awaitable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;
import com.zamzar.api.model.ModelImport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Provides operations that can be performed on an import request running on the Zamzar API servers.
 */
public class ImportManager extends Awaitable<ImportManager> {

    protected static List<ModelImport.StatusEnum> TERMINAL_STATUSES = Arrays.asList(
        ModelImport.StatusEnum.SUCCESSFUL,
        ModelImport.StatusEnum.FAILED
    );

    protected final ZamzarClient zamzar;
    protected final ModelImport model;

    protected ImportManager(ZamzarClient zamzar, ModelImport model) {
        this.zamzar = zamzar;
        this.model = model;
    }

    /**
     * Returns the import request's metadata.
     */
    public ModelImport getModel() {
        return model;
    }

    /**
     * Returns the import request's ID.
     */
    public Integer getId() {
        return getModel().getId();
    }

    /**
     * Indicates whether the import request has completed.
     */
    public boolean hasCompleted() {
        return TERMINAL_STATUSES.contains(getModel().getStatus());
    }

    /**
     * Indicates whether the import request has successfully completed.
     */
    public boolean hasSucceeded() {
        return getModel().getStatus() == ModelImport.StatusEnum.SUCCESSFUL;
    }

    /**
     * If the import request has failed, returns the reason for the failure.
     */
    public Failure getFailure() {
        return getModel().getFailure();
    }

    /**
     * Performs an API request to determine the current state of the import request.
     */
    public ImportManager refresh() throws ApiException {
        return zamzar.imports().find(getId());
    }

    /**
     * Returns a file manager for the imported file.
     *
     * @see FileManager
     */
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
