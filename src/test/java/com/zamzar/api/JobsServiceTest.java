package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Job;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class JobsServiceTest extends ZamzarApiTest {

    @Test
    public void find() throws Exception {
        final JobManager job = zamzar().jobs().find(SUCCEEDING_JOB_ID);
        assertEquals(SUCCEEDING_JOB_ID, job.getId());
    }

    @Test
    public void list() throws ApiException {
        final Paged<JobManager, Integer> jobs = zamzar().jobs().list();

        for (JobManager job : jobs.getItems()) {
            assertTrue(job.getId() > 0);
        }
    }

    @Test
    public void listAndPageForwards() throws ApiException {
        // There are at least 3 jobs in the mock server
        // We'll therefore navigate 2 items at a time, and can expect at least 2 pages
        int numberOfPages = 0;
        Paged<JobManager, Integer> current = zamzar().jobs().list(2);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 2);
            current = current.nextPage();
        }
        assertTrue(numberOfPages >= 2);
    }

    @Test
    public void listAndPageBackwards() throws ApiException {
        // There are at least 3 jobs in the mock server, and using an anchor reduces that to at least 2 jobs
        // We'll therefore navigate 1 item at a time, and can expect at least 2 pages
        int numberOfPages = 0;
        Paged<JobManager, Integer> current = zamzar().jobs().list(Anchor.before(1), 1);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 1);
            current = current.previousPage();
        }
        assertTrue(numberOfPages >= 2);
    }

    @Test
    public void create() throws Exception {
        final Path input = createTempFile("input");

        final JobManager job = zamzar()
            .jobs()
            .create(input.toFile(), "txt");

        assertTrue(job.getId() > 0);
    }

    @Test
    public void createWithSourceFormatAndOptionsAndExport() throws Exception {
        final Path input = createTempFile("input");

        final SpyingInterceptor spy = new SpyingInterceptor();
        final JobManager job = zamzar(spy)
            .jobs()
            .create(
                input.toFile(),
                "txt",
                builder -> builder
                    .from("pdf")
                    .option("quality", "50")
                    .option("ocr", "true")
                    .exportingTo("s3://bucket-name/path/to/export")
            );

        spy.assertRequestHasPart("source_format");
        spy.assertRequestHasPart("options");
        spy.assertRequestHasPart("export_url");

        assertTrue(job.getId() > 0);
    }

    @Test
    public void createFromUrl() throws Exception {
        final JobManager job = zamzar()
            .jobs()
            .create("https://www.example.com/logo.png", "jpg");

        // Check that a non-empty file has been downloaded
        assertTrue(job.getId() > 0);
    }

    @Test
    public void createThrowsWhenTargetFormatIsUnsupported() {
        assertThrows(
            ApiException.class,
            () -> zamzar().jobs().create(createTempFile("input").toFile(), "unsupported")
        );
    }

    @Test
    public void cancel() throws ApiException {
        final JobManager job = zamzar().jobs().cancel(SUCCEEDING_JOB_ID);

        assertEquals(SUCCEEDING_JOB_ID, job.getId());
        assertEquals(Job.StatusEnum.CANCELLED, job.getModel().getStatus());

        // Check that fresh requests return the updated status
        assertEquals(Job.StatusEnum.CANCELLED, job.refresh().getModel().getStatus());
    }

    private void createJobs(int count) throws Exception {
        for (int i = 0; i < count; i++) {
            zamzar().jobs().create(createTempFile("input-" + i).toFile(), "txt");
        }
    }

    @NotNull
    private static List<Integer> getIdsOfJobsIn(Paged<JobManager, Integer> page) {
        return page.getItems().stream().map(JobManager::getId).collect(Collectors.toList());
    }
}
