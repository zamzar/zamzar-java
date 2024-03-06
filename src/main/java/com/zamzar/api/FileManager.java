package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.ModelFile;

import java.io.File;
import java.util.Objects;

public class FileManager {

    protected final ZamzarClient zamzar;
    protected final ModelFile model;

    public FileManager(ZamzarClient zamzar, ModelFile model) {
        this.zamzar = zamzar;
        this.model = model;
    }

    public ModelFile getModel() {
        return model;
    }

    public Integer getId() {
        return getModel().getId();
    }

    public FileManager delete() throws ApiException {
        return zamzar.files().delete(getId());
    }

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
