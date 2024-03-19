package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Account;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZamzarClientTest extends ZamzarApiTest {

    @Test
    public void convertStoreDeleteAll() throws Exception {
        final Path input = createTempFile("input");
        final Path output = createTempFile("output");

        assertEmptyFile(output);

        final JobManager job = zamzar()
            .convert(
                input.toFile(),
                "txt",
                builder -> builder
                    .from("pdf")
                    .option("quality", "50")
                    .option("ocr", "true")
                    .exportingTo("s3://bucket-name/path/to/export")
            );

        // Now download the converted file
        job.store(output.toFile());
        assertNonEmptyFile(output);

        // Now delete the source and target files
        job.deleteAllFiles();
        assert404s(() -> zamzar().files().find(job.getSourceFileId()));
        for (Integer targetFileId : job.getTargetFileIds()) {
            assert404s(() -> zamzar().files().find(targetFileId));
        }
    }

    @Test
    public void uploadConvertDownloadDelete() throws Exception {
        final Path input = createTempFile("input");
        final Path output = createTempFile("output");

        assertEmptyFile(output);

        final FileManager source = zamzar().upload(input.toFile());
        final JobManager job = zamzar().convert(source.getId(), "txt").awaitOrThrow();
        final FileManager target = zamzar().download(job.getTargetFileIds().get(0), output.toFile());
        source.delete();
        target.delete();

        // Check that a non-empty file has been downloaded
        assertNonEmptyFile(output);

        // Check that the source and target files no longer exist
        assert404s(() -> zamzar().files().find(source.getId()));
        assert404s(() -> zamzar().files().find(job.getTargetFileIds().get(0)));
    }

    @Test
    public void downloadAndDeleteWhenMultipleTargetFiles() throws Exception {
        final Path output = createTempFile("output");

        assertEmptyFile(output);

        final JobManager job = zamzar()
            .jobs()
            .find(SUCCEEDING_MULTI_OUTPUT_JOB_ID)
            .awaitOrThrow()
            .store(output.toFile())
            .deleteAllFiles();

        // Check that a non-empty file has been downloaded
        assertNonEmptyFile(output);

        // Check that the directory contains 3 non-empty pngs
        final List<Path> pngs = findTempFiles(path -> path.toString().endsWith(".png"));
        assertEquals(3, pngs.size());
        pngs.forEach(ZamzarApiTest::assertNonEmptyFile);

        // Check that the source and target files no longer exist
        assert404s(() -> zamzar().files().find(job.getSourceFileId()));
        for (Integer targetFileId : job.getTargetFileIds()) {
            assert404s(() -> zamzar().files().find(targetFileId));
        }
    }

    @Test
    public void canHitProduction() throws ApiException {
        assertNotNull(new ZamzarClient(API_KEY, ZamzarEnvironment.PRODUCTION).testConnection());
    }

    @Test
    public void canHitSandbox() throws ApiException {
        assertNotNull(new ZamzarClient(API_KEY, ZamzarEnvironment.SANDBOX).testConnection());
    }

    @Test
    public void hasUserAgent() throws Exception {
        final SpyingInterceptor spy = new SpyingInterceptor();
        zamzar(spy).account().get();
        spy.assertRequestHasHeader("User-Agent", ZamzarClient.USER_AGENT);
    }

    @Test
    public void hasTimeouts() throws Exception {
        final OkHttpClient transport = zamzar().client.getHttpClient();

        assertEquals(ZamzarClient.HTTP_CALL_TIMEOUT, transport.callTimeoutMillis());
        assertEquals(ZamzarClient.HTTP_CONNECTION_TIMEOUT, transport.connectTimeoutMillis());
        assertEquals(ZamzarClient.HTTP_READ_TIMEOUT, transport.readTimeoutMillis());
        assertEquals(ZamzarClient.HTTP_WRITE_TIMEOUT, transport.writeTimeoutMillis());
    }

    @Test
    public void retriesOnServerErrors() throws Exception {
        final Interceptor downForTwoRequests = new Interceptor() {
            private int count = 0;

            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                if (count++ < 2) {
                    return new Response.Builder()
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(503)
                        .message("Service Unavailable")
                        .body(ResponseBody.create("We're down for maintenance", MediaType.get("text/plain")))
                        .build();
                } else {
                    return chain.proceed(chain.request());
                }
            }
        };

        final Account account = zamzar(downForTwoRequests).account().get();

        assertNotNull(account.getPlan());
    }

    @Test
    public void retriesWhenRateLimited() throws Exception {
        final Interceptor rateLimitedForTwoRequests = new Interceptor() {
            private int count = 0;

            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                if (count++ < 2) {
                    return new Response.Builder()
                        .protocol(Protocol.HTTP_1_1)
                        .request(chain.request())
                        .code(429)
                        .header("Retry-After", "1")
                        .message("Too Many Requests")
                        .body(ResponseBody.create("Rate limited", MediaType.get("text/plain")))
                        .build();
                } else {
                    return chain.proceed(chain.request());
                }
            }
        };

        final Account account = zamzar(rateLimitedForTwoRequests).account().get();

        assertNotNull(account.getPlan());
    }

    @Test
    public void capturesLatestCreditUsage() throws Exception {
        final Interceptor alterCreditUsage = chain -> {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                .header(ZamzarClient.CREDITS_REMAINING_HEADER, "42")
                .header(ZamzarClient.TEST_CREDITS_REMAINING_HEADER, "24")
                .build();
        };

        // should be null before any request has been made
        assertNull(zamzar(alterCreditUsage).getLastProductionCreditsRemaining());
        assertNull(zamzar(alterCreditUsage).getLastSandboxCreditsRemaining());

        // make a request
        zamzar(alterCreditUsage).jobs().list();

        // should capture the latest credit usage
        assertEquals(42, zamzar(alterCreditUsage).getLastProductionCreditsRemaining());
        assertEquals(24, zamzar(alterCreditUsage).getLastSandboxCreditsRemaining());
    }
}
