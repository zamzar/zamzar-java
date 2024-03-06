package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ZamzarApiTest {

    protected static final String API_URL = Optional.ofNullable(System.getenv("API_URL")).orElse("http://localhost:8080");

    protected static final String API_KEY = Optional.ofNullable(System.getenv("API_KEY")).orElse("GiVUYsF4A8ssq93FR48H");

    protected static final Path TEMP_DIR = Paths.get(System.getProperty("java.io.tmpdir"), "zamzar-java");


    // IDs below correspond to those defined in zamzar-mock
    // See: https://github.com/zamzar/zamzar-mock/blob/main/README.md

    protected static final int SUCCEEDING_JOB_ID = 1;
    protected static final int SUCCEEDING_MULTI_OUTPUT_JOB_ID = 2;
    protected static final int FAILING_JOB_ID = 3;
    protected static final int SUCCEEDING_IMPORT_ID = 1;
    protected static final int FAILING_IMPORT_ID = 2;
    protected static final int FILE_ID = 1;

    private ZamzarClient zamzar;

    @BeforeEach
    public void resetZamzarClient() {
        this.zamzar = null;
    }

    @BeforeEach
    public void clearGeneratedFiles() throws IOException {
        // Delete the temp directory and its contents if it exists, then recreate it
        if (Files.exists(TEMP_DIR)) {
            Files.walk(TEMP_DIR)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
        Files.createDirectories(TEMP_DIR);
    }

    @BeforeEach
    public void resetZamzarMock() throws IOException {
        // Construct the URL for to reset the mock
        // See: https://github.com/zamzar/zamzar-mock/blob/main/README.md
        final URL url = new URL(API_URL + "/__admin/scenarios/reset");
        final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        httpURLConnection.connect();

        // Check the response code to determine if the request was successful
        final int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            fail("Failed to reset mock server. Response code: " + responseCode);
        }
    }

    protected void destroyInMock(String path) throws IOException {
        // Construct the URL to destroy a resource in the mock
        // See: https://github.com/zamzar/zamzar-mock/blob/main/README.md
        final URL url = new URL(API_URL + path + "/destroy");
        final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        httpURLConnection.connect();

        // Check the response code to determine if the request was successful
        final int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            fail("Failed to destroy resource (" + path + "). Response code: " + responseCode);
        }
    }

    protected ZamzarClient zamzar() {
        return zamzar(ZamzarClient.getDefaultTransportBuilder().build());
    }

    protected ZamzarClient zamzar(Interceptor interceptor) {
        return zamzar(ZamzarClient.getDefaultTransportBuilder().addInterceptor(interceptor).build());
    }

    protected Path createTempFile(String id) throws IOException {
        final String testClassName = getClass().getSimpleName();
        final String testMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        final String prefix = testClassName + "-" + testMethodName + "-";
        final String suffix = "-" + id;

        return Files.createTempFile(TEMP_DIR, prefix, suffix);
    }

    protected List<Path> findTempFiles() throws IOException {
        return findTempFiles((p) -> true);
    }

    protected List<Path> findTempFiles(Predicate<? super Path> filter) throws IOException {
        return Files.list(TEMP_DIR).filter(filter).collect(Collectors.toList());
    }

    protected ZamzarClient zamzar(OkHttpClient transport) {
        if (zamzar == null) {
            zamzar = new ZamzarClient(API_KEY, API_URL, transport);
        }
        return zamzar;
    }

    protected static void assertEmptyFile(Path file) {
        assertFalse(file.toFile().length() > 0, "Expected empty file: " + file);
    }

    protected static void assertNonEmptyFile(Path file) {
        assertTrue(file.toFile().length() > 0, "Expected non-empty file: " + file);
    }

    protected static void assert404s(Executable e) {
        final ApiException ex = assertThrows(ApiException.class, e);
        assertEquals(404, ex.getCode());
    }
}
