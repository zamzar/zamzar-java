package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JobBuilder {

    protected final Source source;
    protected final String targetFormat;
    protected String sourceFormat;
    protected String exportUrl;
    protected Map<String, Object> options = new HashMap<>();

    public JobBuilder(File source, String targetFormat) {
        this(new LocalFile(source), targetFormat);
    }

    public JobBuilder(Integer sourceId, String targetFormat) {
        this(new ExistingFile(sourceId), targetFormat);
    }

    public JobBuilder(String sourceUrl, String targetFormat) {
        this(new RemoteFile(sourceUrl), targetFormat);
    }

    protected JobBuilder(Source source, String targetFormat) {
        this.source = source;
        this.targetFormat = targetFormat;
    }

    public JobBuilder from(String sourceFormat) {
        this.sourceFormat = sourceFormat;
        return this;
    }

    public JobBuilder option(String key, Object value) {
        options.put(key, value);
        return this;
    }

    public JobBuilder exportingTo(String exportUrl) {
        this.exportUrl = exportUrl;
        return this;
    }

    public Source getSource() {
        return source;
    }

    public String getTargetFormat() {
        return targetFormat;
    }

    public String getSourceFormat() {
        return sourceFormat;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public interface Source {
        Integer prepare(ZamzarClient client) throws ApiException;
    }

    static class LocalFile implements Source {
        protected final File file;

        public LocalFile(File file) {
            this.file = file;
        }

        public Integer prepare(ZamzarClient client) throws ApiException {
            return client.upload(file).getId();
        }
    }

    static class ExistingFile implements Source {
        protected final Integer id;

        public ExistingFile(Integer id) {
            this.id = id;
        }

        public Integer prepare(ZamzarClient client) {
            return id;
        }
    }

    static class RemoteFile implements Source {
        protected final String url;

        public RemoteFile(String url) {
            this.url = url;
        }

        public Integer prepare(ZamzarClient zamzar) throws ApiException {
            return zamzar.imports().start(url).awaitOrThrow().getImportedFile().getId();
        }
    }
}
