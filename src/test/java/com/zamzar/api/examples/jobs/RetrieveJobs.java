package com.zamzar.api.examples.jobs;

import com.zamzar.api.JobManager;
import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Job;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

public class RetrieveJobs {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Retrieve information about a single job
        Job job = zamzar.jobs().find(123456).getModel();
        System.out.println("Job ID: " + job.getId() + " was created at " + job.getCreatedAt());

        // List jobs (returns at most 50 jobs at a time)
        for (JobManager jobManager : zamzar.jobs().list().getItems()) {
            System.out.println("Job ID: " + jobManager.getModel().getId() + " was created at " + jobManager.getModel().getCreatedAt());
        }

        // To page through all jobs, use the nextPage method:
        Paged<JobManager, Integer> currentPage = zamzar.jobs().list();
        do {
            for (JobManager jobManager : currentPage.getItems()) {
                System.out.println("Job ID: " + jobManager.getModel().getId() + " was created at " + jobManager.getModel().getCreatedAt());
            }
            currentPage = currentPage.nextPage();
        } while (!currentPage.getItems().isEmpty());

        // For fine-grained control over pagination, use an anchor and a limit
        // For example, retrieve the 20 jobs immediately after job ID 123456
        Paged<JobManager, Integer> targetedPage = zamzar.jobs().list(Anchor.after(123456), 20);

        // To list or page through **only** successful jobs, use the successful() method:
        for (JobManager jobManager : zamzar.jobs().successful().list().getItems()) {
            System.out.println("Successful Job ID: " + jobManager.getModel().getId() + " was created at " + jobManager.getModel().getCreatedAt());
        }
    }
}
