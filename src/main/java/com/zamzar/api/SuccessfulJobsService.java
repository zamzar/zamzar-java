package com.zamzar.api;

import com.zamzar.api.core.JobsApi;
import com.zamzar.api.internal.Listable;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Job;
import com.zamzar.api.model.Jobs;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import com.zamzar.api.pagination.Paging;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Retrieves information about existing successful jobs on the Zamzar API servers.
 */
public class SuccessfulJobsService implements Listable<JobManager, Integer> {

    protected final ZamzarClient zamzar;
    protected final JobsApi api;

    protected SuccessfulJobsService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new JobsApi(zamzar.client);
    }

    /**
     * Retrieves a list of successful jobs.
     *
     * @param anchor indicates the position in the list from which to start retrieving jobs
     * @param limit  indicates the maximum number of jobs to retrieve
     */
    @Override
    public Paged<JobManager, Integer> list(Anchor<Integer> anchor, Integer limit) throws ApiException {
        final Integer after = anchor == null ? null : anchor.getAfterParameterValue();
        final Integer before = anchor == null ? null : anchor.getBeforeParameterValue();

        final Jobs response = api.listSuccessfulJobs(limit, after, before);
        return new Paged<>(this, toJobs(response.getData()), Paging.fromNumeric(response.getPaging()));
    }

    protected List<JobManager> toJobs(List<Job> models) {
        return models.stream().map(this::toJob).collect(Collectors.toList());
    }

    protected JobManager toJob(Job model) {
        return new JobManager(zamzar, model);
    }
}
