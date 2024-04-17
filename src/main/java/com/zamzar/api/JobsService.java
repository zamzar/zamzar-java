package com.zamzar.api;

import com.zamzar.api.core.JobsApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Job;
import com.zamzar.api.model.Jobs;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Starts jobs -- and retrieves information about existing jobs -- on the Zamzar API servers.
 */
public class JobsService implements Listable<JobManager, Integer> {

    protected final ZamzarClient zamzar;
    protected final JobsApi api;

    protected JobsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new JobsApi(zamzar.client);
    }


    /**
     * Retrieves a service for managing successful jobs.
     */
    public SuccessfulJobsService successful() {
        return new SuccessfulJobsService(zamzar);
    }

    /**
     * Retrieves a job by its ID.
     */
    public JobManager find(Integer id) throws ApiException {
        return toJob(api.getJobById(id));
    }

    /**
     * Retrieves a list of jobs.
     *
     * @param anchor indicates the position in the list from which to start retrieving jobs
     * @param limit  indicates the maximum number of jobs to retrieve
     */
    @Override
    public Paged<JobManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Jobs response = api.listJobs(limit, after, before);
        return new Paged<>(this, toJobs(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    /**
     * Immediately cancels a job by its ID.
     */
    public JobManager cancel(Integer id) throws ApiException {
        return toJob(api.cancelJobById(id));
    }

    /**
     * Starts a job to convert a local file, returning once the job has been created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(File source, String targetFormat) throws ApiException {
        return create(source, targetFormat, JobBuilder.Modifier.identity());
    }

    /**
     * Starts a job to convert a local file, with a customised job builder; returning once the job has been created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(File source, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return create(modifier.modify(new JobBuilder(source, targetFormat)));
    }

    /**
     * Starts a job to convert a file already resident on the Zamzar API servers; returning once the job has been
     * created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(Integer sourceId, String targetFormat) throws ApiException {
        return create(sourceId, targetFormat, JobBuilder.Modifier.identity());
    }

    /**
     * Starts a job to convert a file already resident on the Zamzar API servers, with a customised job builder.
     * Returns once the job has been created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(Integer sourceId, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return create(modifier.modify(new JobBuilder(sourceId, targetFormat)));
    }

    /**
     * Starts a job to convert a file from a URL, returning once the job has been created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(URI source, String targetFormat) throws ApiException {
        return create(source, targetFormat, JobBuilder.Modifier.identity());
    }

    /**
     * Starts a job to convert a file from a URL, with a customised job builder; returning once the job has been
     * created.
     * <p>
     * Call {@link JobManager#awaitOrThrow()} on the returned object to block until the job has completed.
     */
    public JobManager create(URI source, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return create(modifier.modify(new JobBuilder(source, targetFormat)));
    }

    protected JobManager create(JobBuilder builder) throws ApiException {
        final Integer sourceId = builder.prepareSource(zamzar);
        final String targetFormat = builder.getTargetFormat();
        final String sourceFormat = builder.getSourceFormat();
        final String exportUrl = builder.getDestination() == null ? null : builder.getDestination().toString();
        final Map<String, Object> options = builder.getOptions();

        final Job job = api.submitJob(sourceId, targetFormat, sourceFormat, exportUrl, options);

        return new JobManager(zamzar, job);
    }

    protected List<JobManager> toJobs(List<Job> models) {
        return models.stream().map(this::toJob).collect(Collectors.toList());
    }

    protected JobManager toJob(Job model) {
        return new JobManager(zamzar, model);
    }
}
