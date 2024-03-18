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

/**
 * Uploads to, downloads from, and retrieves files on the Zamzar API servers.
 */
public class FilesService implements Listable<FileManager, Integer> {

    protected final ZamzarClient zamzar;
    protected final FilesApi api;

    protected FilesService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new FilesApi(zamzar.client);
    }

    /**
     * Retrieves a file by its ID.
     *
     * @see FileManager
     */
    public FileManager find(Integer id) throws ApiException {
        return toFile(api.getFileById(id));
    }

    /**
     * Retrieves a list of files.
     *
     * @param anchor indicates the position in the list from which to start retrieving files
     * @param limit  indicates the maximum number of files to retrieve
     * @see Paged
     * @see FileManager
     */
    @Override
    public Paged<FileManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Files response = api.listFiles(limit, after, before);
        return new Paged<>(this, toFiles(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    /**
     * Immediately deletes a file from the Zamzar API servers.
     */
    public FileManager delete(Integer id) throws ApiException {
        return toFile(api.deleteFileById(id));
    }

    /**
     * Downloads a file to the specified destination. Blocks until the download is complete.
     */
    public FileManager download(Integer id, File destination) throws ApiException {
        return find(id).download(destination);
    }

    /**
     * Uploads a file to the Zamzar API servers. The file's name is used as the name of the file on the server.
     * Blocks until the upload is complete.
     */
    public FileManager upload(File file) throws ApiException {
        return upload(file, file.getName());
    }

    /**
     * Uploads a file to the Zamzar API servers. Blocks until the upload is complete.
     */
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
