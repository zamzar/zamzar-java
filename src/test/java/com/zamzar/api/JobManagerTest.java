package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Export;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JobManagerTest extends ZamzarApiTest {
    @Test
    public void await() throws Exception {
        assertTrue(zamzar().jobs().find(SUCCEEDING_JOB_ID).await().hasSucceeded());
        assertNull(zamzar().jobs().find(SUCCEEDING_JOB_ID).await().getFailure());
        assertTrue(zamzar().jobs().find(SUCCEEDING_JOB_ID).awaitOrThrow().hasSucceeded());
    }

    @Test
    public void awaitRespectsExports() throws Exception {
        final JobManager finishedJobWithExports = zamzar().jobs().find(SUCCEEDING_MULTI_OUTPUT_JOB_ID).await();

        // sanity check -- there should be at least 1 export
        assertFalse(finishedJobWithExports.getModel().getExports().isEmpty());

        for (Export export : finishedJobWithExports.getModel().getExports()) {
            assertTrue(
                export.getStatus() == Export.StatusEnum.SUCCESSFUL || export.getStatus() == Export.StatusEnum.FAILED,
                "Export " + export.getId() + " should have completed, but is: " + export.getStatus()
            );
        }
    }

    @Test
    public void awaitFailing() throws Exception {
        assertTrue(zamzar().jobs().find(FAILING_JOB_ID).await().hasFailed());
        assertNotNull(zamzar().jobs().find(FAILING_JOB_ID).await().getFailure().getCode());
        assertNotNull(zamzar().jobs().find(FAILING_JOB_ID).await().getFailure().getMessage());
        assertThrows(ApiException.class, () -> zamzar().jobs().find(FAILING_JOB_ID).awaitOrThrow());
    }

    @Test
    public void throwsWhenAwaitedNotFound() throws Exception {
        final JobManager job = zamzar().jobs().find(SUCCEEDING_JOB_ID);
        destroyInMock("/jobs/" + SUCCEEDING_JOB_ID);
        assertThrows(ApiException.class, job::await);
    }

    @Test
    public void storeMultiFileJob() throws Exception {
        final Path output = createTempFile("output");

        assertEmptyFile(output);

        zamzar()
            .jobs()
            .find(SUCCEEDING_MULTI_OUTPUT_JOB_ID)
            .awaitOrThrow()
            .store(output.toFile());

        // Check that a non-empty file has been downloaded
        assertNonEmptyFile(output);

        // Check that the directory contains 3 non-empty pngs
        final List<Path> pngs = findTempFiles(path -> path.toString().endsWith(".png"));
        assertEquals(3, pngs.size());
        pngs.forEach(ZamzarApiTest::assertNonEmptyFile);
    }

    @Test
    public void storeMultiFileJobToDirectory() throws Exception {
        assertEquals(findTempFiles().size(), 0, "Sanity check failed. Expected output files not to exist yet");

        zamzar()
            .jobs()
            .find(SUCCEEDING_MULTI_OUTPUT_JOB_ID)
            .awaitOrThrow()
            .store(TEMP_DIR.toFile());

        // Check that the directory contains 3 non-empty pngs
        final List<Path> pngs = findTempFiles(path -> path.toString().endsWith(".png"));
        assertEquals(3, pngs.size());
        pngs.forEach(ZamzarApiTest::assertNonEmptyFile);
    }

    @Test
    public void storeThrowsWhenNoTargetFiles() throws Exception {
        final JobManager job = zamzar().jobs().find(FAILING_JOB_ID).await();
        assertThrows(ApiException.class, () -> job.store(createTempFile("output").toFile()));
    }
}
