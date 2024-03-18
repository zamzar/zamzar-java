package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.ModelFile;

import java.io.File;
import java.util.Objects;

/**
 * Provides operations that can be performed on a file resident on the Zamzar API servers.
 */
public class FileManager {

    protected final ZamzarClient zamzar;
    protected final ModelFile model;

    protected FileManager(ZamzarClient zamzar, ModelFile model) {
        this.zamzar = zamzar;
        this.model = model;
    }

    /**
     * Returns the file's metadata.
     */
    public ModelFile getModel() {
        return model;
    }

    /**
     * Returns the file's ID.
     */
    public Integer getId() {
        return getModel().getId();
    }

    /**
     * Immediately deletes the file from the Zamzar API servers.
     */
    public FileManager delete() throws ApiException {
        return zamzar.files().delete(getId());
    }

    /**
     * Downloads the file to the specified destination, blocking until the download is complete.
     */
    public FileManager download(File destination) throws ApiException {
        zamzar.files().download(getModel(), destination);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileManager that = (FileManager) o;
        return Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileManager{");
        sb.append("model=").append(model);
        sb.append('}');
        return sb.toString();
    }
}
