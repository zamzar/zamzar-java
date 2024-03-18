package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds a job request to be submitted to the Zamzar API servers.
 * <p>
 * Often, you can use the convenience methods in {@link ZamzarClient} to create a job request without using this class
 * directly. However, this class provides a way to create a job request with more control over the request's parameters.
 */
public class JobBuilder {

    protected final Source source;
    protected final String targetFormat;
    protected String sourceFormat;
    protected String exportUrl;
    protected Map<String, Object> options = new HashMap<>();

    /**
     * Begin to build a conversion job that operates on a local file.
     */
    public JobBuilder(File source, String targetFormat) {
        this(new LocalFile(source), targetFormat);
    }

    /**
     * Begin to build a conversion job that operates on a file already resident on the Zamzar API servers.
     */
    public JobBuilder(Integer sourceId, String targetFormat) {
        this(new ExistingFile(sourceId), targetFormat);
    }

    /**
     * Begin to build a conversion job that operates on a file accessible via a URL.
     */
    public JobBuilder(String sourceUrl, String targetFormat) {
        this(new RemoteFile(sourceUrl), targetFormat);
    }

    protected JobBuilder(Source source, String targetFormat) {
        this.source = source;
        this.targetFormat = targetFormat;
    }

    /**
     * Override the format of the source file. Useful when the source file's format cannot be inferred from the
     * extension of a local file or the URL of a remote file.
     */
    public JobBuilder from(String sourceFormat) {
        this.sourceFormat = sourceFormat;
        return this;
    }

    /**
     * Customises the conversion job with an option. The options available depend on the source and target formats.
     */
    public JobBuilder option(String key, Object value) {
        options.put(key, value);
        return this;
    }

    /**
     * After converting the file, export the results to the specified URL.
     */
    public JobBuilder exportingTo(String exportUrl) {
        this.exportUrl = exportUrl;
        return this;
    }

    protected Source getSource() {
        return source;
    }

    protected String getTargetFormat() {
        return targetFormat;
    }

    protected String getSourceFormat() {
        return sourceFormat;
    }

    protected String getExportUrl() {
        return exportUrl;
    }

    protected Map<String, Object> getOptions() {
        return options;
    }

    protected interface Source {
        Integer prepare(ZamzarClient client) throws ApiException;
    }

    protected static class LocalFile implements Source {
        protected final File file;

        protected LocalFile(File file) {
            this.file = file;
        }

        public Integer prepare(ZamzarClient client) throws ApiException {
            return client.upload(file).getId();
        }
    }

    protected static class ExistingFile implements Source {
        protected final Integer id;

        protected ExistingFile(Integer id) {
            this.id = id;
        }

        public Integer prepare(ZamzarClient client) {
            return id;
        }
    }

    protected static class RemoteFile implements Source {
        protected final String url;

        protected RemoteFile(String url) {
            this.url = url;
        }

        public Integer prepare(ZamzarClient zamzar) throws ApiException {
            return zamzar.imports().start(url).awaitOrThrow().getImportedFile().getId();
        }
    }
}
