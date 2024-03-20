package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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
    protected URI destination;
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
    public JobBuilder(URI source, String targetFormat) {
        this(new RemoteFile(source), targetFormat);
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
    public JobBuilder exportingTo(String destination) throws ApiException {
        try {
            return this.exportingTo(new URI(destination));
        } catch (URISyntaxException e) {
            throw new ApiException(e);
        }
    }

    /**
     * After converting the file, export the results to the specified URL.
     */
    public JobBuilder exportingTo(URI destination) {
        this.destination = destination;
        return this;
    }

    protected Integer prepareSource(ZamzarClient zamzar) throws ApiException {
        return source.prepare(this, zamzar);
    }

    protected String getTargetFormat() {
        return targetFormat;
    }

    protected String getSourceFormat() {
        return sourceFormat;
    }

    protected URI getDestination() {
        return destination;
    }

    protected Map<String, Object> getOptions() {
        return options;
    }

    @FunctionalInterface
    public interface Modifier {
        static Modifier identity() {
            return (builder) -> builder;
        }

        JobBuilder modify(JobBuilder jobBuilder) throws ApiException;
    }

    protected interface Source {
        Integer prepare(JobBuilder builder, ZamzarClient zamzar) throws ApiException;
    }

    protected static class LocalFile implements Source {
        protected final File file;

        protected LocalFile(File file) {
            this.file = file;
        }

        public Integer prepare(JobBuilder builder, ZamzarClient zamzar) throws ApiException {
            return zamzar.upload(file).getId();
        }
    }

    protected static class ExistingFile implements Source {
        protected final Integer id;

        protected ExistingFile(Integer id) {
            this.id = id;
        }

        public Integer prepare(JobBuilder builder, ZamzarClient zamzar) {
            return id;
        }
    }

    protected static class RemoteFile implements Source {
        protected final URI url;

        protected RemoteFile(URI url) {
            this.url = url;
        }

        public Integer prepare(JobBuilder builder, ZamzarClient zamzar) throws ApiException {
            final String url = this.url.toString();
            final String filename = this.getFilename(builder.getSourceFormat());
            final ImportManager _import = zamzar.imports().start(url, filename).awaitOrThrow();
            return _import.getImportedFile().getId();
        }

        protected String getFilename(String extension) throws ApiException {
            final File file = new File(url.getPath());

            if (file.getName().isEmpty()) {
                throw new ApiException("Could not infer filename from URL (" + url + "). Provide a URL that contains a path.");
            } else if (file.getName().contains(".")) {
                return file.getName();
            } else if (extension == null || extension.isEmpty()) {
                throw new ApiException("Could not infer filename from URL (" + url + "). Provide an extension to disambiguate.");
            } else {
                return file.getName() + "." + extension;
            }
        }
    }
}
