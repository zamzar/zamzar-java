package com.zamzar.api;

import com.zamzar.api.internal.Awaitable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Export;
import com.zamzar.api.model.Failure;
import com.zamzar.api.model.Job;
import com.zamzar.api.model.ModelFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides operations that can be performed on a job running on the Zamzar API servers.
 */
public class JobManager extends Awaitable<JobManager> {

    protected static List<Job.StatusEnum> TERMINAL_STATUSES = Arrays.asList(
        Job.StatusEnum.SUCCESSFUL,
        Job.StatusEnum.FAILED,
        Job.StatusEnum.CANCELLED
    );

    protected static List<Export.StatusEnum> TERMINAL_EXPORT_STATUSES = Arrays.asList(
        Export.StatusEnum.SUCCESSFUL,
        Export.StatusEnum.FAILED
    );

    protected final ZamzarClient zamzar;
    protected final Job model;

    protected JobManager(ZamzarClient zamzar, Job model) {
        this.zamzar = zamzar;
        this.model = model;
    }

    /**
     * Returns the job's metadata.
     */
    public Job getModel() {
        return model;
    }

    /**
     * Returns the job's ID.
     */
    public Integer getId() {
        return getModel().getId();
    }

    /**
     * Returns the ID of the source file being converted.
     */
    public Integer getSourceFileId() {
        return getModel().getSourceFile().getId();
    }

    /**
     * Returns the IDs of the target files produced by the conversion.
     */
    public List<Integer> getTargetFileIds() {
        if (getTargetFiles() == null) {
            return Collections.emptyList();
        }

        return getTargetFiles()
            .stream()
            .mapToInt(ModelFile::getId)
            .boxed()
            .collect(Collectors.toList());
    }

    /**
     * Indicates whether the job has completed.
     */
    public boolean hasCompleted() {
        return jobHasCompleted() && allExportsHaveCompleted();
    }

    /**
     * Indicates whether the job has successfully completed.
     */
    public boolean hasSucceeded() {
        return getModel().getStatus() == Job.StatusEnum.SUCCESSFUL;
    }

    /**
     * If the job has failed, returns the reason for the failure.
     */
    public Failure getFailure() {
        return getModel().getFailure();
    }

    /**
     * Performs an API request to determine the current state of the job.
     */
    public JobManager refresh() throws ApiException {
        return zamzar.jobs().find(getId());
    }

    /**
     * Downloads all the target files produced by the conversion to the specified destination, blocking until the
     * download is complete.
     *
     * @param destination The file or directory to store the output.
     */
    public JobManager store(File destination) throws ApiException {
        return store(destination, true);
    }

    /**
     * Downloads all the target files produced by the conversion to the specified destination, blocking until the
     * download is complete.
     *
     * @param destination               The file or directory to store the output.
     * @param extractMultipleFileOutput If false, do not extract ZIP contents for multi-file jobs.
     */
    public JobManager store(File destination, boolean extractMultipleFileOutput) throws ApiException {
        if (getTargetFileIds().isEmpty()) {
            throw new ApiException("No target files to download");
        }
        ModelFile source = getPrimaryTargetFile();
        destination = zamzar.files().download(source, destination);
        if (getTargetFileIds().size() > 1 && extractMultipleFileOutput) {
            this.extract(destination);
        }
        return this;
    }

    /**
     * Immediately deletes the source file and all target files from the Zamzar API servers.
     */
    public JobManager deleteAllFiles() throws ApiException {
        this.deleteSourceFile();
        this.deleteTargetFiles();
        return this;
    }

    /**
     * Immediately deletes the source file from the Zamzar API servers.
     */
    public JobManager deleteSourceFile() throws ApiException {
        zamzar.files().delete(getSourceFileId());
        return this;
    }

    /**
     * Immediately deletes all target files from the Zamzar API servers.
     */
    public JobManager deleteTargetFiles() throws ApiException {
        for (int id : getTargetFileIds()) {
            zamzar.files().delete(id);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobManager that = (JobManager) o;
        return Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobManager{");
        sb.append("model=").append(model);
        sb.append('}');
        return sb.toString();
    }

    protected void extract(File zip) throws ApiException {
        final File destination = zip.getParentFile();
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zip.toPath()))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String filePath = destination + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
                        byte[] bytesIn = new byte[4096];
                        int read;
                        while ((read = zis.read(bytesIn)) != -1) {
                            bos.write(bytesIn, 0, read);
                        }
                    }
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    protected ModelFile getPrimaryTargetFile() throws ApiException {
        return getTargetFiles().size() == 1 ?
            getTargetFiles().get(0) :
            getTargetFileZip();
    }

    protected ModelFile getTargetFileZip() throws ApiException {
        return getTargetFiles()
            .stream()
            .filter(f -> f.getName().endsWith(".zip"))
            .findFirst()
            .orElseThrow(() -> new ApiException("Expected a zip file to be present in the target files, but none was found"));
    }

    protected List<ModelFile> getTargetFiles() {
        return getModel().getTargetFiles();
    }

    protected boolean jobHasCompleted() {
        return TERMINAL_STATUSES.contains(getModel().getStatus());
    }

    protected boolean allExportsHaveCompleted() {
        // If there's no export URL, no exports have been requested => they are all complete
        if (getModel().getExportUrl() == null || getModel().getExportUrl().isEmpty()) {
            return true;
        }

        // If we're expecting exports but none have been created => they are not yet complete
        if (getModel().getExports() == null || getModel().getExports().isEmpty()) {
            return false;
        }

        // Return true if and only if all exports have completed
        return getModel().getExports().stream().allMatch(e -> TERMINAL_EXPORT_STATUSES.contains(e.getStatus()));
    }
}
