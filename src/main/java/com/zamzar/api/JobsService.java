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
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class JobsService implements Listable<JobManager, Integer> {

    protected final ZamzarClient zamzar;
    protected final JobsApi api;

    public JobsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new JobsApi(zamzar.client);
    }

    public JobManager find(Integer id) throws ApiException {
        return toJob(api.getJobById(id));
    }

    @Override
    public Paged<JobManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Jobs response = api.listJobs(limit, after, before);
        return new Paged<>(this, toJobs(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    public JobManager create(File source, String targetFormat) throws ApiException {
        return create(source, targetFormat, UnaryOperator.identity());
    }

    public JobManager create(File source, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return create(build.apply(new JobBuilder(source, targetFormat)));
    }

    public JobManager create(Integer sourceId, String targetFormat) throws ApiException {
        return create(sourceId, targetFormat, UnaryOperator.identity());
    }

    public JobManager create(Integer sourceId, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return create(build.apply(new JobBuilder(sourceId, targetFormat)));
    }

    public JobManager create(String sourceUrl, String targetFormat) throws ApiException {
        return create(sourceUrl, targetFormat, UnaryOperator.identity());
    }

    public JobManager create(String sourceUrl, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return create(build.apply(new JobBuilder(sourceUrl, targetFormat)));
    }

    public JobManager create(JobBuilder builder) throws ApiException {
        final Integer sourceId = builder.getSource().prepare(zamzar);
        final String targetFormat = builder.getTargetFormat();
        final String sourceFormat = builder.getSourceFormat();
        final String exportUrl = builder.getExportUrl();
        final Map<String, Object> options = builder.getOptions();

        final Job job = api.submitJob(sourceId, targetFormat, sourceFormat, exportUrl, options);

        return new JobManager(zamzar, job);
    }

    public JobManager cancel(Integer id) throws ApiException {
        return toJob(api.cancelJobById(id));
    }

    protected List<JobManager> toJobs(List<Job> models) {
        return models.stream().map(this::toJob).collect(Collectors.toList());
    }

    protected JobManager toJob(Job model) {
        return new JobManager(zamzar, model);
    }
}
