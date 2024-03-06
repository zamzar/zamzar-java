package com.zamzar.api;

import com.zamzar.api.core.FilesApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Files;
import com.zamzar.api.model.ModelFile;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FilesService implements Listable<FileManager, Integer> {

    protected final ZamzarClient zamzar;
    protected final FilesApi api;

    public FilesService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new FilesApi(zamzar.client);
    }

    public FileManager find(Integer id) throws ApiException {
        return toFile(api.getFileById(id));
    }

    @Override
    public Paged<FileManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Files response = api.listFiles(limit, after, before);
        return new Paged<>(this, toFiles(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    public FileManager delete(Integer id) throws ApiException {
        return toFile(api.deleteFileById(id));
    }

    public FileManager download(Integer id, File destination) throws ApiException {
        return find(id).download(destination);
    }

    public FileManager upload(File file) throws ApiException {
        return upload(file, file.getName());
    }

    public FileManager upload(File file, String name) throws ApiException {
        return toFile(api.uploadFile(file, name));
    }

    protected File download(ModelFile model, File destination) throws ApiException {
        if (destination.isDirectory()) {
            String name = model.getName() == null ? model.getId().toString() : model.getName();
            destination = new File(destination, name);
        }

        final File downloaded = api.getFileContentById(model.getId());
        downloaded.renameTo(destination);
        return destination;
    }

    protected List<FileManager> toFiles(List<ModelFile> models) {
        return models.stream().map(this::toFile).collect(Collectors.toList());
    }

    protected FileManager toFile(ModelFile model) {
        return new FileManager(zamzar, model);
    }
}
